package org.openuss.web.servlets.feedservlets;

import org.openuss.web.feeds.FeedWrapper;
import org.openuss.web.feeds.MailingListFeed;
import org.springframework.web.servlet.mvc.Controller;

public class MailingListFeedController extends AbstractFeedServlet implements Controller {

	private MailingListFeed mailingListFeed;

	public MailingListFeed getMailingListFeed() {
		return mailingListFeed;
	}

	public void setMailingListFeed(MailingListFeed mailingListFeed) {
		this.mailingListFeed = mailingListFeed;
	}

	@Override
	protected String domainParameterName() {
		return "course";
	}

	@Override
	protected FeedWrapper getFeedWrapper(Long domainId) {
		return mailingListFeed.getFeed(domainId);
	}

}