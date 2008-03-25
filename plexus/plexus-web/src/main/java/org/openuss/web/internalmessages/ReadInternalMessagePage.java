package org.openuss.web.internalmessages;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.internalMessage.InternalMessageInfo;
import org.openuss.internalMessage.InternalMessageService;
import org.openuss.security.UserInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Ralf Plattfaut
 *
 */
@Bean(name = "views$secured$internalmessages$readmessage", scope = Scope.REQUEST)
@View
public class ReadInternalMessagePage extends BasePage {
	
	@Property(value= "#{"+Constants.SHOW_USER_PROFILE+"}")
	public UserInfo profile;
	
	@Property(value = "#{internalMessageService}")
	private InternalMessageService internalMessageService;
	
	@Property(value= "#{"+Constants.OPENUSS4US_INTERNALMESSAGE_MESSAGE+"}")
	private InternalMessageInfo internalMessageInfo = new InternalMessageInfo();
			
	public String linkToSender(){
		profile.setId(internalMessageInfo.getSenderId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
	
	
	public String delete(){
		internalMessageService.deleteInternalMessage(internalMessageInfo);
		addMessage(i18n("openuss4us_message_messagecenter_delete"));
		return Constants.OPENUSS4US_MESSAGECENTER;
	}
	
	
	
	
	public String answerMessage(){
		InternalMessageInfo imInfoHelp = new InternalMessageInfo();
		imInfoHelp.setSubject("Re: " + internalMessageInfo.getSubject());
		setSessionBean(Constants.OPENUSS4US_INTERNALMESSAGE_MESSAGE, imInfoHelp);
		profile.setId(internalMessageInfo.getSenderId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.OPENUSS4US_MESSAGECENTER_CREATE;
	}
	
	@Prerender
	public void prerender() throws Exception {
		if(internalMessageInfo.getId() == null){
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		}
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("openuss4us_command_messagecenter"));
		crumb.setHint(i18n("openuss4us_command_messagecenter"));
//		breadcrumbs.loadBaseCrumbs();
		breadcrumbs.addCrumb(crumb);
	}
	
	public UserInfo getProfile() {
		return profile;
	}
	
	
	public void setProfile(UserInfo profile) {
		this.profile = profile;
	}


	public InternalMessageInfo getInternalMessageInfo() {
		return internalMessageInfo;
	}


	public void setInternalMessageInfo(InternalMessageInfo internalMessageInfo) {
		this.internalMessageInfo = internalMessageInfo;
	}


	public InternalMessageService getInternalMessageService() {
		return internalMessageService;
	}


	public void setInternalMessageService(
			InternalMessageService internalMessageService) {
		this.internalMessageService = internalMessageService;
	}



}
