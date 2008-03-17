package org.openuss.web.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openuss.web.dav.OpenUSSWebDAVContext;
import org.openuss.web.dav.RootResource;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.MultiStatusAnswer;
import org.openuss.webdav.MultiStatusAnswerImpl;
import org.openuss.webdav.MultiStatusResponse;
import org.openuss.webdav.MultiStatusStatusResponse;
import org.openuss.webdav.NullIOContext;
import org.openuss.webdav.PropertyResponse;
import org.openuss.webdav.PropertyResponseImpl;
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
import org.openuss.webdav.XMLPropertyResponseNode;
import org.openuss.webdav.XMLWebDAVAnswer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	private static final String INIT_PARAMETER_WRITE_LIMIT = "write-limit";
	private static final String INIT_PARAMETER_READ_LIMIT = "read-limit";
	private static final String INIT_PARAMETER_LOCK_LIMIT = "lock-limit";
	private static final String INIT_PARAMETER_EVADE_UMLAUTS = "evade-umlauts";
	
	/**
	 * Do not limit the particular resource
	 */
	private static final int NO_LIMIT = -1;
	/**
	 * Do not limit the particular resource, but do not allow recursive requests at all (makes sense only for PROPFIND)
	 */
	private static final int NO_RECURSIVE = -2;
		
	// WebDAV compliance
	private static final String DAV_COMPLIANCE_LEVEL = "1,2";
	private static final String DAV_ALLOWED_METHODS = "OPTIONS,GET,HEAD,DELETE,PUT,PROPFIND,PROPPATCH,MKCOL,COPY,MOVE,POST,LOCK,UNLOCK";
	
	/**
	 * If set, disable WebDAV
	 */
	private boolean killBit;
	
	// Limit the number of reading and writing requests in one call
	private int readLimit; 
	private int writeLimit;
	private int lockLimit;
	
 	private String resourcePathPrefix;
 	private transient WebDAVContext davContext;
	
 	/* (non-Javadoc)
 	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 	 */
 	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 		if (killBit) {
 			WebDAVAnswer a = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, "WebDAV is disabled.");
 			printAnswer(response, a);
 			return;
 		}
 		
 		try	{
 			String method = request.getMethod();
			int code = WebDAVMethods.getMethodCode(method);
			WebDAVPath path;
			WebDAVResource resource;
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
				delete(request);
				answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_OK);
				printAnswer(response, answer);
				break;
			case WebDAVMethods.DAV_MKCOL:
				answer = mkcol(request);
				printAnswer(response, answer);
				break;
			case WebDAVMethods.DAV_COPY:
				answer = copy(request, false);
				printAnswer(response, answer);
				break;
			case WebDAVMethods.DAV_MOVE:
				answer = copy(request, true);
				printAnswer(response, answer);
				break;
			case WebDAVMethods.DAV_PUT:
				put(request);
				answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_CREATED);
				printAnswer(response, answer);
				break;
			case WebDAVMethods.DAV_PROPFIND:
				answer = propFind(request);
				printAnswer(response, answer);
				break;
			case WebDAVMethods.DAV_PROPPATCH:
				answer = propPatch(request);
				printAnswer(response, answer);
				break;
			case WebDAVMethods.DAV_LOCK:
				answer = lock(request);
				printAnswer(response, answer);
			case WebDAVMethods.DAV_UNLOCK:
				answer = unlock(request);
				printAnswer(response, answer);
			default:
				throw new WebDAVException(WebDAVStatusCodes.SC_METHOD_NOT_ALLOWED);
			}
		} catch(WebDAVException ex){
			logger.debug("Outputting WebDAVException", ex);
			printAnswer(response, ex);
		} catch(IOException ioe) {
			logger.debug(ioe);
		}
 	}
 	
	/**
 	 * Handles a PROPPATCH request.
 	 * @param request The client's format. 
 	 * @return an MultiStatusAnswer object
 	 * @throws WebDAVException On errors when changing the top resource or reaching the limit.
 	 */
 	protected MultiStatusAnswer propPatch(HttpServletRequest request) throws WebDAVException, IOException{
 		WebDAVPath path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
		WebDAVResource resource = getRoot().resolvePath(path);
		Document reqDoc = WebDAVUtils.getRequestDocument(request);
		int depth = WebDAVUtils.readDepthHeader(request);
		
 		MultiStatusAnswer answer = new MultiStatusAnswerImpl();
 		
 		Counter counter = new Counter(writeLimit);
 		propPatch(resource, reqDoc, answer, depth, counter);
 		
 		return answer;
	}
 	
 	/**
 	 * help method for {@link #propPatch(WebDAVResource, Document, int)} for recursive PROPPATCH
 	 * @param resource the resource with the properties
 	 * @param doc the request document
 	 * @param answer the MultiStatusAnswer object
 	 * @param recursive true iff depth is INFINITY
 	 * @param counter The limitation enforcement facility.
 	 * @throws WebDAVException When reaching a limit
 	 */
 	protected static void propPatch(WebDAVResource resource, Document doc, MultiStatusAnswer answer, int depth, Counter counter) throws WebDAVException{
 		counter.check();
 		
 		MultiStatusResponse response = resource.updateProperties(doc);
		answer.addResponse(response);
		
 		try {
	 		if ((depth == WebDAVConstants.DEPTH_INFINITY) || (depth == WebDAVConstants.DEPTH_1)) {
	 			List<WebDAVResource> children = resource.getChildren();
	 			if (children != null) {
	 				int newDepth = (depth == WebDAVConstants.DEPTH_INFINITY) ? WebDAVConstants.DEPTH_INFINITY : WebDAVConstants.DEPTH_0;
	 				for (WebDAVResource c : children) {
	 					propPatch(c, doc, answer, newDepth, counter);	
	 				}
	 			}
	 		}
 		} catch (WebDAVHrefException ex){
 			answer.addResponse(ex.toStatusResponse());
 		}
 	}

 	/**
 	 * Handle a PROPFIND request
 	 * 
 	 * @param request The request
 	 * @return An answer detailling all the results
 	 * @throws IOException On reading errors
 	 * @throws WebDAVException 
 	 */
 	protected WebDAVAnswer propFind(HttpServletRequest request) throws IOException, WebDAVException {
 		WebDAVPath path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
		WebDAVResource resource = getRoot().resolvePath(path);
		Document reqDoc = WebDAVUtils.getRequestDocument(request);
		
		// Read and check depth
		int depth = WebDAVUtils.readDepthHeader(request);
		if ((readLimit == NO_RECURSIVE) && (depth == WebDAVConstants.DEPTH_INFINITY)) {
			throw new WebDAVException(WebDAVStatusCodes.SC_FORBIDDEN, "Infinite depth requests are forbidden");
		}
		
		MultiStatusAnswer answer = new MultiStatusAnswerImpl();
 		try {
			propFind(resource, reqDoc, answer, depth, new Counter(readLimit));
		} catch (WebDAVException e) {
			logger.warn("Reached PROPFIND reading limit", e);
		}
 		return answer;
 	}
 	
 	/**
 	 * help method for {@link #propFind(WebDAVResource, Document, int)} for recursive PROPFIND
 	 * @param resource the resource with the properties
 	 * @param reqDoc the request document
 	 * @param answer the MultiStatusAnswer object
 	 * @param depth The value of the Depth header.
 	 * @param counter The limitation enforcement facility.
 	 * @return The number of all handled requests until now.
 	 * @throws WebDAVException If a limit was reached.
 	 */
 	protected static void propFind(WebDAVResource resource, Document reqDoc, MultiStatusAnswer answer, int depth, Counter counter) throws WebDAVException {
 		counter.check();
 		
 		MultiStatusResponse response = resource.getProperties(reqDoc);
		answer.addResponse(response);
		
 		if ((depth == WebDAVConstants.DEPTH_INFINITY) || (depth == WebDAVConstants.DEPTH_1)) {
 			try {
	 			List<WebDAVResource> children = resource.getChildren();
	 			if (children != null) {
	 				int newDepth = (depth == WebDAVConstants.DEPTH_INFINITY) ? WebDAVConstants.DEPTH_INFINITY : WebDAVConstants.DEPTH_0;
	 				for (WebDAVResource c : children) {
	 					propFind(c, reqDoc, answer, newDepth, counter);	
	 				}
	 			}
	 		} catch (WebDAVResourceException ex){
	 			// Allow users to view the name and collection state of empty collections
	 			if (ex.getStatusCode() == WebDAVStatusCodes.SC_FORBIDDEN) {
	 				answer.addResponse(generateRestrictedProperties(ex.getResource()));
	 			}
	 			
	 			answer.addResponse(ex.toStatusResponse());
	 		}
 		}
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
 		CopyChecker cc = new CopyChecker(writeLimit);
 		
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
		cc.check(dstParent);
		
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
		boolean copyRes = copy(srcRes, dstParent, name, recursive, overwrite, answer, cc);
		
		if (copyRes && move) {
			srcRes.delete();
		}
		
		// Set correct status code
		if (copyRes) {
			int commonCode = WebDAVStatusCodes.SC_CREATED;
			
			for (MultiStatusResponse msr : answer.getResponses()) {
				if (msr instanceof MultiStatusStatusResponse) {
					int thisCode = ((MultiStatusStatusResponse) msr).getStatusCode();
					if (thisCode != WebDAVStatusCodes.SC_CREATED) {
						commonCode = thisCode;
						break;
					}
				}
			}
			
			throw new WebDAVException(commonCode);
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
 	 * @param affectedResources A set of already touched resources to prevent infinite loops
 	 * @param true Iff all resources could be copied
 	 */
 	protected boolean copy(WebDAVResource src, WebDAVResource dstParent, String dstName,
 							boolean recursive, boolean overwrite, MultiStatusAnswer answer,
 							CopyChecker copyChecker) {
 		boolean res = true;
 		
 		try {
 	 		copyChecker.check(src);
 	 		
 			WebDAVResource dstRes = null;
 			
 			if (dstParent.hasChild(dstName)) {
 				if (!overwrite) {
 					throw new WebDAVPathException(
 							WebDAVStatusCodes.SC_PRECONDITION_FAILED,
 							dstParent.getPath().concat(dstName).asResolved());
 				}
 				
 				dstRes = dstParent.resolvePathElem(dstName);
 	 			copyChecker.check(dstRes);
 			}
 			
 	 		if (src.isCollection()) {
 	 			MultiStatusResponse msr;
 	 			
 	 			if (dstRes == null) {
 	 				dstRes = dstParent.createCollection(dstName);
 	 				copyChecker.check(dstRes);
 	 				msr = new SimpleStatusResponse(dstRes, WebDAVStatusCodes.SC_CREATED);
 	 			} else {
 	 				if (!dstRes.isCollection()) {
 	 					dstRes.delete();
 	 					dstRes = dstParent.createCollection(dstName);
 	 	 				copyChecker.check(dstRes);
 	 				} 
				
 	 				msr = new SimpleStatusResponse(dstRes, WebDAVStatusCodes.SC_NO_CONTENT);
 	 			}
 				if (!dstRes.isWritable()) {
 					throw new WebDAVResourceException(WebDAVStatusCodes.SC_FORBIDDEN, dstRes);
 				}
 	 			answer.addResponse(msr);
 	 				
 				if (recursive){
					for (WebDAVResource cr : src.getChildren()){
						res &= copy(cr, dstRes, cr.getName(), recursive, overwrite, answer, copyChecker);
					}
 				}
			} else { // this is a file
				int statusCode;
				if (dstRes == null) {
					statusCode = WebDAVStatusCodes.SC_CREATED;
				} else {
					dstRes.delete();
					statusCode = WebDAVStatusCodes.SC_NO_CONTENT; 
				}
				
				IOContext context = src.readContent();
 				dstRes = dstParent.createFile(dstName, context);
 				
 				MultiStatusResponse msr = new SimpleStatusResponse(dstRes, statusCode);
 	 			answer.addResponse(msr);
 			}		
 		} catch(WebDAVHrefException exDAV){
 			answer.addResponse(exDAV.toStatusResponse());
 			
 			res = false;
 		} catch (WebDAVException e) {
 			// Limit reached / catastrohpic error
 			res = false;
 		} catch(IOException exIO){
 			MultiStatusResponse exceptionResponse = new SimpleStatusResponse(src, WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR); 
 			answer.addResponse(exceptionResponse);
 			
 			res = false;
 		}
 		
 		return res;
 	}
 	
 	/**
 	 * Handles a MKCOL request
 	 * 
 	 * @param request The sent request
 	 * @param The answer to send to the client
 	 * @throws WebDAVException On errors, especially not finding 
 	 */
 	protected WebDAVAnswer mkcol(HttpServletRequest request) throws WebDAVException {
 		WebDAVAnswer answer;
		WebDAVPath path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
		WebDAVResource parentResource = resolveParent(path);
		
		String newFilename = path.asResolved().getFileName();
		if (parentResource.hasChild(newFilename)){
			answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_METHOD_NOT_ALLOWED);
		} else {
			parentResource.createCollection(newFilename);
			answer = new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_CREATED);
		}
		
		return answer;
 	}
 	
 	/**
 	 * Handles a WebDAV put request
 	 * 
 	 * @param request The client's request
 	 * @return The created resource
 	 * @throws WebDAVException On any errors
 	 * @throws IOException On errors when reading from/writing to the client
 	 */
 	protected WebDAVResource put(HttpServletRequest request) throws WebDAVException, IOException {
		WebDAVPath path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
		WebDAVResource parentResource = resolveParent(path);
		boolean overwrite = WebDAVUtils.readOverwriteHeader(request);
		String fileName = path.asResolved().getFileName();
		IOContext ioc = WebDAVUtils.getClientInputContext(request);
		WebDAVResource resource;
		
		if (parentResource.hasChild(fileName)){
			if (overwrite){
				resource = parentResource.resolvePath(path.resolveAllBut(1));
				
				if (resource.isCollection()) {
					throw new WebDAVResourceException(WebDAVStatusCodes.SC_METHOD_NOT_ALLOWED, resource);
				} else {
					resource.writeContent(ioc);
				}
			} else {
				throw new WebDAVException(WebDAVStatusCodes.SC_PRECONDITION_FAILED);
			}
		} else {
			resource = parentResource.createFile(fileName, ioc);
		}
		
		return resource;
 	}
 	
 	/**
 	 * Handles a DELETE request
 	 * 
 	 * @param request The sent request
 	 * @throws WebDAVException On any errors (path resolving, no permissions etc.)
 	 */
 	protected void delete(HttpServletRequest request) throws WebDAVException {
		WebDAVPath path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
		WebDAVResource resource = getRoot().resolvePath(path);
		resource.delete();
 	}
 	
 	/**
 	 * Handles a LOCK request.
 	 * 
 	 * @param request The client's request.
 	 * @return An answer to print out
 	 * @throws WebDAVException On any errors
 	 * @throws IOException On IO exceptions
 	 */
 	protected WebDAVAnswer lock(HttpServletRequest request) throws WebDAVException, IOException {
 		// Parse request document
 		Document reqDoc = WebDAVUtils.getRequestDocument(request);
 		if (reqDoc == null) {
 			// Refreshing a lock is not yet implemented
 			throw new WebDAVException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, "Refreshing locks is not yet supported");
 		}
 		boolean exclusive = getLockTypeByReqDoc(reqDoc);
 		
		int depth = WebDAVUtils.readDepthHeader(request);
		String token = WebDAVUtils.genLockToken();
		WebDAVPath path = WebDAVPathImpl.parse(resourcePathPrefix, request.getRequestURI());
		WebDAVResource parent = resolveParent(path);
		String fileName = path.asResolved().getFileName();
		WebDAVResource resource = null;
		int statusCode = WebDAVStatusCodes.SC_OK;
		if (fileName == null) { // Locking root?
			statusCode = WebDAVStatusCodes.SC_OK;
			resource = parent;
		} else if (parent.hasChild(fileName)) {
			statusCode = WebDAVStatusCodes.SC_OK;
			resource = parent.resolvePathElem(fileName);
		} else {
			statusCode = WebDAVStatusCodes.SC_CREATED;
			resource = parent.createFile(fileName, null);
		}
		
		if (resource.isCollection()) {
			if (depth == WebDAVConstants.DEPTH_INFINITY) {
				if ((lockLimit == NO_RECURSIVE) && (resource.isCollection())) {
					throw new WebDAVException(WebDAVStatusCodes.SC_FORBIDDEN, "Infinite depth requests are forbidden");
				}
			} else if (depth != WebDAVConstants.DEPTH_0) {
				throw new WebDAVException(WebDAVStatusCodes.SC_BAD_REQUEST, "Invalid depth value for a LOCK request");
			}
			boolean recursive = (depth == WebDAVConstants.DEPTH_INFINITY);
			Counter lc = new Counter(lockLimit);
			
			MultiStatusAnswer manswer = new MultiStatusAnswerImpl();
			// The set of successfully acquired locks
			Set<WebDAVResource> acquiredLocks = new HashSet<WebDAVResource>();
			
			boolean res = lock(resource, recursive, exclusive, token, lc, acquiredLocks, manswer);
			
			WebDAVAnswer answer;
			if (res) {
				answer = createSuccessfulLockAnswer(statusCode, exclusive, resource, recursive, token);
			} else {
				answer = manswer;
				
				for (WebDAVResource wdr : acquiredLocks) {
					try {
						wdr.unlock(token);
					} catch (WebDAVException wde) {
						// Catch silently
						logger.error("Error when unrolling a recursive lock", wde);
					}
				}
			}
			
			return answer;
		} else {
			resource.lock(exclusive, token);
			
			return createSuccessfulLockAnswer(statusCode, exclusive, resource, (depth == WebDAVConstants.DEPTH_INFINITY), token);
		}
 	}
 	
	/**
	 * @param resource The resource to lock
	 * @param recursive Whether to climb down the resource tree
	 * @param exclusive The type of the lock to acquire
	 * @param token The lock token
	 * @param lc The counter to enforce limits
	 * @param acquiredLocks The set to add resources that suceeded in acquiring locks
	 * @param manswer The MultiStatusAnswer to add failures to
	 * @return true iff locking was successful
	 */
	protected boolean lock(WebDAVResource resource, boolean recursive,
			boolean exclusive, String token, Counter lc,
			Set<WebDAVResource> acquiredLocks, MultiStatusAnswer manswer) {
		
		boolean res = true;
		
		try {
			lc.check();
			resource.lock(exclusive, token);
			acquiredLocks.add(resource);
			
			if (recursive && resource.isCollection()) {
				for (WebDAVResource childRes : resource.getChildren()) {
					res &= lock(childRes, recursive, exclusive, token, lc, acquiredLocks, manswer);
				}
			}
		} catch (WebDAVException wde) {
			WebDAVHrefException hrefEx;
			if (wde instanceof WebDAVHrefException) {
				hrefEx = (WebDAVHrefException) wde;
			} else {
				hrefEx = new WebDAVResourceException(wde.getStatusCode(), resource, wde.getMessage(), wde.getCause());
			}
			
			MultiStatusResponse response = SimpleStatusResponse.createFromWebDAVException(hrefEx);
			manswer.addResponse(response);
			res = false;
		}
		
		return res;
	}

	/**
 	 * Handles an UNLOCK request.
 	 * 
 	 * @param request The client's request.
 	 * @return An answer to print out
 	 */
 	protected WebDAVAnswer unlock(HttpServletRequest request) {
 		// Just simulated in this version
		return new SimpleWebDAVAnswer(WebDAVStatusCodes.SC_NO_CONTENT);
	}
 	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		logger.info("init() started.");
		
		super.init();
		
		// Kill bit to disable WebDAV
		killBit = Boolean.parseBoolean(getInitParameter(INIT_PARAMETER_KILLBIT));
		if (killBit) {
			logger.warn("WebDAV killbit activated");
		}
		
		// read resource path prefix from configuration and store it in the context
		resourcePathPrefix = getServletContext().getContextPath() + getInitParameter(INIT_PARAMETER_RESOURCE_PATH_PREFIX);
		if (resourcePathPrefix == null) {
			throw new ServletException("resource path prefix not set");
		}
		logger.info("ResourcePathPrefix: " + resourcePathPrefix);
		
		// Limits for reading and writing collections
		String wlStr = getInitParameter(INIT_PARAMETER_WRITE_LIMIT);
		writeLimit = (wlStr == null) ? NO_LIMIT : Integer.valueOf(wlStr);
		String rlStr = getInitParameter(INIT_PARAMETER_READ_LIMIT);
		readLimit = (rlStr == null) ? NO_LIMIT : Integer.valueOf(rlStr);
		String llStr = getInitParameter(INIT_PARAMETER_LOCK_LIMIT);
		lockLimit = (llStr == null) ? NO_LIMIT : Integer.valueOf(llStr);
		
		String maxFileSizeStr = getInitParameter(INIT_PARAMETER_MAX_FILE_SIZE);
		long maxFileSize = (maxFileSizeStr == null) ? WebDAVContext.NO_MAX_FILESIZE :
				Long.valueOf(maxFileSizeStr);
		
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		
		boolean evadeUmlautsFlag = Boolean.parseBoolean(getInitParameter(INIT_PARAMETER_EVADE_UMLAUTS));
		
		davContext = new OpenUSSWebDAVContext(wac, maxFileSize, evadeUmlautsFlag);
		
        logger.info(" init() done.");
	}
	
	/**
	 * @return The root resource
	 */
	protected RootResource getRoot() {
		return RootResource.getRoot(davContext, resourcePathPrefix);
	}
	
	/**
	 * Resolves the parent resource of a given one.
	 * 
	 * @param childPath The path of the child resource, completely unresolved
	 * @return The resolved parent resource
	 * @throws WebDAVException On errors when resolving the parent resource.
	 */
	protected WebDAVResource resolveParent(WebDAVPath childPath) throws WebDAVException {
		WebDAVPath parentPath = childPath.getParent();
		WebDAVResource parentResource;
		try {
			parentResource = getRoot().resolvePath(parentPath);
		} catch (WebDAVException e) {
			if (e.getStatusCode() == WebDAVStatusCodes.SC_NOT_FOUND) {
				e.setStatusCode(WebDAVStatusCodes.SC_CONFLICT);
			}
			
			throw e;
		}
		
		return parentResource;
	}
	
	/**
	 * @param statusCode The status code
	 * @param exclusive Whether the created lock was exclusive
	 * @param resource The resource the lock was granted on
	 * @param recursive Whether the lock was recursive
	 * @param token The used token
	 * @return The document to return to the client
	 */
	protected WebDAVAnswer createSuccessfulLockAnswer(int statusCode,
			boolean exclusive, WebDAVResource resource, boolean recursive,
			String token) {
		
		Document doc = WebDAVUtils.newDocument();
		
		Element prop = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_PROP);
		doc.appendChild(prop);

		Element lockdiscovery = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_LOCKDISCOVERY);
		prop.appendChild(lockdiscovery);
		
		Element activelock = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_ACTIVELOCK);
		lockdiscovery.appendChild(activelock);
		
		Element locktype = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_LOCKTYPE);
		activelock.appendChild(locktype);
		Element write = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_WRITE);
		locktype.appendChild(write);
		
		Element lockscope = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_LOCKSCOPE);
		activelock.appendChild(lockscope);
		Element lockscopeVal = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV,
					exclusive ? WebDAVConstants.XML_EXCLUSIVE : WebDAVConstants.XML_SHARED);
		lockscope.appendChild(lockscopeVal);
		
		Element depth = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_DEPTH);
		activelock.appendChild(depth);
		depth.appendChild(doc.createTextNode(recursive ? WebDAVConstants.DEPTH_INFINITY_STRING : WebDAVConstants.DEPTH_0_STRING));
		
		Element timeout = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_TIMEOUT);
		activelock.appendChild(timeout);
		timeout.appendChild(doc.createTextNode(WebDAVConstants.TIMEOUT_INFINITE));
		
		Element locktoken = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_LOCKTOKEN);
		activelock.appendChild(locktoken);
		Element tokenHref = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_HREF);
		locktoken.appendChild(tokenHref);
		tokenHref.appendChild(doc.createTextNode(WebDAVUtils.presentLockToken(token)));
		
		Element lockroot = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_LOCKROOT);
		activelock.appendChild(lockroot);
		Element rootHref = doc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_HREF);
		lockroot.appendChild(rootHref);
		rootHref.appendChild(doc.createTextNode(resource.getPath().toClientString()));
		
		XMLWebDAVAnswer res = new XMLWebDAVAnswer(statusCode, doc);
		
		res.addHeader(WebDAVConstants.HEADER_LOCK_TOKEN, WebDAVUtils.presentLockTokenHeader(token));
		
		return res;
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
	
	/**
	 * Parses the information contained in a lock request.
	 * 
	 * @param reqDoc The request document
	 * @return true iff an exclusive lock was requested
	 * @throws WebDAVException On invalid request documents
	 */
	protected static boolean getLockTypeByReqDoc(Document reqDoc) throws WebDAVException { 
		Node lockRequest = WebDAVUtils.getChildNode(reqDoc, WebDAVConstants.XML_LOCKINFO);
		Node lockScope = WebDAVUtils.getChildNode(lockRequest, WebDAVConstants.XML_LOCKSCOPE);
		NodeList lockScopeChildren = lockScope.getChildNodes();
		boolean exclusive = true;
		
		for (int i = 0;i < lockScopeChildren.getLength();i++) {
			Node lockNode = lockScopeChildren.item(i);
			
			if (WebDAVUtils.isDavElement(lockNode, WebDAVConstants.XML_SHARED)) {
				exclusive = false;
				break;
			}
		}
		
		return exclusive;
	}
	
	/**
	 * @param resource The resource to describe
	 * @return Information about a restricted set of properties of the resource that can be presented to users that are not allowed to read it.
	 */
	protected static PropertyResponse generateRestrictedProperties(
			WebDAVResource resource) {
		
		PropertyResponseImpl pr = new PropertyResponseImpl(resource.getPath().toClientString(), "This is a restricted view of this resource.");
		
		// Add just the resourcetype property
		Document tmpDoc = WebDAVUtils.newDocument();
		Element propElem = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_RESOURCETYPE);
		if (resource.isCollection()) {
			Element collectionNode = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_COLLECTION);
			propElem.appendChild(collectionNode);
		}
		XMLPropertyResponseNode xprn = new XMLPropertyResponseNode(propElem);
		pr.addProperty(WebDAVStatusCodes.SC_OK, xprn);

		
		return pr;
	}
	
	/**
 	 * Updates the response object by the given answer object and prints it
 	 * @param response the response object
 	 * @param answer the SimpleWebDAVAnswer object
 	 * @throws IOException
 	 */
 	public static void printAnswer(HttpServletResponse response, WebDAVAnswer answer) throws IOException{
 		String msg = answer.getMessage();
 		Map<String,String> xHeaders = answer.getXHeaders();
 		response.setStatus(answer.getStatusCode());
 		
 		if (xHeaders != null) {
	 		for (Entry<String,String> e : xHeaders.entrySet()) {
	 			response.addHeader(e.getKey(), e.getValue());
	 		}
 		}
 		
 		if (msg != null) {
 	 		String contentType = answer.getContentType();
 	 		response.setContentType(contentType + WebDAVConstants.MIMETYPE_ENCODING_SEP + "\"" + WebDAVConstants.DEFAULT_CHARSET.name() + "\"");
 		}
 		
 		OutputStream os = response.getOutputStream();
 		if (msg != null) {
 			OutputStreamWriter osw = new OutputStreamWriter(os, WebDAVConstants.DEFAULT_CHARSET);
 			osw.write(msg);
 			osw.close();
 		}
 		os.close();
 	}
	
	/**
	 * Helper class to enforce limits.
	 */
	protected static class Counter {
		protected long limit;
		protected long count;
		
		public Counter(long limit) {
			this.limit = limit;
			this.count = 0;
		}
		
		/**
		 * Checks another requests.
		 * 
		 * @throws WebDAVException Iff the limit is reached
		 */
		public void check() throws WebDAVException {
			count++;
			if ((limit == NO_LIMIT) || (limit == NO_RECURSIVE)) {
				return;
			}
			
			if (count > limit) {
				throw new WebDAVException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, "Read limit exceeded");
			}
		}
		
		public long getCount() {
			return this.count;
		}

		public long getLimit() {
			return limit;
		}
	}
	
	/**
	 * A class that prevents infinite recursive copying by
	 * <ul>
	 * <li>limiting the number of copy actions</li>
	 * <li>Storing all already touched resources</li>
	 * </ul>
	 */
	protected static class CopyChecker {
		protected Set<WebDAVResource> affectedResources;
		protected int limit;
		
		public CopyChecker(int limit) {
			this.limit = limit;
			affectedResources = new HashSet<WebDAVResource>();
		}
		
		/**
		 * Helper class that checks whether to copy further elements.
		 * The caller must ensure this gets called only once per input value.
		 * 
		 * @param wdr The resource that is handled.
		 * @param affectedResources The resources already handled
		 * @throws WebDAVException When the limit is reached or an already handled resource was touched.
		 */
		public void check(WebDAVResource wdr) throws WebDAVException {
			if (! affectedResources.add(wdr)) {
				logger.info("Found a copy in itself " + wdr);
				throw new WebDAVException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, "Recursive copy found");
			}
			
			if ((limit != NO_LIMIT) && (affectedResources.size() > limit)) {
				logger.info("WebDAV client reached write limit while working on " + wdr);
				throw new WebDAVException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, "Copy limit reached");
			}
		}
		
	}
}
