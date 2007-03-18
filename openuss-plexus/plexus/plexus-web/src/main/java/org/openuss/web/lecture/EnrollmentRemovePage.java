package org.openuss.web.lecture;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

/**
 * Enrollment Edit Page Controller
 * @author Ingo Dueppe
 *
 */
@Bean(name="views$secured$lecture$enrollmentremove",scope=Scope.REQUEST)
@View
public class EnrollmentRemovePage extends AbstractEnrollmentPage {

	private static final Logger logger = Logger.getLogger(EnrollmentRemovePage.class);

	private static final long serialVersionUID = 8821048605517398410L;
	
	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		enrollment = lectureService.getEnrollment(enrollment.getId());
		setSessionBean(Constants.ENROLLMENT, enrollment );
	}
	
	/**
	 * Remove the current enrollment and all its data
	 * @return outcome
	 * @throws LectureException 
	 */
	public String removeEnrollment() throws LectureException {
		logger.trace("removing enrollment");
		lectureService.removeEnrollment(enrollment.getId());
		addMessage(i18n("faculty_enrollment_removed_succeed"));
		return Constants.FACULTY_PERIODS_PAGE;
	}
	
	/**
	 * Validator to check wether or not the removement is accepted
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput)toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}
}