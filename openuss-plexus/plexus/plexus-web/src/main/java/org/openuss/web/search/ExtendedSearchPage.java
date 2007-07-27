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
import org.openuss.lecture.LectureSearcher;
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
		// set possible organisations
		ArrayList<SelectItem> dummy = new ArrayList<SelectItem>();
		dummy.add(new SelectItem(
				new Integer(1), 
				"Uni Münster"));
		dummy.add(new SelectItem(
				new Integer(2), 
				"Uni Paderborn"));
		dummy.add(new SelectItem(
				new Integer(3), 
				"Uni Duisburg-Essen"));
		extendedSearchResults.setOrganisations(dummy);
		// reset all other combo boxes
		extendedSearchResults.setSuborganisations(new ArrayList<SelectItem>());
		extendedSearchResults.setInstitutions(new ArrayList<SelectItem>());
		extendedSearchResults.setCourseTypes(new ArrayList<SelectItem>());
		extendedSearchResults.setPeriods(new ArrayList<SelectItem>());
	}
	
	/**
	 * event handler which is called when the "organisation" combo box is changed
	 * @param vce
	 */
	public void organisationChanged(ValueChangeEvent vce){
		// determine the departments which belong to the given university
		ArrayList<SelectItem> dummy = new ArrayList<SelectItem>();
		dummy.add(new SelectItem(
				new Integer(4), 
				"BWL"));
		dummy.add(new SelectItem(
				new Integer(5), 
				"Jura"));
		dummy.add(new SelectItem(
				new Integer(6), 
				"Theologie"));
		extendedSearchResults.setSuborganisations(dummy);
		// reset the all other more detailed combo boxes
		extendedSearchResults.setInstitutions(new ArrayList<SelectItem>());
		extendedSearchResults.setCourseTypes(new ArrayList<SelectItem>());
		extendedSearchResults.setPeriods(new ArrayList<SelectItem>());
	}
	
	/**
	 * event handler which is called when the "suborganisation" combo box is changed
	 * @param vce
	 */
	public void suborganisationChanged(ValueChangeEvent vce){
		// determine the institutions which belong to the given department
		ArrayList<SelectItem> dummy = new ArrayList<SelectItem>();
		dummy.add(new SelectItem(
				new Integer(7), 
				"LS 1"));
		dummy.add(new SelectItem(
				new Integer(8), 
				"LS 2"));
		dummy.add(new SelectItem(
				new Integer(9), 
				"LS 3"));
		extendedSearchResults.setInstitutions(dummy);
		// reset the all other more detailed combo boxes
		extendedSearchResults.setCourseTypes(new ArrayList<SelectItem>());
		extendedSearchResults.setPeriods(new ArrayList<SelectItem>());
	}
	
	/**
	 * event handler which is called when the "institution" combo box is changed
	 * @param vce
	 */
	public void institutionChanged(ValueChangeEvent vce){
		// determine the course types offered by the given institution
		ArrayList<SelectItem> dummy = new ArrayList<SelectItem>();
		dummy.add(new SelectItem(
				new Integer(10), 
				"VL 1"));
		dummy.add(new SelectItem(
				new Integer(11), 
				"VL 2"));
		dummy.add(new SelectItem(
				new Integer(12), 
				"VL 3"));
		extendedSearchResults.setCourseTypes(dummy);
		ArrayList<SelectItem> dummy2 = new ArrayList<SelectItem>();
		dummy2.add(new SelectItem(
				new Integer(13), 
				"SS 2007"));
		dummy2.add(new SelectItem(
				new Integer(14), 
				"WS 2007/2008"));
		dummy2.add(new SelectItem(
				new Integer(15), 
				"SS 2008"));
		extendedSearchResults.setPeriods(dummy2);
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
		if( extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION
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
		if(extendedSearchResults.getResultTypeId() > Constants.EXTENDED_SEARCH_RESULT_TYPE_COURSE_TYPE
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
		int scope = extendedSearchResults.getSearchScopeId();
		switch(scope){
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
		int scope = extendedSearchResults.getSearchScopeId();
		switch(scope){
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
		int scope = extendedSearchResults.getSearchScopeId();
		switch(scope){
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
		int scope = extendedSearchResults.getSearchScopeId();
		switch(scope){
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
		int scope = extendedSearchResults.getSearchScopeId();
		switch(scope){
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

}
