package org.openuss.web.dav;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVResource;
import org.openuss.webdav.WebDAVResourceException;

/**
 * A simple WebDAVResource that avoids collisions between identical entry names by appending an id.
 * Furthermore, it reformats names so that they do not lead to special characters in common clients.  
 */
public abstract class CollisionAvoidingSimpleWebDAVResource extends SimpleWebDAVResource {
	private static Logger logger = Logger.getLogger(CollisionAvoidingSimpleWebDAVResource.class);
	/**
	 * Unspecified id (Wasn't contained in the original query).
	 */
	protected static final long ID_NONE = -1;
	/**
	 * Separator for path specification including an id.
	 */
	protected static final String ID_SEP = "-";
	/**
	 * Regex for invalid characters.
	 */
	protected static final String INVALID_CHAR_REGEX = "(/)|(\\\\)";
	/**
	 * Replacement for invalid characters.
	 */
	protected static final String INVALID_CHAR_REPLACEMENT = "_";
	
	/**
	 * The internal id of this object.
	 */
	protected final long id;
	
	/**
	 * Constructor.
	 * 
	 * @param wac The WebApplication
	 * @param path The path of this resource.
	 * @param id The internal Id of this resource.
	 */
	public CollisionAvoidingSimpleWebDAVResource(WebDAVContext context,
			WebDAVPath path, long id) {
		super(context, path);
		
		this.id = id;
	}
	

	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#getChild(java.lang.String, org.openuss.webdav.WebDAVPath)
	 */
	protected final WebDAVResource getChild(String name, WebDAVPath newPath) {
		ParsedName pn = parseName(name);
		return getChild(pn.id, pn.name, newPath);
	}
	
	/**
	 * @param id The Id of the child to resolve or ID_NONE if the name has to be resolved.
	 * @param sname The sanitized name of the resource. This should only be used if id == ID_NONE.
	 * @param path The WebDAVPath representing the full address of the resource to resolve.
	 * @return A WebDAVResource representing the child. If it can not be found, null.
	 */
	protected abstract WebDAVResource getChild(long id, String sname, WebDAVPath path);

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
			
			if (allNames.contains(sname)) {
				ambiguousNames.add(sname);
			} else {
				allNames.add(sname);
			}
		}
		
		Set<WebDAVResource> res = new HashSet<WebDAVResource>();
		for (Entry<Long,String> e : rawChildNames.entrySet()) {
			long id = e.getKey();
			String rawName = e.getValue();
			
			String sanName = sanitizeName(rawName);
			String fullName = genName(sanName, id, ambiguousNames.contains(sanName));
			
			WebDAVPath childPath = path.concat(fullName).asResolved();
			WebDAVResource childRes = getChild(id, sanName, childPath);
			
			if (childRes == null) {
				logger.error("Child \"" + fullName + "\" was listed, but not found!");
				continue;
			}
			
			if (childRes.isReadable()) {
				res.add(childRes);
			}
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

	/* (non-Javadoc)
	 * 
	 * @see org.openuss.webdav.WebDAVResource#createCollection()
	 */
	public WebDAVResource createCollection(String name) throws WebDAVResourceException {
		name = sanitizeName(name);
		
		return super.createCollection(name);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.web.dav.SimpleWebDAVResource#createFile(java.lang.String, org.openuss.webdav.IOContext)
	 */
	public WebDAVResource createFile(String name, IOContext ioc)
			throws WebDAVResourceException {
		name = sanitizeName(name);
		
		return super.createFile(name, ioc);
	}
	
	/**
	 * @return The id of this resource or ID_PRELIMINARY if it does not yet exist.
	 */
	public long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVResource#hasChild(java.lang.String)
	 */
	public boolean hasChild(String name) {
		return getRawChildNames().containsValue(name);
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
	 * @return The sanitized name or a string equal to origName if no sanitization was necessary.
	 */
	protected String sanitizeName(String origName) { 
		if (origName.equals("")) {
			return INVALID_CHAR_REPLACEMENT;
		}
		
		origName = origName.replaceAll(INVALID_CHAR_REGEX, INVALID_CHAR_REPLACEMENT);
			
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
