package org.openuss.web.dav;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.tools.ant.filters.StringInputStream;
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
 * <li>Special character handling.</li>
 * <li>Ignoring third-party XML namespaces</li>
 * <li>Content type determination</li>
 * <li>Add regularly used properties to the answer of a PROPFIND.</li>
 * </ul>
 * 
 * @see CollisionAvoidingSimpleWebDAVResource
 */
public abstract class SimpleWebDAVResource implements WebDAVResource {
	/**
	 * The Spring bean factory.
	 */
	private final WebApplicationContext wac;
	/**
	 * The path of this resource.
	 */
	protected final WebDAVPath path;

	
	/**
	 * Constructor. Subclasses typically set the corresponding backend object here.
	 * 
	 * @param wac The WebApplication
	 * @param path The path of this resource.
	 */
	protected SimpleWebDAVResource(WebApplicationContext wac, WebDAVPath path) {
		this.wac = wac;
		this.path = path.asFinalPath();
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
		
		Set<String> reqProps = null; // The set of the requested properties
		boolean reqValues = true; // Request demands the values of the properties
		
		if (req == null) {
			// RFC 4918, 9.1 forth paragraph: null = allprop
			reqProps = null;
			reqValues = true;
		} else {
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
								(WebDAVConstants.NAMESPACE_WEBDAV.equals(reqPropNode.getNamespaceURI()))) {
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
		}
		
		Map<String,String> values = simpleGetProperties(reqProps);
		
		if (values == null) {
			values = Collections.emptyMap();
		}
		
		// Create result document
		PropertyResponseImpl pr = new PropertyResponseImpl(getHref());
		// Found properties
		for (Entry<String,String> e : values.entrySet()) {
			pr.addSimpleProperty(WebDAVStatusCodes.SC_OK, e.getKey(), reqValues ? e.getValue() : null);
		}
		
		// Auto-add properties, if requested
		Set<String> autoAdded = new TreeSet<String>();
		
		// getcontenttype
		if (((reqProps == null) || reqProps.contains(WebDAVConstants.XML_GETCONTENTTYPE))
				&& (!values.containsKey(WebDAVConstants.XML_GETCONTENTTYPE))) {
			pr.addSimpleProperty(WebDAVStatusCodes.SC_OK, WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_GETCONTENTTYPE, reqValues ? getContentType() : null);
			autoAdded.add(WebDAVConstants.XML_GETCONTENTTYPE);
		}
		// resourcetype
		if (((reqProps == null) || reqProps.contains(WebDAVConstants.XML_RESOURCETYPE))
				&& (!values.containsKey(WebDAVConstants.XML_RESOURCETYPE))) {
			Document tmpDoc = WebDAVUtils.newDocument();
			
			Element propElem = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_RESOURCETYPE);
			if (isCollection()) {
				Element collectioNode = tmpDoc.createElementNS(WebDAVConstants.NAMESPACE_WEBDAV, WebDAVConstants.XML_COLLECTION);
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

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#resolvePath(org.openuss.webdav.WebDAVPath)
	 */
	public WebDAVResource resolvePath(WebDAVPath path) throws WebDAVHrefException {
		String nextName = path.getNextName();
		
		// Is resolved
		if (nextName == null) {
			return this;
		}
		
		WebDAVPath nextPath = path.next();
		WebDAVResource nextRes = getChild(nextName, path);
		
		if (nextRes == null) {
			// Cannot find sub-element
			throw new WebDAVPathException(WebDAVStatusCodes.SC_NOT_FOUND, nextPath);
		}
		
		return nextRes.resolvePath(nextPath);
	}
	
	/**
	 * @param name The name of the searched sub-element.
	 * @param path The path of the WebDAVResource to create.
	 * @return The sub-element in this resource bearing the name name or null if it can not be found.
	 */
	protected abstract WebDAVResource getChild(String name, WebDAVPath path);
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#getChildren()
	 */
	public abstract Set<WebDAVResource> getChildren();
	
	/**
	 * Return child names. This method can assume that it is only called on collections.
	 * 
	 * @return The map of IDs to the names of the resources in this collection or null if this is not a collection.
	 * 			If you can ensure that the names are unique, any key set will do (1,2,3,4,... is a suggesting choice)
	 */
	protected abstract Map<Long,String> getRawChildNames();
	
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
		
		return createCollectionImpl(name);
	}
	
	/**
	 * Implementation of createCollection that does not check preconditions.
	 * 
	 * @param name The name of the collection to create.
	 * @throws WebDAVResourceException On special errors. This shouldn't be thrown under normal circumstances.
	 */
	protected abstract WebDAVResource createCollectionImpl(String name) throws WebDAVResourceException;

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#createFile(java.lang.String)
	 */
	public WebDAVResource createFile(String name)
			throws WebDAVResourceException {
		checkCreate();
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

	/**
	 * @return A virtual IOContext representing this element's children.
	 */
	protected IOContext readCollectionContent() {
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
		header.setTextContent(getPath().getPrefix());
		body.appendChild(header);
		
		Element list = doc.createElement(WebDAVConstants.XHTML_UNORDERED_LIST);
		body.appendChild(list);
		
		Collection<WebDAVResource> children = getChildren();
		for (WebDAVResource c : children) {
			Element li = doc.createElement(WebDAVConstants.XHTML_LIST_ELEM);
			list.appendChild(li);
			
			Element link = doc.createElement(WebDAVConstants.XHTML_LINK);
			link.setAttribute(WebDAVConstants.XHTML_HREF, c.getPath().getPrefix());
			link.setTextContent(c.getPath().getFileName());
			li.appendChild(link);
		}
		
		StringInputStream sis = new StringInputStream(WebDAVUtils.documentToString(doc));
		IOContextImpl ioc = new IOContextImpl();
		ioc.setContentType(WebDAVConstants.MIMETYPE_HTML);
		ioc.setInputStream(sis);
		
		return ioc;
	}

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
}