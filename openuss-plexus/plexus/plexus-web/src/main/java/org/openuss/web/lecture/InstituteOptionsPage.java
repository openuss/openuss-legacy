package org.openuss.web.lecture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.ApplicationInfo;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadedDocument;

/**
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 * @author Malte Stockmann
 * @author Tianyu Wang
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$instituteoptions", scope = Scope.REQUEST)
@View
public class InstituteOptionsPage extends AbstractLecturePage {

	private static final long serialVersionUID = -202776319652385870L;

	@Property(value = "#{securityService}")
	private SecurityService securityService;

	private ApplicationInfo applicationInfo = new ApplicationInfo();

	private Long universityId;
	private Long departmentId;

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
		crumb.setName(i18n("institute_command_settings"));
		crumb.setHint(i18n("institute_command_settings"));

		breadcrumbs.loadInstituteCrumbs(instituteInfo);
		breadcrumbs.addCrumb(crumb);
	}

	/**
	 * Save institute options.
	 * 
	 * @return outcome
	 * @throws LectureException
	 * @throws DocumentApplicationException
	 * @throws IOException
	 */
	public String saveInstitute() throws LectureException, DocumentApplicationException, IOException {
		saveInstituteLogo();
		// save actual institute data
		instituteService.update(instituteInfo);
		addMessage(i18n("institute_message_command_save_succeed"));

		// FIXME This must be done in the business logic
		// start department application process if a department is selected which differs from the current one
		if (!departmentId.equals(departmentInfo.getId())) {
			ApplicationInfo pendingApplication = instituteService.findApplicationByInstituteAndConfirmed(instituteInfo.getId(), false);
			// only apply when there is not already a pending application for the same department
			if (!(pendingApplication != null && departmentId.equals(pendingApplication.getDepartmentInfo().getId()))) {
				this.apply();
			}
		}

		return Constants.SUCCESS;
	}
	
	private void apply() {
		logger.debug("entering apply method (backing bean)");
		logger.debug("instituteId: " + instituteInfo.getId());

		try {
			Long applicationId = instituteService.applyAtDepartment(instituteInfo.getId(), departmentId, user.getId());
			logger.debug("created department application: " + applicationId);
		} catch (Exception e) {
			logger.debug(e);
			addError(i18n("error_department_application_start"));
		}
	}


	private void saveInstituteLogo() throws DocumentApplicationException, IOException {
		// fetch uploaded file and remove it from upload manager
		UploadedDocument uploaded = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
		if (uploaded != null) {
			if (instituteInfo.getImageId() != null) {
				getDocumentService().removeFolderEntry(instituteInfo.getImageId());
			}
			FileInfo imageFile = new FileInfo();

			imageFile.setName(Constants.ORGANISATION_IMAGE_NAME);
			imageFile.setFileName(uploaded.getFileName());
			imageFile.setContentType(uploaded.getContentType());
			imageFile.setFileSize(uploaded.getFileSize());
			imageFile.setInputStream(uploaded.getInputStream());

			FolderInfo folder = getDocumentService().getFolder(instituteInfo);
			getDocumentService().createFileEntry(imageFile, folder);

			permitRolesImageReadPermission(imageFile);

			instituteInfo.setImageId(imageFile.getId());

			removeSessionBean(Constants.UPLOADED_FILE);
			getUploadFileManager().removeDocument(uploaded);
		}
	}

	private void permitRolesImageReadPermission(FileInfo imageFile) {
		// FIXME Should be done within the business layer
		securityService.setPermissions(Roles.ANONYMOUS, imageFile, LectureAclEntry.READ);
		securityService.setPermissions(Roles.USER, imageFile, LectureAclEntry.READ);
	}

	public void removeImage(ActionEvent event) throws DocumentApplicationException {
		if (instituteInfo.getImageId() != null) {
			Long fileId = instituteInfo.getImageId();
			instituteInfo.setImageId(null);
			getDocumentService().removeFolderEntry(fileId);
		}
		instituteService.update(instituteInfo);
		setBean(Constants.LAST_VIEW, Constants.USER_PROFILE_VIEW_PAGE);
	}

	/** ***************************** begin application ******************** */

	public Long getUniversityId() {
		if (universityId == null) {
			departmentId = instituteService.findInstitute(instituteInfo.getId()).getDepartmentId();
			universityId = departmentService.findDepartment(departmentId).getUniversityId();
		} 
		return universityId;
	}

	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllUniversities() {
		List<SelectItem> universityItems = new ArrayList<SelectItem>();
		List<UniversityInfo> allEnabledUniversities = universityService.findUniversitiesByEnabled(true);
		List<UniversityInfo> allDisabledUniversities = universityService.findUniversitiesByEnabled(false);
		
		if (!allEnabledUniversities.isEmpty()) {
			universityItems.add(new SelectItem(Constants.UNIVERSITIES_ENABLED, i18n("universities_enabled")));
			for (UniversityInfo university: allEnabledUniversities) {
				universityItems.add(new SelectItem(university.getId(),university.getName()));
			}
		}
		
		if (!allDisabledUniversities.isEmpty()) {
			universityItems.add(new SelectItem(Constants.UNIVERSITIES_ENABLED, i18n("universities_disabled")));
			for (UniversityInfo university: allDisabledUniversities) {
				universityItems.add(new SelectItem(university.getId(),university.getName()));
			}
		}
		
		return universityItems;
	}
	
	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllDepartments() {
		logger.debug("getting departments for university:" + universityId);

		List<SelectItem> departmentItems = new ArrayList<SelectItem>();

		List<DepartmentInfo> allEnabledDepartments = departmentService.findDepartmentsByUniversityAndEnabled(getUniversityId(), true);
		if (!allEnabledDepartments.isEmpty()) {
			departmentItems.add(new SelectItem(Constants.DEPARTMENTS_ENABLED, i18n("departments_enabled")));
			sortInDepartments(allEnabledDepartments, departmentItems);
		}
		
		List<DepartmentInfo> allDisabledDepartments = departmentService.findDepartmentsByUniversityAndEnabled(getUniversityId(), false);
		if(!allDisabledDepartments.isEmpty()) {
			departmentItems.add(new SelectItem(Constants.DEPARTMENTS_DISABLED, i18n("departments_disabled")));
			sortInDepartments(allDisabledDepartments, departmentItems);
		}
		return departmentItems;
	}

	private void sortInDepartments(List<DepartmentInfo> departments, List<SelectItem> departmentItems) {
		String suffixOffical = " - ("+ i18n("departmenttype_official") + ")";
		String suffixNonOffical = " - ("+ i18n("departmenttype_non_offical") + ")";

		for (DepartmentInfo department : departments) {
			if (department.getDepartmentType() == DepartmentType.OFFICIAL) {
				departmentItems.add(new SelectItem(department.getId(), department.getName() + suffixOffical ));
			} else if (department.getDepartmentType() == DepartmentType.NONOFFICIAL) {
				departmentItems.add(new SelectItem(department.getId(), department.getName() + suffixNonOffical));
			} 
		}
	}
	
	public void processUniversitySelectChanged(ValueChangeEvent event) {
		if (event.getNewValue() instanceof Long) {
			universityId = (Long) event.getNewValue();
			logger.info("ValueChangeEvent: Changing university id for new institute to " + universityId);
		}
	}

	/**
	 * Store the selected institute into session scope and go to institute
	 * disable confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectInstituteAndConfirmDisable() {
		logger.debug("Starting method selectInstituteAndConfirmDisable");
		logger.debug(instituteInfo.getId());
		setBean(Constants.INSTITUTE_INFO, instituteInfo);

		return Constants.INSTITUTE_CONFIRM_DISABLE_PAGE;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public ApplicationInfo getApplicationInfo() {
		return applicationInfo;
	}

	public void setApplicationInfo(ApplicationInfo applicationInfo) {
		this.applicationInfo = applicationInfo;
	}

	public Long getDepartmentId() {
		if (departmentId == null) {
			departmentId = instituteInfo.getDepartmentId();
		}
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getPendingApplicationInfo() {
		// abort, when there is no instituteId set
		if (instituteInfo == null || instituteInfo.getId() == null) {
			return null;
		}
		// check whether there is a pending application request
		ApplicationInfo pendingApplication = instituteService.findApplicationByInstituteAndConfirmed(instituteInfo
				.getId(), false);
		String appStatusDescription = "";
		if (pendingApplication != null) {
			appStatusDescription = i18n("application_pending_info", pendingApplication.getDepartmentInfo().getName());
		}
		// return information string if there is a pending application
		if (!appStatusDescription.equals("")) {
			return appStatusDescription;
		} else {
			return null;
		}
	}

	public String getPendingApplicationResponsibleInfo() {
		// abort, when there is no instituteId set
		if (instituteInfo == null || instituteInfo.getId() == null) {
			return null;
		}
		// check whether there is a pending application request
		ApplicationInfo pendingApplication = instituteService.findApplicationByInstituteAndConfirmed(instituteInfo
				.getId(), false);
		String appStatusDescription = "";
		if (pendingApplication != null) {
			appStatusDescription = i18n("application_pending_responsible_info", pendingApplication.getDepartmentInfo()
					.getOwnerName());
		}
		// return information string if there is a pending application
		if (!appStatusDescription.equals("")) {
			return appStatusDescription;
		} else {
			return null;
		}
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

}
