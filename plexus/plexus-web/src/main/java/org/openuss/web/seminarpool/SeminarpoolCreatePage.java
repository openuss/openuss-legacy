package org.openuss.web.seminarpool;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.xss.HtmlInputFilter;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;

/**
 * Seminarpool Create Page Controller
 * * Backing bean for the seminarpool registration. Is responsible starting the
 * wizard, binding the values and registering the seminarpool.
 * 
 * @author PS-Seminarplatzvergabeteam
 * 
 */
@Bean(name = Constants.SEMINARPOOL_CREATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class SeminarpoolCreatePage extends BasePage {

private static final Logger logger = Logger.getLogger(SeminarpoolCreatePage.class);
	
	@Property(value = "#{seminarpoolInfo}")
	protected SeminarpoolInfo seminarpoolInfo;
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService;
	@Property(value="#{universityService}")
	protected UniversityService universityService;
		
	private String name;
	private String shortcut;
	private String password;
	private String description;
	private Integer accessType;
	private Integer maxSeminarAllocations;
	private Integer priorities;
	private Date registrationStartTime;
	private Date registrationEndTime;
	private Long universityId;
	
	
	/* ----- business logic ----- */
	
	public String create() {
//		if(seminarpoolService.isUniqueShortcut(shortcut)){
//			logger.debug("START CREAT GROUP");
			logger.debug("START CREATE SEMINARPOOL");
			// create seminarpool info object
			seminarpoolInfo = new SeminarpoolInfo();
			seminarpoolInfo.setId(null);
			seminarpoolInfo.setName(name);
			seminarpoolInfo.setShortcut(shortcut);
			// XSS Filter Content
			seminarpoolInfo.setDescription(new HtmlInputFilter().filter(description));
			if (accessType == 0){
				password = null;
			}	
			seminarpoolInfo.setPassword(password);
			seminarpoolInfo.setMaxSeminarAllocations(maxSeminarAllocations);
			seminarpoolInfo.setPriorities(priorities);
			
			seminarpoolInfo.setRegistrationStartTime(registrationStartTime);
			seminarpoolInfo.setRegistrationEndTime(registrationEndTime);
			seminarpoolInfo.setSeminarpoolStatus(org.openuss.seminarpool.SeminarpoolStatus.PREPARATIONPHASE);
			
			seminarpoolInfo.setUniversityId(universityId);

			// create seminarpool and set id
			Long newSeminarpoolId = seminarpoolAdministrationService.createSeminarpool(seminarpoolInfo, user.getId());
			seminarpoolInfo.setId(newSeminarpoolId);
			
			logger.debug("END CREATE SEMINARPOOL");
			return "desktop";
	}

	public List<SelectItem> getAccessTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(0, i18n("seminarpool_accesstype_open")));
		items.add(new SelectItem(1, i18n("seminarpool_accesstype_password")));
		return items;
	}

	public void processAccessTypeChanged(ValueChangeEvent event) {
		Object accessTypeGroup = event.getNewValue();
		accessType = (Integer) accessTypeGroup;
		if (accessType == 1){
			password = "Password";
		} else {
			password = null;
		}
	}
	
	
/**** University selection stuff  *****/
	
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
	
	/* ----- getter and setter ----- */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortcut() {
		return shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAccessType() {
		return accessType;
	}

	public void setAccessType(Integer accessType) {
		this.accessType = accessType;
	}

	public Integer getMaxSeminarAllocations() {
		return maxSeminarAllocations;
	}
	
	public void setMaxSeminarAllocations(Integer maxSeminarAllocations) {
		this.maxSeminarAllocations = maxSeminarAllocations;
	}
	
	public Integer getPriorities() {
		return priorities;
	}
	
	public void setPriorities(Integer priorities) {
		this.priorities=priorities;
	}
	
	public void setRegistrationStartTime(Date registrationStartTime){
		this.registrationStartTime=registrationStartTime;
	}
	
	public Date getRegistrationStartTime() {
		return registrationStartTime;
	}
	
	public void setRegistrationEndTime(Date registrationEndTime){
		this.registrationEndTime=registrationEndTime;
	}
	
	public Date getRegistrationEndTime(){
		return registrationEndTime;
	}
	
	public void setUniversityId(Long universityId){
		this.universityId=universityId;
	}
	
	public Long getUniversityId(){
		return universityId;
	}

	public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
		return seminarpoolAdministrationService;
	}

	public void setSeminarpoolAdministrationService(SeminarpoolAdministrationService seminarpoolAdministrationService) {
		this.seminarpoolAdministrationService = seminarpoolAdministrationService;
	}

	public SeminarpoolInfo getSeminarpoolInfo() {
		return seminarpoolInfo;
	}

	public void setSeminarpoolInfo(SeminarpoolInfo seminarpoolInfo) {
		this.seminarpoolInfo = seminarpoolInfo;
	}
	
	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}
	

}
