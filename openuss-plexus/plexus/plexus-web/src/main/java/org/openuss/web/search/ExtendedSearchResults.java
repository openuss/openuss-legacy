package org.openuss.web.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.search.DomainResult;
import org.openuss.web.Constants;


/**
 * Extended Search Result Container 
 * @author Kai Stettner
 * @author Malte Stockmann
 */

@Bean(name="extended_search_results", scope=Scope.SESSION)
public class ExtendedSearchResults implements Serializable {
	
	private static final long serialVersionUID = 2703663293293922929L;
	
	private static final Logger logger = Logger.getLogger(ExtendedSearchResults.class);

	private List<DomainResult> hits;
	
	private String textToSearch;
	private boolean titleOnly;
	private boolean officialOnly;
	private Long resultTypeId;
	private Long universityId;
	private Long departmentId;
	private Long instituteId;
	private Long courseTypeId;
	private Long periodId;
	private List<SelectItem> universities;
	private List<SelectItem> departments;
	private List<SelectItem> institutes;
	private List<SelectItem> courseTypes;
	private List<SelectItem> periods;
	
	public ExtendedSearchResults(){
		titleOnly = false;
		officialOnly = false;
		resultTypeId = (long) Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION;
		universityId = 0L;
		departmentId = 0L;
		instituteId = 0L;
		courseTypeId = 0L;
		periodId = 0L;
		universities = new ArrayList<SelectItem>();
		departments = new ArrayList<SelectItem>();
		institutes = new ArrayList<SelectItem>();
		courseTypes = new ArrayList<SelectItem>();
		periods = new ArrayList<SelectItem>();
	}
	

	public List<DomainResult> getHits() {
		return hits;
	}

	public void setHits(List<DomainResult> hits) {
		this.hits = hits;
	}

	public String getTextToSearch() {
		return textToSearch;
	}

	public void setTextToSearch(String textToSearch) {
		this.textToSearch = textToSearch;
	}
	
	public boolean isTitleOnly() {
		return titleOnly;
	}

	public void setTitleOnly(boolean titleOnly) {
		this.titleOnly = titleOnly;
	}

	public boolean isOfficialOnly() {
		return officialOnly;
	}

	public void setOfficialOnly(boolean officialOnly) {
		this.officialOnly = officialOnly;
	}

	public Long getResultTypeId() {
		return resultTypeId;
	}

	public void setResultTypeId(Long resultTypeId) {
		this.resultTypeId = resultTypeId;
	}

	public Long getUniversityId() {
		return universityId;
	}


	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}


	public Long getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}


	public Long getInstituteId() {
		return instituteId;
	}


	public void setInstituteId(Long instituteId) {
		this.instituteId = instituteId;
	}

	public Long getCourseTypeId() {
		return courseTypeId;
	}

	public void setCourseTypeId(Long courseTypeId) {
		this.courseTypeId = courseTypeId;
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public List<SelectItem> getResultTypes() {
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		ArrayList<SelectItem> resultTypes = new ArrayList<SelectItem>();
		resultTypes.add(new SelectItem(Long.valueOf(Constants.EXTENDED_SEARCH_RESULT_TYPE_ALL), rb.getString("extended_search_get_all")));
		resultTypes.add(new SelectItem(Long.valueOf(Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION), rb.getString("extended_search_university")));
		resultTypes.add(new SelectItem(Long.valueOf(Constants.EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION),	rb.getString("extended_search_department")));
		resultTypes.add(new SelectItem(Long.valueOf(Constants.EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION),	rb.getString("extended_search_institute")));
		resultTypes.add(new SelectItem(Long.valueOf(Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE),	rb.getString("extended_search_course")));
		return resultTypes;
	}
	
	/**
	 * generates the CSS tag which determines whether the result data table is displayed
	 * @return
	 */
	public String getVisibilityResultTable(){
		logger.debug("test"+this.getHitCounts());
		if(this.getHitCounts() > 0){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}
	
	/**
	 * generates the CSS tag which determines whether the result data table is displayed
	 * @return
	 */
	public String getVisibilityNotificationZero(){
		logger.debug("test"+this.getHitCounts());
		if(this.getHitCounts() == 0){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}

	public List<SelectItem> getUniversities() {
		return universities;
	}


	public void setUniversities(List<SelectItem> universities) {
		this.universities = universities;
	}


	public List<SelectItem> getDepartments() {
		return departments;
	}


	public void setDepartments(List<SelectItem> departments) {
		this.departments = departments;
	}


	public List<SelectItem> getInstitutes() {
		return institutes;
	}


	public void setInstitutes(List<SelectItem> institutes) {
		this.institutes = institutes;
	}

	public List<SelectItem> getCourseTypes() {
		return courseTypes;
	}

	public void setCourseTypes(List<SelectItem> courseTypes) {
		this.courseTypes = courseTypes;
	}

	public List<SelectItem> getPeriods() {
		return periods;
	}

	public void setPeriods(List<SelectItem> periods) {
		this.periods = periods;
	}

	public int getHitCounts() {
		return hits != null ? hits.size() : 0;
	}


	
	
}
