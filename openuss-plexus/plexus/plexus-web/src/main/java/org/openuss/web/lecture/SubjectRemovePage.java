package org.openuss.web.lecture;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * Removing subject from faculty
 * 
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$subjectremove", scope = Scope.REQUEST)
@View
public class SubjectRemovePage extends AbstractLecturePage {

	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		reloadSubject();
	}


	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		reloadSubject();
	}

	private void reloadSubject() throws LectureException {
		subject = lectureService.getSubject(subject.getId());
		setSessionBean(Constants.SUBJECT, subject);
	}

	/**
	 * Remove the subject
	 * 
	 * @return outcome
	 * @throws LectureException 
	 */
	public String removeSubject() throws LectureException {
		lectureService.removeSubject(subject.getId());
		addMessage(i18n("faculty_message_remove_subject_succeed"));
		removeSessionBean(Constants.SUBJECT);
		return Constants.FACULTY_SUBJECTS_PAGE;
	}

	/**
	 * Cancel removing subjects
	 * 
	 * @return outcome
	 */
	public String cancelSubject() {
		removeSessionBean(Constants.SUBJECT);
		return Constants.FACULTY_SUBJECTS_PAGE;
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
