package org.openuss.web.servlets.feedservlets;

import java.text.DateFormat;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.openuss.lecture.EnrollmentInfo;
import org.openuss.web.feeds.FeedWrapper;
import org.openuss.web.feeds.MailingListFeed;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class MailingListFeedController extends AbstractFeedServlet implements Controller{

	private static Logger logger = Logger.getLogger(MailingListFeedController.class);
	
	private MailingListFeed mailingListFeed;
	
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Long enrollmentId = Long.parseLong(req.getParameter("enrollment"));
		String modifiedSince = req.getParameter(IF_MODIFIED_SINCE);
		
		if (enrollmentId!=null) {
			EnrollmentInfo enrollment = new EnrollmentInfo();
			enrollment.setId(enrollmentId);
			if (!checkEnrollmentAccess(enrollment)){
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
			FeedWrapper  feedWrapper = mailingListFeed.getFeed(enrollmentId);
			if (feedWrapper==null){
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
			
			if (modifiedSince!=null&&modifiedSince!=""&&feedWrapper.getLastModified()!=null){
				try {
					if (DateFormat.getDateTimeInstance().parse(modifiedSince).getTime()<feedWrapper.getLastModified().getTime()){
						res.sendError(HttpServletResponse.SC_NOT_MODIFIED);
						return null;
					}
				} catch (ParseException e) {
					logger.debug("Malformed header information");
				}
			}
			res.setContentType(APPLICATION_RSS_XML);
			res.getWriter().write(feedWrapper.getWriter().toString());
			
			if (feedWrapper.getLastModified()!=null){
				String lastModified = DateFormatUtils.format(feedWrapper.getLastModified(), DATE_FORMAT);
				res.setHeader(LAST_MODIFIED, lastModified);
			}
			return null;
		}
		res.sendError(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

	public MailingListFeed getMailingListFeed() {
		return mailingListFeed;
	}

	public void setMailingListFeed(MailingListFeed mailingListFeed) {
		this.mailingListFeed = mailingListFeed;
	}
	
}