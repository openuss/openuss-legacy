package org.openuss.web.discussion;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
@Bean(name = "views$secured$discussion$removethread", scope = Scope.REQUEST)
@View
public class RemoveThreadPage extends AbstractDiscussionPage{
	
	public String removeThread(){
		discussionService.deleteTopic(topic);
		addMessage(i18n("discussion_topic_deleted", topic.getTitle()));
		return Constants.DISCUSSION_MAIN;
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