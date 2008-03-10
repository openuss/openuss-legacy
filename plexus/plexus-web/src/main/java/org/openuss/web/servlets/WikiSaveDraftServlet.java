package org.openuss.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/** Saves a draft of a wiki post. (currently only session-ping)
 *
 * @author Christian Beer
 */
public class WikiSaveDraftServlet extends HttpServlet {
	private static final long serialVersionUID = 5078393540079721424L;
	
	private static final Logger logger = Logger.getLogger(WikiSaveDraftServlet.class);

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		// currently only kind of "ping"... has to be implemented
		HttpSession sess = req.getSession(false);
		sess.setAttribute("-session-ping-", System.currentTimeMillis());
		
		if (logger.isInfoEnabled()) {
			logger.info("Draft saved on " + SimpleDateFormat.getDateTimeInstance().format(new Date()));
		}
		
		// create xml-answer
		String command = req.getParameter("action");
		
        resp.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
		resp.setHeader("Cache-Control", "public");
        resp.setHeader("Last-Modified", calcModify());
        
		// HTTP/1.1
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// HTTP/1.0
        resp.setHeader("Pragma", "no-cache");
        
        // Set the response format.
        resp.setHeader("Content-Type", "text/xml; charset=UTF-8");

        PrintWriter out = resp.getWriter();
		// Create the XML document header.
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		// Create the main "adapter" node.
		out.println("<adapter command=\"" + command + "\" >");
		
		// successful result
		out.println("<result message=\"success\"/>");
		// error result
		// <error errorNumber="' . $error . '" errorData="' . saveAdapter::convertToXmlAttribute($errorData) . '" />
		
		out.println("</adapter>");
	}
	
	private static final String calcModify() {
		Date mod = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z",Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(mod);
	}
}
