package org.openuss.web.dav;

import org.apache.commons.lang.StringUtils;
import org.openuss.webdav.WebDAVPath;
import org.openuss.webdav.WebDAVURLUTF8Encoder;

/**
 * A WebDAVPath in OpenUSS.
 * See RFC 4918, especially chapter 8.3.1, for details.
 */
public class WebDAVPathImpl implements WebDAVPath {
	/**
	 * A prefix to the already resolved path.
	 */
	protected final String path;
	/**
	 * The path yet to resolve as supplied by the client.
	 * This does not include the root path specification or any other prefixes and therefore should not start with a path separator.
	 * null if everything is resolved.
	 */
	protected final String toResolve;
	
	/**
	 * Constructor for parsed client-supplied values.
	 * 
	 * @see #parse(String, String)
	 * @param path A prefix to the already resolved path. If toResolve != null, this should end with a path separator.
	 * @param toResolve The yet unresolved path of the path or null.
	 */
	public WebDAVPathImpl(String path, String toResolve) {
		if ((toResolve == null) || "".equals(toResolve)) {
			toResolve = null;
		} else if(PATH_SEP.equals(toResolve)) {
			path = path + PATH_SEP;
			toResolve = null;
		} else if ((!path.endsWith(PATH_SEP)) && toResolve.startsWith(PATH_SEP)) {
			path = path + PATH_SEP;
			toResolve = toResolve.substring(PATH_SEP.length());
		}
		
		this.path = path;
		this.toResolve = toResolve;
	}
	
