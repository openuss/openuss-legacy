package org.openuss.web.servlets;

import java.text.DateFormat;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.openuss.web.feeds.EnrollmentFeed;
import org.openuss.web.feeds.FeedWrapper;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class EnrollmentFeedController implements Controller{

	private static final String DATE_FORMAT = "EEE, dd MMM yyyy hh:mm:ss zzz";

	/**
	 * 
	 */
	private static final long serialVersionUID = -8840385885628513134L;

	private static Logger logger = Logger.getLogger(EnrollmentFeedController.class);
	
	private EnrollmentFeed enrollmentFeed;
	
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Long enrollmentId = Long.parseLong(req.getParameter("enrollment"));
		String modifiedSince = req.getParameter("If-Modified-Since");
		
		if (enrollmentId!=null) {
			FeedWrapper  feedWrapper = enrollmentFeed.getFeed(enrollmentId);
			if (feedWrapper==null){
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
			
			if (modifiedSince!=null&&modifiedSince!=""){
				try {
					if (DateFormat.getDateTimeInstance().parse(modifiedSince).getTime()<feedWrapper.getLastModified().getTime()){
						res.sendError(HttpServletResponse.SC_NOT_MODIFIED);
						return null;
					}
				} catch (ParseException e) {
					logger.debug("Malformed header information");
				}
			}
			res.setContentType("application/rss+xml");
			res.getWriter().write(feedWrapper.getWriter().toString());
			String lastModified = DateFormatUtils.format(feedWrapper.getLastModified(), DATE_FORMAT);
			res.setHeader("Last-Modified", lastModified);
			return null;
		}
		res.sendError(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

	public EnrollmentFeed getEnrollmentFeed() {
		return enrollmentFeed;
	}

	public void setEnrollmentFeed(EnrollmentFeed enrollmentFeed) {
		this.enrollmentFeed = enrollmentFeed;
	}
	
}