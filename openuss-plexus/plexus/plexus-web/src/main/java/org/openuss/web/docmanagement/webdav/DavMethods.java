package org.openuss.web.docmanagement.webdav;

import java.util.HashMap;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class DavMethods {
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
	
	private static HashMap<String, Integer> methodHashMap = new HashMap<String, Integer>();
	
	static {
		methodHashMap.put(METHOD_OPTIONS, new Integer(DAV_OPTIONS));
		methodHashMap.put(METHOD_GET, new Integer(DAV_GET));
		methodHashMap.put(METHOD_HEAD, new Integer(DAV_HEAD));
		methodHashMap.put(METHOD_POST, new Integer(DAV_POST));
		methodHashMap.put(METHOD_DELETE, new Integer(DAV_DELETE));
		methodHashMap.put(METHOD_PUT, new Integer(DAV_PUT));
		methodHashMap.put(METHOD_PROPFIND, new Integer(DAV_PROPFIND));
		methodHashMap.put(METHOD_PROPPATCH, new Integer(DAV_PROPPATCH));
		methodHashMap.put(METHOD_MKCOL, new Integer(DAV_MKCOL));
		methodHashMap.put(METHOD_COPY, new Integer(DAV_COPY));
		methodHashMap.put(METHOD_MOVE, new Integer(DAV_MOVE));
		methodHashMap.put(METHOD_LOCK, new Integer(DAV_LOCK));
		methodHashMap.put(METHOD_UNLOCK, new Integer(DAV_UNLOCK));
	}
	
	/**
	 * Returns an integer value identifying the method.
	 * @param method The name of the method to lookup.
	 * @return The identifying integer value or 0 for unknown method.
	 */
	public static int getMethodCode(String method) {
		Integer value = methodHashMap.get(method);
		if (value == null) {
			return 0;
		}
		return value.intValue();
	}
}
