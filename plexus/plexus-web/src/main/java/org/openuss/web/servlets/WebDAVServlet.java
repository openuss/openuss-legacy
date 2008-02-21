package org.openuss.web.servlets;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;



/**
 * This is the entry point for WebDAV requests.
 * To configure the path of the WebDAV namespace, change web.xml. 
 * 
 */
public class WebDAVServlet extends HttpServlet {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 34L;
	
	private final static Logger logger = Logger.getLogger(WebDAVServlet.class);
	
	// servlet initialization parameter names
	private static final String INIT_PARAMETER_RESOURCE_PATH_PREFIX = "resource-path-prefix";
	private static final String INIT_PARAMETER_RESOURCE_CONFIGURATION = "resource-config";
	private static final String INIT_PARAMETER_AUTHENTICATE_HEADER = "authenticate-header";

	// default servlet initialization parameter values
	private static final String DEFAULT_RESOURCE_PATH_PREFIX = "/WEBDAVPATHNOTSET/"; 
	
	// operational configuration
	private static final String CONTEXT_ATTRIBUTE_RESOURCE_PATH_PREFIX = "docmanagement.resourcepathprefix";
	
	
	// WebDAV compliance
	private static final String DAV_COMPLIANCE_LEVEL = "1";
	private static final String DAV_ALLOWED_METHODS = "OPTIONS, GET, HEAD, POST, DELETE, PUT, PROPFIND, PROPPATCH, MKCOL, COPY, MOVE";
	
 	private String resourcePathPrefix;
	
 	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		response.getWriter().write("THIS IS WEBDAV");
 	}
