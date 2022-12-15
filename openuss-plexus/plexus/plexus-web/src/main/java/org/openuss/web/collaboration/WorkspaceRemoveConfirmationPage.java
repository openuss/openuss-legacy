package org.openuss.web.collaboration;

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
import org.openuss.web.Constants;

/** Controller for the workspaceremoveconfirmation.xhtml view.
 * 
 * @author  Projektseminar WS 07/08, Team Collaboration
 */
@Bean(name = "views$secured$collaboration$removeconfirmation", scope = Scope.REQUEST)
@View
public class WorkspaceRemoveConfirmationPage extends AbstractCollaborationPage {
	
	/** Logger for this class */
	private static final Logger LOGGER = Logger.getLogger(WorkspaceRemoveConfirmationPage.class);

	@Prerender
	public void prerender() {
		try {
			super.prerender();
			breadcrumbs.loadCourseCrumbs(courseInfo);
			
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("collaboration_workspace_remove_header"));
			newCrumb.setHint(i18n("collaboration_workspace_remove_header"));
			breadcrumbs.addCrumb(newCrumb);
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
	
	/**
	 * Delete course including all data
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeWorkspace() throws LectureException {
		try {
			workspaceService.removeWorkspace(workspaceInfo.getId());
			addMessage(i18n("collaboration_workspace_removed_succeed"));
			return Constants.COLLABORATION_MAIN_PAGE;
		} catch (Exception e) {
			LOGGER.error("Removing workspace failed", e);
			addMessage(i18n("collaboration_error_cannot_be_removed"));
			return Constants.COLLABORATION_MAIN_PAGE;
		}
	}
	
	/**
	 * Validator to check wheather the user has accepted the user agreement or not.
	 * 
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateRemoveConfirmation(final FacesContext context, final UIComponent toValidate, final Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			if (toValidate instanceof UIInput) {
				((UIInput) toValidate).setValid(false);
			}
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}
}