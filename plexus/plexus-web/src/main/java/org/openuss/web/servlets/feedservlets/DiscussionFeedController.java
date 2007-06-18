package org.openuss.web.servlets.feedservlets;

import org.openuss.web.feeds.DiscussionFeed;
import org.openuss.web.feeds.FeedWrapper;
import org.springframework.web.servlet.mvc.Controller;

public class DiscussionFeedController extends AbstractFeedServlet implements Controller {

	private DiscussionFeed discussionFeed;

	public DiscussionFeed getDiscussionFeed() {
		return discussionFeed;
	}

	public void setDiscussionFeed(DiscussionFeed discussionFeed) {
		this.discussionFeed = discussionFeed;
	}

	@Override
	protected FeedWrapper getFeedWrapper(Long enrollmentId) {
		return discussionFeed.getFeed(enrollmentId);
	}

	@Override
	protected String domainParameterName() {
		return "enrollment";
	}

}