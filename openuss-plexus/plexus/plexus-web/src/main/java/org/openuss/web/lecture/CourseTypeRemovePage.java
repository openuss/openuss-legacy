package org.openuss.web.lecture;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * Removing courseType from institute
 * 
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$coursetyperemove", scope = Scope.REQUEST)
@View
public class CourseTypeRemovePage extends AbstractLecturePage {

	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		reloadCourseType();
	}


	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		reloadCourseType();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("coursetype_remove_header"));
		crumb.setHint(i18n("coursetype_remove_header"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}
	
	private void reloadCourseType() throws LectureException {
		courseType = lectureService.getCourseType(courseType.getId());
		setSessionBean(Constants.COURSE_TYPE, courseType);
	}

	/**
	 * Remove the courseType
	 * 
	 * @return outcome
	 * @throws LectureException 
	 */
	public String removeCourseType() throws LectureException {
		lectureService.removeCourseType(courseType.getId());
		addMessage(i18n("institute_message_remove_coursetype_succeed"));
		removeSessionBean(Constants.COURSE_TYPE);
		return Constants.INSTITUTE_COURSE_TYPES_PAGE;
	}

	/**
	 * Cancel removing courseTypes
	 * 
	 * @return outcome
	 */
	public String cancelCourseType() {
		removeSessionBean(Constants.COURSE_TYPE);
		return Constants.INSTITUTE_COURSE_TYPES_PAGE;
	}

	/**
	 * Validator to check wether the user has accepted the user agreement or
	 * not.
	 * 
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}
}
