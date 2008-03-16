package org.openuss.webdav;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.openuss.webdav.WebDAVConstants.LockState;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An implementation of a WebDAV resource for simple backends.
 * 
 * It handles the following aspects:
 * 
 * <ul>
 * <li>Special character handling.</li>
 * <li>Ignoring third-party XML namespaces</li>
 * <li>Content type determination</li>
 * <li>Add regularly used properties to the answer of a PROPFIND.</li>
 * <li>Access checking.</li>
 * <li>Simulation of locking.</li>
 * </ul>
 * 
 * @see CollisionAvoidingSimpleWebDAVResource
 */
public abstract class SimpleWebDAVResource implements WebDAVResource {

	/**
	 * The path of this resource.
	 */
	protected final WebDAVPath path;
	
	protected final WebDAVContext context;

	
	/**
	 * Constructor. Subclasses typically set the corresponding backend object here.
	 * 
	 * @param wac The WebApplication
	 * @param path The path of this resource.
	 */
	protected SimpleWebDAVResource(WebDAVContext context, WebDAVPath path) {
		this.path = path.asFinalPath();
		this.context = context;
	}
		
	
	/* Publicly available security-relevant methods */
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getProperties(org.w3c.dom.Document)
	 */
	public final MultiStatusResponse getProperties(Document req)
			throws WebDAVResourceException {
		checkReadable();
		
		return getPropertiesImpl(req);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#updateProperties(org.w3c.dom.Document)
	 */
	public final MultiStatusResponse updateProperties(Document req) throws WebDAVResourceException {
		checkWritable();
		
		return updatePropertiesImpl(req);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#resolvePath(org.openuss.webdav.WebDAVPath)
	 */
	public final WebDAVResource resolvePath(WebDAVPath path) throws WebDAVHrefException {
		checkReadable();
		
		return resolvePathImpl(path);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#resolvePathElem(java.lang.String)
	 */
	public final WebDAVResource resolvePathElem(String elem) throws WebDAVHrefException {
		return resolvePath(getPath().concat(elem));
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getChildren()
	 */
	public final List<WebDAVResource> getChildren() throws WebDAVResourceException {
		checkReadable();
		
		return sortByName(getChildrenImpl());
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#hasChild(java.lang.String)
	 */
	public final boolean hasChild(String name) throws WebDAVResourceException {
		checkReadable();
		
		return hasChildImpl(sanitizeName(name));
	}

	/* (non-Javadoc)
	 * 
	 * @see org.openuss.webdav.WebDAVResource#createCollection()
	 */
	public final WebDAVResource createCollection(String name) throws WebDAVResourceException {
		checkCreate();
		checkFreeName(sanitizeName(name));
		
		return createCollectionImpl(name);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#createFile(java.lang.String, org.openuss.webdav.IOContext)
	 */
	public final WebDAVResource createFile(String name, IOContext ioc)
			throws WebDAVResourceException {
		String sname = sanitizeName(name);
		
		checkCreate();
		checkFreeName(sname);
		
		IOContext nioc = getExistingIOC(ioc);
		checkFileSize(nioc.getContentLength());
		
		return createFileImpl(sname, nioc);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#readContent()
	 */
	public final IOContext readContent() throws WebDAVResourceException, IOException {
		checkReadable();
		
		return readContentImpl();
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#writeContent(org.openuss.webdav.IOContext)
	 */
	public final void writeContent(IOContext ioc) throws WebDAVResourceException,
			IOException {
		checkWritable();
		checkFileSize(ioc.getContentLength());
		
		writeContentImpl(ioc);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#delete()
	 */
	public final void delete() throws WebDAVResourceException {
		checkDeletable();
		
		deleteImpl();
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getLockState()
	 */
	public final LockState getLockState() throws WebDAVException {
		checkReadable();
		
		return getLockStateImpl();
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#lock(boolean, java.lang.String)
	 */
	public final void lock(boolean exclusive, String token) throws WebDAVException {
		if (exclusive) {
			checkWritable();
		} else {
			checkReadable();
		}
		
		checkLockable(exclusive);
		lockImpl(exclusive, token);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#unlock(java.lang.String)
	 */
	public final void unlock(String token) throws WebDAVException  {
		checkReadable(); // Check only readability because token needs to be known anyway
		checkLockable();
		
		unlockImpl(token);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#supportsLocks(boolean)
	 */
	public /* not final */ boolean supportsLocks(boolean exclusive) {
		return true; // simulate it
	}


 

	
	
	
	
	
		



	/**
	 * @see WebDAVResource#getProperties(Document)
	 */
	protected MultiStatusResponse getPropertiesImpl(Document req)
		throws WebDAVResourceException {
		
		// Create result document
		PropertyResponseImpl pr = new PropertyResponseImpl(getHref());
		
		Set<String> reqProps = null; // The set of the requested properties
		boolean reqValues = true; // Request demands the values of the properties
		
		if (req == null) {
			// RFC 4918, 9.1 forth paragraph: null = allprop
			reqProps = null;
			reqValues = true;
		} else {
			// Acquire root node
			Node rootNode;
			try {
				rootNode = WebDAVUtils.getChildNode(req, WebDAVConstants.XML_PROPFIND);
			} catch (WebDAVException wde) {
				throw new WebDAVResourceException(wde.getStatusCode(), this, wde.getMessage(), wde.getCause());
			}
			
			// Acquire the real request node
			NodeList reqNodeList = rootNode.getChildNodes();
			
			boolean foundNode = false;
			for (int i = 0;i < reqNodeList.getLength();i++) {
				Node n = reqNodeList.item(i);
				
				if (WebDAVUtils.isDavElement(n, WebDAVConstants.XML_PROP)) {
					// Normal request
					foundNode = true;
					
					reqProps = new HashSet<String>();
					NodeList reqPropNodes = n.getChildNodes();
					for (int j = 0;j < reqPropNodes.getLength();j++) {
						Node reqPropNode = reqPropNodes.item(j);
						
						if (reqPropNode instanceof Element) {
							if (WebDAVConstants.NAMESPACE_WEBDAV.equals(reqPropNode.getNamespaceURI())) {
								reqProps.add(reqPropNode.getLocalName());
							} else {
								pr.addSimpleProperty(WebDAVStatusCodes.SC_NOT_FOUND, reqPropNode.getNamespaceURI(), reqPropNode.getLocalName(), null);
							}
						}
					}
					
					break;
				} else if (WebDAVUtils.isDavElement(n, WebDAVConstants.XML_PROPNAME)) {
					reqValues = false;
					
					foundNode = true;
					break;
				} else if (WebDAVUtils.isDavElement(n, WebDAVConstants.XML_ALLPROP)) {
					foundNode = true;				
					break;
				}
			}
			if (!foundNode) {
				throw new WebDAVResourceException(WebDAVStatusCodes.SC_BAD_REQUEST, this, "Did not find a valid request node."); 
			}
		}
		
		Map<String,String> values = simpleGetProperties(reqProps);
		
		if (values == null) {
			values = Collections.emptyMap();
		}
		
		// Found properties
		for (Entry<String,String> e : values.entrySet()) {
			pr.addSimpleProperty(WebDAVStatusCodes.SC_OK, e.getKey(), reqValues ? e.getValue() : null);
		}
		
		// Auto-add properties
		Set<String> autoAdded = autoAddProperties(reqProps, values.keySet(), pr, reqValues);
		
		// Non-existant properties
		Set<String> propsNotFound = disjunction(reqProps, values.keySet());
		for (String prop : propsNotFound) {
			if (! autoAdded.contains(prop)) {
				pr.addSimpleProperty(WebDAVStatusCodes.SC_NOT_FOUND, prop, null);
			}
		}
		
		return pr;
	}
	
	/**
	 * A simplified version of {@link #getProperties(Document)}.
	 * Like its brother, it is used to request document meta-data.
	 * 
	 * @param propNames The names of the requested properties. If null, all properties are requested.
	 * @return A map of found properties and their values.
	 */
	protected abstract Map<String,String> simpleGetProperties(Set<String> propNames);
	
	/**
	 * @see WebDAVResource#updateProperties(Document)
	 */
	protected MultiStatusResponse updatePropertiesImpl(Document req)
		throws WebDAVResourceException {
		// This implementation just returns a 403 forbidden message for all properties after rudimentary parsing.
		
		if (req == null) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_BAD_REQUEST, this, "Empty body in a PROPPATCH request");
		}
		
		Node updateNode = null;
		NodeList rootNodeList = req.getChildNodes();
		for (int i = 0;i < rootNodeList.getLength();i++) {
			Node n = rootNodeList.item(i);
			
			if (WebDAVUtils.isDavElement(n, WebDAVConstants.XML_PROPERTYUPDATE)) {
				updateNode = n;
				break;
			}
		}
		if (updateNode == null) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_BAD_REQUEST, this, "Did not find " + WebDAVConstants.XML_PROPERTYUPDATE + " element.");
		}
		
		PropertyResponseImpl pr = new PropertyResponseImpl(getHref());
		NodeList setdelNodeList = updateNode.getChildNodes();
		for (int i = 0;i < setdelNodeList.getLength();i++) {
			Node n = rootNodeList.item(i);
			
			if (WebDAVUtils.isDavElement(n, WebDAVConstants.XML_SET) || WebDAVUtils.isDavElement(n, WebDAVConstants.XML_REMOVE)) {
				NodeList tmpNodeList = n.getChildNodes();
				for (int j = 0;j < tmpNodeList.getLength();j++) {
					Node n2 = tmpNodeList.item(j);
					
					pr.addSimpleProperty(WebDAVStatusCodes.SC_FORBIDDEN, n2.getNamespaceURI(), n2.getNodeName());
				}
			}
		}
		
		return pr;
	}
	
	/**
	 * @see WebDAVResource#resolvePath(WebDAVPath)
	 */
	protected WebDAVResource resolvePathImpl(WebDAVPath path) throws WebDAVHrefException {
		String nextName = path.getNextName();
		
		// Is resolved
		if (nextName == null) {
			return this;
		}
		
		WebDAVPath nextPath = path.next();
		WebDAVResource nextRes = getChild(nextName, nextPath);
		
		if (nextRes == null) {
			// Cannot find sub-element
			if (nextPath.isResolved()) {
				throw new WebDAVPathException(WebDAVStatusCodes.SC_NOT_FOUND, nextPath);				
			} else {
				// Intermediary resource not found
				throw new WebDAVPathException(WebDAVStatusCodes.SC_CONFLICT, nextPath.asFinalPath());
			}
		}
		
		return nextRes.resolvePath(nextPath);
	}
	
	/**
	 * @param name The name of the searched sub-element.
	 * @param path The path of the WebDAVResource to create.
	 * @return The sub-element in this resource bearing the name name or null if it can not be found.
	 */
	protected abstract WebDAVResource getChild(String name, WebDAVPath path);
	
	/**
	 * The potentially unsorted list of children.
	 * @see WebDAVResource#getChildren()
	 */
	protected abstract Set<WebDAVResource> getChildrenImpl() throws WebDAVResourceException;
	
	/**
	 * @see WebDAVResource#hasChild(String)
	 */
	protected abstract boolean hasChildImpl(String name) throws WebDAVResourceException;
	
	/**
	 * Implementation of createCollection that does not check preconditions.
	 * 
	 * @param name The name of the collection to create.
	 * @throws WebDAVResourceException On special errors. This shouldn't be thrown under normal circumstances.
	 */
	protected abstract WebDAVResource createCollectionImpl(String name) throws WebDAVResourceException;

	/**
	 * Creates a new file in this collection without having to check preconditions.
	 * Implementations must call {@link #checkFileSize(long)} as soon as they know the real size.
	 * 
	 * @param name The name of the file to create.
	 * @param ioc The data to write. This may be a NullIOContext. Guaranteed not to be null.
	 * @return A WebDAVResource pointing to the created file.
	 */
	protected abstract WebDAVResource createFileImpl(String name, IOContext ioc) throws WebDAVResourceException;

	/**
	 * Deletes the resource without checking whether the user is allowed to delete. 
	 * 
	 * @throws WebDAVException On further errors.
	 */
	abstract protected void deleteImpl() throws WebDAVResourceException;

	/**
	 * Implementation of readContent() that does not need to check the user's permissions.
	 * 
	 * @see #readContent()
	 */
	protected abstract IOContext readContentImpl() throws WebDAVResourceException, IOException;

	/**
	 * @return A virtual IOContext representing this element's children.
	 * @throws WebDAVResourceException On access errors
	 */
	protected IOContext readCollectionContent() throws WebDAVResourceException {
		assert(isCollection());
		
		Document doc = WebDAVUtils.newDocument();
		
		Element html = doc.createElementNS(WebDAVConstants.NAMESPACE_HTML, WebDAVConstants.XHTML_HTML);
		doc.appendChild(html);
		
		// From here on without namespace to allow off-the-shelf browsers to parse i
		
		// Header
		Element head = doc.createElement(WebDAVConstants.XHTML_HEAD);
		html.appendChild(head);
		
		Element title = doc.createElement(WebDAVConstants.XHTML_TITLE);
		title.setTextContent("OpenUSS - " + getPath().getFileName());
		head.appendChild(title);
		
		// Body
		Element body = doc.createElement(WebDAVConstants.XHTML_BODY);
		html.appendChild(body);
		
		Element header = doc.createElement(WebDAVConstants.XHTML_HEADER);
		header.setTextContent(getPath().getResolved());
		body.appendChild(header);
		
		Element list = doc.createElement(WebDAVConstants.XHTML_UNORDERED_LIST);
		body.appendChild(list);
		
		Collection<WebDAVResource> children = getChildren();
		for (WebDAVResource c : children) {
			Element li = doc.createElement(WebDAVConstants.XHTML_LIST_ELEM);
			list.appendChild(li);
			
			Element link = doc.createElement(WebDAVConstants.XHTML_LINK);
			link.setAttribute(WebDAVConstants.XHTML_HREF, c.getPath().getResolved());
			link.setTextContent(c.getPath().getFileName());
			li.appendChild(link);
		}
		
		String docStr = WebDAVUtils.documentToString(doc);
		byte[] bar = docStr.getBytes(WebDAVConstants.DEFAULT_CHARSET);
		ByteArrayInputStream bais = new ByteArrayInputStream(bar);
		
		IOContextImpl ioc = new IOContextImpl();
		ioc.setContentType(WebDAVConstants.MIMETYPE_HTML +
					WebDAVConstants.MIMETYPE_ENCODING_SEP + WebDAVConstants.DEFAULT_CHARSET.name());
		ioc.setInputStream(bais);
		
		return ioc;
	}

	/**
	 * Implementation of writeContent() that does not need to check the user's permissions.
	 * Implementations must call {@link #checkFileSize(long)} as soon as they know the real size.
	 * 
	 * @see #writeContent()
	 */
	protected abstract void writeContentImpl(IOContext ioc) throws WebDAVResourceException;
	
	/**
	 * @return The WebApplicationContext that allows to retrieve beans.
	 */
	protected WebApplicationContext getWAC() {
		return this.context.getWAC();
	}

	/**
	 * @param ioc The IOContext to validate.
	 * @return ioc or a virtual IOContext, iff ioc is null
	 */
	protected static IOContext getExistingIOC(IOContext ioc) {
		if (ioc == null) {
			return new NullIOContext(null);
		} else {
			return ioc;
		}
	}
	
	/**
	 * @see {@link WebDAVResource#getLockState()}
	 */
	protected LockState getLockStateImpl() {
		// Simulation
		return LockState.UNLOCKED;
	}
	
	/**
	 * @see {@link WebDAVResource#lock(boolean, String)}
	 */
	protected void lockImpl(boolean exclusive, String token) {
		; // We just simulate locking
	}
	
	/**
	 * @see {@link WebDAVResource#unlock(String)}
	 */
	protected void unlockImpl(String token) {
		; // We just simulate unlocking
	}

	public WebDAVContext getContext() {
		return context;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#isDeletable()
	 */
	public boolean isDeletable() {
		return isWritable();
	}
	
	/**
	 * Checks the precondition that the resource may be read.
	 *
	 * @see #checkExists()
	 * @throws WebDAVException An exception if the resource is not readable. 
	 */
	private void checkReadable() throws WebDAVResourceException {
		if (! isReadable()) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_FORBIDDEN, this);
		}
	}
	
	/**
	 * Checks the precondition that the resource may be written.
	 *
	 * @throws WebDAVException An exception if the resource is not writable. 
	 */
	private void checkWritable() throws WebDAVResourceException {
		if (! isWritable()) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_FORBIDDEN, this);
		}
	}
	
	/**
	 * Checks the precondition that the resource may be deleted.
	 *
	 * @throws WebDAVException An exception if the resource may not be deleted. 
	 */
	private void checkDeletable() throws WebDAVResourceException {
		if (! isDeletable()) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_FORBIDDEN, this);
		}
	}
	
	/**
	 * @throws WebDAVResourceException
	 */
	private void checkCreate() throws WebDAVResourceException {
		if (!isWritable()) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_FORBIDDEN, this);
		}
		
		if (!isCollection()) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_CONFLICT, this, "Can not create a sub-resource of a file");
		}
	}
	
