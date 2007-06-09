package org.openuss.web.servlets.feedservlets;

import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.EnrollmentInfo;
import org.openuss.security.acl.LectureAclEntry;

public abstract class AbstractFeedServlet{
	public boolean checkEnrollmentAccess(EnrollmentInfo enrollment){
		if (AcegiUtils.hasPermission(enrollment.getId(), new Integer[] { LectureAclEntry.READ })) {
			return true;
		}
		return false;
	}
}