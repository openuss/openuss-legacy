package org.openuss.web.dav;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openuss.webdav.MultiStatusResponse;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVException;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;
import org.openuss.webdav.WebDAVStatusCodes;
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
 * </ul>
 */
public abstract class SimpleWebDAVResource implements WebDAVResource {
	private static final Logger logger = Logger.getLogger(SimpleWebDAVResource.class);
	/**
	 * Unspecified object id.
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
	 * A simplified version of {@link #getProperties(Document)}.
	 * Like its brother, it is used to request document meta-data.
	 * 
	 * @param propNames The names of the requested properties. If null, all properties are requested.
	 * @return A map of found properties and their values.
	 */
	protected abstract Map<String,String> simpleGetProperties(Set<String> propNames);
	/**
	 * The path of this resource.
	 */
	protected WebDAVPath path;
	
	/**
	 * Constructor. Subclasses typically set the corresponding backend object here.
	 * 
	 * @param path The path of this resource.
	 */
	protected SimpleWebDAVResource(WebDAVPath path) {
		this.path = path;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getProperties(org.w3c.dom.Document)
	 */
	public MultiStatusResponse getProperties(Document req)
			throws WebDAVResourceException {
		checkExists();
		
		// Acquire root node
		Node rootNode = null;
		
		NodeList rootNodeList = req.getChildNodes();
		for (int i = 0;i < rootNodeList.getLength();i++) {
			Node n = rootNodeList.item(i);
			
			if ((n instanceof Element) && WebDAVConstants.XML_PROPFIND.equals(rootNode.getNodeName())) {
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
			if (!(n instanceof Element)) {
				continue;
			}
			
			String localName = n.getNodeName();
			
			if (localName.equals(WebDAVConstants.XML_PROP)) {
				// Normal request
				foundNode = true;
				
				reqProps = new HashSet<String>();
				NodeList reqPropNodes = n.getChildNodes();
				for (int j = 0;j < reqPropNodes.getLength();j++) {
					Node reqPropNode = reqPropNodes.item(i);
					
					if (reqPropNode instanceof Element) {
						reqProps.add(reqPropNode.getNodeName());
					}
				}
				
				break;
			} else if (localName.equals(WebDAVConstants.XML_PROPNAME)) {
				reqValues = false;
				
				foundNode = true;
				break;
			} else if (localName.equals(WebDAVConstants.XML_ALLPROP)) {
				foundNode = true;				
				break;
			}
		}
		if (!foundNode) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_BAD_REQUEST, this, "Did not find a valid request node."); 
		}
		
		Map<String,String> values = simpleGetProperties(reqProps);
		
		// Create result document
		PropertyResponse pr = new PropertyResponse(getHref());
		
		// Found properties
		for (String k : values.keySet()) {
			pr.addProperty(WebDAVStatusCodes.SC_OK, k, reqValues ? values.get(k) : null);
		}
		
		// Non-existant properties
		Set<String> propsNotFound = disjunction(reqProps, values.keySet());
		for (String prop : propsNotFound) {
			pr.addProperty(WebDAVStatusCodes.SC_NOT_FOUND, prop, null);
		}
		
		return pr;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#updateProperties(org.w3c.dom.Document)
	 */
	public MultiStatusResponse updateProperties(Document req)
		throws WebDAVResourceException {
		// This implementation just returns a 403 forbidden message for all properties after rudimentary parsing.
		
		checkExists();
		
		Node updateNode = null;
		NodeList rootNodeList = req.getChildNodes();
		for (int i = 0;i < rootNodeList.getLength();i++) {
			Node n = rootNodeList.item(i);
			
			if ((n instanceof Element) && n.getNodeName().equals(WebDAVConstants.XML_PROPERTYUPDATE)) {
				updateNode = n;
				break;
			}
		}
		if (updateNode == null) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_BAD_REQUEST, this, "Did not find " + WebDAVConstants.XML_PROPERTYUPDATE + " element.");
		}
		
		PropertyResponse pr = new PropertyResponse(getHref());
		NodeList setdelNodeList = updateNode.getChildNodes();
		for (int i = 0;i < setdelNodeList.getLength();i++) {
			Node n = rootNodeList.item(i);
			
			if ((n instanceof Element) &&
					(n.getNodeName().equals(WebDAVConstants.XML_SET) || n.getNodeName().equals(WebDAVConstants.XML_REMOVE))) {
				
				NodeList tmpNodeList = n.getChildNodes();
				for (int j = 0;j < tmpNodeList.getLength();j++) {
					Node n2 = tmpNodeList.item(j);
					
					pr.addProperty(WebDAVStatusCodes.SC_FORBIDDEN, n2.getNamespaceURI(), n2.getNodeName());
				}
			}
		}
		
		return pr;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#resolvePath(org.openuss.webdav.WebDAVPath)
	 */
	public WebDAVResource resolvePath(WebDAVPath path) throws WebDAVResourceException {
		String nextName = path.getNextName();
		
		if (nextName == null) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_INTERNAL_SERVER_ERROR, this, "resolvePath() called despite lack of further path elements");
		}
		
		ParsedName pn = parseName(nextName);
		WebDAVResource nextRes = getChild(pn.id, pn.name, path.next());
		
		WebDAVResource res = nextRes.resolvePath(path);
		
		return res;
	}
	
	/**
	 * @param id The Id of the child to resolve or ID_NONE
	 * @param name The name of the resource. This should only be used if id == ID_NONE.
	 * @param path The WebDAVPath representing the full address of the resource to resolve.
	 * @return A WebDAVResource representing the child.
	 * @throws WebDAVResourceException If the resource can not be found.
	 */
	protected abstract WebDAVResource getChild(long id, String name, WebDAVPath path) throws WebDAVResourceException;
	
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
			WebDAVResource childRes;
			try {
				childRes = getChild(id, sanName, childPath);
				
				res.add(childRes);
			} catch (WebDAVResourceException e) {
				logger.error("Internal error: Resource " + id + " (\"" + sanName + "\") was listed as child, but could not be resolved.");
			}
		}
		
		return res;
	}
	
	/**
	 * @return The map of IDs to the names of the resources in this collection or null if this is not a collection.
	 * 			If you can ensure that the names are unique, any key set will do (1,2,3,4,... is a suggesting choice)
	 */
	protected abstract Map<Long,String> getRawChildNames();
	
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

	/**
	 * @return true if the current user is allowed to read the resource, otherwise false.
	 * 				Implementations may return true even if the ressource does not exist.
	 */
	protected abstract boolean isReadable();
	
	/**
	 * @return true iff the current user is allowed to write the resource.
	 */
	protected abstract boolean isWritable();
	
	
	/**
	 * @return true if the current user may delete this resource.
	 * 			Implementations may return true even if the file does not exist.
	 */
	protected boolean isDeletable() {
		return isWritable();
	}
	
	/**
	 * Checks the precondition that the resource exists
	 * 
	 * @throws WebDAVException An exception if the resource does not exist.
	 */
	protected void checkExists() throws WebDAVResourceException {
		if (! exists()) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_NOT_FOUND, this);
		}
	}
	
	/**
	 * Checks the precondition that the resource may be read.
	 *
	 * @see #checkExists()
	 * @throws WebDAVException An exception if the resource does not exist or is not readable. 
	 */
	protected void checkReadable() throws WebDAVResourceException {
		checkExists();
		
		if (! isReadable()) {
			throw new WebDAVResourceException(WebDAVStatusCodes.SC_FORBIDDEN, this);
		}
	}
	
	/**
	 * @return The path of this resource, ready to be presented to the client
	 */
	public String getHref() {
		return getPath().toClientString();
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
