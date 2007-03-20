package org.openuss.web.docmanagement.webdav;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavLocatorFactory;
import org.apache.jackrabbit.webdav.DavMethods;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.jackrabbit.webdav.io.InputContextImpl;
import org.apache.jackrabbit.webdav.io.OutputContextImpl;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.openuss.docmanagement.webdav.DavLocatorFactoryImpl;
import org.openuss.docmanagement.webdav.DavResourceConfiguration;
import org.openuss.docmanagement.webdav.DavService;
import org.openuss.docmanagement.webdav.HttpStatus;
import org.openuss.docmanagement.webdav.MultiStatus;
import org.openuss.docmanagement.webdav.SessionProvider;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.8
 */
public class WebDavServlet extends HttpServlet {
	private final static Logger logger = Logger.getLogger(WebDavServlet.class);
	
	// TODO passenden Wert eintragen
	private static final long serialVersionUID = 23;
	
	private static final String INIT_PARAMETER_RESOURCE_PATH_PREFIX = "resource-path-prefix";
	private static final String CONTEXT_ATTRIBUTE_RESOURCE_PATH_PREFIX = "docmanagement.resourcepathprefix";
	
	private static final String INIT_PARAMETER_RESOURCE_CONFIGURATION = "resource-config";
	private static final String INIT_PARAMETER_AUTHENTICATE_HEADER = "authenticate-header";
	private static final String DEFAULT_AUTHENTICATE_HEADER = "Basic realm=\"OpenUSS Webdav Server\"";
	
	private static final String DAV_COMPLIANCE_CLASS = "1";
	private static final String DAV_ALLOWED_METHODS = "OPTIONS, GET, HEAD, POST, DELETE, PUT, PROPFIND, PROPPATCH, MKCOL, COPY, MOVE";
	
	private String resourcePathPrefix;
	private String authenticateHeader;
	
	private DavResourceConfiguration configuration;
	private DavService davService;
	
	private SessionProvider sessionProvider;
	
	private DavLocatorFactory locatorFactory;
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		logger.debug("init started.");
		
		super.init();
		
		//set sessionProvider
		final WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		sessionProvider = (SessionProvider) wac.getBean("sessionProvider", SessionProvider.class);
		
		// read resource path prefix from configuration and store it in the context
		resourcePathPrefix = getInitParameter(INIT_PARAMETER_RESOURCE_PATH_PREFIX);
		if (resourcePathPrefix == null) {
			resourcePathPrefix = "";
		} else if (resourcePathPrefix.endsWith("/")) {
			resourcePathPrefix = resourcePathPrefix.substring(0, resourcePathPrefix.length() - 1);
		}
		getServletContext().setAttribute(CONTEXT_ATTRIBUTE_RESOURCE_PATH_PREFIX, resourcePathPrefix);
		logger.debug("ResourcePathPrefix: " + resourcePathPrefix);
		
		// read the authenticate header from configuration
        authenticateHeader = getInitParameter(INIT_PARAMETER_AUTHENTICATE_HEADER);
        if (authenticateHeader == null) {
            authenticateHeader = DEFAULT_AUTHENTICATE_HEADER;
        }
        logger.debug("AuthenticateHeader: " + authenticateHeader);
		
		// read the location of the configuration file and parse it
		configuration = new DavResourceConfiguration();
        String configurationParameter = getInitParameter(INIT_PARAMETER_RESOURCE_CONFIGURATION);
        if (configurationParameter != null) {
        	try {
            	configuration = new DavResourceConfiguration();
            	configuration.parse(getServletContext().getResource(configurationParameter));
        	} catch (MalformedURLException ex) {
        		logger.error("Unable to parse resource configuration file.");
        		logger.error("Error message:\n" + ex.getMessage());
        	}
        }
        
        logger.debug("init() done.");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// retrieve method
		int methodCode = DavMethods.getMethodCode(request.getMethod());
		
