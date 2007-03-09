package org.openuss.web.docmanagement.webdav;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavLocatorFactory;
import org.apache.jackrabbit.webdav.DavMethods;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.jackrabbit.webdav.io.InputContextImpl;
import org.apache.jackrabbit.webdav.io.OutputContextImpl;
import org.apache.log4j.Logger;
import org.openuss.docmanagement.ResourceConfiguration;
import org.openuss.docmanagement.webdav.DavLocatorFactoryImpl;
import org.openuss.docmanagement.webdav.DavService;
import org.openuss.docmanagement.webdav.SessionProvider;
import org.openuss.repository.RepositoryService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author David Ullrich
 * @version 0.6
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
	
	private String resourcePathPrefix;
	private String authenticateHeader;
	
	private ResourceConfiguration configuration;
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
		configuration = new ResourceConfiguration();
        String configurationParameter = getInitParameter(INIT_PARAMETER_RESOURCE_CONFIGURATION);
        if (configurationParameter != null) {
        	try {
            	configuration = new ResourceConfiguration();
            	configuration.parse(getServletContext().getResource(configurationParameter));
        	} catch (MalformedURLException ex) {
        		// TODO  handle exception
        		logger.debug("Unable to parse resource configuration file.");
        		logger.debug("Error message:\n" + ex.getMessage());
        	}
        }
        
        logger.debug("init done.");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// retrieve method
		int methodCode = DavMethods.getMethodCode(request.getMethod());
		logger.debug("Method: " + request.getMethod() + "(" + methodCode + ")");
		
		try {
			// guarantee an authenticated user
			logger.debug("Attaching session.");
			if (!getSessionProvider().attachSession(request, getDavService())) {
				return;
			}
			
			// create resource locator
			DavResourceLocator locator = getResourceLocator(request);
			
			// execute method or delegate to super class
			if (!execute(request, response, locator, methodCode)) {
				logger.debug("Method execution failed. Delegating to super class.");
				super.service(request, response);
			}
		} catch (IOException ex) {
			logger.debug("IOException occured.");
			logger.debug("Exception: " + ex.getMessage());
			// TODO handle IOException
		} catch (DavException ex) {
			logger.debug("DavException occured. ErrorCode: " + ex.getErrorCode());
			logger.debug("Exception: " + ex.getMessage());
			// test, if user is unauthorized
			if (ex.getErrorCode() == HttpServletResponse.SC_UNAUTHORIZED) {
				// user unauthorized, request authentication
                response.setHeader("WWW-Authenticate", getAuthenticateHeaderValue());
                response.sendError(ex.getErrorCode(), ex.getStatusPhrase());
			} else {
				// undefined error, send error code and message
				response.sendError(ex.getErrorCode(), ex.getStatusPhrase());
			}
		} finally {
			// release session from request
			logger.debug("Releasing session.");
			getSessionProvider().releaseSession(getDavService());
		}
	}
	
	/**
	 * @param request
	 * @param response
	 * @param locator
	 * @param methodCode
	 * @return
	 * @throws IOException
	 */
	private boolean execute(HttpServletRequest request, HttpServletResponse response, DavResourceLocator locator, int methodCode) throws IOException {
		switch (methodCode) {
			case DavMethods.DAV_HEAD:
				// spool header of resource, no content
				getDavService().spoolResource(new OutputContextImpl(response, null), locator, false);
				break;
			case DavMethods.DAV_GET:
				// spool entire resource
				getDavService().spoolResource(new OutputContextImpl(response, response.getOutputStream()), locator, true);
				break;
			case DavMethods.DAV_POST:
			case DavMethods.DAV_PUT:
				// add Content to resource
				getDavService().addMember(new InputContextImpl(request, request.getInputStream()), locator);
				break;
			default:
				// unknown or unsupported method code
				return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public String getAuthenticateHeaderValue() {
		return authenticateHeader;
	}
	
	/**
	 * @return
	 */
	public DavService getDavService() {
		if (davService == null) {
			davService = new DavService(configuration);
		}
		return davService;
	}
	
	/**
	 * @return
	 */
	public SessionProvider getSessionProvider() {
		return sessionProvider;
	}
	
	/**
	 * @return
	 */
	public DavLocatorFactory getLocatorFactory() {
		if (locatorFactory == null) {
			locatorFactory = new DavLocatorFactoryImpl(resourcePathPrefix);
		}
		return locatorFactory;
	}
	
	/**
	 * @param request
	 * @return
	 */
	private DavResourceLocator getResourceLocator(HttpServletRequest request) {
		// TODO kommentieren
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();
		if (path.startsWith(contextPath)) {
			path = path.substring(contextPath.length());
		}
		String hrefPrefix = request.getScheme() + "://" + request.getHeader("Host") + contextPath;
		return getLocatorFactory().createResourceLocator(hrefPrefix, path);
	}

	public void setSessionProvider(SessionProvider sessionProvider) {
		this.sessionProvider = sessionProvider;
	}
}
