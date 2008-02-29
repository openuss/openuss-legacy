package org.openuss.web.internalMessages;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.internalMessage.*;
import org.openuss.security.User;
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
	public User profile;
	
	@Property(value = "#{internalMessageService}")
	private InternalMessageService internalMessageService;
	
	@Property(value= "#{"+Constants.OPENUSS4US_INTERNALMESSAGE_MESSAGE+"}")
	private InternalMessageInfo internalMessageInfo = new InternalMessageInfo();
	

		
	private static final Logger logger = Logger
			.getLogger(ReadInternalMessagePage.class);
	
		
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
		addMessage(i18n("openuss4us_message_messagecenter_sendmessage"));
		return Constants.OPENUSS4US_MESSAGECENTER_CREATE;
	}
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("openuss4us_command_messagecenter"));
		crumb.setHint(i18n("openuss4us_command_messagecenter"));
		breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(crumb);
	}
	
	public User getProfile() {
		return profile;
	}
	
	
	public void setProfile(User profile) {
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
