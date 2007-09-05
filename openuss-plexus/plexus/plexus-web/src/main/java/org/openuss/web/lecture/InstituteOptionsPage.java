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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.openuss.lecture.ApplicationInfo;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.security.UserInfo;
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
		// fetch uploaded files and remove it from upload manager
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
		
		instituteService.update(instituteInfo);
		addMessage(i18n("institute_message_command_save_succeed"));
	
		if (departmentId!=departmentInfo.getId())
			this.apply();
		
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

	/*******************************begin application*********************/ 	
	private static ResourceBundle getResourceBundle(){
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle rb = ResourceBundle.getBundle(
				context.getApplication().getMessageBundle(), 
				context.getViewRoot().getLocale());
		return rb;
	}
	private Long getUniversityId(){
		Long departmentId = instituteService.findInstitute(instituteInfo.getId()).getDepartmentId();
		Long  universityId= departmentService.findDepartment(departmentId).getUniversityId();
		UniversityInfo universityInfo = universityService.findUniversity(universityId); 
		return universityInfo.getId();
	}
	public List<SelectItem> getAllDepartments(){
		
		ResourceBundle rb = getResourceBundle();
		ApplicationInfo app = instituteService.findApplicationByInstitute(instituteInfo.getId());
		String appStatusDescription="";
		if (app!=null)
		{
			departmentId = app.getDepartmentInfo().getId();
					
			if (app.isConfirmed())			
				appStatusDescription  =  rb.getString("application_accept_info");
			else
			appStatusDescription  =  rb.getString("application_working_info");
			
		}
		else
			departmentId=instituteInfo.getDepartmentId();
		
		
		departmentItems = new ArrayList<SelectItem>();
				
		logger.info("universityId:"+getUniversityId());
		allDepartments = departmentService.findDepartmentsByUniversity(getUniversityId());
		Iterator<DepartmentInfo> iter = allDepartments.iterator();
		DepartmentInfo department;
		SelectItem item;
		while (iter.hasNext()){
			department = iter.next();
			if (department.getId()==departmentId)
				item = new SelectItem(department.getId(), department.getName()+ appStatusDescription);
			else
				item = new SelectItem(department.getId(), department.getName());
			departmentItems.add(item);
		}
		
		logger.info("DepartmentId:" + allDepartments.get(0).getId());
		return departmentItems;
	
	}
	
	private String apply(){
		signoffInstitute();
		logger.debug("Debug apply");
		
		logger.debug("InstituteI"+instituteInfo.getId());
		
		UserInfo userInfo = new UserInfo();
		userInfo.setId(user.getId());
		DepartmentInfo appliedDepartment = new DepartmentInfo();
		appliedDepartment.setId(departmentId);
		applicationInfo.setApplyingUserInfo(userInfo);
		applicationInfo.setInstituteInfo(instituteInfo);
		applicationInfo.setDepartmentInfo(appliedDepartment);
		
		
		try{
			Long app = instituteService.applyAtDepartment(applicationInfo);
			}
		catch(Exception e){;}
		
		
		return Constants.SUCCESS;
	}
	
  
    	
    private String signoffInstitute(){
    	try{
    	departmentService.signoffInstitute(instituteInfo.getId());
    	Long departmentId = instituteService.findInstitute(instituteInfo.getId()).getDepartmentId();
    	DepartmentInfo departmentInfo = departmentService.findDepartment(departmentId);
    	setSessionBean(Constants.DEPARTMENT_INFO,departmentInfo);}
    	catch(Exception e){;}
    	
    	  	
    	return Constants.SUCCESS;
    }
    /*******************************end application*********************/ 	
    
    
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
}
