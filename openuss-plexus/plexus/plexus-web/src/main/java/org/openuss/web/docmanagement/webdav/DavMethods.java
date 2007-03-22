package org.openuss.web.docmanagement.webdav;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.5
 */
public class DavMethods {
	public static final int DAV_OPTIONS = 1;
	public static final int DAV_MOVE = 2;
	public static final int DAV_LOCK = 3;
	public static final int DAV_UNLOCK = 4;
	public static final int DAV_GET = 5;
	public static final int DAV_PUT = 6;
	public static final int DAV_POST = 7;
	public static final int DAV_HEAD = 8;
	public static final int DAV_DELETE = 9;
	public static final int DAV_COPY = 10;
	public static final int DAV_MKCOL = 11;
	public static final int DAV_PROPFIND = 12;
	public static final int DAV_PROPPATCH = 13;
	
	public static int getMethodCode(String method) {
		return 0;
	}
}
