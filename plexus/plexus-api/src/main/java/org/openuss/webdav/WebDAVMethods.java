package org.openuss.webdav;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the HTTP/WebDAV methods as defined in RFC 2616 and 4918.
 * Status codes are defined in {@link WebDAVStatusCodes}.
 *  
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 1.0
 */
public class WebDAVMethods {
	// List of method names and randomly chosen values (Java cannot switch-case Strings)
	public static final int DAV_INVALID = 0;
	public static final String METHOD_OPTIONS = "OPTIONS";
	public static final int DAV_OPTIONS = 1;
	public static final String METHOD_GET = "GET";
	public static final int DAV_GET = DAV_OPTIONS + 1;
	public static final String METHOD_HEAD = "HEAD";
	public static final int DAV_HEAD = DAV_GET + 1;
	public static final String METHOD_POST = "POST";
	public static final int DAV_POST = DAV_HEAD + 1;
	public static final String METHOD_DELETE = "DELETE";
	public static final int DAV_DELETE = DAV_POST + 1;
	public static final String METHOD_PUT = "PUT";
	public static final int DAV_PUT = DAV_DELETE + 1;
	public static final String METHOD_PROPFIND = "PROPFIND";
	public static final int DAV_PROPFIND = DAV_PUT + 1;
	public static final String METHOD_PROPPATCH = "PROPPATCH";
	public static final int DAV_PROPPATCH = DAV_PROPFIND + 1;
	public static final String METHOD_MKCOL = "MKCOL";
	public static final int DAV_MKCOL = DAV_PROPPATCH + 1;
	public static final String METHOD_COPY = "COPY";
	public static final int DAV_COPY = DAV_MKCOL + 1;
	public static final String METHOD_MOVE = "MOVE";
	public static final int DAV_MOVE = DAV_COPY + 1;
	public static final String METHOD_LOCK = "LOCK";
	public static final int DAV_LOCK = DAV_MOVE + 1;
	public static final String METHOD_UNLOCK = "UNLOCK";
	public static final int DAV_UNLOCK = DAV_LOCK + 1;
	
	private static Map<String, Integer> methodMap = new HashMap<String, Integer>();
	
	static {
		// add methods to map with adequate Integer object
		methodMap.put(METHOD_OPTIONS, Integer.valueOf(DAV_OPTIONS));
		methodMap.put(METHOD_GET, Integer.valueOf(DAV_GET));
		methodMap.put(METHOD_HEAD, Integer.valueOf(DAV_HEAD));
		methodMap.put(METHOD_POST, Integer.valueOf(DAV_POST));
		methodMap.put(METHOD_DELETE, Integer.valueOf(DAV_DELETE));
		methodMap.put(METHOD_PUT, Integer.valueOf(DAV_PUT));
		methodMap.put(METHOD_PROPFIND, Integer.valueOf(DAV_PROPFIND));
		methodMap.put(METHOD_PROPPATCH, Integer.valueOf(DAV_PROPPATCH));
		methodMap.put(METHOD_MKCOL, Integer.valueOf(DAV_MKCOL));
		methodMap.put(METHOD_COPY, Integer.valueOf(DAV_COPY));
		methodMap.put(METHOD_MOVE, Integer.valueOf(DAV_MOVE));
		methodMap.put(METHOD_LOCK, Integer.valueOf(DAV_LOCK));
		methodMap.put(METHOD_UNLOCK, Integer.valueOf(DAV_UNLOCK));
	}
	
	/**
	 * Returns an integer value identifying the method.
	 * @param method The name of the method to lookup.
	 * @return The identifying integer value or DAV_INVALID for unknown method.
	 */
	public static int getMethodCode(String method) {
		// lookup method in map by name
		Integer value = methodMap.get(method);
		if (value == null) {
			return DAV_INVALID;
		}
		return value.intValue();
	}
}
