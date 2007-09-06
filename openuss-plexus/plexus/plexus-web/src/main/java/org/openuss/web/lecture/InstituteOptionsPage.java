package org.openuss.web.lecture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

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
import org.openuss.lecture.ApplicationInfo;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
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
 * @author Tianyu Wang
 */
@Bean(name = "views$secured$lecture$instituteoptions", scope = Scope.REQUEST)
@View
public class InstituteOptionsPage extends AbstractLecturePage {

	private static final long serialVersionUID = -202776319652385870L;

	@Property (value="#{securityService}")
	private SecurityService securityService;
	
	@Property (value="#{documentService}")
	private DocumentService documentService;
	
	@Property(value = "#{uploadFileManager}")
	private UploadFileManager uploadFileManager;
	
	private List<SelectItem> departmentItems;

	private List<DepartmentInfo> allDepartments;
	
	private ApplicationInfo applicationInfo = new ApplicationInfo();
	
	private Long departmentId;
	
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if(instituteInfo != null) {
			if (!instituteInfo.isEnabled()) {
				addMessage(i18n("institute_not_activated"));
			}
		}
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("institute_command_settings"));
		crumb.setHint(i18n("institute_command_settings"));
		
		breadcrumbs.loadInstituteCrumbs(instituteInfo);
		breadcrumbs.addCrumb(crumb);
	}	
	
	/**
	 * Save institute options.
	 * @return outcome
	 * @throws LectureException
	 * @throws DocumentApplicationException 
	 * @throws IOException 
	 */
	public String saveInstitute() throws LectureException, DocumentApplicationException, IOException {
		// fetch uploaded file and remove it from upload manager
		UploadedDocument uploaded = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
		if (uploaded != null) {
			if (instituteInfo.getImageId() != null) {
				documentService.removeFolderEntry(instituteInfo.getImageId());
			}
			FileInfo imageFile = new FileInfo();
				
			imageFile.setName(Constants.ORGANISATION_IMAGE_NAME);
			imageFile.setFileName(uploaded.getFileName());
			imageFile.setContentType(uploaded.getContentType());
			imageFile.setFileSize(uploaded.getFileSize());
			imageFile.setInputStream(uploaded.getInputStream());
			
			FolderInfo folder = documentService.getFolder(instituteInfo);
			documentService.createFileEntry(imageFile, folder);
			
			permitRolesImageReadPermission(imageFile);
			
			instituteInfo.setImageId(imageFile.getId());

			removeSessionBean(Constants.UPLOADED_FILE);
			uploadFileManager.removeDocument(uploaded);
		}
		
		// save actual institute data
		instituteService.update(instituteInfo);
		addMessage(i18n("institute_message_command_save_succeed"));
		
		// start department application process if a department is selected 
		// which differs from the current one 
		if (!departmentId.equals(departmentInfo.getId())){
			ApplicationInfo pendingApplication = 
					instituteService.findApplicationByInstituteAndConfirmed(instituteInfo.getId(), false);
			 // only apply when there is not already a pending application 
			 // for the same department
			if( !(pendingApplication != null 
					&& departmentId.equals(pendingApplication.getDepartmentInfo().getId())) ){
				this.apply();
			}
		}
		
		return Constants.SUCCESS;
	}
	
	private void permitRolesImageReadPermission(FileInfo imageFile) {
		// TODO should be done within the business layer
		securityService.setPermissions(Roles.ANONYMOUS, imageFile, LectureAclEntry.READ);
		securityService.setPermissions(Roles.USER, imageFile, LectureAclEntry.READ);
	}
	
	public void removeImage(ActionEvent event) throws DocumentApplicationException {
		if (instituteInfo.getImageId() != null) {
			Long fileId = instituteInfo.getImageId();
			instituteInfo.setImageId(null);
			documentService.removeFolderEntry(fileId);
		}
		instituteService.update(instituteInfo);
		setSessionBean(Constants.LAST_VIEW, Constants.USER_PROFILE_VIEW_PAGE);
	}

	/******************************* begin application *********************/ 	
	private Long getUniversityId(){
		Long departmentId = instituteService.findInstitute(instituteInfo.getId()).getDepartmentId();
		Long  universityId= departmentService.findDepartment(departmentId).getUniversityId();
		UniversityInfo universityInfo = universityService.findUniversity(universityId); 
		return universityInfo.getId();
	}
	
	public List<SelectItem> getAllDepartments(){
		
		// set departmentId according to the current department selection
		departmentId = instituteInfo.getDepartmentId();
		
		// get a list of the associated university's departments 
		// (the institute cannot be moved to another university, so only 
		// departments of the currently associated university must be displayed) 
		Long universityId = getUniversityId();
		logger.debug("getting departments for university:"+universityId);
		allDepartments = departmentService.findDepartmentsByUniversity(universityId);
		departmentItems = new ArrayList<SelectItem>();
		Iterator<DepartmentInfo> iter = allDepartments.iterator();
		DepartmentInfo department;
		SelectItem item;
		while (iter.hasNext()){
			department = iter.next();
//			// if there is a pending application request for another department, 
//			// then show a hint next to the combo box entry for the currently associated department   
//			if (pendingApplication != null && department.getId().equals(departmentId)){
//				item = new SelectItem(department.getId(), department.getName()+ appStatusDescription);
//			} else {
				item = new SelectItem(department.getId(), department.getName());
//			}
			departmentItems.add(item);
		}
		
		return departmentItems;
	}
	
	private void apply(){
		logger.debug("entering apply method (backing bean)");
		logger.debug("instituteId: "+instituteInfo.getId());
		
		Long applicationId;
		try{
			applicationId = instituteService.applyAtDepartment(instituteInfo.getId(), departmentId, user.getId());
			logger.debug("created department application: " + applicationId);
		} catch(Exception e){
			//logger.debug(e.);
			e.printStackTrace();
			addError(i18n("error_department_application_start"));
		}
	}
    
    /******************************* end application *********************/ 	
    
    
	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
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
	public ApplicationInfo getApplicationInfo() {
		return applicationInfo;
	}

	public void setApplicationInfo(ApplicationInfo applicationInfo) {
		this.applicationInfo = applicationInfo;
	}
	
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getPendingApplicationInfo(){
		// abort, when there is no instituteId set
		if(instituteInfo == null || instituteInfo.getId() == null){
			return null;
		}
		// check whether there is a pending application request
		ApplicationInfo pendingApplication = instituteService.findApplicationByInstituteAndConfirmed(instituteInfo.getId(), false);
		String appStatusDescription = "";
		if(pendingApplication != null){
			appStatusDescription = i18n("application_pending_info", pendingApplication.getDepartmentInfo().getName());
		}
		// return information string if there is a pending application
		if(!appStatusDescription.equals("")){
			return appStatusDescription;
		} else {
			return null;
		}
	}
	
}
