package org.openuss.web.search;

import java.io.FileNotFoundException;
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
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
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
		breadcrumbs.loadExtendedSearchCrumbs();
		if (!isPostBack()){
			extendedSearchResults = new ExtendedSearchResults();
			initSearchForm();
		}
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
				case Constants.EXTENDED_SEARCH_RESULT_TYPE_ALL:
					domainType = null;
					break;
					case Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION:
						domainType = "university";
						break;
					case Constants.EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION:
						domainType = "department";
						break;
					case Constants.EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION:
						domainType = "institute";
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
						extendedSearchResults.getPeriodId(),
						extendedSearchResults.isOfficialOnly(), 
						extendedSearchResults.isTitleOnly()
						);
				extendedSearchResults.setHits(searchResult);
				if(searchResult == null || searchResult.size() == 0){
					getFacesContext().addMessage(null, new FacesMessage(i18n("search_no_matches_found")) );
				}
			} catch (LuceneSearchException ex) {
				logger.error(ex);
				// search index file is not available (maybe the index was not created)
				if(ex.getCause().getClass().equals(FileNotFoundException.class)){
					addError(i18n("search_error_index_not_found"));
				// unspecified Lucene error
				} else {
					addError(i18n("search_text_error"));
				}
			} catch (Exception ex){
				logger.error(ex);
				// too many search results
				if(ex.toString().equals("org.apache.lucene.search.BooleanQuery$TooManyClauses: maxClauseCount is set to 1024")){
					addError(i18n("search_error_too_many_results"));
				// unspecified error
				} else {
					addError(i18n("search_text_error"));
				}
			}
		}
		return Constants.EXTENDED_SEARCH_RESULT;
	}
	
	public String initSearchForm(){
		extendedSearchResults.setTextToSearch("");
		extendedSearchResults.setTitleOnly(false);
		extendedSearchResults.setOfficialOnly(false);
		extendedSearchResults.setResultTypeId((long)Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE);
		resetUniversities();
		extendedSearchResults.setDepartments(defaultSelectItemList());
		extendedSearchResults.setInstitutes(defaultSelectItemList());
		extendedSearchResults.setCourseTypes(defaultSelectItemList());
		extendedSearchResults.setPeriods(defaultSelectItemList());
		return Constants.EXTENDED_SEARCH_VIEW;
	}
	
	/* "ON CHANGE" EVENT HANDLER */
	
	
	/**
	 * event handler which is called when the "only official" check box is changed
	 * @param vce
	 */
	public void onlyOfficialFlagChanged(ValueChangeEvent vce){
		logger.debug(">>> onlyOfficialFlagChanged");

		if(vce == null){
			return;
		}
		
		Boolean onlyOfficial = (Boolean) vce.getNewValue();
		extendedSearchResults.setOfficialOnly(onlyOfficial.booleanValue());
		
		// save old values
		Long oldUniversityId = extendedSearchResults.getUniversityId();
//		Long oldDepartmentId = extendedSearchResults.getDepartmentId();
		
		
		// set content of combo box "organisation" if this is necessary for the selected result type
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION){
			resetUniversities();
			extendedSearchResults.setUniversityId(oldUniversityId);
		} else {
			extendedSearchResults.setUniversities(defaultSelectItemList());
		}
		
		// set content of combo box "departments" if departments were shown before
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION
				&& extendedSearchResults.getDepartmentId().intValue() > 0){
			resetDepartments(extendedSearchResults.getUniversityId());
		} else {
			extendedSearchResults.setDepartments(defaultSelectItemList());
		}
		
		// reset all other combo boxes
		extendedSearchResults.setInstitutes(defaultSelectItemList());
		extendedSearchResults.setCourseTypes(defaultSelectItemList());
		extendedSearchResults.setPeriods(defaultSelectItemList());
		
		logger.debug("<<< onlyOfficialFlagChanged");
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
		Long resultTypeId = (Long) vce.getNewValue();
		extendedSearchResults.setResultTypeId(resultTypeId);
		
		
		// set content of combo box "organisation" if this is necessary for the selected result type
		if(resultTypeId > Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION){
			resetUniversities();
		} else {
			extendedSearchResults.setUniversities(defaultSelectItemList());
		}
		
		// reset all other combo boxes
		extendedSearchResults.setDepartments(defaultSelectItemList());
		extendedSearchResults.setInstitutes(defaultSelectItemList());
		extendedSearchResults.setCourseTypes(defaultSelectItemList());
		extendedSearchResults.setPeriods(defaultSelectItemList());
		
		logger.debug("<<< resultTypeChanged");
	}
	
	/**
	 * event handler which is called when the "university" combo box is changed
	 * @param vce
	 */
	public void universityChanged(ValueChangeEvent vce){
		logger.debug(">>> universityChanged");
		
		if(vce.getNewValue() == null){
			return;
		}
		Long selectedUniversity = (Long) vce.getNewValue();
		extendedSearchResults.setUniversityId(selectedUniversity);
		
		// set content of combo box "period" if this is necessary for the selected result type
		if(extendedSearchResults.getResultTypeId().intValue() == Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE){
			resetPeriods(selectedUniversity);
		} else {
			extendedSearchResults.setPeriods(defaultSelectItemList());
		}
		
		// set content of combo box "department" if this is necessary for the selected result type
		if(extendedSearchResults.getResultTypeId().intValue() > Constants.EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION){
			resetDepartments(selectedUniversity);
		} else {
			extendedSearchResults.setDepartments(defaultSelectItemList());
		}

		// reset the all other more detailed combo boxes
		extendedSearchResults.setInstitutes(defaultSelectItemList());
		extendedSearchResults.setCourseTypes(defaultSelectItemList());
		
		logger.debug("<<< universityChanged");
	}
	
	/**
	 * event handler which is called when the "department" combo box is changed
	 * @param vce
	 */
	public void departmentChanged(ValueChangeEvent vce){
		logger.debug(">>> departmentChanged");
		
		if(vce.getNewValue() == null){
			return;
		}
		Long selectedDepartment = (Long) vce.getNewValue();
		extendedSearchResults.setDepartmentId(selectedDepartment);
		
		// set content of combo box "institute" if this is necessary for the selected result type
		if(extendedSearchResults.getResultTypeId().intValue() > Constants.EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION){
			resetInstitutes(selectedDepartment);
		} else {
			extendedSearchResults.setInstitutes(defaultSelectItemList());
		}
		
		// reset the all other more detailed combo boxes
		extendedSearchResults.setCourseTypes(defaultSelectItemList());
		
		logger.debug("<<< departmentChanged");
	}
	
	
	
	/**
	 * event handler which is called when the "institute" combo box is changed
	 * @param vce
	 */
	public void instituteChanged(ValueChangeEvent vce){
		logger.debug(">>> instituteChanged");
		
		if(vce.getNewValue() == null){
			return;
		}
		Long selectedInstitute = (Long) vce.getNewValue();
		extendedSearchResults.setInstituteId(selectedInstitute);
		
		// determine the course types offered by the given instutute
		resetCourseTypes(selectedInstitute);		
		
		logger.debug("<<< instituteChanged");
	}
	
	/*** RESET METHODS FOR COMBO BOXES ***/
	
	private void resetUniversities(){
		// initialize
		List<SelectItem> universitiesToDisplay = new ArrayList<SelectItem>();
		UniversityInfo universityInfo;
		// set possible universities
		List<?> universities = universityService.findUniversitiesByEnabled(true);
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
		extendedSearchResults.setUniversities(universitiesToDisplay);
		extendedSearchResults.setUniversityId(null);
	}
	
	private void resetDepartments(Long organisationId){
		List<SelectItem> departmentsToDisplay = new ArrayList<SelectItem>();
		DepartmentInfo departmentInfo;
		
		// determine the departments which belong to the given university
		if(organisationId.intValue() > 0){
			List<?> departments = departmentService.findDepartmentsByUniversityAndEnabled(organisationId, true);
			if(departments.size() > 0){
				departmentsToDisplay.add(getAllSelectItem());
			} else {
				departmentsToDisplay.add(notFoundSelectItem());
			}
			for(Object departmentTemp : departments){
				departmentInfo = (DepartmentInfo) departmentTemp;
				// only add the department to the list if it is either official 
				// or non-official departments are allowed to be listed, too 
				if(!extendedSearchResults.isOfficialOnly() 
						|| departmentInfo.getDepartmentType().equals(DepartmentType.OFFICIAL)){
					departmentsToDisplay.add(
							new SelectItem(
									departmentInfo.getId(), 
									departmentInfo.getName()));
				}
			}
		} else if (organisationId.equals(Constants.EXTENDED_SEARCH_GET_ALL) ){
			departmentsToDisplay.add(getAllSelectItem());
		} else {
			departmentsToDisplay.add(notFoundSelectItem());
		}
		extendedSearchResults.setDepartments(departmentsToDisplay);
	}
	
	private void resetInstitutes(Long suborganisationId){
		List<SelectItem> institutesToDisplay = new ArrayList<SelectItem>();
		InstituteInfo instituteInfo;
		
		if(suborganisationId > Constants.EXTENDED_SEARCH_GET_ALL){
			List<?> institutes = instituteService.findInstitutesByDepartmentAndEnabled(suborganisationId, true);
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
		} else if (suborganisationId.equals(Constants.EXTENDED_SEARCH_GET_ALL)){
			institutesToDisplay.add(getAllSelectItem());
		} else {
			institutesToDisplay.add(notFoundSelectItem());
		}
		extendedSearchResults.setInstitutes(institutesToDisplay);
	}
	
	private void resetCourseTypes(Long instituteId){
		List<SelectItem> courseTypesToDisplay = new ArrayList<SelectItem>();
		CourseTypeInfo courseTypeInfo;

		if(instituteId.intValue() > 0){
			List<?> courseTypes = courseTypeService.findCourseTypesByInstitute(instituteId);
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
		} else if (instituteId.equals(Constants.EXTENDED_SEARCH_GET_ALL)){
			courseTypesToDisplay.add(getAllSelectItem());
		} else {
			courseTypesToDisplay.add(notFoundSelectItem());
		}
		extendedSearchResults.setCourseTypes(courseTypesToDisplay);
	}
	
	private void resetPeriods(Long universityId){
		List<SelectItem> periodToDisplay = new ArrayList<SelectItem>();
		PeriodInfo periodInfo;
		
		// determine periods in which the institute offers courses
		if(universityId.intValue() > 0){
			List<?> periods = universityService.findPeriodsByUniversity(universityId);
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
		} else if (universityId.equals(Constants.EXTENDED_SEARCH_GET_ALL)){
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
	
	private List<SelectItem> defaultSelectItemList(){
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(getAllSelectItem());
		return list;
	}
		
	/*** CHECK VISIBILITY METHODS ***/
	
	/**
	 * generates the CSS tag which determines whether the "university" combo box is displayed
	 * @return
	 */
	public String getVisibilityUniversity(){
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}
	
	/**
	 * generates the CSS tag which determines whether the "department" combo box is displayed
	 * @return
	 */
	public String getVisibilityDepartment(){
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}
	
	/**
	 * generates the CSS tag which determines whether the "institute" combo box is displayed
	 * @return
	 */
	public String getVisibilityInstitute(){
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION){
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
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE_TYPE){
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
		if(extendedSearchResults.getResultTypeId().intValue() > Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE_TYPE){
			return "display:inline;";
		} else {
			return "display:none;";
		}
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
