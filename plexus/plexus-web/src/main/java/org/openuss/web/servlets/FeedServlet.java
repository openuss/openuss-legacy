package org.openuss.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openuss.feed.FeedService;
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
		if (enrollmentId!=null) {
			String xmlFeed = feedService.getRssFeedForEnrollment(enrollmentId);
			res.setContentType("application/xml; charset=utf-8");
			res.getWriter().write(xmlFeed);
			logger.debug(xmlFeed);
		}
	}
	
}