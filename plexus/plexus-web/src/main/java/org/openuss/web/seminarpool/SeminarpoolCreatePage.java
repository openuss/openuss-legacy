package org.openuss.web.seminarpool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
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
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.framework.web.xss.HtmlInputFilter;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.OrganisationServiceException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

import org.openuss.security.UserInfo;
import org.openuss.seminarpool.ConditionType;
import org.openuss.seminarpool.SeminarConditionInfo;
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
@Bean(name = Constants.SEMINARPOOL_CREATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class SeminarpoolCreatePage extends BasePage {

private static final Logger logger = Logger.getLogger(SeminarpoolCreatePage.class);
	
	@Property(value = "#{seminarpoolInfo}")
	protected SeminarpoolInfo seminarpoolInfo;
	@Property(value = "#{seminarConditionInfo}")
	protected SeminarConditionInfo seminarConditionInfo;
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService;
	@Property(value="#{universityService}")
	protected UniversityService universityService;
	@Property(value="#{desktopService2}")
	protected DesktopService2 desktopService2;
	@Property(value="#{"+ Constants.SEMINARPOOL_CONDITIONS_LIST + "}")
	private List<SeminarConditionInfo> conditionsList;
	
	private ConditionsTable conditionsTable = new ConditionsTable();

	
	/* ----- business logic ----- */
	
	/**
	 * Refreshing seminarpool entity
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing seminarpool session object");
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
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		breadcrumbs.init();
		
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_create_block"));
		crumb.setHint(i18n("seminarpool_create_block"));
		breadcrumbs.addCrumb(crumb);
	}
	
	
	/**
	 * starts the seminarpool registration process
	 * @return Outcome
	 */
	public String start() {
		logger.debug("Start seminarpool registration process");

		// create new objects
		seminarpoolInfo = new SeminarpoolInfo();
		seminarConditionInfo = new SeminarConditionInfo();
		conditionsList = new ArrayList<SeminarConditionInfo>();
		setSessionBean(Constants.SEMINARPOOL_CONDITIONS_LIST, conditionsList);
		
		return Constants.SEMINARPOOL_REGISTRATION_STEP1_PAGE;
	}
	
	/**
	 * Final creation method
	 * @return Outcome
	 */
	public String create() {
			logger.debug("START CREATE SEMINARPOOL");
//FIXME	ACCESS-TYPE regulation
			seminarpoolInfo.setSeminarpoolStatus(SeminarpoolStatus.PREPARATIONPHASE);
			// create seminarpool and set id
			Long newSeminarpoolId = seminarpoolAdministrationService.createSeminarpool(seminarpoolInfo, user.getId());
			seminarpoolInfo.setId(newSeminarpoolId);
			// add the conditions
			for(SeminarConditionInfo aktcondi : conditionsList) {
				aktcondi.setSeminarpoolId(seminarpoolInfo.getId());
				seminarpoolAdministrationService.addConditionToSeminarpool(aktcondi);
			}
			// create Bookmark
			try{
				DesktopInfo desktopInfo = desktopService2.findDesktopByUser(user.getId()); 
				desktopService2.linkSeminarpool(desktopInfo.getId(), newSeminarpoolId);
			} catch (DesktopException ex){
				return "seminarpool_create_failure";
			}
			// clean session
			removeSessionBean(Constants.SEMINARPOOL_INFO);
			removeSessionBean(Constants.SEMINARPOOL_CONDITIONS_LIST);

			logger.debug("END CREATE SEMINARPOOL");
			return "desktop";
	}
	
	/**
	 * add the new condition temporarily into the list
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void addCondition(ActionEvent event) throws Exception {
		conditionsList.add(seminarConditionInfo);
		seminarConditionInfo = new SeminarConditionInfo();
		setSessionBean(Constants.SEMINARPOOL_CONDITIONS_LIST, conditionsList);
	}
	
	public void removeCondition(ActionEvent event) {
		
	}

// ACCESS-TYPE-SELECTION
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

// CONDITION-TYPE-SELECTION
	/**
	 * get possible seminarpool conditiontypes
	 * @return List of selectable conditiontypes
	 */
	public List<SelectItem> getConditionTypes() {
		List<SelectItem> conditionItems = new ArrayList<SelectItem>();
		conditionItems.add(new SelectItem(ConditionType.TEXTFIELD, i18n("seminarpool_conditiontype_textfield")));
		conditionItems.add(new SelectItem(ConditionType.TEXTAREA, i18n("seminarpool_conditiontype_textarea")));
		conditionItems.add(new SelectItem(ConditionType.CHECKBOX, i18n("seminarpool_conditiontype_textfield")));
		return conditionItems;
	}
	
	/**
	 * processing the selection of an entry in the conditiontype pulldown menu
	 * @param event ValueChangeEvent
	 */
	public void processConditionTypeChanged(ValueChangeEvent event) {
		Object conditionTypeGroup = event.getNewValue();
		seminarConditionInfo.setFieldType((ConditionType) conditionTypeGroup);
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
	
	
	/**
	 * Local DataModel of seminarpool conditions
	 */
	private class ConditionsTable extends AbstractPagedTable<SeminarConditionInfo> {

		private static final long serialVersionUID = 449438749521068451L;

		@Override
		public DataPage<SeminarConditionInfo> getDataPage(int startRow, int pageSize) {
			if(conditionsList == null)
				conditionsList = new ArrayList<SeminarConditionInfo>();
//FIXME 			sort(conditionsList);
			return new DataPage<SeminarConditionInfo>(conditionsList.size(), 0, conditionsList);
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

	public SeminarConditionInfo getSeminarConditionInfo() {
		return seminarConditionInfo;
	}

	public void setSeminarConditionInfo(SeminarConditionInfo seminarConditionInfo) {
		this.seminarConditionInfo = seminarConditionInfo;
	}

	public List<SeminarConditionInfo> getConditionsList() {
		return conditionsList;
	}

	public void setConditionsList(List<SeminarConditionInfo> conditionsList) {
		this.conditionsList = conditionsList;
	}

	public ConditionsTable getConditionsTable() {
		return conditionsTable;
	}

	public void setConditionsTable(ConditionsTable conditionsTable) {
		this.conditionsTable = conditionsTable;
	}
	
}
