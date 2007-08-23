package org.openuss.web.search;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.CourseTypeService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.LectureSearcher;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.lecture.UniversityType;
import org.openuss.search.DomainResult;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.springmodules.lucene.search.LuceneSearchException;

/**
 * Lecture Extended Search page 
 * @author Kai Stettner
 * @author Malte Stockmann
 */
@Bean(name = "views$public$search$extendedsearch", scope = Scope.REQUEST)
@View
public class ExtendedSearchPage extends BasePage {
	
private static final Logger logger = Logger.getLogger(ExtendedSearchPage.class);
	
	@Property(value="#{universityService}")
	private UniversityService universityService;

	@Property(value="#{departmentService}")
	private DepartmentService departmentService;
	
	@Property(value="#{instituteService}")
	private InstituteService instituteService;
	
	@Property(value="#{courseTypeService}")
	private CourseTypeService courseTypeService;
	
	@Property(value="#{lectureSearcher}")
	private LectureSearcher lectureSearcher;
	
	@Property(value="#{extended_search_results}")
	private ExtendedSearchResults extendedSearchResults;
	
	private ExtendedSearchResultDataProvider resultProvider = new ExtendedSearchResultDataProvider();	 
	
	@Prerender
	public void prerender(){
		//bread crumbs shall not be displayed on search pages
		crumbs.clear();
	}
	
	/**
	 * performs an extended search
	 * @return outcome of action (used for navigation)
	 */
	public String extendedSearch() {
		logger.debug("Starting method extendedSearch");
		if (StringUtils.isNotBlank(extendedSearchResults.getTextToSearch())) {
			logger.debug("Extended Search for "+extendedSearchResults.getTextToSearch());
			try {
				extendedSearchResults.setHits(lectureSearcher.search(extendedSearchResults.getTextToSearch()));
			} catch (LuceneSearchException ex) {
				logger.error(ex);
				addError(i18n("search_text_error"));
			}
		}
		return Constants.EXTENDED_SEARCH_RESULT;
	}
	
	/**
	 * dummy method - all the necessary action is performed by value change listeners which are 
	 * called during the JSF lifecycle before this method will be executed 
	 * 
	 */
	public String extendedSearchCriteriaUpdate(){
		return Constants.EXTENDED_SEARCH;
	}
	
	/*** "ON CHANGE" EVENT HANDLER ***/
	
	/**
	 * event handler which is called when the "search scope" combo box is changed
	 * @param vce
	 */
	public void searchScopeChanged(ValueChangeEvent vce){
		if(vce == null){
			return;
		}
		
		// "result type" combo box is not required to be filled here because it is always generated dynamically
		
		// clear all other combo boxes
		extendedSearchResults.setOrganisations(new ArrayList<SelectItem>());
		extendedSearchResults.setSuborganisations(new ArrayList<SelectItem>());
		extendedSearchResults.setInstitutions(new ArrayList<SelectItem>());
		extendedSearchResults.setCourseTypes(new ArrayList<SelectItem>());
		extendedSearchResults.setPeriods(new ArrayList<SelectItem>());
		
	}
	
	
		
	/**
	 * event handler which is called when the "result type" combo box is changed
	 * @param vce
	 */
	public void resultTypeChanged(ValueChangeEvent vce){
		logger.debug(">>> resultTypeChanged");

		if(vce == null){
			return;
		}
		
		// set content of combo box "organisation" if this is necessary for the selected result type
		Long resultTypeId = (Long) vce.getNewValue();
		if(resultTypeId > Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION){
			resetOrganisations(extendedSearchResults.getSearchScopeId());
		} else {
			extendedSearchResults.setOrganisations(new ArrayList<SelectItem>());
		}
		
		// reset all other combo boxes
		extendedSearchResults.setSuborganisations(new ArrayList<SelectItem>());
		extendedSearchResults.setInstitutions(new ArrayList<SelectItem>());
		extendedSearchResults.setCourseTypes(new ArrayList<SelectItem>());
		extendedSearchResults.setPeriods(new ArrayList<SelectItem>());
		
		logger.debug("<<< resultTypeChanged");
	}
	
	/**
	 * event handler which is called when the "organisation" combo box is changed
	 * @param vce
	 */
	public void organisationChanged(ValueChangeEvent vce){
		logger.debug(">>> organisationChanged");
		
		if(vce.getNewValue() == null){
			return;
		}
		
		// set content of combo box "suborganisation" if this is necessary for the selected result type
		Long selectedOrganisation = (Long) vce.getNewValue();
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION){
			resetSuborganisations(selectedOrganisation);
		} else {
			extendedSearchResults.setSuborganisations(new ArrayList<SelectItem>());
		}