	/**
	 * Create a new WebDAVPath object out of a client's path specification.
	 * 
	 * @param prefix The prefix as determined by the environment.
	 * @param clientInput The complete requested path as specified and encoded by the WebDAV client.
	 * @return A WebDAVPath element representing the client request address.
	 * @throws IllegalArgumentException If the decoded client specification does not start with prefix. 
	 */
	public static WebDAVPathImpl parse(String prefix, String clientInput) throws IllegalArgumentException {
		if (!prefix.endsWith(PATH_SEP)) {
			prefix = prefix + PATH_SEP;
		}
		
		clientInput = WebDAVURLUTF8Encoder.decode(clientInput);
		
		if (!clientInput.startsWith(prefix)) {
			throw new IllegalArgumentException("Client-supplied path \"" + clientInput + "\" does not start with prefix \"" + prefix + "\"");
		}
		
		clientInput = clientInput.substring(prefix.length());
		
		return new WebDAVPathImpl(prefix, clientInput);
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
		
		return new WebDAVPathImpl(prefix, "");
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getFileName()
	 */
	public String getFileName() {
		// Find end of file name
		int endPos = path.length(); // Last character of the file name + 1
		while ((endPos - PATH_SEP.length() >= 0) && PATH_SEP.equals(path.substring(endPos - PATH_SEP.length(), endPos))) {
			endPos -= PATH_SEP.length();
		}
		
		// No content
		if (endPos <= 0) {
			return null;
		}
		
		// Find previous path separator
		int startPos = path.lastIndexOf(PATH_SEP, endPos - 1);
		if (startPos < 0) {
			startPos = 0;
		}
		
		String res = path.substring(startPos, endPos);
		if ("".equals(res)) {
			return null;
		}
		
		return res;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getFileExt()
	 */
	public String getFileExt() {
		String fileName = getFileName();

		if (fileName == null) {
			return null;
		}
		
		int dotPos = fileName.lastIndexOf(FILEEXT_SEP);
		if ((dotPos > 0) && (dotPos < fileName.length() - 1)) {
			return fileName.substring(dotPos + 1);
		} else {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o instanceof WebDAVPath) {
			return equals((WebDAVPath)o);
		} else {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#equals(org.openuss.webdav.WebDAVPath)
	 */
	public boolean equals(WebDAVPath p) {
		return getCompleteString().equals(p.getCompleteString());
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#concat(java.lang.String)
	 */
	public WebDAVPath concat(String subResStr) {
		String newPath = path;
		String newToResolve;
		if (toResolve != null) {
			newToResolve = toResolve;

			if (! newToResolve.endsWith(PATH_SEP)) {
				newToResolve = newToResolve + PATH_SEP;
			}
			
			newToResolve += subResStr;
		} else {
			newToResolve = subResStr;
			if (! newToResolve.endsWith(PATH_SEP)) {
				newToResolve = newToResolve + PATH_SEP;
			}
			
			if (! newPath.endsWith(PATH_SEP)) {
				newPath += PATH_SEP;
			}
		}
		
		return new WebDAVPathImpl(newPath, newToResolve);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#toClientString()
	 */
	public String toClientString() {
		return WebDAVURLUTF8Encoder.encode(path);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVRemainingPath#getNextName()
	 */
	public String getNextName() {
		if (toResolve == null) {
			return null;
		}
		
		int endPos = toResolve.indexOf(PATH_SEP);
		String res;
		if (endPos == -1) {
			res = toResolve;
		} else {
			res = toResolve.substring(0, endPos);
		}
		
		return res;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVRemainingPath#next()
	 */
	public WebDAVPath next() {
		if (toResolve == null) {
			return null;
		}
		
		String newPath = path;
		String newToResolve;
		
		int endPos = toResolve.indexOf(PATH_SEP);
		
		if (endPos == -1) {
			newPath += toResolve;
			newToResolve = null;
		} else {
			newPath += toResolve.substring(0, endPos+PATH_SEP.length());
			newToResolve = toResolve.substring(endPos + PATH_SEP.length());
		}
		
		return new WebDAVPathImpl(newPath, newToResolve);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#common(org.openuss.webdav.WebDAVPath)
	 */
	public CommonPathRes common(WebDAVPath other) {
		String cs1 = getCompleteString();
		String cs2 = other.getCompleteString();
		
		int di = StringUtils.indexOfDifference(cs1, cs2);
		if ((di == -1) || (di == 0)) { // Both equal or completely different
			return new CommonPathRes(this, other);
		}
		di = cs1.lastIndexOf(PATH_SEP, di - 1);
		if (di == -1) {
			return new CommonPathRes(this, other);
		}
		WebDAVPath p1 = (di > getPrefix().length()) ? this :
				new WebDAVPathImpl(cs1.substring(0, di + 1), cs1.substring(di + 1)); 
		WebDAVPath p2 = (di > other.getPrefix().length()) ? other :
			new WebDAVPathImpl(cs2.substring(0, di + 1), cs2.substring(di + 1)); 
		
		return new CommonPathRes(p1, p2);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getCompleteString()
	 */
	public String getCompleteString() {
		if (toResolve == null) {
			return path;
		} else {
			return path + toResolve;
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getPrefix()
	 */
	public String getPrefix() {
		return path;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getToResolve()
	 */
	public String getToResolve() {
		return toResolve;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#isResolved()
	 */
	public boolean isResolved() {
		return (toResolve == null);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#asResolved()
	 */
	public WebDAVPath asResolved() {
		return new WebDAVPathImpl(path + toResolve, null);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#asFinalPath()
	 */
	public WebDAVPath asFinalPath() {
		if (isResolved()) {
			return this;
		} else {
			return new WebDAVPathImpl(path, null);
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getNumberOfElemsToResolve()
	 */
	public int getNumberOfElemsToResolve() {
		String toResolve = getToResolve();
		int res = StringUtils.countMatches(toResolve, PATH_SEP);
		
		if (toResolve.endsWith(PATH_SEP)) {
			res--;
		}
		
		return res;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVPath#getParent()
	 */
	public WebDAVPath getParent() {
		if (isResolved()) {
			return this;
		}
		
		int p = toResolve.length() - 1;
		
		if (toResolve.endsWith(PATH_SEP)) {
			p -= PATH_SEP.length();
		}
		
		p = toResolve.lastIndexOf(PATH_SEP, p);
		
		if (p <= 0) {
			return this;
		}
		
		return new WebDAVPathImpl(path, toResolve.substring(0, p));
	}
}
