package org.openuss.webdav;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A WebDAVPath in OpenUSS.
 * See RFC 4918, especially chapter 8.3.1, for details.
 */
public class WebDAVPathImpl implements WebDAVPath {
	/**
	 * Serialization version id.
	 */
	private static final long serialVersionUID = -1217812515953264658L;
	/**
	 * Pattern that matches the start of an URL
	 */
	protected static final Pattern STARTURL_PATTERN = Pattern.compile("^http(s)?://[^/]+");
	/**
	 * The prefix of all paths. 
	 */
	protected final String prefix;
	/**
	 * The path elements.
	 */
	protected final List<String> path;
	/**
	 * The index of the next element to resolve in toResolve.
	 */
	protected final int nextToResolvePos;
	
	/**
	 * Constructor for parsed client-supplied values.
	 * 
	 * @see #parse(String, String)
	 * @see #getRoot(String)
	 * @param prefix The prefix of all paths. 
	 * @param path The path elements.
	 * @param nextToResolvePos The position of the next path element to resolve in toResolve.
	 */
	protected WebDAVPathImpl(String prefix, List<String> path, int nextToResolvePos) {
		assert(nextToResolvePos >= 0);
		assert(nextToResolvePos < path.size());
		
		this.prefix = prefix;
		this.path = path;
		this.nextToResolvePos = nextToResolvePos;
	}
	
	/**
	 * Create a new WebDAVPath object out of a client's path specification.
	 * 
	 * @param prefix The prefix as determined by the environment.
	 * @param clientInput The complete requested path as specified and encoded by the WebDAV client.
	 * @return A WebDAVPath element representing the client request address.
	 * @throws WebDAVException If the decoded client specification does not start with prefix. 
	 */
	public static WebDAVPathImpl parse(String prefix, String clientInput) throws WebDAVException {
		if (!prefix.endsWith(PATH_SEP)) {
			prefix = prefix + PATH_SEP;
		}
		
		clientInput = WebDAVURLUTF8Encoder.decode(clientInput);
		
		boolean alteredCorrect = true; // clientInput is correct or made so
		if (!clientInput.startsWith(prefix)) {
			if (prefix.equals(clientInput + PATH_SEP)) {
				clientInput += PATH_SEP;
			} else {
				// Check whether the clientInput just starts with a server specification
				Matcher m = STARTURL_PATTERN.matcher(clientInput);
				if (m.find()) {
					clientInput = clientInput.substring(m.end());
					
					if (!clientInput.startsWith(prefix)) {
						alteredCorrect = false;
					}
				} else {
					alteredCorrect = false;
				}
			}
		}
		if (!alteredCorrect) {
			throw new WebDAVException(WebDAVStatusCodes.SC_BAD_REQUEST,
					"Client-supplied path \"" + clientInput + "\" does not start with prefix \"" + prefix + "\"");
		}
		
		clientInput = clientInput.substring(prefix.length());

		if (clientInput.endsWith(PATH_SEP)) {
			clientInput = clientInput.substring(0, clientInput.length() - PATH_SEP.length());
		}
		
		List<String> toResolve;
		if ((!prefix.endsWith(PATH_SEP)) && clientInput.startsWith(PATH_SEP)) {
			prefix = prefix + PATH_SEP;
			clientInput = clientInput.substring(PATH_SEP.length());
		}
			
		toResolve = explode(clientInput);
		
		return new WebDAVPathImpl(prefix, toResolve, 0);
	}

