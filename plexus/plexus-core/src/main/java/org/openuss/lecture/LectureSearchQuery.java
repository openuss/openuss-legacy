package org.openuss.lecture;

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
import org.openuss.search.DomainResult;
import org.springmodules.lucene.search.factory.SearcherFactory;
import org.springmodules.lucene.search.object.SimpleLuceneSearchQuery;

/**
 * Gobal Search
 * Composes the search query string and calls Lucene search method.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class LectureSearchQuery extends SimpleLuceneSearchQuery implements LectureSearcher {

	private static final Logger logger = Logger.getLogger(LectureSearchQuery.class);
	

	public LectureSearchQuery(SearcherFactory searcherFactory, Analyzer analyzer) {
		super(searcherFactory, analyzer);
	}

	@Override
	protected Query constructSearchQuery(String textToSearch) throws ParseException {
		QueryParser parser = new QueryParser("CONTENT", getTemplate().getAnalyzer());
		parser.setAllowLeadingWildcard(true);
		parser.setDefaultOperator(Operator.AND);
		return parser.parse(textToSearch);
	}
	
	@Override
	protected Object extractResultHit(int id, Document document, float score) {
		DomainResultBean domainResult = new DomainResultBean();
		String domainType = document.get(DomainIndexer.DOMAINTYPE);
		try {
			domainResult.setScore(score);
			domainResult.setName(document.get(DomainIndexer.NAME));
			domainResult.setDetails(document.get(DomainIndexer.DETAILS));
			domainResult.setId(Long.parseLong(document.get(DomainIndexer.IDENTIFIER)));
			domainResult.setModified(DateTools.stringToDate(document.get(DomainIndexer.MODIFIED)));
			domainResult.setDomainType(domainType);
			domainResult.setUniversityId(document.get(DomainIndexer.UNIVERSITY_IDENTIFIER));
			domainResult.setDepartmentId(document.get(DomainIndexer.DEPARTMENT_IDENTIFIER));
			domainResult.setInstituteId(document.get(DomainIndexer.INSTITUTE_IDENTIFIER));
			domainResult.setCourseTypeId(document.get(DomainIndexer.COURSE_TYPE_IDENTIFIER));
		} catch (java.text.ParseException e) {
			logger.error(e);
		}
		return domainResult;
	}
	
	/**
	 * Performs a discussion search.
	 * 
	 * @param textToSearch search term 
	 * @param fuzzy use fuzzy search
	 * @return List<DomainResult>
	 */
	@SuppressWarnings("unchecked")
	public List<DomainResult> search(String textToSearch, boolean fuzzy) {
		StringBuilder queryString = new StringBuilder();
				
		if(!textToSearch.isEmpty()) {
			queryString.append(DomainIndexer.CONTENT);
			queryString.append(":(");
			queryString.append(textToSearch);
			if(fuzzy){
				queryString.append("~");
			}
			queryString.append(")");				
		}
		
		String searchQuery = queryString.toString();		
		logger.debug("DiscussionSearchQuery.class - search query string: "+searchQuery);
					
		return this.search(searchQuery);
	}
}
