package org.openuss.web.servlets.feedservlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openuss.web.feeds.FeedWrapper;
import org.openuss.web.feeds.GlobalFeed;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 */
public class GlobalFeedController extends AbstractFeedServlet implements Controller{
	private GlobalFeed globalFeed;
	
	public GlobalFeed getGlobalFeed() {
		return globalFeed;
	}

	public void setGlobalFeed(GlobalFeed globalFeed) {
		this.globalFeed = globalFeed;
	}

	
	protected FeedWrapper getFeedWrapper() {
		return globalFeed.getFeed();
	}

	@Override
	protected String domainParameterName() {
		return "global";
	}

	@Override
	public boolean hasPermissions(Long domainIdentifier) {
		return true; // anonymous access allowed
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		FeedWrapper feedWrapper = getFeedWrapper();
		
		sendFeed(req, res, feedWrapper);
		
		return null;
	}

	/**
	 * This method has no application for a global, domain-independet feed like this.
	 */
	@Override
	protected FeedWrapper getFeedWrapper(Long domainId) {
		return null;
	}

}