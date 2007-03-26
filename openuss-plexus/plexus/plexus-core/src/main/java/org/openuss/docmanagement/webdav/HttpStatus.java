package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public class HttpStatus {
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
	
	private static final Logger logger = Logger.getLogger(HttpStatus.class);
	
	private static final Properties reasonPhrases = new Properties();
	
	static {
		// load reason phrases from properties file
		try {
			reasonPhrases.load(HttpStatus.class.getResourceAsStream("httpReasonPhrases.properties"));
		} catch (IOException ex) {
			logger.error("IO exception occurred.");
			logger.error("Exception: " + ex.getMessage());
		}
	}
	
	/**
	 * Getter for a HTTP reason phrase for a given status code.
	 * @param statusCode The status code.
	 * @return The reason phrase.
	 */
	public static String getReasonPhrase(int statusCode) {
		return reasonPhrases.getProperty(statusCode + "");
	}

	/**
	 * Getter for a HTTP status line for a given status code.
	 * @param statusCode The status code.
	 * @return The status line.
	 */
	public static String getStatusLine(int statusCode) {
		return HTTP_VERSION + " " + statusCode + " " + getReasonPhrase(statusCode);
	}
}