		try {
			// guarantee an authenticated user
			logger.debug("Attaching session.");
			if (!getSessionProvider().attachSession(request, getDavService())) {
				return;
			}
			
			// create resource locator
			DavResourceLocator locator = getResourceLocator(request);
			
			// try to execute method or delegate to super class
			if (!execute(request, response, locator, methodCode)) {
				logger.debug("Method execution failed. Delegating to super class.");
				super.service(request, response);
			}
		} catch (DavException ex) {
			logger.error("DavException occured. ErrorCode: " + ex.getErrorCode());
			logger.error("Exception: " + ex.getMessage());
			// check status code, if user is unauthorized
			if (ex.getErrorCode() == HttpStatus.SC_UNAUTHORIZED) {
				// user unauthorized, request authentication
                response.setHeader("WWW-Authenticate", getAuthenticateHeaderValue());
                response.sendError(ex.getErrorCode(), ex.getStatusPhrase());
			} else {
				// any other error, send error code and message
				response.sendError(ex.getErrorCode(), ex.getStatusPhrase());
			}
		} finally {
			// release session from request
			logger.debug("Releasing session.");
			getSessionProvider().releaseSession(getDavService());
		}
	}
	
	/**
	 * Executes requested method.
	 * @param request Reference to the request of the servlet.
	 * @param response Reference to the response of the servlet.
	 * @param locator The locator identifying requested resource.
	 * @param methodCode The method code identifying requested method.
	 * @return True, if method code is known and method could be executed.
	 * @throws DavException
	 */
	private boolean execute(HttpServletRequest request, HttpServletResponse response, DavResourceLocator locator, int methodCode) throws DavException {
		// TODO support DAV_LOCK and DAV_UNLOCK for compliance level 2
		// TODO Fehler werfen bei ungültigem Wert von depth
		try {
			switch (methodCode) {
				case DavMethods.DAV_OPTIONS:
					// reply to OPTIONS method
					logger.debug("Method OPTIONS requested");
					publishOptions(response);
					break;
				case DavMethods.DAV_HEAD:
					// spool header of resource, no content
					logger.debug("Method HEAD requested for resource " + locator.getResourcePath());
					getDavService().spoolResource(new OutputContextImpl(response, null), locator);
					break;
				case DavMethods.DAV_GET:
					// spool entire resource
					logger.debug("Method GET requested for resource " + locator.getResourcePath());
					getDavService().spoolResource(new OutputContextImpl(response, response.getOutputStream()), locator);
					break;
				case DavMethods.DAV_POST:
				case DavMethods.DAV_PUT:
					// add content to resource
					logger.debug("Method POST or PUT requested for resource " + locator.getResourcePath());
					getDavService().addMember(new InputContextImpl(request, request.getInputStream()), locator);
					break;
				case DavMethods.DAV_DELETE:
					// delete resource
					logger.debug("Method DELETE requested for resource " + locator.getResourcePath());
					getDavService().deleteResource(locator);
					break;
				case DavMethods.DAV_PROPFIND:
					// fill properties from resource and send to client
					logger.debug("Method PROPFIND requested for resource " + locator.getResourcePath() + " with depth " + getDepth(request));
					MultiStatus multistatus = getDavService().getProperties(getRequestDocument(request), locator, getDepth(request));
					sendMultiStatus(multistatus, response);
					break;
				case DavMethods.DAV_PROPPATCH:
					// alter values of properties
					logger.debug("Method PROPPATCH requested for resource " + locator.getResourcePath());
					// TODO
					break;
				case DavMethods.DAV_MKCOL:
					// create collection
					logger.debug("Method MKCOL requested for resource " + locator.getResourcePath());
					getDavService().createCollection(locator);
					break;
				case DavMethods.DAV_COPY:
					// copy resource to another collection
					logger.debug("Method COPY requested for resource " + locator.getResourcePath() + ". Destination: " + getDestination(request));
					getDavService().copyResource(locator, getResourceLocator(request, getDestination(request)));
					break;
				case DavMethods.DAV_MOVE:
					// move resource to another collection
					logger.debug("Method MOVE requested for resource " + locator.getResourcePath() + ". Destination: " + getDestination(request));
					getDavService().moveResource(locator, getResourceLocator(request, getDestination(request)));
					break;
				default:
					// unknown or unsupported method code, cannot execute
					return false;
			}
		} catch (IOException ex) {
			logger.error("IOException occured.");
			logger.error("Exception: " + ex.getMessage());
			// rethrow IOException as DavException
			throw new DavException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Handles method OPTIONS as demanded in RFC2518.
	 * @param response Reference to the response of the servlet.
	 */
	private void publishOptions(HttpServletResponse response) {
		// add required headers and send status code 200 (OK)
		response.addHeader(DavConstants.HEADER_DAV, DAV_COMPLIANCE_CLASS);
		response.addHeader("Allowed", DAV_ALLOWED_METHODS);
		response.addHeader("MS-Author-Via", DavConstants.HEADER_DAV);
		response.setStatus(HttpStatus.SC_OK);
	}
	
	/**
	 * Reads value of depth header as required for some methods. See RFC2518 for further details.
	 * @param request Reference to the request of the servlet.
	 * @return The value of the depth header.
	 * @throws DavException
	 */
	private int getDepth(HttpServletRequest request) throws DavException {
		// retrieve value for header from request
		String depthHeaderValue = request.getHeader(DavConstants.HEADER_DEPTH);
		
		// empty header has to be interpreted as infinity, if depth header is expected
		if ((depthHeaderValue == null) || (depthHeaderValue.length() == 0) || depthHeaderValue.equalsIgnoreCase(DavConstants.DEPTH_INFINITY_S)) {
			return DavConstants.DEPTH_INFINITY;
		} else if (depthHeaderValue.equals(DavConstants.DEPTH_0 + "")) {
			return DavConstants.DEPTH_0;
		} else if (depthHeaderValue.equals(DavConstants.DEPTH_1 + "")) {
			return DavConstants.DEPTH_1;
		} else {
			// invalid or not supported value for depth header found
			logger.error("Depth header has an invalid value: " + depthHeaderValue);
			throw new DavException(HttpServletResponse.SC_BAD_REQUEST, "Invalid value for depth header.");
		}
	}
	
	/**
	 * @param request
	 * @return
	 */
	private String getDestination(HttpServletRequest request) {
		return request.getHeader(DavConstants.HEADER_DESTINATION);
	}
	
	/**
	 * Parses the request body as a XML document.
	 * @param request Reference to the request of the servlet.
	 * @return The request body parsed as a XML document.
	 * @throws DavException
	 */
	private Document getRequestDocument(HttpServletRequest request) throws DavException {
		Document document = null;
		
		// return null, if request body is empty
		if (request.getContentLength() == 0) {
			return document;
		}
		
		try {
			// retrieve the input stream from request
			InputStream inputStream = request.getInputStream();

			if (inputStream != null) {
				// create buffered stream and snoop for content, since client can send fragmented request
				InputStream bufferedInputStream = new BufferedInputStream(inputStream);
				bufferedInputStream.mark(1);
				boolean hasContent = (bufferedInputStream.read() != -1);
				bufferedInputStream.reset();
				
				// read content of request body as string, if present
				if (hasContent) {
					// read in 8k blocks until read operation reaches end of stream
					StringBuilder content = new StringBuilder();
					byte[] buffer = new byte[8192];
					int readBytes = 0;
					do {
						readBytes = bufferedInputStream.read(buffer);
						if (readBytes >= 0) {
							content.append(new String(buffer, 0, readBytes));
						}
					} while (readBytes > 0);
					
					// try to parse content as XML document
					document = DocumentHelper.parseText(content.toString());
					logger.debug("Request document: " + document.asXML());
				}
			}
		} catch (IOException ex) {
			// error while operating with streams
			logger.error("IO exception occurred.");
			logger.error("Exception: " + ex.getMessage());
			throw new DavException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		} catch (DocumentException ex) {
			// parsing failed, send 400 (Bad Request) to client
			logger.error("Document exception occurred.");
			logger.error("Exception: " + ex.getMessage());
			throw new DavException(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}
		
		return document;		
	}
	
	/**
	 * Send multi-status to client.
	 * @param multistatus The multi-status to send.
	 * @param response Reference to the response of the servlet.
	 * @throws IOException
	 */
	private void sendMultiStatus(MultiStatus multistatus, HttpServletResponse response) throws IOException {
		// set status of http-reply to 207 (multi-status)
		response.setStatus(HttpStatus.SC_MULTI_STATUS);
		
		// send multi-status, if present
		if (multistatus != null) {
			// create output stream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			// create XML document and serialize multistatus
			Document responseDocument = DocumentHelper.createDocument();
			multistatus.toXml(responseDocument);

			// set output format and write document to output stream
			OutputFormat outputFormat = new OutputFormat("", true, "UTF-8");
			XMLWriter writer = new XMLWriter(outputStream, outputFormat);
			writer.write(responseDocument);
			writer.flush();

			logger.debug("Response document: " + responseDocument.asXML());

			// convert document in stream to byte array and write to response
			byte[] bytes = outputStream.toByteArray();
			response.setContentType("text/xml; charset=UTF-8");
			response.setContentLength(bytes.length);
			response.getOutputStream().write(bytes);
		}
	}

	/**
	 * Getter for authenticate header value.
	 * @see WebDavServlet#init()
	 * @return The authenticate header value.
	 */
	public String getAuthenticateHeaderValue() {
		return authenticateHeader;
	}
	
	/**
	 * Getter for the {@link DavService}.
	 * @return The DavService.
	 */
	public DavService getDavService() {
		if (davService == null) {
			davService = new DavService(configuration);
		}
		return davService;
	}
	
	/**
	 * Getter for the {@link SessionProvider}.
	 * @return The SessionProvider.
	 */
	public SessionProvider getSessionProvider() {
		return sessionProvider;
	}

	/**
	 * Setter for the {@link SessionProvider}.
	 * @param sessionProvider The session provider to set.
	 */
	public void setSessionProvider(SessionProvider sessionProvider) {
		this.sessionProvider = sessionProvider;
	}
	
	/**
	 * Getter for the {@link DavLocatorFactory}.
	 * @return The DavLocatorFactory.
	 */
	public DavLocatorFactory getLocatorFactory() {
		if (locatorFactory == null) {
			locatorFactory = new DavLocatorFactoryImpl(resourcePathPrefix);
		}
		return locatorFactory;
	}
	
	/**
	 * Getter for a {@link DavResourceLocator} correspondig to the request URI.
	 * @param request Reference to the request of the servlet.
	 * @return The DavResourceLocator.
	 */
	private DavResourceLocator getResourceLocator(HttpServletRequest request) {
		return getResourceLocator(request, request.getRequestURI());
	}
	
	/**
	 * Getter for a {@link DavResourceLocator} correspondig to the given path.
	 * @param request Reference to the request of the servlet.
	 * @param path The given path.
	 * @return The DavResourceLocator.
	 */
	private DavResourceLocator getResourceLocator(HttpServletRequest request, String path) {
		// get context path from request
		String contextPath = request.getContextPath();

		// remove context path, if present in path
		if (path.startsWith(contextPath)) {
			path = path.substring(contextPath.length());
		}
		
		// create href prefix from request
		String hrefPrefix = request.getScheme() + "://" + request.getHeader("Host") + contextPath;
		
		return getLocatorFactory().createResourceLocator(hrefPrefix, path);
	}
}
