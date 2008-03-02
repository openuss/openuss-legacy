package org.openuss.web.seminarpool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.xss.HtmlInputFilter;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

import org.openuss.seminarpool.SeminarpoolAccessType;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.seminarpool.SeminarpoolStatus;

/**
 * Seminarpool Create Page Controller
 * * Backing bean for the seminarpool registration. Is responsible starting the
 * wizard, binding the values and registering the seminarpool.
 * 
 * @author PS-Seminarplatzvergabeteam
 * 
 */
@Bean(name = Constants.SEMINARPOOL_CREATION_CONTROLLER, scope = Scope.SESSION)
@View
public class SeminarpoolCreatePage extends BasePage {

private static final Logger logger = Logger.getLogger(SeminarpoolCreatePage.class);
	
	@Property(value = "#{seminarpoolInfo}")
	protected SeminarpoolInfo seminarpoolInfo;
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService;
	@Property(value="#{universityService}")
	protected UniversityService universityService;
	@Property(value="#{desktopService2}")
	protected DesktopService2 desktopService2;

	
	/* ----- business logic ----- */
	
	/**
	 * Refreshing seminarpool entity
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing institute session object");
		if (seminarpoolInfo != null) {
			seminarpoolInfo = seminarpoolAdministrationService.findSeminarpool(seminarpoolInfo.getId());
		} else {
			seminarpoolInfo = (SeminarpoolInfo) getSessionBean(Constants.SEMINARPOOL_INFO);
		}
		setSessionBean(Constants.SEMINARPOOL_INFO, seminarpoolInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing seminarpool session object");
		refreshSeminarpool();
		if (seminarpoolInfo == null) {
			addError(i18n("message_error_no_seminarpool_selected"));
			redirect(Constants.DESKTOP);
		} 
	}
	
	
	/**
	 * starts the seminarpool registration process
	 * @return Outcome
	 */
	public String start() {
		logger.debug("Start seminarpool registration process");

		// create new seminarpoolInfo object for session
		seminarpoolInfo = new SeminarpoolInfo();
		
		return Constants.SEMINARPOOL_REGISTRATION_STEP1_PAGE;
	}
	
	
	public String create() {
			logger.debug("START CREATE SEMINARPOOL");
//FIXME	ACCESS-TYPE regulation
			seminarpoolInfo.setSeminarpoolStatus(SeminarpoolStatus.PREPARATIONPHASE);
			// create seminarpool and set id
			Long newSeminarpoolId = seminarpoolAdministrationService.createSeminarpool(seminarpoolInfo, user.getId());
			seminarpoolInfo.setId(newSeminarpoolId);
			// create Bookmark
			try{
				DesktopInfo desktopInfo = desktopService2.findDesktopByUser(user.getId()); 
				desktopService2.linkSeminarpool(desktopInfo.getId(), newSeminarpoolId);
			} catch (DesktopException ex){
				return "seminarpool_create_failure";
			}
			
//			setSessionBean(Constants.SEMINARPOOL_INFO, seminarpoolInfo);

			logger.debug("END CREATE SEMINARPOOL");
			return "desktop";
	}

	/**
	 * get possible seminarpool accesstypes
	 * @return List of selectable accesstypes
	 */
	public List<SelectItem> getAccessTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(SeminarpoolAccessType.OPEN, i18n("seminarpool_accesstype_open")));
		items.add(new SelectItem(SeminarpoolAccessType.PASSWORD, i18n("seminarpool_accesstype_password")));
		return items;
	}
	
	/**
	 * processing the selection of an entry in the accesstype pulldown menu
	 * @param event ValueChangeEvent
	 */
	public void processAccessTypeChanged(ValueChangeEvent event) {
		Object accessTypeGroup = event.getNewValue();
		seminarpoolInfo.setAccessType((SeminarpoolAccessType) accessTypeGroup);
		if ( seminarpoolInfo.getAccessType() == SeminarpoolAccessType.PASSWORD){
			seminarpoolInfo.setPassword("Password");
		} else {
			seminarpoolInfo.setPassword(null);
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
			seminarpoolInfo.setUniversityId((Long) event.getNewValue());
			logger.info("ValueChangeEvent: Changing university id for new seminarpool to " + seminarpoolInfo.getUniversityId());
		}
	}
	
	private void refreshSeminarpool() {
		logger.debug("Starting method refresh seminarpool");
		if (seminarpoolInfo != null) {
			if (seminarpoolInfo.getId() != null) {
				seminarpoolInfo = seminarpoolAdministrationService.findSeminarpool(seminarpoolInfo.getId());
				setSessionBean(Constants.SEMINARPOOL_INFO, seminarpoolInfo);
			}
		}
	}
	
	/* ----- getter and setter ----- */
	
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

	public DesktopService2 getDesktopService2() {
		return desktopService2;
	}

	public void setDesktopService2(DesktopService2 desktopService2) {
		this.desktopService2 = desktopService2;
	}
	
}
