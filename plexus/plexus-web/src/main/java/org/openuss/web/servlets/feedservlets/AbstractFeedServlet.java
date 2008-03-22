package org.openuss.web.servlets.feedservlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.feeds.FeedWrapper;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractFeedServlet {

	private static final Logger logger = Logger.getLogger(AbstractFeedServlet.class);

	public static final String IF_MODIFIED_SINCE = "If-Modified-Since";

	public static final String APPLICATION_RSS_XML = "application/rss+xml";

	public static final String LAST_MODIFIED = "Last-Modified";

	public static final String DATE_FORMAT = "EEE, dd MMM yyyy hh:mm:ss zzz";

	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Long domainIdentifier = Long.parseLong(req.getParameter(domainParameterName()));

		if (domainIdentifier != null) {
			if (!hasPermissions(domainIdentifier)) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}

			FeedWrapper feedWrapper = getFeedWrapper(domainIdentifier);

			sendFeed(req, res, feedWrapper);
		}

		return null;
	}

	/**
	 * Sends the actual feed.
	 * 
	 * @param req
	 *            The request
	 * @param res
	 *            Response facility.
	 * @param feedWrapper
	 *            The feed wrapper determining the feed.
	 * @return true if a non-error message was sent.
	 * @throws IOException
	 *             Indicates a problem when writing the HTTP answer.
	 */
	protected boolean sendFeed(HttpServletRequest req, HttpServletResponse res, FeedWrapper feedWrapper)
			throws IOException {
		if (feedWrapper == null) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

		String modifiedSince = req.getParameter(IF_MODIFIED_SINCE);
		if (StringUtils.isNotBlank(modifiedSince) && feedWrapper.getLastModified() != null) {
			try {
				if (DateFormat.getDateTimeInstance().parse(modifiedSince).getTime() < feedWrapper.getLastModified().getTime()) {
					res.sendError(HttpServletResponse.SC_NOT_MODIFIED);
					return true;
				}
			} catch (ParseException e) {
				logger.debug("Malformed header information");
			}
		} else {
			res.setContentType(APPLICATION_RSS_XML);
			if (feedWrapper.getWriter() != null) {
				res.getWriter().write(feedWrapper.getWriter().toString());
			}

			if (feedWrapper.getLastModified() != null) {
				String lastModified = DateFormatUtils.format(feedWrapper.getLastModified(), DATE_FORMAT);
				res.setHeader(LAST_MODIFIED, lastModified);
			}
		}
		return true;
	}

	/**
	 * Hook method to define the domain object request parameter name
	 * 
	 * @return name of the parameter
	 */
	protected abstract String domainParameterName();

	/**
	 * Hook method to retrieve the feed wrapper
	 * 
	 * @param domainId -
	 *            unique domain identifier
	 * @return
	 */
	protected abstract FeedWrapper getFeedWrapper(Long domainId);

	/**
	 * Has the current user the permission to view the rss feed?
	 * 
	 * @param domainIdentifier
	 * @return true - user may access the rss feed
	 */
	public boolean hasPermissions(Long domainIdentifier) {
		return AcegiUtils.hasPermission(domainIdentifier, new Integer[] { LectureAclEntry.READ });
	}

}
