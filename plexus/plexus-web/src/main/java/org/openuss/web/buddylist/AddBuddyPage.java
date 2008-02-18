package org.openuss.web.buddylist;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.web.BasePage;
import org.openuss.buddylist.BuddyService;
import org.openuss.web.Constants;
import org.openuss.security.User;
import org.openuss.security.UserInfo;


@Bean(name = "views$secured$buddylist$addbuddy", scope = Scope.REQUEST)
@View
public class AddBuddyPage extends BasePage{
	
	private static final Logger logger = Logger.getLogger(AddBuddyPage.class);
	
	@Property(value = "#{buddyService}")
	private BuddyService buddyService;
	
	@Property(value="#{"+Constants.SHOW_USER_PROFILE+"}")
	private User profile;
	
	public String addBuddy() {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(profile.getId());
		buddyService.addBuddy(userInfo);
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

	public void setProfile(User profile) {
		this.profile = profile;
	}


	

	
	

}
