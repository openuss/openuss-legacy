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
	
	/**
	 * event handler which is called when the "search scope" combo box is changed
	 * @param vce
	 */
	public void searchScopeChanged(ValueChangeEvent vce){
		// "result type" combo box is not required to be filled here because it is always generated dynamically
		
		// reset all other combo boxes
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
		
		List<SelectItem> universitiesToDisplay = new ArrayList<SelectItem>();
		UniversityInfo universityInfo;
		
		// set possible organisations
		List universities = universityService.findUniversitiesByEnabled(true);
		for(Object universityTemp : universities){
			universityInfo = (UniversityInfo) universityTemp;
			universitiesToDisplay.add(
					new SelectItem(
							universityInfo.getId().intValue(), 
							universityInfo.getName()));
		}
		extendedSearchResults.setOrganisations(universitiesToDisplay);
		
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
		
		List<SelectItem> departmentsToDisplay = new ArrayList<SelectItem>();
		DepartmentInfo departmentInfo;
		
		// determine the departments which belong to the given university
		Long selectedOrganisation = (Long) vce.getNewValue();
		List departments = departmentService.findDepartmentsByUniversityAndEnabled(selectedOrganisation, true);
		for(Object departmentTemp : departments){
			departmentInfo = (DepartmentInfo) departmentTemp;
			departmentsToDisplay.add(
					new SelectItem(
							departmentInfo.getId().intValue(), 
							departmentInfo.getName()));
		}
		extendedSearchResults.setSuborganisations(departmentsToDisplay);

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
		
		List<SelectItem> institutesToDisplay = new ArrayList<SelectItem>();
		InstituteInfo instituteInfo;
		
		// determine the institutions which belong to the given department
		Long selectedSuborganisation = (Long) vce.getNewValue();
		List institutes = instituteService.findInstitutesByDepartmentAndEnabled(selectedSuborganisation, true);
		for(Object instituteTemp : institutes){
			instituteInfo = (InstituteInfo) instituteTemp;
			institutesToDisplay.add(
					new SelectItem(
							instituteInfo.getId().intValue(), 
							instituteInfo.getName()));
		}
		extendedSearchResults.setInstitutions(institutesToDisplay);
		
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
		
		List<SelectItem> courseTypesToDisplay = new ArrayList<SelectItem>();
		List<SelectItem> periodToDisplay = new ArrayList<SelectItem>();
		CourseTypeInfo courseTypeInfo;
		PeriodInfo periodInfo;
		
		// determine the course types offered by the given institution
		Long selectedInstitute = (Long) vce.getNewValue();
		List courseTypes = courseTypeService.findCourseTypesByInstitute(selectedInstitute); 
		for(Object courseTypeTemp : courseTypes){
			courseTypeInfo = (CourseTypeInfo) courseTypeTemp;
			courseTypesToDisplay.add(
					new SelectItem(
							courseTypeInfo.getId().intValue(), 
							courseTypeInfo.getName()));
		}
		extendedSearchResults.setCourseTypes(courseTypesToDisplay);

		// determine periods in which the institute offers courses
		List periods = universityService.findPeriodsByUniversity(extendedSearchResults.getOrganisationId());
		for(Object periodTemp : periods){
			periodInfo = (PeriodInfo) periodTemp;
			periodToDisplay.add(
					new SelectItem(
							periodInfo.getId().intValue(), 
							periodInfo.getName()));
		}
		extendedSearchResults.setPeriods(periodToDisplay);
		
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




	

	

}
