package org.openuss.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Query;
import org.openuss.discussion.DiscussionSearchDomainResultBean;
import org.openuss.discussion.DiscussionSearcher;
import org.openuss.lecture.LectureSearchQuery;
import org.springmodules.lucene.search.factory.SearcherFactory;
import org.springmodules.lucene.search.object.SimpleLuceneSearchQuery;

/**
 * Discussion Search
 * 
 * @author Peter Schuh
 * @author Jürgen de Braaf
 * @author Thomas Jansing
 * @author Tobias Brockmann
 */
public class DiscussionSearchQuery extends SimpleLuceneSearchQuery implements DiscussionSearcher {

	private static final Logger logger = Logger.getLogger(LectureSearchQuery.class);

	public DiscussionSearchQuery(SearcherFactory searcherFactory, Analyzer analyzer) {
		super(searcherFactory, analyzer);
	}

	@Override
	protected Query constructSearchQuery(String textToSearch) throws ParseException {
		
		QueryParser parser = new QueryParser("CONTENT", getTemplate().getAnalyzer());
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
			domainResult.setModified(DateTools.stringToDate(document.get(DomainIndexer.MODIFIED)));
			domainResult.setPostId(document.get(DomainIndexer.POST_IDENTIFIER));			
			domainResult.setCourseId(document.get(DomainIndexer.COURSE_IDENTIFIER));
		} catch (java.text.ParseException e) {
			logger.error(e);
		}
		return domainResult;
	}
	
	/**
	 * performs a discussion search
	 * @param textToSearch search term 
	 * @param postId 
	 * @param onlyInTitle
	 * @param submitter
	 */
	public List<DiscussionSearchDomainResult> search(String textToSearch, Long courseId, boolean onlyInTitle, String submitter) {
		
		StringBuilder queryString = new StringBuilder();
		
		if(!onlyInTitle){
			queryString.append(textToSearch);
		} else {
			queryString.append(DomainIndexer.POST_TITLE);
			queryString.append(":(");
			queryString.append(textToSearch);
			queryString.append(")");
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
			queryString.append(":");
			queryString.append(submitter);
		}
		
		String searchQuery = queryString.toString();
		logger.debug("Discussion Search - search query: "+searchQuery);
		
		// TODO Dummy überarbeiten
		
		List<DiscussionSearchDomainResult> testHitsList = new ArrayList<DiscussionSearchDomainResult>(null);
		
		return testHitsList;
		
		//return this.search(searchQuery);
		
		
		
	}

}
