package org.openuss.web.servlets.feedservlets;

import org.openuss.web.feeds.FeedWrapper;
import org.openuss.web.feeds.NewsletterFeed;
import org.springframework.web.servlet.mvc.Controller;

public class NewsletterFeedController extends AbstractFeedServlet implements Controller {

	private NewsletterFeed newsletterFeed;

	public NewsletterFeed getNewsletterFeed() {
		return newsletterFeed;
	}

	public void setNewsletterFeed(NewsletterFeed newsletterFeed) {
		this.newsletterFeed = newsletterFeed;
	}

	@Override
	protected String domainParameterName() {
		return "course";
	}

	@Override
	protected FeedWrapper getFeedWrapper(Long domainId) {
		return newsletterFeed.getFeed(domainId);
	}

}