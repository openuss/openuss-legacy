package org.openuss.web.seminarpool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
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
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.web.Constants;
import org.openuss.web.BasePage;


/**
 * 
 * @author Simon Weiﬂ
 */
/*
@Bean(name = "views$secured$seminarpool$seminarpooloptions", scope = Scope.REQUEST)
@View */
public class SeminarpoolOptionsPage extends BasePage {

	private static final long serialVersionUID = -202776319652385870L;
	private static final Logger logger = Logger.getLogger(SeminarpoolOptionsPage.class);
	
	@Property(value = "#{securityService}")
	private SecurityService securityService;

	private ApplicationInfo applicationInfo = new ApplicationInfo();

	private Long universityId;
	
	@Property(value = "#{seminarpoolInfo}")
	protected SeminarpoolInfo seminarpoolInfo;
	
	@Property(value = "#{seminarpoolService}")
	protected SeminarpoolAdministrationService seminarpoolService;
	
	@Property(value = "#{universityService}")
	protected UniversityService universityService;

/*	@Prerender
	public void prerender() {
		super.prerender();
		addPageCrumb();
	}
*/
/*	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("institute_command_settings"));
		crumb.setHint(i18n("institute_command_settings"));

		breadcrumbs.loadInstituteCrumbs(instituteInfo);
		breadcrumbs.addCrumb(crumb);
	}
*/

	/**
	 * Save seminarpool options.
	 * 
	 * @return outcome
	 */
	public String saveSeminarpool()  {
		// save actual seminarpool data
		seminarpoolService.updateSeminarpool(seminarpoolInfo);
		addMessage(i18n("seminarpool_message_command_save_succeed"));

		return Constants.SUCCESS;
	}



	/** ***************************** begin application ******************** */

/*	public Long getUniversityId() {
		if (universityId == null) {
			universityId = SeminarpoolService.findDepartment(departmentId).getUniversityId();
		} 
		return universityId;
	}
*/

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

	
	public void processUniversitySelectChanged(ValueChangeEvent event) {
		if (event.getNewValue() instanceof Long) {
			universityId = (Long) event.getNewValue();
			logger.info("ValueChangeEvent: Changing university id for new seminarpool to " + universityId);
		}
	}

	/**
	 * Store the selected seminarpool into session scope and go to seminarpool
	 * disable confirmation page.
	 * 
	 * @return Outcome
	 *
	public String selectInstituteAndConfirmDisable() {
		logger.debug("Starting method selectInstituteAndConfirmDisable");
		logger.debug(instituteInfo.getId());
		setSessionBean(Constants.INSTITUTE_INFO, instituteInfo);

		return Constants.INSTITUTE_CONFIRM_DISABLE_PAGE;
	}
*/

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


/*	public String getPendingApplicationInfo() {
		// abort, when there is no seminarpoolId set
		if (seminarpoolInfo == null || seminarpoolInfo.getId() == null) {
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
		if (seminarpoolInfo == null || seminarpoolInfo.getId() == null) {
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
*/

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}
	
	public Long getUniversityId() {
		return universityId;
	}

}
