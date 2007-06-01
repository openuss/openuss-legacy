package org.openuss.web.servlets;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openuss.feed.FeedService;
import org.openuss.feed.FeedWrapper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class FeedServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8840385885628513134L;

	private static Logger logger = Logger.getLogger(FeedServlet.class);
	
	private transient FeedService feedService;
	
	@Override
	public void init() throws ServletException {
		logger.info("Initialise OpenUSS-Plexus FeedServlet");
		super.init();

		final WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		feedService = (FeedService) wac.getBean("feedService", FeedService.class);
	}
	

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
		Long enrollmentId = Long.parseLong(req.getParameter("enrollment"));
		String modifiedSince = req.getParameter("If-Modified-Since");
		if (enrollmentId!=null) {
			FeedWrapper  feedWrapper = feedService.getRssFeedForEnrollment(enrollmentId);
			if (modifiedSince!=null&&modifiedSince!=""){
				try {
					if (DateFormat.getDateTimeInstance().parse(modifiedSince).getTime()<feedWrapper.getLastModified().getTime()){
						res.sendError(HttpServletResponse.SC_NOT_MODIFIED);
						return;
					}
				} catch (ParseException e) {
					logger.debug("Malformed header information");
				}
				Calendar c = new GregorianCalendar();
			}
			//res.setContentType("application/xml");
			res.setHeader("ETag", "46a963-1316-70caa880");
			res.getWriter().write(feedWrapper.getWriter().toString());
			res.setHeader("Last-Modified", feedWrapper.getLastModified().toString());
		}
	}
	
}