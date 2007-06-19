package org.openuss.web.servlets.feedservlets;

import org.openuss.web.feeds.DocumentsFeed;
import org.openuss.web.feeds.FeedWrapper;
import org.springframework.web.servlet.mvc.Controller;

public class DocumentsFeedController extends AbstractFeedServlet implements Controller {

	private DocumentsFeed documentFeed;

	protected FeedWrapper getFeedWrapper(Long courseId) {
		return documentFeed.getFeed(courseId);
	}

	public DocumentsFeed getDocumentFeed() {
		return documentFeed;
	}

	public void setDocumentFeed(DocumentsFeed documentFeed) {
		this.documentFeed = documentFeed;
	}

	@Override
	protected String domainParameterName() {
		return "course";
	}

}