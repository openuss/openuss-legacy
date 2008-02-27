package org.openuss.web.buddylist;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.buddylist.BuddyInfo;
import org.openuss.buddylist.BuddyService;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


@Bean(name = "views$secured$buddylist$addbuddy", scope = Scope.REQUEST)
@View
public class AddBuddyPage extends BasePage{
	
	private static final Logger logger = Logger.getLogger(AddBuddyPage.class);
	
	@Property(value = "#{buddyService}")
	private BuddyService buddyService;
	
	@Property(value="#{"+Constants.SHOW_USER_PROFILE+"}")
	private User profile;
	
	@Property(value = "#{securityService}")
	private SecurityService securityService;
	
	public String addBuddy() {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(profile.getId());
		try {
			buddyService.addBuddy(userInfo);
		} catch (Exception e) {
			this.addError("TODO: ERROR");
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

	public User getProfile() {
		return profile;
	}
	
	public boolean getAllowAdd(){
		if(profile.getId().equals(securityService.getCurrentUser().getId())){
			return false;
		}
		for(BuddyInfo buddy : (List<BuddyInfo>)buddyService.getBuddyList()){
			if(buddy.getUserId().equals(profile.getId())){
				return false;
			}
		}
		return true;
	}

	public void setProfile(User profile) {
		this.profile = profile;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}
