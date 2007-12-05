package org.openuss.web.servlets.feedservlets;

import java.text.DateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;

import org.apache.commons.lang.time.DateFormatUtils;
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
	
	private static final Logger logger = Logger.getLogger(AbstractFeedServlet.class);
	
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
		
		String modifiedSince = req.getParameter(IF_MODIFIED_SINCE);

		

			FeedWrapper feedWrapper = getFeedWrapper();

			if (feedWrapper == null) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}

			if (modifiedSince != null && modifiedSince != "" && feedWrapper.getLastModified() != null) {
				try {
					if (DateFormat.getDateTimeInstance().parse(modifiedSince).getTime() < feedWrapper.getLastModified()
							.getTime()) {
						res.sendError(HttpServletResponse.SC_NOT_MODIFIED);
						return null;
					}
				} catch (ParseException e) {
					logger.debug("Malformed header information");
				}
			}
			res.setContentType(APPLICATION_RSS_XML);
			res.getWriter().write(feedWrapper.getWriter().toString());

			if (feedWrapper.getLastModified() != null) {
				String lastModified = DateFormatUtils.format(feedWrapper.getLastModified(), DATE_FORMAT);
				res.setHeader(LAST_MODIFIED, lastModified);
			}
			return null;
		}

	@Override
	protected FeedWrapper getFeedWrapper(Long domainId) {
		// TODO Auto-generated method stub
		return null;
	}

}