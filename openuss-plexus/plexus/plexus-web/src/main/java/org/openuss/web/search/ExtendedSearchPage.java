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
 * Lecture Extended Search page 
 * @author Kai Stettner
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
