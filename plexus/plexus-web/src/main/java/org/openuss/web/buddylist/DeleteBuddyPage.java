package org.openuss.web.buddylist;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.buddylist.BuddyService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.buddylist.BuddyInfo;

@Bean(name = "views$secured$buddylist$deletebuddy", scope = Scope.REQUEST)
@View
public class DeleteBuddyPage extends BasePage{
private static final Logger logger = Logger.getLogger(DeleteBuddyPage.class);
	
	@Property(value = "#{buddyService}")
	private BuddyService buddyService;
	
	@Property(value="#{"+Constants.SHOW_USER_PROFILE+"}")
	private User profile;
	
	@Property(value= "#{"+Constants.OPENUSS4US_CHOSEN_BUDDYINFO+"}")
	private BuddyInfo buddyInfo;
	
	public BuddyInfo getBuddyInfo() {
		return buddyInfo;
	}

	public void setBuddyInfo(BuddyInfo buddyInfo) {
		this.buddyInfo = buddyInfo;
	}

	
	
	
	public String deleteBuddy() {
		buddyService.deleteBuddy(buddyInfo);
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
