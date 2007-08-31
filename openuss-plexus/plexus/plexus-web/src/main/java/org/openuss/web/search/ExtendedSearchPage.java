package org.openuss.web.search;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
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
import org.openuss.search.ExtendedSearcher;
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
	
	@Property(value="#{extendedSearcher}")
	private ExtendedSearcher extendedSearcher;
	
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
		List<DomainResult> searchResult = null;
		if (StringUtils.isNotBlank(extendedSearchResults.getTextToSearch())) {
			logger.debug("Extended Search for "+extendedSearchResults.getTextToSearch());
			try {
				String domainType;
				switch(extendedSearchResults.getResultTypeId().intValue()){
				case Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION:
					domainType = "university";
					break;
				case Constants.EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION:
					domainType = "department";
					break;
				case Constants.EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION:
					domainType = "institute";
					break;
				case Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE_TYPE:
					domainType = "coursetype";
					break;
				case Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE:
					domainType = "course";
					break;
				default:
					throw new IllegalArgumentException("invalid result type id!");
				}
				
				searchResult = extendedSearcher.search(
						extendedSearchResults.getTextToSearch(),
						domainType,
						extendedSearchResults.getUniversityId(),
						extendedSearchResults.getDepartmentId(),
						extendedSearchResults.getInstituteId(),
						extendedSearchResults.getCourseTypeId(),
						extendedSearchResults.isOfficialOnly(), 
						extendedSearchResults.isTitleOnly()
						);
				extendedSearchResults.setHits(searchResult);
				if(searchResult == null || searchResult.size() == 0){
					getFacesContext().addMessage(null, new FacesMessage(i18n("extended_search_no_matches_found")) );
				}
				
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
			resetUniversities();
		} else {
			extendedSearchResults.setUniversities(new ArrayList<SelectItem>());
		}
		
		// reset all other combo boxes
		extendedSearchResults.setDepartments(new ArrayList<SelectItem>());
		extendedSearchResults.setInstitutes(new ArrayList<SelectItem>());
		extendedSearchResults.setCourseTypes(new ArrayList<SelectItem>());
		extendedSearchResults.setPeriods(new ArrayList<SelectItem>());
		
		logger.debug("<<< resultTypeChanged");
	}
	
	/**
	 * event handler which is called when the "organisation" combo box is changed
	 * @param vce
	 */
	public void universityChanged(ValueChangeEvent vce){
		logger.debug(">>> universityChanged");
		
		if(vce.getNewValue() == null){
			return;
		}
		
		// set content of combo box "suborganisation" if this is necessary for the selected result type
		Long selectedUniversity = (Long) vce.getNewValue();
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION){
			resetDepartments(selectedUniversity);
		} else {
			extendedSearchResults.setDepartments(new ArrayList<SelectItem>());
		}

		// reset the all other more detailed combo boxes
		extendedSearchResults.setInstitutes(new ArrayList<SelectItem>());
		extendedSearchResults.setCourseTypes(new ArrayList<SelectItem>());
		extendedSearchResults.setPeriods(new ArrayList<SelectItem>());
		
		logger.debug("<<< universityChanged");
	}
	
	/**
	 * event handler which is called when the "suborganisation" combo box is changed
	 * @param vce
	 */
	public void departmentChanged(ValueChangeEvent vce){
		logger.debug(">>> departmentChanged");
		
		if(vce.getNewValue() == null){
			return;
		}
		
		// set content of combo box "institute" if this is necessary for the selected result type
		Long selectedDepartment = (Long) vce.getNewValue();
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION){
			resetInstitutes(selectedDepartment);
		} else {
			extendedSearchResults.setInstitutes(new ArrayList<SelectItem>());
		}
		
		// reset the all other more detailed combo boxes
		extendedSearchResults.setCourseTypes(new ArrayList<SelectItem>());
		extendedSearchResults.setPeriods(new ArrayList<SelectItem>());
		
		logger.debug("<<< departmentChanged");
	}
	
	
	
	/**
	 * event handler which is called when the "institution" combo box is changed
	 * @param vce
	 */
	public void instituteChanged(ValueChangeEvent vce){
		logger.debug(">>> instituteChanged");
		
		if(vce.getNewValue() == null){
			return;
		}
		
		// determine the course types offered by the given institution
		Long selectedInstitute = (Long) vce.getNewValue();
		resetCourseTypesAndPeriods(selectedInstitute);		
		
		logger.debug("<<< instituteChanged");
	}
	
	/*** RESET METHODS FOR COMBO BOXES ***/
	
	private void resetUniversities(){
		// initialize
		List<SelectItem> universitiesToDisplay = new ArrayList<SelectItem>();
		UniversityInfo universityInfo;
		// set possible organisations
		List universities = universityService.findUniversitiesByEnabled(true);
		if(universities.size() > 0){
			universitiesToDisplay.add(getAllSelectItem());
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
		extendedSearchResults.setUniversityId(Constants.EXTENDED_SEARCH_GET_ALL);
		extendedSearchResults.setUniversities(universitiesToDisplay);
	}
	
	private void resetDepartments(Long organisationId){
		List<SelectItem> departmentsToDisplay = new ArrayList<SelectItem>();
		DepartmentInfo departmentInfo;
		
		// determine the departments which belong to the given university
		if(organisationId > 0){
			List departments = departmentService.findDepartmentsByUniversityAndEnabled(organisationId, true);
			if(departments.size() > 0){
				departmentsToDisplay.add(getAllSelectItem());
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
			departmentsToDisplay.add(getAllSelectItem());
		} else {
			departmentsToDisplay.add(notFoundSelectItem());
		}
		extendedSearchResults.setDepartments(departmentsToDisplay);
	}
	
	private void resetInstitutes(Long suborganisationId){
		List<SelectItem> institutesToDisplay = new ArrayList<SelectItem>();
		InstituteInfo instituteInfo;
		
		if(suborganisationId > 0){
			List institutes = instituteService.findInstitutesByDepartmentAndEnabled(suborganisationId, true);
			if(institutes.size() > 0){
				institutesToDisplay.add(getAllSelectItem());
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
			institutesToDisplay.add(getAllSelectItem());
		} else {
			institutesToDisplay.add(notFoundSelectItem());
		}
		extendedSearchResults.setInstitutes(institutesToDisplay);
	}
	
	private void resetCourseTypesAndPeriods(Long instituteId){
		List<SelectItem> courseTypesToDisplay = new ArrayList<SelectItem>();
		List<SelectItem> periodToDisplay = new ArrayList<SelectItem>();
		CourseTypeInfo courseTypeInfo;
		PeriodInfo periodInfo;
		
		if(instituteId > 0){
			List courseTypes = courseTypeService.findCourseTypesByInstitute(instituteId);
			if(courseTypes.size() > 0){
				courseTypesToDisplay.add(getAllSelectItem());
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
			courseTypesToDisplay.add(getAllSelectItem());
		} else {
			courseTypesToDisplay.add(notFoundSelectItem());
		}
		extendedSearchResults.setCourseTypes(courseTypesToDisplay);

		// determine periods in which the institute offers courses
		if(extendedSearchResults.getUniversityId() > 0){
			List periods = universityService.findPeriodsByUniversity(extendedSearchResults.getUniversityId());
			if(periods.size() > 0){
				periodToDisplay.add(getAllSelectItem());
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
		} else if (extendedSearchResults.getUniversityId() == 0){
			periodToDisplay.add(getAllSelectItem());
		} else {
			periodToDisplay.add(notFoundSelectItem());
		}	
		extendedSearchResults.setPeriods(periodToDisplay);
	}
	
	private SelectItem getAllSelectItem(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		return new SelectItem(Constants.EXTENDED_SEARCH_GET_ALL, rb.getString("extended_search_get_all"));
	}
	
	private SelectItem notFoundSelectItem(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		return new SelectItem(Constants.EXTENDED_SEARCH_NO_RECORDS_FOUND, rb.getString("extended_search_no_records_found"));
	}
		
	/*** CHECK VISIBILITY METHODS ***/
	
	/**
	 * generates the CSS tag which determines whether the "organisation" combo box is displayed
	 * @return
	 */
	public String getVisibilityUniversity(){
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION
				&& extendedSearchResults.getUniversities().size() > 0){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}
	
	/**
	 * generates the CSS tag which determines whether the "suborganisation" combo box is displayed
	 * @return
	 */
	public String getVisibilityDepartment(){
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION
				&& extendedSearchResults.getDepartments().size() > 0){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}
	
	/**
	 * generates the CSS tag which determines whether the "institution" combo box is displayed
	 * @return
	 */
	public String getVisibilityInstitute(){
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION
				&& extendedSearchResults.getInstitutes().size() > 0){
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
	
	public String getUniversityLabel(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		return rb.getString("extended_search_organisation_univ");
	}
	
	public String getDepartmentLabel(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		return rb.getString("extended_search_suborganisation_univ");
	}
	
	public String getInstituteLabel(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		return rb.getString("extended_search_institution_univ");
	}
	
	public String getCourseTypeLabel(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		return rb.getString("extended_search_course_type_univ");
	}
	
	public String getPeriodLabel(){
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		return rb.getString("extended_search_period_univ");
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

	public ExtendedSearcher getExtendedSearcher() {
		return extendedSearcher;
	}

	public void setExtendedSearcher(ExtendedSearcher extendedSearcher) {
		this.extendedSearcher = extendedSearcher;
	}

}
