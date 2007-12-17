package org.openuss.web.security.profile;

import org.apache.log4j.Logger;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


/**
 * ViewUserProfile page to display user profile informations
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$user$userprofile",scope=Scope.REQUEST)
@View
public class ViewUserProfilePage extends BasePage{
	private static final Logger logger = Logger.getLogger(ViewUserProfilePage.class);

	@Property(value="#{securityService}")
	private SecurityService securityService;
	
	@Property(value="#{"+Constants.SHOW_USER_PROFILE+"}")
	private UserInfo profile;
	
	@Prerender
	public void prerender() {
		logger.debug("prerender - refreshing showuser session bean");
		if ((profile != null)&&(profile.getId()!=null)) {
			profile = securityService.getUser(profile.getId());
			setSessionBean(Constants.SHOW_USER_PROFILE, profile);
		}
		if (profile==null||profile.getId()==null) {
			addError(i18n("user_profile_notexisting"));
			redirect(Constants.DESKTOP);			
		}
		
		breadcrumbs.loadProfileCrumbs();
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public UserInfo getProfile() {
		return profile;
	}

	public void setProfile(UserInfo profile) {
		this.profile = profile;
	}

}