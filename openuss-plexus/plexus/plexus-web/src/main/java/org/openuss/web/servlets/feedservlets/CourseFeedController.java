package org.openuss.web.servlets.feedservlets;

import org.openuss.web.feeds.CourseFeed;
import org.openuss.web.feeds.FeedWrapper;
import org.springframework.web.servlet.mvc.Controller;

public class CourseFeedController extends AbstractFeedServlet implements Controller{

	private static final long serialVersionUID = -8840385885628513134L;

	private CourseFeed courseFeed;

	public CourseFeed getCourseFeed() {
		return courseFeed;
	}

	public void setCourseFeed(CourseFeed courseFeed) {
		this.courseFeed = courseFeed;
	}

	@Override
	protected FeedWrapper getFeedWrapper(Long courseId) {
		return courseFeed.getFeed(courseId);
	}

	@Override
	protected String domainParameterName() {
		return "course";
	}
	
}