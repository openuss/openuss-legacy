package org.openuss.webdav;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A WebDAV path specification.
 * It consists of multiple path elements.
 * The user has to know whether it represents an absolute or relative path. 
 */
public class WebDAVPath {
	/**
	 * HTTP path separator.
	 */
	protected static final String PATH_SEP ="/";
	/**
	 * A regular expression matching a path separator and equivalent substrings.
	 */
	protected static final String PATH_SEP_REGEXP = "/+";
	/**
	 * Regexp to remove everything but the server-dependent part of an URI
	 */
	protected static final String URI_SANITIZATION_REGEXP = "^http.?://[^/]+/+";
	/**
	 * The file extension marker.
	 * @see #getFileExt()
	 */
	protected static final String FILEEXT_SEP = ".";
	/**
	 * Path represented by this object.
	 */
	final protected List<String> pathElems;
	/**
	 * The String representation of this path as supplied by the client.
	 * null if none is specified.
	 */
	final protected String clientString;
	
	/**
	 * Constructor for a yet unresolved path, starting at root.
	 * 
	 * @param pathElems The sanitized elements of the path.
	 */
	protected WebDAVPath(List<String> pathElems) {
		this(pathElems, null);
	}
	
	/**
	 * @param pathElems The sanitized elements of the path.
	 * @param clientPath The path as specified by the client or null for automatic recognition.
	 */
	protected WebDAVPath(List<String> pathElems, String clientPath) {
		this.pathElems = pathElems;
		this.clientString = clientPath;
	}
	
	/**
	 * @param input A user-supplied absolute path without server specification.
	 * @return A WebDAVPath object representig the sanitized path. 
	 */
	public static WebDAVPath parse(String input) {
		String[] elemAr = input.split(PATH_SEP_REGEXP);
		
		return new WebDAVPath(Arrays.asList(elemAr), input);
	}
	
	/**
	 * Parses an HTTP path specification.
	 * 
	 * @param input The user-supplied path information.
	 * @return A path element. 
	 */
	public static WebDAVPath parseURI(String input) {
		// Remove server information
		input = input.replaceAll(URI_SANITIZATION_REGEXP, "");
		
		return parse(input);
	}
	
	/**
	 * Helper method to directly parse an URI to a list of strings. 
	 * 
	 * @param input The input URI.
	 * @return A changeable list of path elements.
	 */
	protected static List<String> parseURIToList(String input) {
		return parseURI(input).pathElems;
	}
	
	/**
	 * Create a new WebDAVPath element out of a list of path elements.  
	 * 
	 * @param elems A list of path elements.
	 * 			It must not contain any elements containg PATH_SEP.
	 * @param input The user-supplied input.
	 * @return The WebDAVPath object representing elems.
	 * 				In most cases, getList() on this object will equal elems afterwards.
	 */
	public static WebDAVPath parse(List<String> elems, String input) {
		return new WebDAVPath(sanitizeList(elems), input);
	}
	
	/**
	 * Create a new WebDAVPath element out of an array of path elements.  
	 * 
	 * @param elems An array of path elements.
	 * 			It must not contain any elements containg PATH_SEP.
	 * @param input The user-supplied input.
	 * 				null for none. 
	 * @return The WebDAVPath object representing elems.
	 */
	public static WebDAVPath parse(String[] elems, String input) {
		return parse(Arrays.asList(elems), input);
	}
	
	/**
	 * @return The root path.
	 */
	public static WebDAVPath getRoot() {
		return new WebDAVPath(new LinkedList<String>());
	}
	
	/**
	 * Add further path specifications.
	 * 
	 * @param subDir The names of sub-directories.
	 * @return A WebDAVPathElement representing the designated sub file.
	 */
	public WebDAVPath concat(List<String> subDir) {
		// Change list
		List<String> resLst = new ArrayList<String>();
		resLst.addAll(pathElems);
		resLst.addAll(subDir);
		resLst = sanitizeList(resLst);
		
		// change clientString
		String resClientString = concatClientString(toRelativeString(subDir));
		
		return new WebDAVPath(resLst, resClientString);
	}
	
	/**
	 * @see #concat(List)
	 */
	public WebDAVPath concat(String[] subDir) {
		return concat(Arrays.asList(subDir));
	}
	
