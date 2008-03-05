package org.openuss.web.papersubmission;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.paperSubmission.PaperSubmissionService;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

/** Controller for the examremove.xhtml view.
 * 
 * @author Christian Beer
 */
@Bean(name = "views$secured$papersubmission$examremove", scope = Scope.REQUEST)
@View
public class PaperSubmissionExamRemovePage extends AbstractPaperSubmissionPage {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(PaperSubmissionExamRemovePage.class);

	private static final long serialVersionUID = -202000019652888870L;

	@Prerender
	public void prerender() throws LectureException {
		try {
			super.prerender();
			breadcrumbs.loadCourseCrumbs(courseInfo);
			
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("paper_remove_header"));
			newCrumb.setHint(i18n("paper_remove_header"));
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
	public String removeExam() throws LectureException {
		try {
			paperSubmissionService.removeExam(examInfo.getId());
			setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, null);
			addMessage(i18n("paper_removed_succeed"));
			return Constants.PAPERSUBMISSION_EXAMLIST_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(i18n("paper_cannot_be_removed"));
			return Constants.PAPERSUBMISSION_EXAMLIST_PAGE;
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
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}
	
}