		// reset the all other more detailed combo boxes
		extendedSearchResults.setInstitutions(new ArrayList<SelectItem>());
		extendedSearchResults.setCourseTypes(new ArrayList<SelectItem>());
		extendedSearchResults.setPeriods(new ArrayList<SelectItem>());
		
		logger.debug("<<< organisationChanged");
	}
	
	/**
	 * event handler which is called when the "suborganisation" combo box is changed
	 * @param vce
	 */
	public void suborganisationChanged(ValueChangeEvent vce){
		logger.debug(">>> suborganisationChanged");
		
		if(vce.getNewValue() == null){
			return;
		}
		
		// set content of combo box "institute" if this is necessary for the selected result type
		Long selectedSuborganisation = (Long) vce.getNewValue();
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION){
			resetInstitutes(selectedSuborganisation);
		} else {
			extendedSearchResults.setInstitutions(new ArrayList<SelectItem>());
		}
		
		// reset the all other more detailed combo boxes
		extendedSearchResults.setCourseTypes(new ArrayList<SelectItem>());
		extendedSearchResults.setPeriods(new ArrayList<SelectItem>());
		
		logger.debug("<<< suborganisationChanged");
	}
	
	
	
	/**
	 * event handler which is called when the "institution" combo box is changed
	 * @param vce
	 */
	public void institutionChanged(ValueChangeEvent vce){
		logger.debug(">>> institutionChanged");
		
		if(vce.getNewValue() == null){
			return;
		}
		
		// determine the course types offered by the given institution
		Long selectedInstitute = (Long) vce.getNewValue();
		resetCourseTypesAndPeriods(selectedInstitute);		
		
		logger.debug("<<< institutionChanged");
	}
	
	/**
	 * event handler which is called when the "course type" combo box is changed
	 * @param vce
	 */
	public void courseTypeChanged(ValueChangeEvent vce){
		// nothing to do
	}
	
	/**
	 * event handler which is called when the "period" combo box is changed
	 * @param vce
	 */
	public void periodChanged(ValueChangeEvent vce){
		// nothing to do
	}
	
	/*** RESET METHODS FOR COMBO BOXES ***/
	
	private void resetOrganisations(Long searchScopeId){
		// initialize
		List<SelectItem> universitiesToDisplay = new ArrayList<SelectItem>();
		UniversityInfo universityInfo;
		// set possible organisations
		UniversityType universityType = null;
		switch(searchScopeId.intValue()){
			case Constants.EXTENDED_SEARCH_SCOPE_UNIVERSITIES:
				universityType = UniversityType.UNIVERSITY;
				break;
			case Constants.EXTENDED_SEARCH_SCOPE_COMPANIES:
				universityType = UniversityType.COMPANY;
				break;
			case Constants.EXTENDED_SEARCH_SCOPE_OTHER:
				universityType = UniversityType.MISC;
				break;
		}
		List universities = universityService.findUniversitiesByTypeAndEnabled(universityType, true);
		if(universities.size() > 0){
			universitiesToDisplay.add(pleaseChooseSelectItem());
		} else {
			universitiesToDisplay.add(notFoundSelectItem());
		}
		for(Object universityTemp : universities){
			universityInfo = (UniversityInfo) universityTemp;
			universitiesToDisplay.add(
					new SelectItem(
							universityInfo.getId(), 
							universityInfo.getName()));
		}
		extendedSearchResults.setOrganisations(universitiesToDisplay);
	}
	
	private void resetSuborganisations(Long organisationId){
		List<SelectItem> departmentsToDisplay = new ArrayList<SelectItem>();
		DepartmentInfo departmentInfo;
		
		// determine the departments which belong to the given university
		if(organisationId > 0){
			List departments = departmentService.findDepartmentsByUniversityAndEnabled(organisationId, true);
			if(departments.size() > 0){
				departmentsToDisplay.add(pleaseChooseSelectItem());
			} else {
				departmentsToDisplay.add(notFoundSelectItem());
			}
			for(Object departmentTemp : departments){
				departmentInfo = (DepartmentInfo) departmentTemp;
				departmentsToDisplay.add(
						new SelectItem(
								departmentInfo.getId(), 
								departmentInfo.getName()));
			}
		} else if (organisationId == 0){
			departmentsToDisplay.add(pleaseChooseSelectItem());
		} else {
			departmentsToDisplay.add(notFoundSelectItem());
		}
		extendedSearchResults.setSuborganisations(departmentsToDisplay);
	}
	
	private void resetInstitutes(Long suborganisationId){
		List<SelectItem> institutesToDisplay = new ArrayList<SelectItem>();
		InstituteInfo instituteInfo;
		
		if(suborganisationId > 0){
			List institutes = instituteService.findInstitutesByDepartmentAndEnabled(suborganisationId, true);
			if(institutes.size() > 0){
				institutesToDisplay.add(pleaseChooseSelectItem());
			} else {
				institutesToDisplay.add(notFoundSelectItem());
			}
			for(Object instituteTemp : institutes){
				instituteInfo = (InstituteInfo) instituteTemp;
				institutesToDisplay.add(
						new SelectItem(
								instituteInfo.getId(), 
								instituteInfo.getName()));
			}
		} else if (suborganisationId == 0){
			institutesToDisplay.add(pleaseChooseSelectItem());
		} else {
			institutesToDisplay.add(notFoundSelectItem());
		}
		extendedSearchResults.setInstitutions(institutesToDisplay);
	}
	
	private void resetCourseTypesAndPeriods(Long instituteId){
		List<SelectItem> courseTypesToDisplay = new ArrayList<SelectItem>();
		List<SelectItem> periodToDisplay = new ArrayList<SelectItem>();
		CourseTypeInfo courseTypeInfo;
		PeriodInfo periodInfo;
		
		if(instituteId > 0){
			List courseTypes = courseTypeService.findCourseTypesByInstitute(instituteId);
			if(courseTypes.size() > 0){
				courseTypesToDisplay.add(pleaseChooseSelectItem());
			} else {
				courseTypesToDisplay.add(notFoundSelectItem());
			}
			for(Object courseTypeTemp : courseTypes){
				courseTypeInfo = (CourseTypeInfo) courseTypeTemp;
				courseTypesToDisplay.add(
						new SelectItem(
								courseTypeInfo.getId(), 
								courseTypeInfo.getName()));
			}
		} else if (instituteId == 0){
			courseTypesToDisplay.add(pleaseChooseSelectItem());
		} else {
			courseTypesToDisplay.add(notFoundSelectItem());
		}
		extendedSearchResults.setCourseTypes(courseTypesToDisplay);

		// determine periods in which the institute offers courses
		if(extendedSearchResults.getOrganisationId() > 0){
			List periods = universityService.findPeriodsByUniversity(extendedSearchResults.getOrganisationId());
			if(periods.size() > 0){
				periodToDisplay.add(pleaseChooseSelectItem());
			} else {
				periodToDisplay.add(notFoundSelectItem());
			}
			for(Object periodTemp : periods){
				periodInfo = (PeriodInfo) periodTemp;
				periodToDisplay.add(
						new SelectItem(
								periodInfo.getId(), 
								periodInfo.getName()));
			}
		} else if (extendedSearchResults.getOrganisationId() == 0){
			periodToDisplay.add(pleaseChooseSelectItem());
		} else {
			periodToDisplay.add(notFoundSelectItem());
		}	
		extendedSearchResults.setPeriods(periodToDisplay);
	}
	
	private SelectItem pleaseChooseSelectItem(){
		return new SelectItem(0L, "<< bitte angeben >>");
	}
	
	private SelectItem notFoundSelectItem(){
		return new SelectItem(-1L, "<< nicht gefunden >>");
	}
		
	/*** CHECK VISIBILITY METHODS ***/
	
	/**
	 * generates the CSS tag which determines whether the "organisation" combo box is displayed
	 * @return
	 */
	public String getVisibilityOrganisation(){
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION
				&& extendedSearchResults.getOrganisations().size() > 0){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}
	
	/**
	 * generates the CSS tag which determines whether the "suborganisation" combo box is displayed
	 * @return
	 */
	public String getVisibilitySuborganisation(){
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION
				&& extendedSearchResults.getSuborganisations().size() > 0){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}
	
	/**
	 * generates the CSS tag which determines whether the "institution" combo box is displayed
	 * @return
	 */
	public String getVisibilityInstitution(){
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION
				&& extendedSearchResults.getInstitutions().size() > 0){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}
	
	/**
	 * generates the CSS tag which determines whether the "course type" combo box is displayed
	 * @return
	 */
	public String getVisibilityCourseType(){
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE_TYPE
				&& extendedSearchResults.getCourseTypes().size() > 0){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}
	
	/**
	 * generates the CSS tag which determines whether the "period" combo box is displayed
	 * @return
	 */
	public String getVisibilityPeriod(){
		if(extendedSearchResults.getResultTypeId().intValue() > Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE_TYPE
				&& extendedSearchResults.getPeriods().size() > 0){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}
	
	// TODO: really required?
	private List<SelectItem> getEmptyComboBox(){
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(new Integer(-1), "- bitte auswählen -"));
		return items;
	}
	
	public String getOrganisationLabel(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		switch(extendedSearchResults.getSearchScopeId().intValue()){
			case Constants.EXTENDED_SEARCH_SCOPE_UNIVERSITIES:
				return rb.getString("extended_search_organisation_univ");
			case Constants.EXTENDED_SEARCH_SCOPE_COMPANIES:
				return rb.getString("extended_search_organisation_comp");
			case Constants.EXTENDED_SEARCH_SCOPE_OTHER:
				return rb.getString("extended_search_organisation_other");
			default:
				return "";
		}
	}
	
	public String getSuborganisationLabel(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		switch(extendedSearchResults.getSearchScopeId().intValue()){
			case Constants.EXTENDED_SEARCH_SCOPE_UNIVERSITIES:
				return rb.getString("extended_search_suborganisation_univ");
			case Constants.EXTENDED_SEARCH_SCOPE_COMPANIES:
				return rb.getString("extended_search_suborganisation_comp");
			case Constants.EXTENDED_SEARCH_SCOPE_OTHER:
				return rb.getString("extended_search_suborganisation_other");
			default:
				return "";
		}
	}
	
	public String getInstitutionLabel(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		switch(extendedSearchResults.getSearchScopeId().intValue()){
			case Constants.EXTENDED_SEARCH_SCOPE_UNIVERSITIES:
				return rb.getString("extended_search_institution_univ");
			case Constants.EXTENDED_SEARCH_SCOPE_COMPANIES:
				return rb.getString("extended_search_institution_comp");
			case Constants.EXTENDED_SEARCH_SCOPE_OTHER:
				return rb.getString("extended_search_institution_other");
			default:
				return "";
		}
	}
	
	public String getCourseTypeLabel(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		switch(extendedSearchResults.getSearchScopeId().intValue()){
			case Constants.EXTENDED_SEARCH_SCOPE_UNIVERSITIES:
				return rb.getString("extended_search_course_type_univ");
			case Constants.EXTENDED_SEARCH_SCOPE_COMPANIES:
				return rb.getString("extended_search_course_type_comp");
			case Constants.EXTENDED_SEARCH_SCOPE_OTHER:
				return rb.getString("extended_search_course_type_other");
			default:
				return "";
		}
	}
	
	public String getPeriodLabel(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		switch(extendedSearchResults.getSearchScopeId().intValue()){
			case Constants.EXTENDED_SEARCH_SCOPE_UNIVERSITIES:
				return rb.getString("extended_search_period_univ");
			case Constants.EXTENDED_SEARCH_SCOPE_COMPANIES:
				return rb.getString("extended_search_period_comp");
			case Constants.EXTENDED_SEARCH_SCOPE_OTHER:
				return rb.getString("extended_search_period_other");
			default:
				return "";
		}
	}
	
	public LectureSearcher getLectureSearcher() {
		return lectureSearcher;
	}

	public void setLectureSearcher(LectureSearcher lectureSearcher) {
		this.lectureSearcher = lectureSearcher;
	}
	
	public ExtendedSearchResultDataProvider getResultProvider() {
		return resultProvider;
	}

	public void setResultProvider(ExtendedSearchResultDataProvider results) {
		this.resultProvider = results;
	}

	public ExtendedSearchResults getExtendedSearchResults() {
		return extendedSearchResults;
	}

	public void setExtendedSearchResults(ExtendedSearchResults extendedSearchResults) {
		this.extendedSearchResults = extendedSearchResults;
	}
	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
		
	private class ExtendedSearchResultDataProvider extends AbstractPagedTable<DomainResult> {

		private static final long serialVersionUID = -2279124332432432432L;
		
		private DataPage<DomainResult> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<DomainResult> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				if (extendedSearchResults.getHits() == null) {
					page = new DataPage<DomainResult>(0,0,null);
				} else {
					page = new DataPage<DomainResult>(extendedSearchResults.getHitCounts(),0,extendedSearchResults.getHits());
				}
			}
			return page;
		}
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public CourseTypeService getCourseTypeService() {
		return courseTypeService;
	}

	public void setCourseTypeService(CourseTypeService courseTypeService) {
		this.courseTypeService = courseTypeService;
	}

	private List<SelectItem> getDummyData(){
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem(1, "Eintrag 1"));
		list.add(new SelectItem(2, "Eintrag 2"));
		return list;
	}


	

	

}
