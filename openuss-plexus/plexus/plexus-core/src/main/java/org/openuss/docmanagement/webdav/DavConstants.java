package org.openuss.docmanagement.webdav;

import org.dom4j.Namespace;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public class DavConstants {
	public final static int DEPTH_0 = 0;
	public final static int DEPTH_1 = 1;
	public final static int DEPTH_INFINITY = Integer.MAX_VALUE;
	public final static String DEPTH_INFINITY_STRING = "";
	
	public final static String HEADER_CONTENT_LANGUAGE = "";
	public final static String HEADER_CONTENT_LENGTH = "";
	public final static String HEADER_CONTENT_TYPE = "";
	public final static String HEADER_DAV = "";
	public final static String HEADER_DEPTH = "";
	public final static String HEADER_DESTINATION = "";
	public final static String HEADER_ETAG = "";
	public final static String HEADER_LAST_MODIFIED = "";
	public final static String HEADER_OVERWRITE = "";
	
	public final static String PROPERTY_CREATIONDATE = "";
	public final static String PROPERTY_DISPLAYNAME = "";
	public final static String PROPERTY_RESOURCETYPE = "";
	
	public final static Namespace XML_DAV_NAMESPACE = new Namespace("D", "DAV:");
	public final static String XML_ALLPROP = "";
	public final static String XML_COLLECTION = "";
	public final static String XML_HREF = "";
	public final static String XML_PROP = "";
	public final static String XML_PROPFIND = "";
	public final static String XML_PROPNAME = "";
	public final static String XML_MULTISTATUS = "";
	public final static String XML_PROPSTAT = "";
	public final static String XML_RESPONSE = "";
	public final static String XML_RESPONSEDESCRIPTION = "";
	public final static String XML_STATUS = "";
}
