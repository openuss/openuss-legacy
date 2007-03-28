package org.openuss.web.security.profile;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.security.SecurityService;
import org.openuss.security.UserContact;
import org.openuss.security.UserPreferences;
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
	
	@Property (value="#{securityService}")
	private SecurityService securityService;
	
	@Property (value="#{documentService}")
	private DocumentService documentService;
	
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
	 * @throws DocumentApplicationException 
	 */
	public void saveProfile(ActionEvent event) throws DocumentApplicationException {
		// fetch uploaded files and remove it from upload manager
		FileInfo attachment = (FileInfo) getSessionBean(Constants.UPLOADED_FILE);
		if (attachment != null) {
			if (user.getImageId() == null) {
				attachment.setName(Constants.USER_IMAGE_NAME);
				FolderInfo folder = documentService.getFolder(user);
				documentService.createFileEntry(attachment, folder);
				user.setImageId(attachment.getId());
			} else {
				FileInfo image = documentService.getFileEntry(user.getImageId(), false);
				image.setFileSize(attachment.getFileSize());
				image.setInputStream(attachment.getInputStream());
				documentService.saveFileEntry(image);
			}
			removeSessionBean(Constants.UPLOADED_FILE);
			uploadFileManager.removeFile(attachment);
		}
		
		securityService.saveUser(user);
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
		addMessage(i18n("userprofile_message_saved_contact_data"));
	}
	
	
	/**
	 * Persist User Preferences
	 */
	public void savePreferences(ActionEvent event) {
		UserPreferences preferences = user.getPreferences();
		securityService.saveUserPreferences(preferences);
		addMessage(i18n("userprofile_message_saved_preferences"));
	}
	
	/**
	 * Show Profile
	 * @return outcome
	 */
	public String showProfile() {
		setSessionBean(Constants.SHOW_USER, user);
		navigator.setLastView(Constants.USER_PROFILE_PAGE);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
	
	public void removeImage(ActionEvent event) throws DocumentApplicationException {
		if (user.getImageId() != null) {
			Long fileId = user.getImageId();
			user.setImageId(null);
			documentService.removeFolderEntry(fileId);
		}
		securityService.saveUser(user);
		
		setSessionBean(Constants.LAST_VIEW, Constants.USER_PROFILE_VIEW_PAGE);
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

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

}
