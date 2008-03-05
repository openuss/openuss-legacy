package org.openuss.web.lecture;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.Course;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** Backing bean for the coursetyperemoveconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$coursetyperemoveconfirmation", scope = Scope.REQUEST)
@View
public class CourseTypeRemoveConfirmationPage extends AbstractCourseTypePage {

	private static final long serialVersionUID = -202000011111888870L;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		breadcrumbs.loadInstituteCrumbs(courseTypeInfo.getInstituteId());
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("coursetype_remove_header"));
		newCrumb.setHint(i18n("coursetype_remove_header"));
		breadcrumbs.addCrumb(newCrumb);
	}
	
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
	}
	
	/**
	 * Course Types
	 * @return List<Course>
	 */
	public List<Course> getCourses() {
		return courseService.findCoursesByCourseType(courseTypeInfo.getId());
	}
	
	/**
	 * Delete course type including all courses and data
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeCourseType() throws LectureException {
		try {
			courseTypeService.removeCourseType(courseTypeInfo.getId());
			setSessionBean("courseTypeInfo", null);
			setSessionBean("courseInfo", null);
			addMessage(i18n("institute_course_type_removed_succeed"));
			return Constants.INSTITUTE_COURSE_TYPES_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(i18n("institute_course_type_cannot_be_removed"));
			return Constants.INSTITUTE_COURSE_TYPES_PAGE;
		}
	}
	
	/**
	 * Validator to check wether the user has accepted the user agreement or not.
	 * 
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			if (toValidate instanceof UIInput) {
				((UIInput) toValidate).setValid(false);
			}
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}
	
}
