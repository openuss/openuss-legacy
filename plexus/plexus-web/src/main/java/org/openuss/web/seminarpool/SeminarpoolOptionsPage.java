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
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.seminarpool.SeminarpoolAccessType;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.web.Constants;
import org.openuss.web.BasePage;


/**
 * 
 * @author PS-Seminarplaceallocation
 */

@Bean(name = "views$secured$seminarpool$seminarpooloptions", scope = Scope.REQUEST)
@View 
public class SeminarpoolOptionsPage extends BasePage {
	
	private static final long serialVersionUID = -202776319652385870L;
	private static final Logger logger = Logger.getLogger(SeminarpoolOptionsPage.class);
	
	@Property(value = "#{securityService}")
	private SecurityService securityService;

	private Long universityId;
//	private Integer accessType; 
	
	@Property(value = "#{seminarpoolInfo}")
	protected SeminarpoolInfo seminarpoolInfo;
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService;
	
	@Property(value = "#{universityService}")
	protected UniversityService universityService;

	@Prerender
	public void prerender() throws Exception {
		if (seminarpoolInfo == null) {
			seminarpoolInfo = (SeminarpoolInfo) getSessionBean(Constants.SEMINARPOOL);
		}
		if (seminarpoolInfo == null) {
			addMessage(i18n("message_error_seminarpool_page"));
			redirect(Constants.OUTCOME_BACKWARD);
		} else {
			if (!isPostBack()) {
				logger.debug("---------- is not postback ---------- refreshing seminarpool");
//				super.prerender();
			} else {
//FIXME				breadcrumbs.loadSeminarpoolCrumbs(seminarpoolInfo);
			}
		}
		setSessionBean(Constants.SEMINARPOOL, seminarpoolInfo);
//FIXME		addPageCrumb();
	}

/*	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("test1"));
		crumb.setHint(i18n("test test"));

		breadcrumbs.loadInstituteCrumbs(seminarpoolInfo);
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
		seminarpoolAdministrationService.updateSeminarpool(seminarpoolInfo);
		addMessage(i18n("seminarpool_message_command_save_succeed"));

		return Constants.SUCCESS;
	}



	/** ***************************** begin application ******************** */


	public List<SelectItem> getAccessTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(SeminarpoolAccessType.OPEN, i18n("seminarpool_accesstype_open")));
		items.add(new SelectItem(SeminarpoolAccessType.PASSWORD, i18n("seminarpool_accesstype_password")));
		return items;
	}

	public void processAccessTypeChanged(ValueChangeEvent event) {
		Object accessTypeGroup = event.getNewValue();
		seminarpoolInfo.setAccessType((SeminarpoolAccessType) accessTypeGroup);
		if ( seminarpoolInfo.getAccessType() == SeminarpoolAccessType.PASSWORD){
			seminarpoolInfo.setPassword("Password");
		} else {
			seminarpoolInfo.setPassword(null);
		}
	}
	
	
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
		logger.debug("Starting method selectSeminarpoolAndConfirmDisable");
		logger.debug(seminarpoolInfo.getId());
		setSessionBean(Constants.SEMINARPOOL_INFO, seminarpoolInfo);

		return Constants.SEMINARPOOL_CONFIRM_DISABLE_PAGE;
	}
*/

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}
	
	public Long getUniversityId() {
		return universityId;
	}

	public SeminarpoolInfo getSeminarpoolInfo() {
		return seminarpoolInfo;
	}

	public void setSeminarpoolInfo(SeminarpoolInfo seminarpoolInfo) {
		this.seminarpoolInfo = seminarpoolInfo;
	}

	public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
		return seminarpoolAdministrationService;
	}

	public void setSeminarpoolAdministrationService(
			SeminarpoolAdministrationService seminarpoolAdministrationService) {
		this.seminarpoolAdministrationService = seminarpoolAdministrationService;
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}
	

/*	public Integer getAccessType() {
		return accessType;
	}
	

	public void setAccessType(Integer accessType) {
		this.accessType = accessType;
	}
*/
}
