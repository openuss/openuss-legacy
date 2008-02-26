package org.openuss.web.seminarpool;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.openuss.lecture.AccessType;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.LectureException;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;

/**
 * Seminarpool Create Page Controller
 * * Backing bean for the seminarpool registration. Is responsible starting the
 * wizard, binding the values and registering the seminarpool.
 * 
 * @author Simon Weiß
 * 
 */
@Bean(name = Constants.SEMINARPOOL_CREATION_CONTROLLER, scope = Scope.REQUEST)
@View

public class SeminarpoolCreatePage extends BasePage {

private static final Logger logger = Logger.getLogger(SeminarpoolCreatePage.class);
	
	@Property(value = "#{seminarpoolInfo}")
	protected SeminarpoolInfo seminarpoolInfo;
	@Property(value = "#{seminarpoolService}")
	protected SeminarpoolAdministrationService seminarpoolService;
		
	private String name;
	private String shortcut;
	private String password;
	private String description;
	private Integer accessType;
	private Integer maxSeminarAllocations;
	private Integer priorities;
	
	/* ----- business logic ----- */
	
	public String create() {
//		if(seminarpoolService.isUniqueShortcut(shortcut)){
//			logger.debug("START CREAT GROUP");
			logger.debug("START CREATE SEMINARPOOL");
			// create group info object
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
//			seminarpoolInfo.setAccessType(GroupAccessType.fromInteger(accessType));
//			seminarpoolInfo.setCreator(user.getId());
			
			seminarpoolInfo.setRegistrationStartTime(new Timestamp(12345L));
			seminarpoolInfo.setRegistrationEndTime(new Timestamp(123456L));
			seminarpoolInfo.setSeminarpoolStatus(org.openuss.seminarpool.SeminarpoolStatus.PREPARATIONPHASE);
			
			

			// create seminarpool and set id
			Long newSeminarpoolId = seminarpoolService.createSeminarpool(seminarpoolInfo, user.getId());
			seminarpoolInfo.setId(newSeminarpoolId);
		
/*			// clear fields
			name = null;
			shortcut = null;
			description = null;
			password = null;
//			accessType = 0;
*/		
			logger.debug("END CREATE SEMINARPOOL");
			return "seminarpool_create_success";
/*		} 
		else {
			// TODO - Lutz: Vernünftiges Propertie bitte
			addError("Test");
			return Constants.OPENUSS4US_GROUPS_CREATE;
		}
*/
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
	

	public SeminarpoolAdministrationService getSeminarpoolService() {
		return seminarpoolService;
	}

	public void setSeminarpoolService(SeminarpoolAdministrationService seminarpoolService) {
		this.seminarpoolService = seminarpoolService;
	}

	public SeminarpoolInfo getSeminarpoolInfo() {
		return seminarpoolInfo;
	}

	public void setSeminarpoolInfo(SeminarpoolInfo seminarpoolInfo) {
		this.seminarpoolInfo = seminarpoolInfo;
	}

}
