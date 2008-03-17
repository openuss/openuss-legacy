package org.openuss.web.lecture;

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
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;
import org.openuss.web.upload.UploadedDocument;

/**
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 * @author Malte Stockmann
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$universityoptions", scope = Scope.REQUEST)
@View
public class UniversityOptionsPage extends AbstractUniversityPage {
	
	private static final Logger logger = Logger.getLogger(UniversityOptionsPage.class);

	private static final long serialVersionUID = -202776319652385870L;
	
	@Property (value="#{securityService}")
	private SecurityService securityService;
	
	@Property (value="#{documentService}")
	private DocumentService documentService;
	
	@Property(value = "#{uploadFileManager}")
	private UploadFileManager uploadFileManager;
	
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("university_command_settings"));
		crumb.setHint(i18n("university_command_settings"));
		
		breadcrumbs.loadUniversityCrumbs(universityInfo);
		breadcrumbs.addCrumb(crumb);
	}	
	
	/**
	 * Save university options.
	 * @return outcome
	 * @throws LectureException
	 * @throws DocumentApplicationException 
	 * @throws IOException 
	 */
	public String saveUniversity() throws LectureException, DocumentApplicationException, IOException {
		// fetch uploaded files and remove it from upload manager
		UploadedDocument uploaded = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
		if (uploaded != null) {
			if (universityInfo.getImageId() != null) {
				documentService.removeFolderEntry(universityInfo.getImageId());
			}
			FileInfo imageFile = new FileInfo();
				
			imageFile.setName(Constants.ORGANISATION_IMAGE_NAME);
			imageFile.setFileName(uploaded.getFileName());
			imageFile.setContentType(uploaded.getContentType());
			imageFile.setFileSize(uploaded.getFileSize());
			imageFile.setInputStream(uploaded.getInputStream());
			
			FolderInfo folder = documentService.getFolder(universityInfo);
			documentService.createFileEntry(imageFile, folder);
			
			permitRolesImageReadPermission(imageFile);
			
			universityInfo.setImageId(imageFile.getId());
			//universityInfo.setImageId(imageFile.getId());

			removeSessionBean(Constants.UPLOADED_FILE);
			uploadFileManager.removeDocument(uploaded);
		}
		universityService.update(universityInfo);
		addMessage(i18n("university_message_command_save_succeed"));
		return Constants.SUCCESS;
	}
	
	private void permitRolesImageReadPermission(FileInfo imageFile) {
		// TODO should be done within the business layer
		securityService.setPermissions(Roles.ANONYMOUS, imageFile, LectureAclEntry.READ);
		securityService.setPermissions(Roles.USER, imageFile, LectureAclEntry.READ);
	}
	
	public void removeImage(ActionEvent event) throws DocumentApplicationException {
		if (universityInfo.getImageId() != null) {
			Long fileId = universityInfo.getImageId();
			universityInfo.setImageId(null);
			documentService.removeFolderEntry(fileId);
		}
		universityService.update(universityInfo);
		setSessionBean(Constants.LAST_VIEW, Constants.USER_PROFILE_VIEW_PAGE);
	}
	
	/**
	 * Store the selected university into session scope and go to university disable confirmation page.
	 * @return Outcome
	 */
	public String selectUniversityAndConfirmDisable() {
		logger.debug("Starting method selectUniversityAndConfirmDisable");	
		setBean(Constants.UNIVERSITY_INFO, universityInfo);
		
		return Constants.UNIVERSITY_CONFIRM_DISABLE_PAGE;
	}
	
	/**
	 * Enables the chosen university. This is just evident for the search indexing.
	 * @return Outcome
	 */
	public String enableUniversity() {
		logger.debug("Starting method enableUniversity");
		// setOrganisationStatus(true) = Enabled
		// setOrganisationStatus(false) = Disbled
		universityService.setUniversityStatus(universityInfo.getId(), true);
		
		addMessage(i18n("message_university_enabled"));
		return Constants.SUCCESS;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public UploadFileManager getUploadFileManager() {
		return uploadFileManager;
	}

	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
