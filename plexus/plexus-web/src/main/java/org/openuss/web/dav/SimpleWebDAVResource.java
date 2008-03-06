package org.openuss.web.dav;

import java.io.IOException;
import java.util.AbstractCollection; // TODO readCollection()
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.openuss.webdav.IOContext;
import org.openuss.webdav.MultiStatusResponse;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVException;
import org.openuss.webdav.WebDAVHrefException;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVPathException;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.openuss.webdav.WebDAVStatusCodes;
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
 * <li>Collision avoidance. This is achieved by adding an id specifier.</li>
 * <li>Ignoring third-party XML namespaces</li>
 * <li>Content type determination</li>
 * <li>Add regularly used properties to the answer of a PROPFIND.</li>
 * </ul>
 */
public abstract class SimpleWebDAVResource implements WebDAVResource {
	/**
	 * Unspecified id (Wasn't contained in the original query).
	 */
	protected static final long ID_NONE = -1;
	/**
	 * Separator for path specification including an id.
	 */
	protected static final String ID_SEP = "-";
	/**
	 * Replacement for invalid characters.
	 */
	protected static final String INVALID_CHAR_REPLACEMENT = "_";
	
	/**
	 * The Spring bean factory.
	 */
	private final WebApplicationContext wac;
	/**
	 * The path of this resource.
	 */
	protected final WebDAVPath path;
	/**
	 * The internal id of this object. ID_PRELIMINARY if this object does not exist.
	 */
	protected final long id;
	
	
	/**
	 * Constructor. Subclasses typically set the corresponding backend object here.
	 * 
	 * @param wac The WebApplication
	 * @param path The path of this resource.
	 * @param id The internal Id of this resource
	 */
	protected SimpleWebDAVResource(WebApplicationContext wac, WebDAVPath path, long id) {
		this.wac = wac;
		this.path = path.asFinalPath();
		this.id = id;
	}
	
