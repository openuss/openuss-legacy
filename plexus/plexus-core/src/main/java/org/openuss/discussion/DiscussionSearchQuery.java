package org.openuss.discussion;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Query;
import org.openuss.search.DomainIndexer;
import org.springmodules.lucene.search.factory.SearcherFactory;
import org.springmodules.lucene.search.object.SimpleLuceneSearchQuery;

/**
 * Discussion Search
 * Composes the search query string and calls Lucene search method.
 * 
 * @author Peter Schuh
 * @author Jürgen de Braaf
 * @author Thomas Jansing
 * @author Tobias Brockmann
 */
public class DiscussionSearchQuery extends SimpleLuceneSearchQuery implements DiscussionSearcher {

	private static final Logger logger = Logger.getLogger(DiscussionSearchQuery.class);

	public DiscussionSearchQuery(SearcherFactory searcherFactory, Analyzer analyzer) {
		super(searcherFactory, analyzer);
	}

	@Override
	protected Query constructSearchQuery(String textToSearch) throws ParseException {
		
		QueryParser parser = new QueryParser(DomainIndexer.CONTENT, getTemplate().getAnalyzer());
		// allows wildcards at the beginning of a search phrase
		parser.setAllowLeadingWildcard(true);
		
		parser.setDefaultOperator(Operator.AND);
		
		return parser.parse(textToSearch);
	}
	
	@Override
	protected Object extractResultHit(int id, Document document, float score) {
		DiscussionSearchDomainResultBean domainResult = new DiscussionSearchDomainResultBean();		
		try {
			
			domainResult.setScore(score);			
			domainResult.setTitle(document.get(DomainIndexer.POST_TITLE));			
			domainResult.setId(Long.parseLong(document.get(DomainIndexer.IDENTIFIER)));
			domainResult.setSubmitter(document.get(DomainIndexer.POST_SUBMITTER_NAME));
			domainResult.setModified(DateTools.stringToDate(document.get(DomainIndexer.MODIFIED)));
			domainResult.setPostId(document.get(DomainIndexer.POST_IDENTIFIER));			
			domainResult.setCourseId(document.get(DomainIndexer.COURSE_IDENTIFIER));
			domainResult.setTopicId(document.get(DomainIndexer.TOPIC_IDENTIFIER));		
			
		} catch (java.text.ParseException e) {
			logger.error(e);
		}
		return domainResult;
	}
	
	/**
	 * Performs a discussion search.
	 * 
	 * @param textToSearch search term 
	 * @param courseId
	 * @param onlyInTitle search only in title?
	 * @param isFuzzy use fuzzy search?
	 * @param submitter search for author/submitter
	 */
	public List<DiscussionSearchDomainResult> search(String textToSearch, Long courseId, boolean onlyInTitle, boolean isFuzzy, String submitter) {
		
		StringBuilder queryString = new StringBuilder();
				
		if(!textToSearch.isEmpty()) {
			if(onlyInTitle){
				queryString.append(DomainIndexer.POST_TITLE);
				queryString.append(":(");
				queryString.append(textToSearch);
				queryString = addFuzzySuffixIfFuzzy(queryString, isFuzzy);
				queryString.append(")");			
			} else {
				queryString.append("(");				
				queryString.append(DomainIndexer.POST_TITLE);
				queryString.append(":(");
				queryString.append(textToSearch);
				queryString = addFuzzySuffixIfFuzzy(queryString, isFuzzy);
				queryString.append(")");
							
				queryString.append(" OR ");			
				queryString.append(DomainIndexer.CONTENT);
				queryString.append(":(");
				queryString.append(textToSearch);
				queryString = addFuzzySuffixIfFuzzy(queryString, isFuzzy);
				queryString.append(")");				
				queryString.append(")");
			}			
		}
		
		if(courseId != null && courseId > 0){
			queryString.append(" ");
			queryString.append(DomainIndexer.COURSE_IDENTIFIER);
			queryString.append(":");
			queryString.append(courseId.toString());
		}
				
		if(submitter != null && !submitter.equals("")){
			queryString.append(" ");
			queryString.append(DomainIndexer.POST_SUBMITTER_NAME);
			queryString.append(":(");
			queryString.append(submitter);
			queryString = addFuzzySuffixIfFuzzy(queryString, isFuzzy);
			queryString.append(")");
		}
		
		String searchQuery = queryString.toString();		
		logger.debug("DiscussionSearchQuery.class - search query string: "+searchQuery);
					
		return this.search(searchQuery);
		
	}
	
	/**
	 * This method adds the fuzzy-suffix ~ if the user wants to perform fuzzy search
	 * @return suffix if wanted, no suffix if not wanted
	 */
	private StringBuilder addFuzzySuffixIfFuzzy(StringBuilder queryString, boolean isFuzzy){
		if(isFuzzy){
			queryString.append("~");
		}
		return queryString;
	}

	public List<DiscussionSearchDomainResult> groupSearch(String textToSearch,
			Long groupId, boolean onlyInTitle, boolean isFuzzy, String submitter) {
		StringBuilder queryString = new StringBuilder();
		
		if(!textToSearch.isEmpty()) {
			if(onlyInTitle){
				queryString.append(DomainIndexer.POST_TITLE);
				queryString.append(":(");
				queryString.append(textToSearch);
				queryString = addFuzzySuffixIfFuzzy(queryString, isFuzzy);
				queryString.append(")");			
			} else {
				queryString.append("(");				
				queryString.append(DomainIndexer.POST_TITLE);
				queryString.append(":(");
				queryString.append(textToSearch);
				queryString = addFuzzySuffixIfFuzzy(queryString, isFuzzy);
				queryString.append(")");
							
				queryString.append(" OR ");			
				queryString.append(DomainIndexer.CONTENT);
				queryString.append(":(");
				queryString.append(textToSearch);
				queryString = addFuzzySuffixIfFuzzy(queryString, isFuzzy);
				queryString.append(")");				
				queryString.append(")");
			}			
		}
		
		if(groupId != null && groupId > 0){
			queryString.append(" ");
			queryString.append(DomainIndexer.COURSE_IDENTIFIER);
			queryString.append(":");
			queryString.append(groupId.toString());
		}
				
		if(submitter != null && !submitter.equals("")){
			queryString.append(" ");
			queryString.append(DomainIndexer.POST_SUBMITTER_NAME);
			queryString.append(":(");
			queryString.append(submitter);
			queryString = addFuzzySuffixIfFuzzy(queryString, isFuzzy);
			queryString.append(")");
		}
		
		String searchQuery = queryString.toString();		
		logger.debug("DiscussionSearchQuery.class - search query string: "+searchQuery);
					
		return this.search(searchQuery);
	}

}
