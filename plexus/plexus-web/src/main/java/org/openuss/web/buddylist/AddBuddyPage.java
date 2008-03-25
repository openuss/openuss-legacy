package org.openuss.web.buddylist;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.buddylist.BuddyService;
import org.openuss.internalMessage.InternalMessageInfo;
import org.openuss.internalMessage.InternalMessageRecipientsInfo;
import org.openuss.internalMessage.InternalMessageService;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


@Bean(name = "views$secured$buddylist$addbuddy", scope = Scope.REQUEST)
@View
public class AddBuddyPage extends BasePage{
	
	private static final Logger logger = Logger.getLogger(AddBuddyPage.class);
	
	@Property(value = "#{buddyService}")
	private BuddyService buddyService;
	
	@Property(value="#{"+Constants.SHOW_USER_PROFILE+"}")
	private UserInfo profile;
	
	@Property(value = "#{securityService}")
	private SecurityService securityService;
	
	@Property(value = "#{internalMessageService}")
	private InternalMessageService internalMessageService;
	
	public InternalMessageService getInternalMessageService() {
		return internalMessageService;
	}

	public void setInternalMessageService(
			InternalMessageService internalMessageService) {
		this.internalMessageService = internalMessageService;
	}
	
	@Property(value = "#{systemService}")
	transient private SystemService systemService;
	
	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Prerender
	public void prerender() {
		logger.debug("prerender - refreshing showuser session bean");
		if ((profile != null)&&(profile.getId()!=null)) {
			profile = securityService.getUser(profile.getId());
			setSessionBean(Constants.SHOW_USER_PROFILE, profile);
		}
		if (profile==null||profile.getId()==null) {
			addError(i18n("user_profile_notexisting"));
			redirect(Constants.OPENUSS4US_BUDDYLIST);			
		}
		
		breadcrumbs.loadProfileCrumbs();
	}


	public String addBuddy() {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(profile.getId());
		try {
			buddyService.addBuddy(userInfo);
			
			 //inform about request
			logger.debug("sending message");
	        InternalMessageInfo imInfo = new InternalMessageInfo();
	        imInfo.setSenderDisplayName(user.getDisplayName());
	        imInfo.setSenderId(user.getId());
	        imInfo.setMessageDate(new Date());
	        //TODO make information dynamically
	        imInfo.setSubject(i18n("openuss4us_buddylist_requestsubject"));
	        imInfo.setContent(user.getDisplayName() + " " + i18n("openuss4us_buddylist_requestcontent") + systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + i18n("openuss4us_buddylist_requestcontent2"));
	        InternalMessageRecipientsInfo imrecInfo = new InternalMessageRecipientsInfo();
	        imrecInfo.setInternalMessageInfo(imInfo);
	        imrecInfo.setRead(false);
	        imrecInfo.setRecipientId(userInfo.getId());
	        List<InternalMessageRecipientsInfo> internalMessageRecipientsInfos = new LinkedList();
	        internalMessageRecipientsInfos.add(imrecInfo);
	        imInfo.setInternalMessageRecipientsInfos(internalMessageRecipientsInfos);
	        imrecInfo.setInternalMessageInfo(imInfo);
	        getInternalMessageService().sendInternalMessage(imInfo);
			
			
			
			
			addMessage(i18n("openuss4us_message_addbuddy_request"));
		} catch (Exception e) {
			addError(i18n("openuss4us_error_addbuddy_request"));
			return Constants.SUCCESS;
		}
		return Constants.OPENUSS4US_BUDDYLIST;
	}

	public BuddyService getBuddyService() {
		return buddyService;
	}

	public void setBuddyService(BuddyService buddyService) {
		this.buddyService = buddyService;
	}

	public UserInfo getProfile() {
		return profile;
	}
	
	public boolean getAllowAdd(){
		if(profile.getId().equals(securityService.getCurrentUser().getId())){
			return false;
		}
		return !buddyService.isUserBuddy(profile.getId());
	}

	public void setProfile(UserInfo profile) {
		this.profile = profile;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}
