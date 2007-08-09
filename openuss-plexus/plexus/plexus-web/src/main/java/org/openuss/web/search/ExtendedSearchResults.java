package org.openuss.web.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
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
	private Long searchScopeId;
	private Long resultTypeId;
	private Long organisationId;
	private Long suborganisationId;
	private Long institutionId;
	private Long courseTypeId;
	private Long periodId;
	private List<SelectItem> organisations;
	private List<SelectItem> suborganisations;
	private List<SelectItem> institutions;
	private List<SelectItem> courseTypes;
	private List<SelectItem> periods;
	
	public ExtendedSearchResults(){
		titleOnly = false;
		officialOnly = false;
		searchScopeId = new Long(Constants.EXTENDED_SEARCH_SCOPE_UNIVERSITIES);
		resultTypeId = new Long(Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION);
		organisationId = new Long(0);
		suborganisationId = new Long(0);
		institutionId = new Long(0);
		courseTypeId = new Long(0);
		periodId = new Long(0);
		organisations = new ArrayList<SelectItem>();
		suborganisations = new ArrayList<SelectItem>();
		institutions = new ArrayList<SelectItem>();
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

	public Long getSearchScopeId() {
		return searchScopeId;
	}

	public void setSearchScopeId(Long searchScopeId) {
		this.searchScopeId = searchScopeId;
	}

	public Long getResultTypeId() {
		return resultTypeId;
	}

	public void setResultTypeId(Long resultTypeId) {
		this.resultTypeId = resultTypeId;
	}

	public Long getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(Long organisationId) {
		this.organisationId = organisationId;
	}
	
	public Long getSuborganisationId() {
		return suborganisationId;
	}

	public void setSuborganisationId(Long suborganisationId) {
		this.suborganisationId = suborganisationId;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
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

	public List<SelectItem> getSearchScopes() {
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		ArrayList<SelectItem> scopes = new ArrayList<SelectItem>();
		scopes.add(new SelectItem(
				new Long(Constants.EXTENDED_SEARCH_SCOPE_UNIVERSITIES), 
				rb.getString("extended_search_scope_universities")));
		scopes.add(new SelectItem(
				new Long(Constants.EXTENDED_SEARCH_SCOPE_COMPANIES), 
				rb.getString("extended_search_scope_companies")));
		scopes.add(new SelectItem(
				new Long(Constants.EXTENDED_SEARCH_SCOPE_OTHER), 
				rb.getString("extended_search_scope_other")));
		return scopes;
	}

	public List<SelectItem> getResultTypes() {
		ResourceBundle rb = ExtendedSearchUtil.getResourceBundle();
		String extension = ExtendedSearchUtil.getResourceExtensionString(getSearchScopeId());
		ArrayList<SelectItem> resultTypes = new ArrayList<SelectItem>();
		resultTypes.add(new SelectItem(
				new Long(Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION), 
				rb.getString("extended_search_organisation_"+extension)));
		resultTypes.add(new SelectItem(
				new Long(Constants.EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION), 
				rb.getString("extended_search_suborganisation_"+extension)));
		resultTypes.add(new SelectItem(
				new Long(Constants.EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION), 
				rb.getString("extended_search_institution_"+extension)));
		resultTypes.add(new SelectItem(
				new Long(Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE_TYPE), 
				rb.getString("extended_search_course_type_"+extension)));
		resultTypes.add(new SelectItem(
				new Long(Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE), 
				rb.getString("extended_search_course_"+extension)));
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

	public List<SelectItem> getOrganisations() {
		return organisations;
	}

	public void setOrganisations(List<SelectItem> organisations) {
		this.organisations = organisations;
	}

	public List<SelectItem> getSuborganisations() {
		return suborganisations;
	}

	public void setSuborganisations(List<SelectItem> suborganisations) {
		this.suborganisations = suborganisations;
	}

	public List<SelectItem> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<SelectItem> institutions) {
		this.institutions = institutions;
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
