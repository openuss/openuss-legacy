package org.openuss.web.seminarpool;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.seminarpool.SeminarpoolAccessType;
import org.openuss.web.Constants;


/**
 * 
 * @author PS-Seminarplaceallocation
 */

@Bean(name = "views$secured$seminarpool$seminarpooloptions", scope = Scope.REQUEST)
@View 
public class SeminarpoolOptionsPage extends AbstractSeminarpoolPage {
	
	private static final long serialVersionUID = -202776319652385870L;
	private static final Logger logger = Logger.getLogger(SeminarpoolOptionsPage.class);
	
	@Property(value = "#{universityService}")
	protected UniversityService universityService;

	private Long universityId;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_command_options_config"));
		crumb.setHint(i18n("seminarpool_command_options_config"));
		breadcrumbs.addCrumb(crumb);
	}

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
	
	/**
	 * goes to final delete seminarpool page
	 * @return outcome
	 */
	public String removeSeminarpool() {
		return "seminarpool_remove_confirmation";
	}
	
	/**
	 * deletes the seminarpool now
	 * @return outcome
	 */
	public String removeSeminarpoolFinal() {
		seminarpoolAdministrationService.removeSeminarpool(seminarpoolInfo.getId());
		removeSessionBean(Constants.SEMINARPOOL_INFO);
		return "desktop";
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

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}

	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}
}
