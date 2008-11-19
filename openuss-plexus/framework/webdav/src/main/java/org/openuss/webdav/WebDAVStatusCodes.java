package org.openuss.webdav;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the HTTP and WebDAV status codes as defined in RFC 2616 and 4918.
 * 
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class WebDAVStatusCodes {
	public static final String HTTP_VERSION = "HTTP/1.1";
	
	// http status code with additional codes as defined in RFC2518
	public static final int SC_CONTINUE = 100;
	public static final int SC_SWITCHING_PROTOCOLS = 101;
	public static final int SC_PROCESSING = 102;
	public static final int SC_OK = 200;
	public static final int SC_CREATED = 201;
	public static final int SC_ACCEPTED = 202;
	public static final int SC_NON_AUTHORITATIVE_INFORMATION = 203;
	public static final int SC_NO_CONTENT = 204;
	public static final int SC_RESET_CONTENT = 205;
	public static final int SC_PARTIAL_CONTENT = 206;
	public static final int SC_MULTI_STATUS = 207;
	public static final int SC_MULTIPLE_CHOICES = 300;
	public static final int SC_MOVED_PERMANENTLY = 301;
	public static final int SC_FOUND = 302;
	public static final int SC_SEE_OTHER = 303;
	public static final int SC_NOT_MODIFIED = 304;
	public static final int SC_USE_PROXY = 305;
	public static final int SC_TEMPORARY_REDIRECT = 307;
	public static final int SC_BAD_REQUEST = 400;
	public static final int SC_UNAUTHORIZED = 401;
	public static final int SC_PAYMENT_REQUIRED = 402;
	public static final int SC_FORBIDDEN = 403;
	public static final int SC_NOT_FOUND = 404;
	public static final int SC_METHOD_NOT_ALLOWED = 405;
	public static final int SC_NOT_ACCEPTABLE = 406;
	public static final int SC_PROXY_AUTHENTICATION_REQUIRED = 407;
	public static final int SC_REQUEST_TIME_OUT = 408;
	public static final int SC_CONFLICT = 409;
	public static final int SC_GONE = 410;
	public static final int SC_LENGTH_REQUIRED = 411;
	public static final int SC_PRECONDITION_FAILED = 412;
	public static final int SC_REQUEST_ENTITY_TOO_LARGE = 413;
	public static final int SC_REQUEST_URI_TOO_LARGE = 414;
	public static final int SC_UNSUPPORTED_MEDIA_TYPE = 415;
	public static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
	public static final int SC_EXPECTATION_FAILED = 417;
	public static final int SC_METHOD_FAILURE = 420;
	public static final int SC_UNPROCESSABLE_ENTITY = 422;
	public static final int SC_LOCKED = 423;
	public static final int SC_FAILED_DEPENDENCY = 424;
	public static final int SC_INTERNAL_SERVER_ERROR = 500;
	public static final int SC_NOT_IMPLEMENTED = 501;
	public static final int SC_BAD_GATEWAY = 502;
	public static final int SC_SERVICE_UNAVAILABLE = 503;
	public static final int SC_GATEWAY_TIME_OUT = 504;
	public static final int SC_HTTP_VERSION_NOT_SUPPORTED = 505;
	public static final int SC_INSUFFICIENT_STORAGE = 507;
	
	private static final Map<Integer,String> reasonPhrases = new HashMap<Integer, String>();
	
	static {
		reasonPhrases.put(100, "Continue");
		reasonPhrases.put(101, "Switching Protocols");
		reasonPhrases.put(102, "Processing");
		reasonPhrases.put(200, "OK");
		reasonPhrases.put(201, "Created");
		reasonPhrases.put(202, "Accepted");
		reasonPhrases.put(203, "Non-Authoritative Information");
		reasonPhrases.put(204, "No Content");
		reasonPhrases.put(205, "Reset Content");
		reasonPhrases.put(206, "Partial Content");
		reasonPhrases.put(207, "Multi-Status");
		reasonPhrases.put(300, "Multiple Choices");
		reasonPhrases.put(301, "Moved Permanently");
		reasonPhrases.put(302, "Found");
		reasonPhrases.put(303, "See Other");
		reasonPhrases.put(304, "Not Modified");
		reasonPhrases.put(305, "Use Proxy");
		reasonPhrases.put(307, "Temporary Redirect");
		reasonPhrases.put(400, "Bad Request");
		reasonPhrases.put(401, "Unauthorized");
		reasonPhrases.put(402, "Payment Required");
		reasonPhrases.put(403, "Forbidden");
		reasonPhrases.put(404, "Not Found");
		reasonPhrases.put(405, "Method Not Allowed");
		reasonPhrases.put(406, "Not Acceptable");
		reasonPhrases.put(407, "Proxy Authentication Required");
		reasonPhrases.put(408, "Request Time-Out");
		reasonPhrases.put(409, "Conflict");
		reasonPhrases.put(410, "Gone");
		reasonPhrases.put(411, "Length Required");
		reasonPhrases.put(412, "Precondition Failed");
		reasonPhrases.put(413, "Request Entity Too Large");
		reasonPhrases.put(414, "Request-URI Too Large");
		reasonPhrases.put(415, "Unsupported Media Type");
		reasonPhrases.put(416, "Requested Range Not Satisfiable");
		reasonPhrases.put(417, "Expectation Failed");
		reasonPhrases.put(420, "Method Failure");
		reasonPhrases.put(422, "Unprocessable Entity");
		reasonPhrases.put(423, "Locked");
		reasonPhrases.put(424, "Failed Dependency");
		reasonPhrases.put(500, "Internal Server Error");
		reasonPhrases.put(501, "Not Implemented");
		reasonPhrases.put(502, "Bad Gateway");
		reasonPhrases.put(503, "Service Unavailable");
		reasonPhrases.put(504, "Gateway Time-Out");
		reasonPhrases.put(505, "HTTP Version Not Supported");
		reasonPhrases.put(507, "Insufficient Storage");
	}
	
	/**
	 * Getter for a HTTP reason phrase for a given status code.
	 * @param statusCode The status code.
	 * @return The reason phrase.
	 */
	public static String getReasonPhrase(int statusCode) {
		return reasonPhrases.get(statusCode);
	}

	/**
	 * Getter for a HTTP status line for a given status code.
	 * @param statusCode The status code.
	 * @return The status line.
	 */
	public static String getStatusLine(int statusCode) {
		return HTTP_VERSION + " " + statusCode + " " + getReasonPhrase(statusCode);
	}

	/**
	 * Checks whether a status code is valid. 
	 * 
	 * @param statusCode The status code to test.
	 * @return true, iff the status code is correct.
	 */
	public static boolean isStatusCode(int statusCode) {
		return reasonPhrases.containsKey(statusCode);
	}
	
	/**
	 * Returns a valid status code.
	 * 
	 * @param statusCode The status code proposal.
	 * @return A value v so that isStatusCode(v) == true.
	 * 	If isStatusCode(statusCode), then statusCode. Otherwise, an error code is returned.
	 */
	public static int sanitizeStatusCode(int statusCode) {
		return isStatusCode(statusCode) ? statusCode : SC_INTERNAL_SERVER_ERROR;
	}
}
