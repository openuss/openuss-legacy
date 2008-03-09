package org.openuss.web.servlets;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openuss.web.dav.MultiStatusAnswerImpl;
import org.openuss.web.dav.SimpleStatusResponse;
import org.openuss.web.dav.NullIOContext;
import org.openuss.web.dav.SimpleWebDAVAnswer;
import org.openuss.web.dav.WebDAVPathImpl;
import org.openuss.web.dav.WebDAVUtils;
import org.openuss.web.dav.backends.RootResource;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.MultiStatusAnswer;
import org.openuss.webdav.MultiStatusResponse;
import org.openuss.webdav.WebDAVAnswer;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVException;
import org.openuss.webdav.WebDAVHrefException;
import org.openuss.webdav.WebDAVMethods;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.openuss.webdav.WebDAVStatusCodes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Document;

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

	// WebDAV compliance
	private static final String DAV_COMPLIANCE_LEVEL = "1";
	private static final String DAV_ALLOWED_METHODS = "OPTIONS, GET, HEAD, POST, DELETE, PUT, PROPFIND, PROPPATCH, MKCOL, COPY, MOVE";
	
 	private String resourcePathPrefix;
 	private WebApplicationContext wac;
	
 	/* (non-Javadoc)
 	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 	 */
 	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 		try	{
 			String destination;
	 		WebDAVPath destinationPath;
	 		WebDAVPath parentPath;
			WebDAVResource parentResource;
 			String method = request.getMethod();
			int code = WebDAVMethods.getMethodCode(method);
			boolean overwrite;
			boolean recursive;
			int depth;
			WebDAVPath path;
			WebDAVResource resource;
			Document doc;
			WebDAVAnswer answer;
			
			logger.debug("WebDAVServlet was called with " + method + " method");
			
			WebDAVResource root = getRoot();
			
			switch(code) {
			case WebDAVMethods.DAV_OPTIONS:
				publishOptions(response); 
				break;
			case WebDAVMethods.DAV_HEAD:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = root.resolvePath(path);
				IOContext context = resource.readContent();
				NullIOContext nullContext = new NullIOContext(context);
				WebDAVUtils.writeToClient(response, nullContext);
				break;
			case WebDAVMethods.DAV_GET:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = root.resolvePath(path);
				WebDAVUtils.writeToClient(response, resource.readContent());
				break;
			case WebDAVMethods.DAV_COPY:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = root.resolvePath(path);
				destination = request.getHeader(WebDAVConstants.HEADER_DESTINATION);
				destinationPath = WebDAVPathImpl.parse(resourcePathPrefix, destination);
				overwrite = WebDAVUtils.readOverwriteHeader(request);
				depth = WebDAVUtils.readDepthHeader(request);
				recursive = (depth == WebDAVConstants.DEPTH_INFINITY);
				answer = copy(resource, destinationPath, recursive, overwrite);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_DELETE:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = root.resolvePath(path);
				resource.delete();
				answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_OK);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_MKCOL:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				parentPath = path.getParent();
				parentResource = root.resolvePath(parentPath);
				overwrite = WebDAVUtils.readOverwriteHeader(request);
				if (parentResource.hasChild(path.getFileName())){
					if (!overwrite){
						answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_PRECONDITION_FAILED);
						printResponse(response, answer);
						break;
					}
				}
				parentResource.createCollection(path.getFileName());
				answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_CREATED);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_MOVE:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = root.resolvePath(path);
				destination = request.getHeader(WebDAVConstants.HEADER_DESTINATION);
				destinationPath = WebDAVPathImpl.parse(resourcePathPrefix, destination);
				overwrite = WebDAVUtils.readOverwriteHeader(request);
				depth = WebDAVUtils.readDepthHeader(request);
				recursive = (depth == WebDAVConstants.DEPTH_INFINITY);
				answer = copy(resource, destinationPath, recursive, overwrite);
				resource.delete();
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_POST:
				answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_BAD_REQUEST);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_PUT:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				parentPath = path.getParent();
				parentResource = root.resolvePath(parentPath);
				overwrite = WebDAVUtils.readOverwriteHeader(request);
				if (parentResource.hasChild(path.getFileName())){
					if (!overwrite){
						answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_PRECONDITION_FAILED);
						printResponse(response, answer);
						break;
					}
				}
				resource = parentResource.createFile(path.getFileName());
				resource.writeContent(WebDAVUtils.getClientInputContext(request));
				answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_CREATED);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_PROPFIND:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = root.resolvePath(path);
				doc = WebDAVUtils.getRequestDocument(request);
				depth = WebDAVUtils.readDepthHeader(request);
				answer = propFind(resource, doc, depth);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_PROPPATCH:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = root.resolvePath(path);
				doc = WebDAVUtils.getRequestDocument(request);
				depth = WebDAVUtils.readDepthHeader(request);
				answer = propPatch(resource, doc, depth);
				printResponse(response, answer);
				break;
			}
		} catch(WebDAVException ex){
			logger.error("Outputting WebDAVException", ex);
			printResponse(response, ex);
		} catch(IOException ioe) {
			logger.error(ioe);
		}
 	}
 	
 	/**
 	 * Handles method PROPPATCH as demanded in RFC2518.
 	 * @param resource the resource with the properties
 	 * @param doc the request document
 	 * @param depth the depth of the request
 	 * @return an MultiStatusAnswer object
 	 * @throws WebDAVResourceException On errors when changing the top resource.
 	 */
 	public static MultiStatusAnswer propPatch(WebDAVResource resource, Document doc, int depth) throws WebDAVResourceException{
 		MultiStatusAnswer answer = new MultiStatusAnswerImpl();
 		propPatch(resource, doc, answer, depth);
 		return answer;
	}
 	
 	/**
 	 * help method for {@link #propPatch(WebDAVResource, Document, int)} for recursive PROPPATCH
 	 * @param resource the resource with the properties
 	 * @param doc the request document
 	 * @param answer the MultiStatusAnswer object
 	 * @param recursive true iff depth is INFINITY
 	 * @throws WebDAVResourceException On errors when changing the top resource.
 	 */
 	protected static void propPatch(WebDAVResource resource, Document doc, MultiStatusAnswer answer, int depth) throws WebDAVResourceException{
 		MultiStatusResponse response = resource.updateProperties(doc);
		answer.addResponse(response);
		
 		try {
	 		if ((depth == WebDAVConstants.DEPTH_INFINITY) || (depth == WebDAVConstants.DEPTH_1)) {
	 			Set<WebDAVResource> children = resource.getChildren();
	 			if (children != null) {
	 				int newDepth = (depth == WebDAVConstants.DEPTH_INFINITY) ? WebDAVConstants.DEPTH_INFINITY : WebDAVConstants.DEPTH_0;
	 				for (WebDAVResource c : children) {
	 					propPatch(c, doc, answer, newDepth);	
	 				}
	 			}
	 		}
 		} catch (WebDAVHrefException ex){
 			// Log, but ignore otherwise
 			logger.error("Error in PROPPATCH", ex);
 		}
 	}

	/**
 	 * Handles method PROPFIND as demanded in RFC2518.
 	 * @param resource the resource with the properties
 	 * @param reqDoc the request document
 	 * @param depth the depth of the request
 	 * @return an MultiStatusAnswer object
	 * @throws WebDAVResourceException On errors when listing the top resource.
 	 */
 	public static MultiStatusAnswer propFind(WebDAVResource resource, Document reqDoc, int depth) throws WebDAVResourceException{
 		MultiStatusAnswer answer = new MultiStatusAnswerImpl();
 		propFind(resource, reqDoc, answer, depth);
 		return answer;
 	}
 	
 	/**
 	 * help method for {@link #propFind(WebDAVResource, Document, int)} for recursive PROPFIND
 	 * @param resource the resource with the properties
 	 * @param reqDoc the request document
 	 * @param answer the MultiStatusAnswer object
 	 * @param depth The value of the Depth header.
 	 * @throws WebDAVResourceException On errors when getting the information of this resource
 	 */
 	protected static void propFind(WebDAVResource resource, Document reqDoc, MultiStatusAnswer answer, int depth) throws WebDAVResourceException {
 		MultiStatusResponse response = resource.getProperties(reqDoc);
		answer.addResponse(response);
		
 		try {
	 		if ((depth == WebDAVConstants.DEPTH_INFINITY) || (depth == WebDAVConstants.DEPTH_1)) {
	 			Set<WebDAVResource> children = resource.getChildren();
	 			if (children != null) {
	 				int newDepth = (depth == WebDAVConstants.DEPTH_INFINITY) ? WebDAVConstants.DEPTH_INFINITY : WebDAVConstants.DEPTH_0;
	 				for (WebDAVResource c : children) {
	 					propFind(c, reqDoc, answer, newDepth);	
	 				}
	 			}
	 		}
 		} catch (WebDAVHrefException ex){
 			// Log, but ignore otherwise
 			logger.error("Error in PROPFIND", ex);
 		}
 	}
 	
 	/**
 	 * Updates the response object by the given answer object and prints it
 	 * @param response the response object
 	 * @param answer the SimpleWebDAVAnswer object
 	 * @throws IOException
 	 */
 	public static void printResponse(HttpServletResponse response, WebDAVAnswer answer) throws IOException{
 		String msg = answer.getMessage();
 		response.setStatus(answer.getStatusCode());
 		response.setContentType(answer.getContentType());
 		
 		Writer w = response.getWriter();
 		w.write(msg);
 		w.close();
 	}
 	
 	/**
 	 * Handles method COPY as demanded in RFC2518.
 	 * @param src the source resource
 	 * @param dst the destination resource
 	 * @param recursive true iff depth is INFINITY
 	 * @param overwrite true iff overwrite is allowed
 	 * @return the MultiStatusAnswer object
 	 */
 	public MultiStatusAnswer copy(WebDAVResource src, WebDAVPath dst, boolean recursive, boolean overwrite){
 		MultiStatusAnswer answer = new MultiStatusAnswerImpl();
 		WebDAVResource parentResource = getRoot();
 		copy(src, parentResource, recursive, overwrite, answer);
 		return answer;
 	}
 	
 	/**
 	 * help method for {@link #copy(WebDAVResource, WebDAVPath, boolean, boolean)} for recursive COPY
 	 * @param src the source resource
 	 * @param dst the parent of the destination resource
 	 * @param recursive true iff depth is INFINITY
 	 * @param overwrite true iff overwrite is allowed
 	 * @param answer the MultiStatusAnswer object
 	 */
 	protected void copy(WebDAVResource src, WebDAVResource parentDst, boolean recursive, boolean overwrite, MultiStatusAnswer answer){
 		try {
 			String filename = src.getPath().getFileName();
 			WebDAVPath destinationPath = parentDst.getPath().concat(filename);
			if (overwrite){
				if (parentDst.hasChild(filename)) {
					parentDst.resolvePath(destinationPath).delete();
				}
			} else {
				MultiStatusResponse msr = new SimpleStatusResponse(destinationPath.toClientString(), WebDAVStatusCodes.SC_PRECONDITION_FAILED); 
 	 			answer.addResponse(msr); 
 	 			return;
			}
 			
 			if (src.isCollection()) {
 				parentDst.createCollection(filename);
 				MultiStatusResponse msr = new SimpleStatusResponse(destinationPath.toClientString(), WebDAVStatusCodes.SC_CREATED); 
 	 			answer.addResponse(msr); 
 				if (recursive){
					Set<WebDAVResource> resources = src.getChildren();
					for (WebDAVResource resource : resources){
						copy(resource, parentDst.resolvePath(destinationPath), recursive, overwrite, answer);
					}
 				}
			} else {
 				IOContext context = src.readContent();
 				WebDAVResource destinationResource = parentDst.createFile(filename);
 				destinationResource.writeContent(context);
 				MultiStatusResponse msr = new SimpleStatusResponse(destinationPath.toClientString(), WebDAVStatusCodes.SC_CREATED); 
 	 			answer.addResponse(msr);
 	 			return;
 			}		
 		}
 		catch(WebDAVHrefException exDAV){
 			MultiStatusResponse exceptionResponse = new SimpleStatusResponse(exDAV.getHref(), exDAV.getStatusCode()); 
 			answer.addResponse(exceptionResponse);
 		}
 		catch(IOException exIO){
 			logger.info("error while copying", exIO);
 		}
 	}
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		logger.info("init() started.");
		
		super.init();

		// read resource path prefix from configuration and store it in the context
		resourcePathPrefix = this.getServletContext().getContextPath() + getInitParameter(INIT_PARAMETER_RESOURCE_PATH_PREFIX);
		if (resourcePathPrefix == null) {
			throw new ServletException("resource path prefix not set");
		}
		wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		
		logger.info("ResourcePathPrefix: " + resourcePathPrefix);   
        logger.info(" init() done.");
	}
	
	/**
	 * @return The root resource
	 */
	protected WebDAVResource getRoot() {
		return RootResource.getRoot(wac, resourcePathPrefix);
	}
	
	/**
	 * Handles method OPTIONS as demanded in RFC2518.
	 * @param response Reference to the response of the servlet.
	 */
	protected static void publishOptions(HttpServletResponse response) {
		// add required headers to response and send status code 200 (OK)
		response.addHeader(WebDAVConstants.HEADER_DAV, DAV_COMPLIANCE_LEVEL);
		response.addHeader("Allowed", DAV_ALLOWED_METHODS);
		response.addHeader("MS-Author-Via", WebDAVConstants.HEADER_DAV);
		response.setStatus(WebDAVStatusCodes.SC_OK);
	}	
}
