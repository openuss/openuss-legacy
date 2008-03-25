package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.CourseServiceException;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

/** Backing bean for the courseremoveconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$courseremoveconfirmation", scope = Scope.REQUEST)
@View
public class CourseRemoveConfirmationPage extends AbstractCoursePage {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(CourseRemoveConfirmationPage.class);

	private static final long serialVersionUID = -202000019652888870L;

	@Prerender
	public void prerender() throws LectureException {
		try {
			super.prerender();
			if (isRedirected()){
				return;
			}
			breadcrumbs.loadCourseCrumbs(courseInfo);
			
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("course_remove_header"));
			newCrumb.setHint(i18n("course_remove_header"));
			breadcrumbs.addCrumb(newCrumb);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Delete course including all data
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeCourse() {
		try {
			courseService.removeCourse(courseInfo.getId());
			instituteInfo = new InstituteInfo();
			instituteInfo.setId(courseInfo.getInstituteId());
			setBean(Constants.INSTITUTE_INFO, instituteInfo);
			setBean("courseInfo", null);
			addMessage(i18n("institute_course_removed_succeed"));
			return Constants.INSTITUTE_COURSES_PAGE;
		} catch (CourseServiceException e) {
			addMessage(i18n("institute_course_cannot_be_removed"));
			return Constants.INSTITUTE_COURSES_PAGE;
		}
	}
}