//
//	/**
//	 * Getter for a {@link DavResourceLocator} correspondig to the request URI.
//	 * @param request Reference to the request of the servlet.
//	 * @return The DavResourceLocator.
//	 */
//	private DavResourceLocator getResourceLocator(HttpServletRequest request) {
//		return getResourceLocator(request, request.getRequestURI());
//	}
//	
//	/**
//	 * Getter for a {@link DavResourceLocator} correspondig to the given path.
//	 * @param request Reference to the request of the servlet.
//	 * @param path The given path.
//	 * @return The DavResourceLocator.
//	 */
//	private DavResourceLocator getResourceLocator(HttpServletRequest request, String path) {
//		// get context path from request
//		String contextPath = request.getContextPath();
//
//		// remove context path, if present in path
//		if (path.startsWith(contextPath)) {
//			path = path.substring(contextPath.length());
//		}
//		
//		// create href prefix from request
//		String hrefPrefix = request.getScheme() + "://" + request.getHeader("Host") + contextPath;
//		
//		return getDavLocatorFactory().createResourceLocator(hrefPrefix, path);
//	}
//	
//	/* (non-Javadoc)
//	 * @see javax.servlet.GenericServlet#init()
//	 */
//	public void init() throws ServletException {
//		logger.info("init() started.");
//		
//		super.init();
//		/*
//		// set sessionProvider from context
//		final WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
//		sessionProvider = (DavSessionProvider) wac.getBean("sessionProvider", DavSessionProvider.class);
//		*/
//		// read resource path prefix from configuration and store it in the context
//		resourcePathPrefix = getInitParameter(INIT_PARAMETER_RESOURCE_PATH_PREFIX);
//		if (resourcePathPrefix == null) {
//			resourcePathPrefix = DEFAULT_RESOURCE_PATH_PREFIX;
//		}
//		getServletContext().setAttribute(CONTEXT_ATTRIBUTE_RESOURCE_PATH_PREFIX, resourcePathPrefix);
//		logger.info("ResourcePathPrefix: " + resourcePathPrefix);
//		
//		// read the location of the configuration file and parse it
//        String configurationParameter = getInitParameter(INIT_PARAMETER_RESOURCE_CONFIGURATION);
//        configuration = new DavConfigurationImpl();
//        if (configurationParameter != null) {
//        	try {
//            	configuration.parse(getServletContext().getResource(configurationParameter));
//        	} catch (MalformedURLException ex) {
//        		logger.error("Unable to parse resource configuration file.");
//        		logger.error("Error message:\n" + ex.getMessage());
//        	}
//        }
//        
//        logger.info(" init() done.");
//	}
//	
//	/**
//	 * Handles method OPTIONS as demanded in RFC2518.
//	 * @param response Reference to the response of the servlet.
//	 */
//	private void publishOptions(HttpServletResponse response) {
//		// add required headers to response and send status code 200 (OK)
//		response.addHeader(WebDAVConstants.HEADER_DAV, DAV_COMPLIANCE_LEVEL);
//		response.addHeader("Allowed", DAV_ALLOWED_METHODS);
//		response.addHeader("MS-Author-Via", WebDAVConstants.HEADER_DAV);
//		response.setStatus(WebDAVStatus.SC_OK);
//	}
//
//	/**
//	 * Send multi-status to client.
//	 * @param multistatus The multi-status to send.
//	 * @param response Reference to the response of the servlet.
//	 * @throws IOException
//	 */
//	private void sendMultiStatus(MultiStatus multistatus, HttpServletResponse response) throws IOException {
//		// send multi-status, if present
//		if (multistatus != null) {
//			// set status of http-reply to 207 (multi-status)
//			response.setStatus(WebDAVStatus.SC_MULTI_STATUS);
//			
//			// create output stream
//			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//			
//			// create XML document and serialize multistatus
//			Document responseDocument = DocumentHelper.createDocument();
//			multistatus.toXml(responseDocument);
//
//			// set output format and write document to output stream
//			OutputFormat outputFormat = new OutputFormat("", true, "UTF-8");
//			XMLWriter writer = new XMLWriter(outputStream, outputFormat);
//			writer.write(responseDocument);
//			writer.flush();
//
//			// convert document in stream to byte array and write to response
//			byte[] bytes = outputStream.toByteArray();
//			response.setContentType("text/xml; charset=UTF-8");
//			response.setContentLength(bytes.length);
//			response.getOutputStream().write(bytes);
//		}
//	}
//	
//	/* (non-Javadoc)
//-	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
//	 */
//	@Override
//	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// retrieve method
//		int methodCode = WebDAVMethods.getMethodCode(request.getMethod());
//		
//		try {
//			logger.info("Creating resource locator from request.");
//			// create resource locator
//			DavResourceLocator locator = getResourceLocator(request);
//			
//			logger.info("Executing method.");
//			// try to execute method or delegate to super class
//			if (!execute(request, response, locator, methodCode)) {
//				logger.debug("Method execution failed. Delegating to super class.");
//				super.service(request, response);
//			}
//		} catch (WebDAVException ex) {
//			logger.error("DavException occured. ErrorCode: " + ex.getErrorCode());
//			logger.error("Exception: " + ex.getMessage());
//			// check status code, if user is unauthorized
//			if (ex.getErrorCode() == WebDAVStatus.SC_UNAUTHORIZED) {
//				// user unauthorized, request authentication
//                response.setHeader("WWW-Authenticate", getAuthenticateHeaderValue());
//                response.sendError(ex.getErrorCode(), ex.getStatusPhrase());
//			} else {
//				// any other error, send error code and message
//				response.sendError(ex.getErrorCode(), ex.getStatusPhrase());
//			}
//		} finally {
//			// release session from request
//			logger.info("Releasing session.");
//			getDavSessionProvider().releaseSession(getDavService());
//		}
//	}
//	
//	/**
//	 * Setter for the {@link DavLocatorFactory}.
//	 * @param locatorFactory The DavLocatorFactory to set.
//	 */
//	public void setDavLocatorFactory(DavLocatorFactory locatorFactory) {
//		this.locatorFactory = locatorFactory;
//	}
//	
//	/**
//	 * Setter for the {@link WebDAVBackend}.
//	 * @param service The DavService to set.
//	 */
//	public void setDavService(WebDAVBackend service) {
//		davService = service;
//	}
//	
//	/**
//	 * Setter for the {@link DavSessionProvider}.
//	 * @param sessionProvider The DavSessionProvider to set.
//	 */
//	public void setDavSessionProvider(DavSessionProvider sessionProvider) {
//		this.sessionProvider = sessionProvider;
//	}
}
