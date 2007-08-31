package org.openuss.web.lecture;

import java.io.IOException;

import javax.faces.event.ActionEvent;

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
 * @author Kai Stettner
 * @author Malte Stockmann
 */
@Bean(name = "views$secured$lecture$departmentoptions", scope = Scope.REQUEST)
@View
public class DepartmentOptionsPage extends AbstractDepartmentPage {

	private static final long serialVersionUID = -202799999652385870L;
	
	@Property (value="#{securityService}")
	private SecurityService securityService;
	
	@Property (value="#{documentService}")
	private DocumentService documentService;
	
	@Property(value = "#{uploadFileManager}")
	private UploadFileManager uploadFileManager;
	
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
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
		setSessionBean(Constants.LAST_VIEW, Constants.USER_PROFILE_VIEW_PAGE);
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