	/**
	 * Factory for the root WebDAV path.
	 * 
	 * @param prefix The prefix of all WebDAV paths.
	 * @return A WebDAVPathImpl object representing the root.
	 */
	public static WebDAVPathImpl getRoot(String prefix) {
		if (!prefix.endsWith(PATH_SEP)) {
			prefix = prefix + PATH_SEP;
		}
		
		List<String> toResolve = Collections.emptyList();
		return new WebDAVPathImpl(prefix, toResolve, 0);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getFileName()
	 */
	public String getFileName() {
		if (isRoot() || (nextToResolvePos == 0)) {
			return null;
		}
		
		return path.get(nextToResolvePos - 1);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getFileExt()
	 */
	public String getFileExt() {
		String fileName = getFileName();

		if (fileName == null) {
			return null;
		}
		
		return getExtension(fileName);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#concat(java.lang.String)
	 */
	public WebDAVPath concat(String subResStr) {
		List<String> newPathElems = explode(subResStr);
		List<String> newPath = new ArrayList<String>(len() + newPathElems.size());
		newPath.addAll(path);
		newPath.addAll(newPathElems);
		
		return new WebDAVPathImpl(prefix, newPath, nextToResolvePos);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#toClientString()
	 */
	public String toClientString() {
		return WebDAVURLUTF8Encoder.encode(getResolved());
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVRemainingPath#getNextName()
	 */
	public String getNextName() {
		if (isResolved()) {
			return null;
		}
		
		return path.get(nextToResolvePos);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVRemainingPath#next()
	 */
	public WebDAVPath next() {
		if (isResolved()) {
			return null;
		}
		
		return new WebDAVPathImpl(prefix, path, nextToResolvePos + 1);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getCompleteString()
	 */
	public String getCompleteString() {
		return prefix + implode(path);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getResolved()
	 */
	public String getResolved() {
		return prefix + implode(getResolvedListImpl());
	}
	
	/**
	 * @return The list of already resolved elements
	 */
	public List<String> getResolvedList() {
		return Collections.unmodifiableList(getResolvedListImpl());
	}
	
	/**
	 * @return The list of already resolved elements
	 */
	protected List<String> getResolvedListImpl() {
		return path.subList(0, nextToResolvePos);
	}
	
	/**
	 * @return The prefix of all paths
	 */
	public String getPrefix() {
		return prefix;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getToResolve()
	 */
	public String getToResolve() {
		if (isResolved()) {
			return null;
		}
		
		return implode(getToResolveListImpl());
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getToResolve()
	 */
	public List<String> getToResolveList() {
		List<String> res = getToResolveListImpl();
		
		if (res == null) {
			return null;
		}
		
		return Collections.unmodifiableList(res);
	}
	
	/**
	 * @see org.openuss.webdav.WebDAVPath#getToResolveList()
	 */
	protected List<String> getToResolveListImpl() {
		if (isResolved()) {
			return null;
		}
		
		return path.subList(nextToResolvePos, len());
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#isResolved()
	 */
	public boolean isResolved() {
		return nextToResolvePos >= len();
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#asResolved()
	 */
	public WebDAVPath asResolved() {
		return new WebDAVPathImpl(prefix, path, len());
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#asFinalPath()
	 */
	public WebDAVPath asFinalPath() {
		if (isResolved()) {
			return this;
		} else {
			return new WebDAVPathImpl(prefix, path.subList(0, nextToResolvePos), nextToResolvePos);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#resolveAllBut(int)
	 */
	public WebDAVPath resolveAllBut(int but) {
		if ((but <= 0) || (isResolved())) {
			return this;
		}
		
		but = Math.min(getNumberOfElemsToResolve(), but);
		
		return new WebDAVPathImpl(prefix, path, len() - but);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getNumberOfElemsToResolve()
	 */
	public int getNumberOfElemsToResolve() {
		return len() - nextToResolvePos;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getParent()
	 */
	public WebDAVPath getParent() {
		if (isResolved() || isResolvedRoot()) {
			return this;
		}
		
		return new WebDAVPathImpl(prefix, path.subList(0, len() - 1), nextToResolvePos);
	}
	
	/**
	 * @return true iff the path represented by this object denominates the root.
	 */
	public boolean isRoot() {
		return nextToResolvePos == 0;
	}
	
	/**
	 * @return true iff the path represented by this object denominates the root even if it would been fully resolved.
	 */
	public boolean isResolvedRoot() {
		return len() == 0;
	}
	
	
	/**
	 * @return Helper function for the number of elements in the path list.
	 */
	protected int len() {
		return path.size();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nextToResolvePos;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof WebDAVPath) {
			WebDAVPath p = (WebDAVPath) obj;
			
			if (!getResolved().equals(p.getResolved())) {
				return false;
			}
			List<String> toResolveList = getToResolveListImpl(); 
			if (toResolveList == null) {
				return (p.getToResolveList() == null);
			} else {
				return toResolveList.equals(p.getToResolveList());
			}
		} else {
			return false;
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 * For debugging output only!
	 */
	public String toString() {
		String res = getResolved();
		
		if (isResolved()) {
			res += " (resolved)";
		} else {
			res = res + "  " + getToResolve();
		}
		
		return res;
	}
	
	/**
	 * Get the base name of a file without the extension.
	 * 
	 * @param fileName The full file name (without directory components)
	 * @return The name without the extension. For example, an input of "test.a.txt" yields "test.a".
	 * 			If no extension is present or the input was null, the full file name is returned.
	 */
	public static String stripExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		
		int dotPos = fileName.lastIndexOf(FILEEXT_SEP);
		
		if (dotPos < 0) {
			return fileName;
		} else {
			return fileName.substring(0, dotPos);
		}
	}
	
	/**
	 * Get the extension of a file nime.
	 * 
	 * @param fileName The full file name (without directory components)
	 * @return The name without the extension. For example, an input of "test.a.txt" yields "txt".
	 * 			If no extension is present or the input was null, null is returned.
	 */
	public static String getExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		
		int dotPos = fileName.lastIndexOf(FILEEXT_SEP);
		
		if (dotPos < 0) {
			return null;
		} else {
			return fileName.substring(dotPos + FILEEXT_SEP.length());
		}
	}
	
	/**
	 * Splits a string into its path components.
	 * 
	 * @param in The input string. A single path separator at the end is ignored.
	 * @return A list of the path elements. The inputs "" and PATH_SEP yield an empty list.
	 */
	public static List<String> explode(String in) {
		if (in.endsWith(PATH_SEP)) {
			in = in.substring(0, in.length() - PATH_SEP.length());
		}
		if ("".equals(in)) {
			return Collections.emptyList();
		}
		
		List<String> res = new ArrayList<String>();
		int start = 0;
		int end;
		for (;;) {
			end = in.indexOf(PATH_SEP, start);
			
			if (end == -1) {
				res.add(in.substring(start));
				break;
			}
			
			res.add(in.substring(start, end));
			start = end + PATH_SEP.length();
		}
		
		return res;
	}
	
	/**
	 * Joins the path components expressed by lst to a string.
	 * 
	 * @param lst The list of path elements.
	 * @return The concatenated string.
	 */
	public static String implode(List<String> lst) {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		
		for (String elem : lst) {
			if (first) {
				first = false;
			} else {
				sb.append(PATH_SEP);
			}
			
			sb.append(elem);
		}
		
		return sb.toString();
	}
	
	/**
	 * @param strPath The input path as a string
	 * @return strPath, ending with a path separator. 
	 */
	public static String appendSep(String strPath) {
		if (!strPath.endsWith(PATH_SEP)) {
			strPath = strPath + PATH_SEP;
		}
		
		return strPath;
	}

}
