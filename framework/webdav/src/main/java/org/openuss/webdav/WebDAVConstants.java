package org.openuss.webdav;

/**
 * Constants as defined in RFC2518.
 * 
 * @see WebDAVStatusCodes for WebDAV/HTTP status codes (207, 404, ...)
 * @see WebDAVMethods for WebDAV/HTTP request methods (GET, PROPFIND, ...)
 */
public class WebDAVConstants {
	// header values
	public final static int DEPTH_0 = 0;
	public final static String DEPTH_0_STRING = "0";
	public final static int DEPTH_1 = 1;
	public final static String DEPTH_1_STRING = "1";
	public final static int DEPTH_INFINITY = Integer.MAX_VALUE;
	public final static String DEPTH_INFINITY_STRING = "infinity";
	public final static String TIMEOUT_INFINITE = "Infinite";

	// MIME types
	public static final String MIMETYPE_DEFAULT = "application/octet-stream";
	public static final String MIMETYPE_DIRECTORY = "httpd/unix-directory";
	public final static String MIMETYPE_XML = "text/xml";
	public final static String MIMETYPE_TEXT = "text/plain";
	public final static String MIMETYPE_HTML = "text/html";
	public final static String MIMETYPE_ENCODING_SEP = "; charset="; 
	
	// header names
	public final static String HEADER_AUTHORIZATION = "Authorization";
	public final static String HEADER_CONTENT_LANGUAGE = "Content-Language";
	public final static String HEADER_CONTENT_LENGTH = "Content-Length";
	public final static String HEADER_CONTENT_TYPE = "Content-Type";
	public final static String HEADER_DAV = "DAV";
	public final static String HEADER_DEPTH = "Depth";
	public final static String HEADER_DESTINATION = "Destination";
	public final static String HEADER_ETAG = "ETag";
	public final static String HEADER_IF = "If";
	public final static String HEADER_LAST_MODIFIED = "Last-Modified";
	public final static String HEADER_LOCK_TOKEN = "Lock-Token";
	public final static String HEADER_OVERWRITE = "Overwrite";
	public final static String HEADER_OVERWRITE_TRUE = "T";
	public final static String HEADER_OVERWRITE_FALSE = "F";
	public final static String HEADER_STATUS_URI = "Status-URI";
	public final static String HEADER_TIMEOUT = "Timeout";
	
	// properties
	public final static String PROPERTY_ACTIVELOCK = "activelock";
	public final static String PROPERTY_CREATIONDATE = "creationdate";
	public final static String PROPERTY_DISPLAYNAME = "displayname";
	public final static String PROPERTY_GETCONTENTLANGUAGE = "getcontentlanguage";
	public final static String PROPERTY_GETCONTENTLENGTH = "getcontentlength";
	public final static String PROPERTY_GETCONTENTTYPE = "getcontenttype";
	public final static String PROPERTY_GETETAG = "getetag";
	public final static String PROPERTY_GETLASTMODIFIED = "getlastmodified";
	public final static String PROPERTY_LINK = "link";
	public final static String PROPERTY_LOCKDISCOVERY = "lockdiscovery";
	public final static String PROPERTY_LOCKENTRY = "lockentry";
	public final static String PROPERTY_RESOURCETYPE = "resourcetype";
	public final static String PROPERTY_SOURCE = "source";
	public final static String PROPERTY_SUPPORTEDLOCK = "supportedlock";
	
	// properties requested by Windows XP but not found in RFC2518
	public final static String PROPERTY_NAME = "name";
	public final static String PROPERTY_PARENTNAME = "parentname";
	public final static String PROPERTY_HREF = "href";
	public final static String PROPERTY_ISHIDDEN = "ishidden";
	public final static String PROPERTY_ISCOLLECTION = "iscollection";
	public final static String PROPERTY_ISREADONLY = "isreadonly";
	public final static String PROPERTY_CONTENTCLASS = "contentclass";
	public final static String PROPERTY_LASTACCESSED = "lastaccessed";
	public final static String PROPERTY_ISSTRUCTUREDDOCUMENT = "isstructureddocument";
	public final static String PROPERTY_DEFAULTDOCUMENT = "defaultdocument";
	public final static String PROPERTY_ISROOT = "isroot";
	
	// property values
	public final static String PROPERTY_VALUE_FALSE = "0";
	public final static String PROPERTY_VALUE_TRUE = "1";
	
	// XML namespaces
	public final static String NAMESPACE_WEBDAV = "DAV:";
	public final static String NAMESPACE_HTML = "http://www.w3.org/1999/xhtml";
	
	// XML-elements
	public final static String XML_ACTIVELOCK = "activelock";
	public final static String XML_ALLPROP = "allprop";
	public final static String XML_COLLECTION = "collection";
	public final static String XML_DEPTH = "depth";
	public final static String XML_DISPLAYNAME = "displayname";
	public final static String XML_DST = "dst";
	public final static String XML_EXCLUSIVE = "exclusive";
	public final static String XML_GETCONTENTTYPE = "getcontenttype";
	public final static String XML_HREF = "href";
	public final static String XML_KEEPALIVE = "keepalive";
	public final static String XML_LINK = "link";
	public final static String XML_LOCKENTRY = "lockentry";
	public final static String XML_LOCKINFO = "lockinfo";
	public final static String XML_LOCKSCOPE = "lockscope";
	public final static String XML_LOCKTOKEN = "locktoken";
	public final static String XML_LOCKTYPE = "locktype";
	public final static String XML_NAME = "name";
	public final static String XML_OMIT = "omit";
	public final static String XML_PROP = "prop";
	public final static String XML_PROPERTYBEHAVIOR = "propertybehavior";
	public final static String XML_PROPERTYUPDATE = "propertyupdate";
	public final static String XML_PROPFIND = "propfind";
	public final static String XML_PROPNAME = "propname";
	public final static String XML_MULTISTATUS = "multistatus";
	public final static String XML_OWNER = "owner";
	public final static String XML_PROPSTAT = "propstat";
	public final static String XML_RESOURCETYPE = "resourcetype";
	public final static String XML_REMOVE = "remove";
	public final static String XML_RESPONSE = "response";
	public final static String XML_RESPONSEDESCRIPTION = "responsedescription";
	public final static String XML_SET = "set";
	public final static String XML_SHARED = "shared";
	public final static String XML_SRC = "src";
	public final static String XML_STATUS = "status";
	public final static String XML_TIMEOUT = "timeout";
	public final static String XML_WRITE = "write";
	
	// (X)HTML elements
	public final static String XHTML_HTML = "html";
	public final static String XHTML_HEAD = "head";
	public final static String XHTML_TITLE = "title";
	public final static String XHTML_BODY = "body";
	public final static String XHTML_HEADER = "h1";
	public final static String XHTML_UNORDERED_LIST = "ul";
	public final static String XHTML_LIST_ELEM = "li";
	public final static String XHTML_LINK = "a";
	public final static String XHTML_HREF = "href";
}
