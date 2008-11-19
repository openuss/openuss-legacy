package org.openuss.web.servlets.feedservlets;

import org.openuss.web.feeds.FeedWrapper;
import org.openuss.web.feeds.InstituteFeed;
import org.springframework.web.servlet.mvc.Controller;

/**
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 */
public class InstituteFeedController extends AbstractFeedServlet implements Controller{

	private InstituteFeed instituteFeed;
	
	public InstituteFeed getInstituteFeed() {
		return instituteFeed;
	}

	public void setInstituteFeed(InstituteFeed instituteFeed) {
		this.instituteFeed = instituteFeed;
	}

	@Override
	protected FeedWrapper getFeedWrapper(Long domainId) {
		return instituteFeed.getFeed(domainId);
	}

	@Override
	protected String domainParameterName() {
		return "institute";
	}

	@Override
	public boolean hasPermissions(Long domainIdentifier) {
		return true; // anonymous access allowed
	}

}