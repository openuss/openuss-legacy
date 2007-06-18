package org.openuss.web.servlets.feedservlets;

import org.openuss.web.feeds.EnrollmentFeed;
import org.openuss.web.feeds.FeedWrapper;
import org.springframework.web.servlet.mvc.Controller;

public class EnrollmentFeedController extends AbstractFeedServlet implements Controller{

	private static final long serialVersionUID = -8840385885628513134L;

	private EnrollmentFeed enrollmentFeed;

	public EnrollmentFeed getEnrollmentFeed() {
		return enrollmentFeed;
	}

	public void setEnrollmentFeed(EnrollmentFeed enrollmentFeed) {
		this.enrollmentFeed = enrollmentFeed;
	}

	@Override
	protected FeedWrapper getFeedWrapper(Long enrollmentId) {
		return enrollmentFeed.getFeed(enrollmentId);
	}

	@Override
	protected String domainParameterName() {
		return "enrollment";
	}
	
}