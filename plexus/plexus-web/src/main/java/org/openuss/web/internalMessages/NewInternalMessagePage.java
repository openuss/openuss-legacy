package org.openuss.web.internalMessages;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.buddylist.Buddy;
import org.openuss.buddylist.BuddyList;
import org.openuss.buddylist.BuddyService;
import org.openuss.internalMessage.*;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;







@Bean(name = "views$secured$internalmessages$newinternalmessage", scope = Scope.REQUEST)
@View
public class NewInternalMessagePage extends BasePage{
	private static final Logger logger = Logger
	.getLogger(NewInternalMessagePage.class);
	
	@Property(value = "#{securityService}")
	private SecurityService securityService;
	
	@Property(value = "#{internalMessageService}")
	private InternalMessageService internalMessageService;
	
	@Property(value="#{"+Constants.SHOW_USER_PROFILE+"}")
	private User profile;
	
	@Property(value= "#{"+Constants.OPENUSS4US_INTERNALMESSAGE_MESSAGE+"}")
	private InternalMessageInfo internalMessageInfo = new InternalMessageInfo();
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		
	}
	
	public String linkToSendMessage(){
		setSessionBean(Constants.OPENUSS4US_INTERNALMESSAGE_MESSAGE, new InternalMessageInfo());
		if(profile.getId().equals(securityService.getCurrentUser().getId())){
			addMessage(i18n("openuss4us_message_messagecenter_addbuddy"));
			return Constants.SUCCESS;
		}
		return Constants.OPENUSS4US_MESSAGECENTER_CREATE;
	}
	
	
	public String sendmessage() {
		logger.debug("Send Message to " + profile.getId());
		if(profile.getId().equals(securityService.getCurrentUser().getId())){
			addMessage(i18n("openuss4us_message_messagecenter_addbuddy"));
			return Constants.OPENUSS4US_MESSAGECENTER_CREATE;
		}
		LinkedList<InternalMessageRecipientsInfo> recipients = new LinkedList<InternalMessageRecipientsInfo>(); 
		InternalMessageRecipientsInfo internalMessageRecipientsInfo = new InternalMessageRecipientsInfo();
		internalMessageRecipientsInfo.setRecipientId(profile.getId());
		internalMessageInfo.setInternalMessageRecipientsInfos(recipients);
		internalMessageRecipientsInfo.setInternalMessageInfo(internalMessageInfo);
		recipients.add(internalMessageRecipientsInfo);
		internalMessageService.sendInternalMessage(internalMessageInfo);
		addMessage(i18n("openuss4us_message_messagecenter_sendmessage"));
	return Constants.OUTCOME_BACKWARD;
	}

	public InternalMessageService getInternalMessageService() {
		return internalMessageService;
	}

	public void setInternalMessageService(
			InternalMessageService internalMessageService) {
		this.internalMessageService = internalMessageService;
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

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}
	
	
	
}