	/**
	 * Checks that there is no sub-resource with the name name.
	 * 
	 * @param name The name to check.
	 * @throws WebDAVResourceExecption A Conflict exception.
	 */
	private void checkFreeName(String name) throws WebDAVResourceException {
		if (hasChild(name)) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_METHOD_NOT_ALLOWED, this, "Name \"" + name + "\" already mapped!");
		}
	}
	
	/**
	 * Checks that this resource supports any type of locks
	 * 
	 * @throws WebDAVResourceExecption If locks are not supported at all 
	 */
	private void checkLockable() throws WebDAVResourceException {
		if (! (supportsLocks(false) || supportsLocks(true))) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_METHOD_NOT_ALLOWED, this, "This resource does not support locking"); 
		}
	}
	
	/**
	 * Checks that this resource supports locks
	 * 
	 * @param exclusive The type of lock to check
	 * @throws WebDAVResourceExecption If the specified locking type is not supported 
	 */
	private void checkLockable(boolean exclusive) throws WebDAVResourceException {
		if (! supportsLocks(exclusive)) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_METHOD_NOT_ALLOWED, this, "This resource does not support locking"); 
		}
	}
	
	/**
	 * @param size The size to check.
	 * @throws WebDAVResourceException If the specified size exceeds the limitations of this server.
	 */
	protected void checkFileSize(long size) throws WebDAVResourceException {
		if (! (getContext().checkMaxFileSize(size))) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_UNSUPPORTED_MEDIA_TYPE, this, "Maximum allowed file size is " + getContext().getMaxFileSize());
		}
	}
	
	/**
	 * Perform a transformation on a name that is going to be created/checked.
	 * 
	 * @param origName The original name supplied by the backend
	 * @return The sanitized version
	 */
	protected String sanitizeName(String origName) {
		if (origName.equals("")) {
			return "_";
		}
		return getContext().evadeUmlauts(origName);
	}
	
	/**
	 * Auto-adds some properties to a PROPFIND answer object
	 * 
	 * @param reqProps The set of requested properties in the DAV namespace
	 * @param providedProps The names of the properties already provided by the implementation
	 * @param pr The object to add the responses to.
	 * @param reqValues Whether values are requested, too, or only the names of the properties
	 * @return The set of the local name of the added properties in the DAV namespace
	 */
	protected Set<String> autoAddProperties(Set<String> reqProps, Set<String> providedProps,
			PropertyResponseImpl pr, boolean reqValues) {
		Set<String> autoAdded = new TreeSet<String>();
		
		// getcontenttype
		Document tmpDoc = WebDAVUtils.newDocument();
		if (((reqProps == null) || reqProps.contains(WebDAVConstants.XML_GETCONTENTTYPE))
				&& (!providedProps.contains(WebDAVConstants.XML_GETCONTENTTYPE))) {
			pr.addSimpleProperty(WebDAVStatusCodes.SC_OK, WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_GETCONTENTTYPE, reqValues ? getContentType() : null);
			autoAdded.add(WebDAVConstants.XML_GETCONTENTTYPE);
		}
		// resourcetype
		if (((reqProps == null) || reqProps.contains(WebDAVConstants.XML_RESOURCETYPE))
				&& (!providedProps.contains(WebDAVConstants.XML_RESOURCETYPE))) {
			
			Element propElem = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_RESOURCETYPE);
			if (isCollection() && reqValues) {
				Element collectionNode = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_COLLECTION);
				propElem.appendChild(collectionNode);
			}
			
			XMLPropertyResponseNode xprn = new XMLPropertyResponseNode(propElem);
			pr.addProperty(WebDAVStatusCodes.SC_OK, xprn);
			autoAdded.add(WebDAVConstants.XML_RESOURCETYPE);
		}
		if (((reqProps == null) || reqProps.contains(WebDAVConstants.XML_SUPPORTEDLOCK))
				&& (!providedProps.contains(WebDAVConstants.XML_SUPPORTEDLOCK))) {
			
			Element supportedLockElem = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_SUPPORTEDLOCK);
			if (reqValues) {
				if (supportsLocks(true)) {
					Element exLockElem = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_LOCKENTRY); 
					supportedLockElem.appendChild(exLockElem);
					
					Element lockscopeElem = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_LOCKSCOPE);
					exLockElem.appendChild(lockscopeElem);
					lockscopeElem.appendChild(tmpDoc.createElementNS(
							WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_EXCLUSIVE));
					
					Element locktypeElem = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_LOCKTYPE);
					exLockElem.appendChild(locktypeElem);
					locktypeElem.appendChild(tmpDoc.createElementNS(
							WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_WRITE));
				}
				
				if (supportsLocks(false)) {
					Element shLockElem = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_LOCKENTRY); 
					supportedLockElem.appendChild(shLockElem);
					
					Element lockscopeElem = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_LOCKSCOPE);
					shLockElem.appendChild(lockscopeElem);
					lockscopeElem.appendChild(tmpDoc.createElementNS(
							WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_SHARED));
					
					Element locktypeElem = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_LOCKTYPE);
					shLockElem.appendChild(locktypeElem);
					locktypeElem.appendChild(tmpDoc.createElementNS(
							WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_WRITE));
				}
			}
			
			XMLPropertyResponseNode xprn = new XMLPropertyResponseNode(supportedLockElem);
			pr.addProperty(WebDAVStatusCodes.SC_OK, xprn);
			autoAdded.add(WebDAVConstants.XML_RESOURCETYPE);
		}
		
		return autoAdded;
	}
	
	/**
	 * Sorts a set of WebDAVResources. This set will not be modified. A new collection will be returned. 
	 * @param children the unsorted set of WebDAVResources
	 * @return A collection of WebDAVResources sorted by name
	 */
	public static List<WebDAVResource> sortByName(Set<WebDAVResource> children) {
		ArrayList<WebDAVResource> sortedCollection = new ArrayList<WebDAVResource>(children);
		Collections.sort(sortedCollection, getNameComparator());
		return sortedCollection;
	}
	
	/**
	 * @return A comparator for WebDAVResources which compares by name
	 */
	public static Comparator<WebDAVResource> getNameComparator() {
		return new Comparator<WebDAVResource>(){
			public int compare(WebDAVResource r1, WebDAVResource r2) {
				return r1.getName().compareTo(r2.getName());
			}
		};
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getContentType()
	 */
	public String getContentType() {
		return isCollection() ? WebDAVConstants.MIMETYPE_DIRECTORY : MimeType.getMimeTypeByExt(getPath().getFileExt());
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getPath()
	 */
	public WebDAVPath getPath() {
		return path;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getName()
	 */
	public String getName() {
		return getPath().getFileName();
	}
	
	/**
	 * @return The path of this resource, ready to be presented to the client
	 */
	public String getHref() {
		return getPath().toClientString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + ": " + getPath().getResolved();
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public abstract boolean equals(Object o);
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public abstract int hashCode();
	
	/**
	 * @param origSet
	 * @param returnedSet
	 * @return A set of all elements that are existant in origSet, but not returnSet.
	 * 		An empty set, if origSet is null.
	 */
	private static Set<String> disjunction(Set<String> origSet, Set<String> returnedSet) {
		Set<String> res = new HashSet<String>();
		
		if (origSet != null) {
			for (String s : origSet) {
				if (! returnedSet.contains(s)) {
					res.add(s);
				}
			}
		}
		
		return res;
	}
}
