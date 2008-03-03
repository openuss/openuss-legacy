package org.openuss.web.discussion.groups;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
@Bean(name = "views$secured$discussion$groups$removethread", scope = Scope.REQUEST)
@View
public class GroupRemoveThreadPage extends AbstractGroupDiscussionPage{
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		if (!isAssistant()){
			addError(i18n("error_access_denied_details"));
			redirect(Constants.FORUM_MAIN);			
		}			
		addPageCrumb();
	}		

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("discussion_remove_topic_header"));
		crumb.setHint(i18n("discussion_remove_topic_header"));
		breadcrumbs.addCrumb(crumb);
	}
	
	public String removeThread(){
		discussionService.deleteTopic(topic);
		addMessage(i18n("discussion_topic_deleted", topic.getTitle()));
		return Constants.FORUM_MAIN;
	}
	
	/**
	 * Validator to check wether the user has accepted to remove the topic or not
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