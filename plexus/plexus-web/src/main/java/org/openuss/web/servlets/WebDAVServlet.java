package org.openuss.web.servlets;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openuss.web.dav.RootResource;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.MultiStatusAnswer;
import org.openuss.webdav.MultiStatusAnswerImpl;
import org.openuss.webdav.MultiStatusResponse;
import org.openuss.webdav.NullIOContext;
import org.openuss.webdav.SimpleStatusResponse;
import org.openuss.webdav.SimpleWebDAVAnswer;
import org.openuss.webdav.WebDAVAnswer;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVContext;
import org.openuss.webdav.WebDAVException;
import org.openuss.webdav.WebDAVHrefException;
import org.openuss.webdav.WebDAVMethods;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVPathException;
import org.openuss.webdav.WebDAVPathImpl;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.openuss.webdav.WebDAVStatusCodes;
import org.openuss.webdav.WebDAVUtils;
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
	private static final String INIT_PARAMETER_MAX_FILE_SIZE = "max-file-size";
	private static final String INIT_PARAMETER_KILLBIT = "killbit";
	
	private static final String KILLBIT_ENABLED = "on";

	// WebDAV compliance
	private static final String DAV_COMPLIANCE_LEVEL = "1";
	private static final String DAV_ALLOWED_METHODS = "OPTIONS, GET, HEAD, DELETE, PUT, PROPFIND, PROPPATCH, MKCOL, COPY, MOVE";

	/**
	 * If set, disable WebDAV
	 */
	private boolean killBit;
	
 	private String resourcePathPrefix;
 	private WebDAVContext davContext;
	
 	/* (non-Javadoc)
 	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 	 */
 	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 		if (killBit) {
 			WebDAVAnswer a = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, "WebDAV is disabled.");
 			printResponse(response, a);
 			return;
 		}
 		
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
			
			switch(code) {
			case WebDAVMethods.DAV_OPTIONS:
				publishOptions(response); 
				break;
			case WebDAVMethods.DAV_HEAD:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = getRoot().resolvePath(path);
				IOContext context = resource.readContent();
				NullIOContext nullContext = new NullIOContext(context);
				WebDAVUtils.writeToClient(response, nullContext);
				break;
			case WebDAVMethods.DAV_GET:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = getRoot().resolvePath(path);
				WebDAVUtils.writeToClient(response, resource.readContent());
				break;
			case WebDAVMethods.DAV_DELETE:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = getRoot().resolvePath(path);
				resource.delete();
				answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_OK);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_MKCOL:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				parentPath = path.getParent();
				parentResource = getRoot().resolvePath(parentPath);
				
				String newFilename = path.asResolved().getFileName();
				if (parentResource.hasChild(newFilename)){
					answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_METHOD_NOT_ALLOWED);
					printResponse(response, answer);
					break;
				}
				parentResource.createCollection(newFilename);
				
				answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_CREATED);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_COPY:
				answer = copy(request, false);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_MOVE:
				answer = copy(request, true);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_POST:
				answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_METHOD_NOT_ALLOWED);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_PUT:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				parentPath = path.getParent();
				parentResource = getRoot().resolvePath(parentPath);
				overwrite = WebDAVUtils.readOverwriteHeader(request);
				String fileName = path.asResolved().getFileName();
				IOContext ioc = WebDAVUtils.getClientInputContext(request);
				
				if (parentResource.hasChild(fileName)){
					if (!overwrite){
						answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_PRECONDITION_FAILED);
						printResponse(response, answer);
						break;
					} else {
						resource = parentResource.resolvePath(parentPath.asResolved().concat(fileName));
						
						if (resource.isCollection()) {
							throw new WebDAVResourceException(WebDAVStatusCodes.SC_METHOD_NOT_ALLOWED, resource);
						} else {
							resource.writeContent(ioc);
						}
					}
				} else {
					resource = parentResource.createFile(fileName, ioc);
				}
				
				answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_CREATED);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_PROPFIND:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = getRoot().resolvePath(path);
				doc = WebDAVUtils.getRequestDocument(request);
				depth = WebDAVUtils.readDepthHeader(request);
				answer = propFind(resource, doc, depth);
				printResponse(response, answer);
				break;
			case WebDAVMethods.DAV_PROPPATCH:
				path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
				resource = getRoot().resolvePath(path);
				doc = WebDAVUtils.getRequestDocument(request);
				depth = WebDAVUtils.readDepthHeader(request);
				answer = propPatch(resource, doc, depth);
				printResponse(response, answer);
				break;
			}
		} catch(WebDAVException ex){
			logger.debug("Outputting WebDAVException", ex);
			printResponse(response, ex);
		} catch(IOException ioe) {
			logger.debug(ioe);
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
 		if (msg != null) {
 			w.write(msg);
 		}
 		w.close();
 	}
 	
 	/**
 	 * Handle a COPY or MOVE request.
 	 * 
 	 * @param request The request of the client
 	 * @param move Iff set, the original resource should be deleted after successfully copying.
 	 * @throws WebDAVException On grave errors
 	 */
 	protected MultiStatusAnswer copy(HttpServletRequest request, boolean move) throws WebDAVException {
 		RootResource root = getRoot();
 		
 		WebDAVPath srcPath = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
 		WebDAVResource srcRes = root.resolvePath(srcPath);
 		
 		String dstStr = request.getHeader(WebDAVConstants.HEADER_DESTINATION);
 		if (dstStr == null) {
 			throw new WebDAVException(WebDAVStatusCodes.SC_BAD_REQUEST, "Destination header missing");
 		}
		WebDAVPath dstPath = WebDAVPathImpl.parse(resourcePathPrefix, dstStr);
		WebDAVPath dstParentPath = dstPath.getParent();
		WebDAVResource dstParent;
		try {
			dstParent = root.resolvePath(dstParentPath);
		} catch (WebDAVException e) {
			if (e.getStatusCode() == WebDAVStatusCodes.SC_NOT_FOUND) {
				e.setStatusCode(WebDAVStatusCodes.SC_CONFLICT);
			}
			
			throw e;
		}
		
		String name = dstPath.asResolved().getFileName();
		
		// Read options
		boolean overwrite = WebDAVUtils.readOverwriteHeader(request);
		boolean recursive;
		if (move) {
			int depth = WebDAVUtils.readDepthHeader(request);
			recursive = (depth == WebDAVConstants.DEPTH_INFINITY);
		} else {
			recursive = true;
		}
		
		// Resolve resources
		MultiStatusAnswer answer = new MultiStatusAnswerImpl();
		boolean copyRes = copy(srcRes, dstParent, name, recursive, overwrite, answer);
		
		if (copyRes && move) {
			srcRes.delete();
		}
		
		return answer;
	}
 	
 	/**
 	 * Helper method for {@link #copy(WebDAVResource, WebDAVPath, boolean, boolean)} for recursive COPY
 	 * @param src the source resource
 	 * @param dstParent the parent of the destination resource
 	 * @param name The name of the resource to create as a child of dstParent
 	 * @param overwrite Iff set, existing resources are overwritten.
 	 * @param recursive true iff depth is INFINITY
 	 * @param answer the MultiStatusAnswer answer object.
 	 * @param true Iff all resources could be copied
 	 */
 	protected boolean copy(WebDAVResource src, WebDAVResource dstParent, String dstName,
 							boolean recursive, boolean overwrite, MultiStatusAnswer answer){
 		boolean res = true;
 		
 		try {
 			WebDAVResource dstRes = null;
 			
 			if (dstParent.hasChild(dstName)) {
 				if (!overwrite) {
 					throw new WebDAVPathException(
 							WebDAVStatusCodes.SC_PRECONDITION_FAILED,
 							dstParent.getPath().concat(dstName).asResolved());
 				}
 				
 				dstRes = dstParent.resolvePathElem(dstName);
 			}
 			
 	 		if (src.isCollection()) {
 	 			MultiStatusResponse msr;
 	 			
 	 			if (dstRes == null) {
 	 				dstRes = dstParent.createCollection(dstName);
 	 				msr = new SimpleStatusResponse(dstRes, WebDAVStatusCodes.SC_CREATED);
 	 			} else {
 	 				msr = new SimpleStatusResponse(dstRes, WebDAVStatusCodes.SC_NO_CONTENT);
 	 			}
 	 			answer.addResponse(msr);
 	 				
 				if (recursive){
					for (WebDAVResource cr : src.getChildren()){
						res &= copy(cr, dstRes, cr.getName(), recursive, overwrite, answer);
					}
 				}
			} else { // this is a file
				int statusCode;
				if (dstRes != null) {
					dstRes.delete();
					statusCode = WebDAVStatusCodes.SC_NO_CONTENT; 
				} else {
					statusCode = WebDAVStatusCodes.SC_CREATED;
				}
				
 				IOContext context = src.readContent();
 				dstRes = dstParent.createFile(dstName, context);
 				
 				MultiStatusResponse msr = new SimpleStatusResponse(dstRes, statusCode);
 	 			answer.addResponse(msr);
 			}		
 		} catch(WebDAVHrefException exDAV){
 			MultiStatusResponse exceptionResponse = new SimpleStatusResponse(exDAV.getHref(), exDAV.getStatusCode()); 
 			answer.addResponse(exceptionResponse);
 			
 			res = false;
 		} catch(IOException exIO){
 			MultiStatusResponse exceptionResponse = new SimpleStatusResponse(src, WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR); 
 			answer.addResponse(exceptionResponse);
 			
 			res = false;
 		}
 		
 		return res;
 	}
 	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		logger.info("init() started.");
		
		super.init();

		killBit = KILLBIT_ENABLED.equals(getInitParameter(INIT_PARAMETER_KILLBIT));
		if (killBit) {
			logger.warn("WebDAV killbit activated");
		}
		
		// read resource path prefix from configuration and store it in the context
		resourcePathPrefix = this.getServletContext().getContextPath() + getInitParameter(INIT_PARAMETER_RESOURCE_PATH_PREFIX);
		if (resourcePathPrefix == null) {
			throw new ServletException("resource path prefix not set");
		}
		
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		String maxFileSizeStr = getInitParameter(INIT_PARAMETER_MAX_FILE_SIZE);
		long maxFileSize = (maxFileSizeStr == null) ? WebDAVContext.NO_MAX_FILESIZE :
				Long.valueOf(maxFileSizeStr);
		davContext = new WebDAVContext(wac, maxFileSize);
		
		logger.info("ResourcePathPrefix: " + resourcePathPrefix);   
        logger.info(" init() done.");
	}
	
	/**
	 * @return The root resource
	 */
	protected RootResource getRoot() {
		return RootResource.getRoot(davContext, resourcePathPrefix);
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
