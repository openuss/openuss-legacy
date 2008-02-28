package org.openuss.web.dav;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.openuss.webdav.IOContext;
import org.openuss.webdav.WebDAVConstants;
import org.openuss.webdav.WebDAVException;
import org.openuss.webdav.WebDAVResourceException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Contains WebDAV helper functions. See RFC 2518 for details.
 * 
 * This class resides in plexus-web instead of plexus-api because it uses {@link HttpServletRequest} and friends.
 */
public class WebDAVUtils {
	
	/* HTTP */
	
	/**
	 * Parses the request body as a XML document.
	 * @param request Reference to the request of the servlet.
	 * @return The request body parsed as a XML document.
	 * @throws WebDAVException A message for the client because the document could not be parsed.
	 */
	public static Document getRequestDocument(HttpServletRequest request) throws WebDAVException {
		Document document = null;
		
		// return null, if request body is empty
		if (request.getContentLength() == 0) {
			return document;
		}
		
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
		} catch (IOException ex) {
			// error while operating with streams
			throw new WebDAVException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex);
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
	
	
	/* Conversion */
	
	/**
	 * Creates a new PropertyResponse out of a WebDAVResourceException.
	 * 
	 * @param wre The exception, containing information about the source.
	 * @return A new PropertyResponse object.
	 */
	public static PropertyResponse createFromResourceException(WebDAVResourceException wre) {
		return PropertyResponse.createFromResourceException(wre);
	}


	/* XML */	

	/**
	 * @return A new standard XML document.
	 */
	public static Document newDocument() {
		// new Document(). dom4j can not be used because the interface in plexus-api requires us to fulfil org.w3c.dom. 
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		return db.newDocument();
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
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document res;
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			res = db.parse(new InputSource(new StringReader(s)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ParserConfigurationException e) {
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
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document res;
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			res = db.parse(new InputSource(is));
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
			
		return res;		
	}
}
