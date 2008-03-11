package org.openuss.web.groups.components;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** 
 * @author Lutz D. Kramer
 */
@Bean(name = "views$secured$groups$components$groupdeleteconfirmation", scope = Scope.REQUEST)
@View
public class GroupDeleteConfirmationPage extends AbstractGroupPage {

	private static final long serialVersionUID = -202000011111888870L;

	/* ----- business logic ----- */
	
	@Prerender
	public void prerender() throws Exception { // NOPMD by devopenuss on 11.03.08 14:24
		super.prerender();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("group_delete_header"));
		newCrumb.setHint(i18n("group_delete_header"));
		breadcrumbs.addCrumb(newCrumb);
	}

	public String removeGroup() throws LectureException {
		try {
			groupService.deleteUserGroup(groupInfo);
			setSessionBean("groupInfo", null);
			addMessage(i18n("group_removed_succeed"));
			return Constants.OPENUSS4US_GROUPS;
		} catch (Exception e) {
			// e.printStackTrace();
			addMessage(i18n("group_removed_failed"));
			return Constants.OPENUSS4US_GROUPS;
		}
	}
	
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}
	
}
