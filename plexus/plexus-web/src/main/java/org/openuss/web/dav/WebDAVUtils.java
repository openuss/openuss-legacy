package org.openuss.web.dav;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Contains WebDAV helper functions. See RFC 2518 for details.
 * 
 * This class resides in plexus-web instead of plexus-api because it uses {@link HttpServletRequest} and friends.
 */
public final class WebDAVUtils {
	/* XML helper objects */
	private final static DocumentBuilderFactory dbf;
	private final static DocumentBuilder db;
	static {
		dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	private final static String DATEFORMAT_RFC1123_PATTERN = "EEE, dd MMM yyyy HH:mm:ss zzz";
	private final static String DATEFORMAT_INTERNET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	protected final static DateFormat rfc1123DateFormat;
	protected final static DateFormat internetDateFormat;
	static {
		TimeZone gmtTz = TimeZone.getTimeZone("GMT");
		rfc1123DateFormat = new SimpleDateFormat(DATEFORMAT_RFC1123_PATTERN, Locale.US);
		rfc1123DateFormat.setTimeZone(gmtTz);
		
		internetDateFormat = new SimpleDateFormat(DATEFORMAT_INTERNET_PATTERN, Locale.US);
		internetDateFormat.setTimeZone(gmtTz);
	}
	

	
	/* HTTP */
	
	/**
	 * Parses the request body as a XML document.
	 * @param request Reference to the request of the servlet.
	 * @return The request body parsed as a XML document. null if no document was sent
	 * @throws WebDAVException A message for the client because the document could not be parsed.
	 * @throws IOException On reading problems.
	 */
	public static Document getRequestDocument(HttpServletRequest request) throws WebDAVException, IOException {
		// return null if request body is empty
		if (request.getContentLength() == 0) {
			return null;
		}
		
		Document document = null;
		
		try {
			// retrieve the input stream from request
			InputStream inputStream = request.getInputStream();

			if (inputStream != null) {
				// create buffered stream and snoop for content, since client can send fragmented request
				InputStream bufferedInputStream = new BufferedInputStream(inputStream);
				bufferedInputStream.mark(1);
				boolean hasContent = (bufferedInputStream.read() != -1);
				bufferedInputStream.reset();
				
				// read content of request body as string, if present
				if (hasContent) {
					document = streamToDocument(bufferedInputStream);
				}
			}
		} catch (SAXException ex) {
			// parsing failed, send 400 (Bad Request) to client
			throw new WebDAVException(HttpServletResponse.SC_BAD_REQUEST, ex);
		}
		
		return document;		
	}
	
	/**
	 * Reads value of depth header as required for some methods. See RFC2518 for further details.
	 * @param request Reference to the request of the servlet.
	 * @return The value of the depth header.
	 * @throws WebDAVException If an invalid value is supplied.
	 */
	public static int readDepthHeader(HttpServletRequest request) throws WebDAVException {
		// retrieve value for header from request
		String depthHeaderValue = request.getHeader(WebDAVConstants.HEADER_DEPTH);
		
		// empty header has to be interpreted as infinity, if depth header is expected
		if ((depthHeaderValue == null) || (depthHeaderValue.length() == 0) || depthHeaderValue.equalsIgnoreCase(WebDAVConstants.DEPTH_INFINITY_STRING)) {
			return WebDAVConstants.DEPTH_INFINITY;
		} else if (depthHeaderValue.equalsIgnoreCase(WebDAVConstants.DEPTH_0_STRING)) {
			return WebDAVConstants.DEPTH_0;
		} else if (depthHeaderValue.equalsIgnoreCase(WebDAVConstants.DEPTH_1_STRING)) {
			return WebDAVConstants.DEPTH_1;
		} else {
			// invalid or not supported value for depth header found
			throw new WebDAVException(HttpServletResponse.SC_BAD_REQUEST, "Invalid value for depth header.");
		}
	}
	
	/**
	 * Returns the destination header value of the request.
	 * @param request Reference to the request of the servlet.
	 * @return The destination header value.
	 */
	public static String readDestinationHeader(HttpServletRequest request) {
		return request.getHeader(WebDAVConstants.HEADER_DESTINATION);
	}
	
	/**
	 * Returns the value of the overwrite header value as a boolean.
	 * @param request Reference to the request of the servlet.
	 * @return Value of the override header.
	 * @throws WebDAVException On illegal values.
	 */
	public static boolean readOverwriteHeader(HttpServletRequest request) throws WebDAVException {
		String overwriteHeaderValue = request.getHeader(WebDAVConstants.HEADER_OVERWRITE);
		if ((overwriteHeaderValue == null) || (overwriteHeaderValue.equals(WebDAVConstants.HEADER_OVERWRITE_TRUE))) {
			// assume true if no header value is present, according to RFC 2518 9.6
			return true;
		} else if (overwriteHeaderValue.equals(WebDAVConstants.HEADER_OVERWRITE_FALSE)) {
			return false;
		} else {
			// invalid or not supported value for depth header found
			throw new WebDAVException(HttpServletResponse.SC_BAD_REQUEST, "Invalid value for depth header.");
		}
	}
	

	/* (HTTP) I/O */
	
	/**
	 * @param response The response that allows writing to the client.
	 * @param c The context of data to write.
	 * @throws IOException On writing errors. 
	 */
	public static void writeToClient(HttpServletResponse response, IOContext c) throws IOException {
		// Content language
		if (c.getContentLanguage() != null) {
			response.setHeader(WebDAVConstants.HEADER_CONTENT_LANGUAGE, c.getContentLanguage());
		}
		
		// Content length
		response.setHeader(WebDAVConstants.HEADER_CONTENT_LENGTH, String.valueOf(c.getContentLength()));
		
		// Content type
		if (c.getContentType() != null) {
			response.setHeader(WebDAVConstants.HEADER_CONTENT_TYPE, c.getContentType());
		}
			
		// Etag
		if (c.getETag() != null) {
			response.setHeader(WebDAVConstants.HEADER_ETAG, c.getETag());
		}
		
		// Modification time
		if (c.getModificationTime() != null) {
			response.setDateHeader(WebDAVConstants.HEADER_LAST_MODIFIED, c.getModificationTime().getNanos());			
		}
		
		// Main data stream
		IOUtils.copyLarge(c.getInputStream(), response.getOutputStream());
	}
	
	/**
	 * @param request The HTTP request object.
	 * @return An IO context with all information set by the client.
	 * @throws IOException On reading errors.
	 */
	public static IOContext getClientInputContext(HttpServletRequest request) throws IOException {
		IOContextImpl ioc = new IOContextImpl();
		
		ioc.setContentLanguage(request.getHeader(WebDAVConstants.HEADER_CONTENT_LANGUAGE));
		ioc.setContentLength(request.getIntHeader(WebDAVConstants.HEADER_CONTENT_LENGTH));
		ioc.setContentType(request.getHeader(WebDAVConstants.HEADER_CONTENT_TYPE));
		ioc.setETag(null);
		ioc.setModificationTime(new Timestamp(request.getDateHeader(WebDAVConstants.HEADER_LAST_MODIFIED)));
		ioc.setInputStream(request.getInputStream());
		
		return ioc;
	}


	/* XML */	

	/**
	 * @return A new standard XML document.
	 */
	public static Document newDocument() {
		// new Document(). dom4j can not be used because the interface in plexus-api requires us to fulfil org.w3c.dom. 
		DocumentBuilder db = newDocumentBuilder();
		
		return db.newDocument();
	}
	
	/**
	 * @return A DocumentBuilderFactory configured for use with WebDAV documents.
	 */
	public static DocumentBuilderFactory newDocumentBuilderFactory() {
		return dbf;
	}
	
	/**
	 * @return A DocumentBuilder configured for use with WebDAV documents.
	 */
	public static DocumentBuilder newDocumentBuilder() {
		return db;
	}
	
	/**
	 * Converts an XML document to a string
	 * 
	 * @param doc The document to convert.
	 * @return The XML string representing the document. 
	 */
	public static String documentToString(Document doc) {
		StringWriter sw = new StringWriter();
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t;
		try {
			t = tf.newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(doc), new StreamResult(sw));
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		
		return sw.toString();
	}
	
	/**
	 * Parses a string to an XML document.
	 * 
	 * @param s The input string.
	 * @return The document representing the input string.
	 * @throws SAXException On a malformed document.
	 */
	public static Document stringToDocument(String s) throws SAXException {
		DocumentBuilder db = newDocumentBuilder();
		Document res;
		
		try {
			res = db.parse(new InputSource(new StringReader(s)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
			
		return res;
	}
	
	/**
	 * Reads a serialized XML document to a Document object.
	 * 
	 * @param is The input stream to read from.
	 * @return The deserialized document.
	 * @throws SAXException On XML errors.
	 * @throws IOException On reading errors.
	 */
	public static Document streamToDocument(InputStream is) throws SAXException, IOException {
		DocumentBuilder db = newDocumentBuilder();
		Document res;
		
		res = db.parse(new InputSource(is));
			
		return res;		
	}
	
	/**
	 * Checks whether an XML node is a DAV element with the specified local name. 
	 * 
	 * @param n The node to check.
	 * @param localName The expected local name.
	 * @return true Iff the node is an Element object in the DAV namespace with the local name localName.
	 */
	public static boolean isDavElement(Node n, String localName) {
		return (n != null) && (n instanceof Element) &&
			(n.getNamespaceURI().equals(WebDAVConstants.NAMESPACE_WEBDAV) &&
			(n.getLocalName().equals(localName)));
			
	}
	
	/* Conversions */
	
	/**
	 * Converts a date object to a timestamp one.
	 * 
	 * @param d The date object to convert.
	 * @return The timestamp representing the specified date.
	 */
	public static Timestamp timestampToDate(Date d) {
		return new Timestamp(d.getTime());
	}
	
	/**
	 * Formats a date in the rfc1123-format as defined in RFC 2616 3.3.1.
	 * 
	 * @param d The date to represent.
	 * @return A WebDAV-compatible string representing the date as defined by RFC2616 3.3.1
	 */
	public static String dateToRFC1123String(Date d) {
		return rfc1123DateFormat.format(d);		
	}
	
	public static String dateToInternetString(Date d) {
		return internetDateFormat.format(d);
	}
}
