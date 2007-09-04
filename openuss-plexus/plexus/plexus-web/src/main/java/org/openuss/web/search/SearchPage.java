package org.openuss.web.search;

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
 * Lecture search page 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 */
@Bean(name = "views$public$search$search", scope = Scope.REQUEST)
@View
public class SearchPage extends BasePage{
	
	private static final Logger logger = Logger.getLogger(SearchPage.class);
	
	@Property(value="#{lectureSearcher}")
	private LectureSearcher lectureSearcher;
	
	
	@Property(value="#{search_results}")
	private SearchResults searchResults;
	
	private SearchResultDataProvider resultProvider = new SearchResultDataProvider();
	
	@Prerender
	public void prerender(){
		breadcrumbs.loadSearchCrumbs();
	}
	
	public String search() {
		if (StringUtils.isNotBlank(searchResults.getTextToSearch())) {
			logger.debug("searching for "+searchResults.getTextToSearch());
			try {
				searchResults.setHits(lectureSearcher.search(searchResults.getTextToSearch()));
			} catch (LuceneSearchException ex) {
				logger.error(ex);
				addError(i18n("search_text_error"));
			}
		}
		return Constants.SEARCH_RESULT;
	}
	
	/**
	 * generates the CSS tag which determines whether the result data table is displayed
	 * @return
	 */
	public String getVisibilityResultTable(){
		logger.debug("test"+searchResults.getHitCounts());
		if(searchResults.getHitCounts() > 0){
			return "display:inline;";
		} else {
			return "display:none;";
		}
	}
	
	
	private class SearchResultDataProvider extends AbstractPagedTable<DomainResult> {

		private static final long serialVersionUID = -2279124328223684525L;
		
		private DataPage<DomainResult> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<DomainResult> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				if (searchResults.getHits() == null) {
					page = new DataPage<DomainResult>(0,0,null);
				} else {
					page = new DataPage<DomainResult>(searchResults.getHitCounts(),0,searchResults.getHits());
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
	
	public SearchResultDataProvider getResultProvider() {
		return resultProvider;
	}

	public void setResultProvider(SearchResultDataProvider results) {
		this.resultProvider = results;
	}

	public SearchResults getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(SearchResults searchResults) {
		this.searchResults = searchResults;
	}

}