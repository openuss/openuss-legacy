package org.openuss.web.course;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

/** Backing bean for the courseremoveconfirmation.xhtml view.
 * 
 * @author Sebastian Roekens
 * 
 */ 
@Bean(name = "views$secured$course$confirmmembercancel", scope = Scope.REQUEST)
@View
public class RemoveCourseMembershipPage extends AbstractCoursePage {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(RemoveCourseMembershipPage.class);

	@Prerender
	public void prerender() throws LectureException {
		try {
			super.prerender();
			if (isRedirected()){
				return;
			}
			breadcrumbs.loadCourseCrumbs(courseInfo);
			
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("membership_remove_header"));
			newCrumb.setHint(i18n("membership_remove_header"));
			breadcrumbs.addCrumb(newCrumb);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Ends Membership of course member
	 * @return outcome
	 */
	public String removeMembership() {
		CourseMemberInfo cmi = new CourseMemberInfo();
		cmi.setCourseId(courseInfo.getId());
		cmi.setUserId(user.getId());
		courseService.removeMember(cmi);
		return Constants.DESKTOP;
	}
}
