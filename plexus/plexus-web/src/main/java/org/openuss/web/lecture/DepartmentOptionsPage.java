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
import org.openuss.lecture.DepartmentServiceException;
import org.openuss.lecture.LectureException;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;
import org.openuss.web.upload.UploadedDocument;

/**
 * 
 * @author Kai Stettner
 * @author Malte Stockmann
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$departmentoptions", scope = Scope.REQUEST)
@View
public class DepartmentOptionsPage extends AbstractDepartmentPage {

	private static final long serialVersionUID = -202799999652385870L;
	
	private static final Logger logger = Logger.getLogger(DepartmentOptionsPage.class);
	
	@Property (value="#{securityService}")
	private SecurityService securityService;
	
	@Property (value="#{documentService}")
	private DocumentService documentService;
	
	@Property(value = "#{uploadFileManager}")
	private UploadFileManager uploadFileManager;
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("department_command_settings"));
		crumb.setHint(i18n("department_command_settings"));
		
		breadcrumbs.loadDepartmentCrumbs(departmentInfo);
		breadcrumbs.addCrumb(crumb);
	}	
	
	/**
	 * Save department options.
	 * @return outcome
	 * @throws LectureException
	 * @throws DocumentApplicationException 
	 * @throws IOException 
	 */
	public String saveDepartment() throws LectureException, DocumentApplicationException, IOException {
		// fetch uploaded files and remove it from upload manager
		UploadedDocument uploaded = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
		if (uploaded != null) {
			if (departmentInfo.getImageId() != null) {
				documentService.removeFolderEntry(departmentInfo.getImageId());
			}
			FileInfo imageFile = new FileInfo();
				
			imageFile.setName(Constants.ORGANISATION_IMAGE_NAME);
			imageFile.setFileName(uploaded.getFileName());
			imageFile.setContentType(uploaded.getContentType());
			imageFile.setFileSize(uploaded.getFileSize());
			imageFile.setInputStream(uploaded.getInputStream());
			
			FolderInfo folder = documentService.getFolder(departmentInfo);
			documentService.createFileEntry(imageFile, folder);
			
			permitRolesImageReadPermission(imageFile);
			
			departmentInfo.setImageId(imageFile.getId());

			removeSessionBean(Constants.UPLOADED_FILE);
			uploadFileManager.removeDocument(uploaded);
		}
		departmentService.update(departmentInfo);
		addMessage(i18n("department_message_command_save_succeed"));
		return Constants.SUCCESS;
	}
	
	private void permitRolesImageReadPermission(FileInfo imageFile) {
		// TODO should be done within the business layer
		securityService.setPermissions(Roles.ANONYMOUS, imageFile, LectureAclEntry.READ);
		securityService.setPermissions(Roles.USER, imageFile, LectureAclEntry.READ);
	}
	
	public void removeImage(ActionEvent event) throws DocumentApplicationException {
		if (departmentInfo.getImageId() != null) {
			Long fileId = departmentInfo.getImageId();
			departmentInfo.setImageId(null);
			documentService.removeFolderEntry(fileId);
		}
		departmentService.update(departmentInfo);
		// FIXME Do not use session bean for navigation
		setSessionBean(Constants.LAST_VIEW, Constants.USER_PROFILE_VIEW_PAGE);
	}
	
	/**
	 * Store the selected department into session scope and go to department
	 * disable confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectDepartmentAndConfirmDisable() {
		logger.debug("Starting method selectDepartmentAndConfirmDisable");
		setBean(Constants.DEPARTMENT_INFO, departmentInfo);

		return Constants.DEPARTMENT_CONFIRM_DISABLE_PAGE;
	}

	/**
	 * Enables the chosen department. This is just evident for the search
	 * indexing.
	 * 
	 * @return Outcome
	 */
	public String enableDepartment() {
		logger.debug("Starting method enableDepartment");
		try {
			departmentService.setDepartmentStatus(departmentInfo.getId(), true);
			addMessage(i18n("message_department_enabled"));
		} catch(DepartmentServiceException iae) {
			addMessage(i18n("message_department_enabled_failed_university_disabled"));
		} catch(Exception ex){
			addMessage(i18n("message_department_enabled_failed"));
		}
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