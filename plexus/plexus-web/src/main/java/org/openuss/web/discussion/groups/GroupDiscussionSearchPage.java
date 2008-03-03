package org.openuss.web.discussion.groups;

import java.io.FileNotFoundException;
import java.util.List;
import javax.faces.application.FacesMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.discussion.DiscussionSearchDomainResult;
import org.openuss.discussion.DiscussionSearcher;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.springmodules.lucene.search.LuceneSearchException;

/**
 * Discussion Search page 
 * 
 * @author Thomas Jansing
 * @author Juergen de Braaf
 * @author Peter Schuh
 * @author Tobias Brockmann
 * @author Lutz D. Kramer
 */
@Bean(name = "views$secured$discussion$groups$discussionsearch", scope = Scope.REQUEST)
@View
public class GroupDiscussionSearchPage extends AbstractGroupDiscussionPage{
	
private static final Logger logger = Logger.getLogger(GroupDiscussionSearchPage.class);
	

	@Property(value="#{discussionSearcher}")
	private DiscussionSearcher discussionSearcher;
	
	@Property(value="#{discussion_search_results}")
	private GroupDiscussionSearchResults discussionSearchResults;
		
	private DiscussionSearchResultDataProvider resultProvider = new DiscussionSearchResultDataProvider();	 
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("discussion_search"));
		crumb.setHint(i18n("discussion_search"));
		breadcrumbs.addCrumb(crumb);
	}
	
	/**
	 * performs a discussion search
	 * @return outcome of action (used for navigation)
	 */
	public String search() {
		
		logger.debug("Starting method search");
		
		List<DiscussionSearchDomainResult> searchResult = null;
		
		if (StringUtils.isNotBlank(discussionSearchResults.getTextToSearch()) || StringUtils.isNotBlank(discussionSearchResults.getSubmitter())) {
						
			try {
				searchResult = 	discussionSearcher.search(
								discussionSearchResults.getTextToSearch(),
								discussionSearchResults.getGroupInfo().getId(),
								discussionSearchResults.isTitleOnly(),
								discussionSearchResults.getIsFuzzy(),
								discussionSearchResults.getSubmitter()
							);
							discussionSearchResults.setHits(searchResult);
				if(searchResult == null || searchResult.size() == 0){
					logger.debug("search_no_matches_found");
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
		return Constants.DISCUSSION_SEARCH_RESULT;
		
	}		
	
	public String cancel() {
		return Constants.DISCUSSION_MAIN;
	}
	
	public String clear() {
		discussionSearchResults.setHits(null);
		return Constants.DISCUSSION_SEARCH_RESULT;
	}
	
	public DiscussionSearchResultDataProvider getResultProvider() {
		return resultProvider;
	}

	public void setResultProvider(DiscussionSearchResultDataProvider results) {
		this.resultProvider = results;
	}

	public GroupDiscussionSearchResults getdiscussionSearchResults() {
		return discussionSearchResults;
	}

	public void setDiscussionSearchResults(GroupDiscussionSearchResults discussionSearchResults) {
		this.discussionSearchResults = discussionSearchResults;
	}
			
	private class DiscussionSearchResultDataProvider extends AbstractPagedTable<DiscussionSearchDomainResult> {

		private static final long serialVersionUID = -2279124332432432432L;
		
		private DataPage<DiscussionSearchDomainResult> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<DiscussionSearchDomainResult> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				if (discussionSearchResults.getHits() == null) {
					page = new DataPage<DiscussionSearchDomainResult>(0,0,null);
				} else {
					page = new DataPage<DiscussionSearchDomainResult>(discussionSearchResults.getHitCounts(),0,discussionSearchResults.getHits());
				}
			}
			return page;
		}
	}

	public DiscussionSearcher getDiscussionSearcher() {
		return discussionSearcher;
	}

	public void setDiscussionSearcher(DiscussionSearcher discussionSearcher) {
		this.discussionSearcher = discussionSearcher;
	}

}
