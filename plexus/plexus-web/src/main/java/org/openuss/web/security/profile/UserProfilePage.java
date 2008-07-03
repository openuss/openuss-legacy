package org.openuss.web.security.profile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.faces.event.ActionEvent;

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
import org.openuss.framework.utilities.ImageUtils;
import org.openuss.framework.web.xss.HtmlInputFilter;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;
import org.openuss.web.upload.UploadedDocument;


/**
 * View page controller to handle user profile views
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 *
 */
@Bean(name="views$secured$user$profile", scope=Scope.REQUEST)
@View
public class UserProfilePage extends BasePage{

	private static final Logger logger = Logger.getLogger(UserProfilePage.class);

	@Property (value="#{securityService}")
	private SecurityService securityService;
	
	@Property (value="#{documentService}")
	private DocumentService documentService;
	
	@Property(value = "#{uploadFileManager}")
	private UploadFileManager uploadFileManager;
	
	@Prerender
	public void prerender() {
		logger.debug("prerender");
		
		if (user != null) {
			user = (UserInfo) securityService.getUser(user.getId());
			setSessionBean(Constants.USER, user);
		}
		
		breadcrumbs.loadProfileCrumbs();
	}
	
	/**
	 * Puts user object into session and redirects to profile page
	 * 
	 */
	public String profilePage(){
		UserInfo profile = new UserInfo();
		profile.setId(this.user.getId());
		setBean(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}	
	
	/**
	 * Persist User Profile
	 * @throws DocumentApplicationException 
	 * @throws IOException 
	 */
	public void saveProfile(ActionEvent event) throws DocumentApplicationException, IOException {
		saveLogin(event);
		// fetch uploaded files and remove it from upload manager
		UploadedDocument uploaded = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
		if (uploaded != null) {
			if (user.getImageId() != null) {
				documentService.removeFolderEntry(user.getImageId());
			}
			FileInfo imageFile = new FileInfo();
				
			imageFile.setName(Constants.USER_IMAGE_NAME);
			imageFile.setFileName(uploaded.getFileName());
			imageFile.setContentType(uploaded.getContentType());

			// resize image
			byte[] image = ImageUtils.resizeImageToByteArray(uploaded.getInputStream(), ImageUtils.IMAGE_UNKNOWN, 100, 100);
			imageFile.setFileSize(image.length);
			imageFile.setInputStream(new ByteArrayInputStream(image));
			
			FolderInfo folder = documentService.getFolder(user);
			documentService.createFileEntry(imageFile, folder);
			
			permitRolesImageReadPermission(imageFile);
			
			user.setImageId(imageFile.getId());

			removeSessionBean(Constants.UPLOADED_FILE);
			uploadFileManager.removeDocument(uploaded);
		}
		user.setPortrait(new HtmlInputFilter().filter(user.getPortrait()) );
		securityService.saveUser(user);
		addMessage(i18n("user_message_saved_profile_successfully"));
	}

	private void permitRolesImageReadPermission(FileInfo imageFile) {
		// TODO should be done within the business layer
		securityService.setPermissions(Roles.ANONYMOUS, imageFile, LectureAclEntry.READ);
		securityService.setPermissions(Roles.USER, imageFile, LectureAclEntry.READ);
	}
	
	/**
	 * Persist User Login Data
	 */
	private void saveLogin(ActionEvent event) {
		logger.debug("save login data");
		logger.debug("user password"+user.getPassword());
		// persist user
		securityService.saveUser(user);
	}		
	
	public void removeImage(ActionEvent event) throws DocumentApplicationException {
		if (user.getImageId() != null) {
			Long fileId = user.getImageId();
			user.setImageId(null);
			documentService.removeFolderEntry(fileId);
		}
		securityService.saveUser(user);
	}
	
	public String removeUser(){
		getSecurityService().removeUser(user);
		addMessage(i18n("user_delete_message"));
		return Constants.DESKTOP;
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

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

}
