package org.openuss.web.servlets.feedservlets;

import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.EnrollmentInfo;
import org.openuss.security.acl.LectureAclEntry;

public abstract class AbstractFeedServlet{
	
	public static final String IF_MODIFIED_SINCE = "If-Modified-Since";

	public static final String APPLICATION_RSS_XML = "application/rss+xml";

	public static final String LAST_MODIFIED = "Last-Modified";

	public static final String DATE_FORMAT = "EEE, dd MMM yyyy hh:mm:ss zzz";

	
	public boolean checkEnrollmentAccess(EnrollmentInfo enrollment){
		if (AcegiUtils.hasPermission(enrollment.getId(), new Integer[] { LectureAclEntry.READ })) {
			return true;
		}
		return false;
	}
}