	/**
	 * Add further path specifications.
	 * 
	 * @param subDirStr The names of sub-directories.
	 * @return A WebDAVPathElement representing the designated sub file.
	 */
	public WebDAVPath concat(String subDirStr) {
		// Change list
		String[] subElemAr = subDirStr.split(PATH_SEP_REGEXP);
		List<String> resLst = new ArrayList<String>();
		resLst.addAll(pathElems);
		resLst.addAll(Arrays.asList(subElemAr));
		resLst = sanitizeList(resLst);
		
		// change clientString
		String resClientString = concatClientString(subDirStr);
		
		return new WebDAVPath(resLst, resClientString);
	}
	
	/**
	 * Alias for toAbsoluteString().
	 */
	public String toString() {
		return toAbsoluteString();
	}
	
	/**
	 * @return A representation of this object as an absolute path.
	 */
	public String toAbsoluteString() {
		return PATH_SEP + toRelativeString();
	}
	
	/**
	 * @return A representation of this object as a relative path.
	 */
	public String toRelativeString() {
		return toRelativeString(pathElems);
	}
	
	/**
	 * @return A string that can be extradited to the WebDAV client.
	 */
	public String toClientString() {
		return (clientString == null) ? toAbsoluteString() : clientString;
	}
	
	/**
	 * @return An unmodifieable list of the remaining path elements represented by this object.
	 */
	public List<String> getList() {
		return Collections.unmodifiableList(pathElems);
	}
	
	/**
	 * @return The local file name.
	 * 			For example, a WebDAVPath("/a/b/cd.e") would return "cd.e".
	 * 			"" is returned for the root path.
	 */
	public String getFileName() {
		if (pathElems.size() >= 1) {
			return pathElems.get(pathElems.size() - 1);
		} else { // root
			return "";
		}
	}
	
	/**
	 * @return The extension of the file represented by this path.
	 * 			For example, a WebDAVPath("/a/b/cd.e") would return "e".
	 * 			null if there is no recognizable extension. For example, "/a/b/cde" would return null.
	 */
	public String getFileExt() {
		String fileName = getFileName();
		
		int dotPos = fileName.lastIndexOf(FILEEXT_SEP);
		if ((dotPos > 0) && (dotPos < fileName.length() - 1)) {
			return fileName.substring(dotPos + 1);
		} else {
			return null;
		}
	}

	/**
	 * Strict equals function.
	 * 
	 * @param o The object to compare with.
	 * @return true iff all requesting methods on both objects will return the same.
	 */
	public boolean equals(Object o) {
		if (o instanceof WebDAVPath) {
			return equals((WebDAVPath)o);
		} else {
			return false;
		}
	}
	
	/**
	 * Strict equals function.
	 * 
	 * @param p The path to compare with.
	 * @return true iff all requesting methods on both objects will return the same.
	 */
	public boolean equals(WebDAVPath p) {
		return toClientString().equals(p.toClientString());
	}
		
	/**
	 * Compares two paths except for the client-dependant part. 
	 * 
	 * @param other The path to compare with.
	 * @return true iff both paths represent the same structure.
	 */
	public boolean semanticEqual(WebDAVPath other) {
		return getList().equals(other.getList());
	}

	
	/* Helper functions */
	
	/**
	 * @param subPath The (potentially client-supplied) path of the element to address, relative to cwd.
	 * @return The concatenation of clientString and subPath.
	 */
	protected String concatClientString(String subPath) {
		if (clientString == null) {
			return null;
		}
		
		boolean addSlash = clientString.endsWith(PATH_SEP) ||
							subPath.startsWith(PATH_SEP);
		
		return clientString + (addSlash ? "" : PATH_SEP) + subPath;
	}
	
	/**
	 * Remove invalid elements from a user-supplied list
	 * 
	 * @param inl The input list. It remains unchanged.
	 * @return A copy of inl without any invalid elements.
	 */
	protected static List<String> sanitizeList(List<String> inl) {
		ArrayList<String> res = new ArrayList<String>(inl.size());
		
		for (String elem : inl) {
			if ("".equals(elem)) {
				continue;
			}
		
			res.add(elem);
		}
		
		res.trimToSize();
		
		return res;
	}
	
	/**
	 * Generates a relative path specification out of the path elements.
	 * 
	 * @param pathElems The elements of the path.
	 * @return implode("/", pathElems). For example, [a,b,c] will yield "a/b/c"
	 */
	protected static String toRelativeString(List<String> pathElems) {
		StringBuilder sb = new StringBuilder();
		
		boolean first = true;
		for (String e : pathElems) {
			if (first) {
				first = false;
			} else {
				sb.append(PATH_SEP);
			}
			
			sb.append(e);
		}
		
		return sb.toString();
	}
}
