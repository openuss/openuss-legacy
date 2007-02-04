package org.openuss.web.security.profile;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.repository.RepositoryFile;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserPreferences;
import org.openuss.security.UserProfile;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.navigation.Navigator;
import org.openuss.web.upload.UploadFileManager;


/**
 * View page controller to handle user profile views
 * @author Ingo Dueppe
 *
 */
@Bean(name="views$secured$user$profile", scope=Scope.REQUEST)
@View
public class UserProfilePage extends BasePage{

	private static final Logger logger = Logger.getLogger(UserProfilePage.class);

	private static final long serialVersionUID = 1L;
	
	@Property (value="#{user}")
	private User user;
	
	@Property (value="#{securityService}")
	private SecurityService securityService;
	
	@Property(value = "#{uploadFileManager}")
	private UploadFileManager uploadFileManager;
	
	@Property(value = "#{navigator}")
	private Navigator navigator;
	
	@Prerender
	public void prerender() {
		logger.debug("prerender");
		
		if (user != null) {
			user = securityService.getUser(user.getId());
			setSessionBean(Constants.USER, user);
		}
	}
	
	/**
	 * Persist User Profile
	 */
	public void saveProfile(ActionEvent event) {
		UserProfile profile = user.getProfile();
		
		// fetch uploaded files and remove it from upload manager
		RepositoryFile attachment = (RepositoryFile) getSessionBean(Constants.UPLOADED_FILE);
		if (attachment != null) {
			profile.setImage(attachment);
			removeSessionBean(Constants.UPLOADED_FILE);
			uploadFileManager.unregisterFile(profile.getImage());
		}
		
		securityService.saveUserProfile(profile);
	}
	
	/**
	 * Persist User Login Data
	 */
	public void saveLogin(ActionEvent event) {
		logger.debug("save login data");
		logger.debug("user password"+user.getPassword());
		// persist user
		securityService.saveUser(user);

		// if password is not null then the password was changed
		if (StringUtils.isNotBlank(user.getPassword())) {
			addMessage(i18n("user_profile_message_password_changed"));
		}
	}		
	
	/**
	 * Persist User Contact Data
	 */
	public void saveContact(ActionEvent event) {
		UserContact contact = user.getContact();
		securityService.saveUserContact(contact);
		// TODO add message here
	}
	
	
	/**
	 * Persist User Preferences
	 */
	public void savePreferences(ActionEvent event) {
		UserPreferences preferences = user.getPreferences();
		securityService.saveUserPreferences(preferences);
		// TODO add message here
	}
	
	/**
	 * Show Profile
	 * @return outcome
	 */
	public String showProfile() {
		setSessionBean(Constants.SHOW_USER, user);
		navigator.setLastView(Constants.USER_PROFILE);
		return Constants.USER_PROFILE_VIEW;
	}
	
	public void removeImage(ActionEvent event) {
		UserProfile profile = user.getProfile();
		
		profile.setImage(null);
		securityService.saveUserProfile(profile);
		
		setSessionBean(Constants.LAST_VIEW, Constants.USER_PROFILE_VIEW);
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public UploadFileManager getUploadFileManager() {
		return uploadFileManager;
	}

	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}
