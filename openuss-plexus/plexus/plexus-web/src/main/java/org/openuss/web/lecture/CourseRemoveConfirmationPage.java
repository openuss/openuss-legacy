package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

/** Backing bean for the courseremoveconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$courseremoveconfirmation", scope = Scope.REQUEST)
@View
public class CourseRemoveConfirmationPage extends AbstractCoursePage {

	private static final long serialVersionUID = -202000019652888870L;

	@Prerender
	public void prerender() throws LectureException {
		try {
			super.prerender();
			breadcrumbs.loadCourseCrumbs(courseInfo);
			
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("course_remove_header"));
			newCrumb.setHint(i18n("course_remove_header"));
			breadcrumbs.addCrumb(newCrumb);
		} catch (Exception e) {
		
		}
	}
	
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
	}
	
	/**
	 * Delete course including all data
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeCourse() throws LectureException {
		try {
			courseService.removeCourse(courseInfo.getId());
			setSessionBean("courseInfo", null);
			addMessage(i18n("institute_course_removed_succeed"));
			return Constants.INSTITUTE_COURSES_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(i18n("institute_course_cannot_be_removed"));
			return Constants.INSTITUTE_COURSES_PAGE;
			
		}
		
	}
	
}