	/**
	 * @return The id of this resource or ID_PRELIMINARY if it does not yet exist.
	 */
	public long getId() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getContentType()
	 */
	public String getContentType() {
		return isCollection() ? MimeType.DIRECTORY_MIMETYPE : MimeType.getMimeType(getPath().getFileExt());
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getPath()
	 */
	public WebDAVPath getPath() {
		return path;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getProperties(org.w3c.dom.Document)
	 */
	public MultiStatusResponse getProperties(Document req)
			throws WebDAVResourceException {
		checkReadable();
		
		// Acquire root node
		Node rootNode = null;
		
		NodeList rootNodeList = req.getChildNodes();
		for (int i = 0;i < rootNodeList.getLength();i++) {
			Node n = rootNodeList.item(i);
			
			if (WebDAVUtils.isDavElement(n, WebDAVConstants.XML_PROPFIND)) {
				rootNode = n;
				break;
			}
		}
		if (rootNode == null) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_BAD_REQUEST, this, "Expected root node " + WebDAVConstants.XML_PROPFIND); 
		}
		
		// Acquire the real request node
		NodeList reqNodeList = rootNode.getChildNodes();
		Set<String> reqProps = null;
		boolean reqValues = true; // Request demands the values of the properties
		
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
					
					if ((reqPropNode instanceof Element) &&
							(WebDAVConstants.NAMESPACE_WEBDAV_URI.equals(reqPropNode.getNamespaceURI()))) {
						reqProps.add(reqPropNode.getLocalName());
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
		
		Map<String,String> values = simpleGetProperties(reqProps);
		
		// Create result document
		PropertyResponseImpl pr = new PropertyResponseImpl(getHref());
		// Found properties
		for (String k : values.keySet()) {
			pr.addSimpleProperty(WebDAVStatusCodes.SC_OK, k, reqValues ? values.get(k) : null);
		}
		
		// Auto-add properties, if requested
		Set<String> autoAdded = new TreeSet<String>();
		
		// getcontenttype
		if (((reqProps == null) || reqProps.contains(WebDAVConstants.XML_GETCONTENTTYPE))
				&& (!values.containsKey(WebDAVConstants.XML_GETCONTENTTYPE))) {
			pr.addSimpleProperty(WebDAVStatusCodes.SC_OK, WebDAVConstants.NAMESPACE_WEBDAV_URI, WebDAVConstants.XML_GETCONTENTTYPE, reqValues ? getContentType() : null);
			autoAdded.add(WebDAVConstants.XML_GETCONTENTTYPE);
		}
		// resourcetype
		if (((reqProps == null) || reqProps.contains(WebDAVConstants.XML_RESOURCETYPE))
				&& (!values.containsKey(WebDAVConstants.XML_RESOURCETYPE))) {
			Document tmpDoc = WebDAVUtils.newDocument();
			
			Element propElem = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV_URI, WebDAVConstants.XML_RESOURCETYPE);
			if (isCollection()) {
				Element collectioNode = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV_URI, WebDAVConstants.XML_COLLECTION);
				propElem.appendChild(collectioNode);
			}
			XMLPropertyResponseNode xprn = new XMLPropertyResponseNode(propElem);
			pr.addProperty(WebDAVStatusCodes.SC_OK, xprn);
			autoAdded.add(WebDAVConstants.XML_RESOURCETYPE);
		}
		
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
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#updateProperties(org.w3c.dom.Document)
	 */
	public MultiStatusResponse updateProperties(Document req)
		throws WebDAVResourceException {
		// This implementation just returns a 403 forbidden message for all properties after rudimentary parsing.
		
		checkWritable();
		
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

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#resolvePath(org.openuss.webdav.WebDAVPath)
	 */
	public WebDAVResource resolvePath(WebDAVPath path) throws WebDAVHrefException {
		String nextName = path.getNextName();
		
		// Is resolved
		if (nextName == null) {
			return this;
		}
		
		ParsedName pn = parseName(nextName);
		WebDAVPath nextPath = path.next();
		WebDAVResource nextRes = getChild(pn.id, pn.name, nextPath);
		
		if (nextRes == null) {
			// Cannot find sub-element
			throw new WebDAVPathException(WebDAVStatusCodes.SC_NOT_FOUND, nextPath);
		}
		
		return nextRes.resolvePath(nextPath);
	}
	
	/**
	 * @param id The Id of the child to resolve or ID_NONE if the name has to be resolved.
	 * @param name The name of the resource. This should only be used if id == ID_NONE || id == ID_PRELIMARY.
	 * @param name The name of the resource. This should only be used if id == ID_NONE.
	 * @param path The WebDAVPath representing the full address of the resource to resolve.
	 * @return A WebDAVResource representing the child.
	 * 		If the id is ID_NONE and the name cannot be found, an implementation should return a new WebDAVResource object whose id is ID_PRELIMINARY.
	 * 		If this is not possible, return null.
	 */
	protected abstract WebDAVResource getChild(long id, String name, WebDAVPath path);
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getChildren()
	 */
	public Set<WebDAVResource> getChildren() {
		Map<Long, String> rawChildNames = getRawChildNames();
		
		if (rawChildNames == null) {
			return null;
		}
		
		// Gather all duplicate names 
		Set<String> allNames = new HashSet<String>();
		Set<String> ambiguousNames = new HashSet<String>();
		for (String name : rawChildNames.values()) {
			String sname = sanitizeName(name);
			
			if (!allNames.add(sname)) {
				ambiguousNames.add(sname);
			}
		}
		
		Set<WebDAVResource> res = new HashSet<WebDAVResource>();
		for (long id : rawChildNames.keySet()) {
			String rawName = rawChildNames.get(id);
			String sanName = sanitizeName(rawName);
			String fullName = genName(sanName, id, (sanName != rawName) || ambiguousNames.contains(sanName));
			
			WebDAVPath childPath = path.concat(fullName);
			WebDAVResource childRes = getChild(id, sanName, childPath);
			
			res.add(childRes);
		}
		
		return res;
	}
	
	/**
	 * Return child names. This method can assume that it is only called on collections.
	 * 
	 * @return The map of IDs to the names of the resources in this collection or null if this is not a collection.
	 * 			If you can ensure that the names are unique, any key set will do (1,2,3,4,... is a suggesting choice)
	 */
	protected abstract Map<Long,String> getRawChildNames();
	
	/**
	 * @return true if the current user is allowed to read the resource, otherwise false.
	 * 				Implementations may return true even if the ressource does not exist.
	 */
	public abstract boolean isReadable();
	
	/**
	 * @return true iff the current user is allowed to write to the resource.
	 * 		For collections, that means the creation of new objects is allowed.
	 */
	public abstract boolean isWritable();
	
	/**
	 * @return true if the current user may delete this resource.
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
	protected void checkReadable() throws WebDAVResourceException {
		if (! isReadable()) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_FORBIDDEN, this);
		}
	}
	
	/**
	 * Checks the precondition that the resource may be written.
	 *
	 * @throws WebDAVException An exception if the resource is not writable. 
	 */
	protected void checkWritable() throws WebDAVResourceException {
		if (! isWritable()) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_FORBIDDEN, this);
		}
	}
	
	/**
	 * Checks the precondition that the resource may be deleted.
	 *
	 * @throws WebDAVException An exception if the resource may not be deleted. 
	 */
	protected void checkDeletable() throws WebDAVResourceException {
		if (! isDeletable()) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_FORBIDDEN, this);
		}
	}
	
	/**
	 * @throws WebDAVResourceException
	 */
	protected void checkCreate() throws WebDAVResourceException {
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
	protected void checkFreeName(String name) throws WebDAVResourceException {
		if (hasChild(name)) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_CONFLICT, this, "Name \"" + name + "\" already mapped!");
		}
	}
		
	/**
	 * @return The path of this resource, ready to be presented to the client
	 */
	public String getHref() {
		return getPath().toClientString();
	}
	
	/* (non-Javadoc)
	 * 
	 * @see org.openuss.webdav.WebDAVResource#createCollection()
	 */
	public WebDAVResource createCollection(String name) throws WebDAVResourceException {
		checkCreate();
		
		checkFreeName(name);
		
		name = sanitizeName(name);
		
		return createCollection(name);
	}
	
	/**
	 * Implementation of createCollection that does not check preconditions.
	 * 
	 * @param name The name of the collection to create.
	 * @throws WebDAVResourceException On special errors. This shouldn't be thrown under normal circumstances.
	 */
	protected abstract void createCollectionImpl(String name) throws WebDAVResourceException;

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#createFile(java.lang.String)
	 */
	public WebDAVResource createFile(String name)
			throws WebDAVResourceException {
		checkCreate();
		
		name = sanitizeName(name);
		
		checkFreeName(name);
		
		return createFileImpl(name);
	}
	
	/**
	 * Creates a new file in this collection without having to check preconditions.
	 * 
	 * @param name The name of the file to create.
	 * @return A WebDAVResource pointing to the created file.
	 */
	protected abstract WebDAVResource createFileImpl(String name) throws WebDAVResourceException;

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#hasChild(java.lang.String)
	 */
	public boolean hasChild(String name) {
		name = sanitizeName(name);
		name = parseName(name).name; // Ensure no future conflicts
		
		for (String cn : getRawChildNames().values()) {
			cn = sanitizeName(cn);
			
			if (name.equals(cn)) {
				return true;
			}
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#delete()
	 */
	public void delete() throws WebDAVResourceException {
		checkDeletable();
		
		deleteImpl();
	}
	
	/**
	 * Deletes the resource without checking whether the user is allowed to delete. 
	 * 
	 * @throws WebDAVException On further errors.
	 */
	abstract protected void deleteImpl() throws WebDAVResourceException;

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#readContent()
	 */
	public IOContext readContent() throws WebDAVResourceException, IOException {
		checkReadable();
		
		return readContentImpl();
	}
	
	/**
	 * Implementation of readContent() that does not need to check the user's permissions.
	 * 
	 * @see #readContent()
	 */
	protected abstract IOContext readContentImpl() throws WebDAVResourceException, IOException;

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#writeContent(org.openuss.webdav.IOContext)
	 */
	public void writeContent(IOContext ioc) throws WebDAVResourceException,
			IOException {
		checkWritable();
		
		writeContentImpl(ioc);
	}
	
	/**
	 * Implementation of writeContent() that does not need to check the user's permissions.
	 * 
	 * @see #writeContent()
	 */
	protected abstract void writeContentImpl(IOContext ioc) throws WebDAVResourceException;
	
	/**
	 * @return The WebApplicationContext that allows to retrieve beans.
	 */
	protected WebApplicationContext getWAC() {
		return this.wac;
	}
	
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
	
	/**
	 * @param s The name as supplied by the client (after decoding)
	 * @return A ParsedName struct. Depending on the input string, it may or may not contain an id.
	 */
	private static ParsedName parseName(String s) {
		for (int p = s.length() - 1;p >= 0;p--) {
			if (!Character.isDigit(s.charAt(p))) {
				p = p + 1 - ID_SEP.length();
				
				if (p < 0) {
					p = 0;
				}
				
				if ((s.substring(p, p + ID_SEP.length()).equals(ID_SEP)) && (p + ID_SEP.length() < s.length())) {
					// Found an id
					long id = Long.parseLong(s.substring(p + ID_SEP.length()));
					
					return new ParsedName(id, s);
				}
				
				break;
			}
		}
		
		return new ParsedName(s);
	}
	private static class ParsedName {
		/**
		 * The id extracted from the original name.
		 */
		public final long id;
		/**
		 * The rest of the file name-
		 */
		public final String name;
		
		public ParsedName(String name) {
			this(ID_NONE, name);
		}
		public ParsedName(long id, String name) {
			this.id = id;
			this.name = name;
		}
		
		/**
		 * @return true iff the ID stored here is a valid one.
		 */
		public boolean hasId() {
			return (id != ID_NONE);
		}
	}
	
	/**
	 * Sanitize invalid path specifications.
	 * 
	 * @param origName The original name, possibly containing special characters.
	 * @return The sanitized name or origName, iff no sanitization was necessary.
	 */
	private static String sanitizeName(String origName) { 
		if (origName.indexOf(WebDAVPath.PATH_SEP) != -1) {
			return origName.replace(WebDAVPath.PATH_SEP, INVALID_CHAR_REPLACEMENT);
		} else if (origName.equals("")) {
			return INVALID_CHAR_REPLACEMENT;
		}
		
		return origName;
	}

	/**
	 * Generates a name that optionally contains further information so that it can be identified by #parseName.
	 *  
	 * @param name The sanitized name.
	 * @param id The id of the element represented.
	 * @param force Whether to force appending the id (for example because the name is already present)
	 * @return A string that may contain the id.
	 */
	private static String genName(String name, long id, boolean force) {
		if (force || parseName(name).hasId()) {
			name += ID_SEP + id;
		}
		
		return name;
	}
}
