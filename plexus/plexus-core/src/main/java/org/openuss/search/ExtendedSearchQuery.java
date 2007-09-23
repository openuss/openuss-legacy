package org.openuss.search;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Query;
import org.openuss.lecture.DomainResultBean;
import org.openuss.lecture.LectureSearchQuery;
import org.springmodules.lucene.search.factory.SearcherFactory;
import org.springmodules.lucene.search.object.SimpleLuceneSearchQuery;

/**
 * Lecture Search
 * @author Ingo Dueppe
 * @author Malte Stockmann
 */
public class ExtendedSearchQuery extends SimpleLuceneSearchQuery implements ExtendedSearcher {

	private static final Logger logger = Logger.getLogger(LectureSearchQuery.class);

	public ExtendedSearchQuery(SearcherFactory searcherFactory, Analyzer analyzer) {
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
	 * performs an extended search
	 * @param textToSearch search term
	 * @param resultTypeId
	 * @param universityId
	 * @param departmentId
	 * @param instituteId
	 * @param courseTypeId
	 * @param onlyOfficial
	 * @param onlyInTitle
	 */
	public List<DomainResult> search(String textToSearch, String domainType, Long universityId,
			Long departmentId, Long instituteId, Long courseTypeId, Long periodId, 
			boolean onlyOfficial, boolean onlyInTitle) {
		
		StringBuilder queryString = new StringBuilder();
		
		if(!onlyInTitle){
			queryString.append(textToSearch);
		} else {
			queryString.append(DomainIndexer.NAME);
			queryString.append(":(");
			queryString.append(textToSearch);
			queryString.append(")");
		}
		
		if(domainType != null && !domainType.equals("")){
			queryString.append(" ");
			queryString.append(DomainIndexer.DOMAINTYPE);
			queryString.append(":");
			queryString.append(domainType);
		}
		
		if(universityId != null && universityId > 0){
			queryString.append(" ");
			queryString.append(DomainIndexer.UNIVERSITY_IDENTIFIER);
			queryString.append(":");
			queryString.append(universityId.toString());
		}
		
		if(departmentId != null && departmentId > 0){
			queryString.append(" ");
			queryString.append(DomainIndexer.DEPARTMENT_IDENTIFIER);
			queryString.append(":");
			queryString.append(departmentId.toString());
		}
		
		if(instituteId != null && instituteId > 0){
			queryString.append(" ");
			queryString.append(DomainIndexer.INSTITUTE_IDENTIFIER);
			queryString.append(":");
			queryString.append(instituteId.toString());
		}
		
		if(courseTypeId != null && courseTypeId > 0){
			queryString.append(" ");
			queryString.append(DomainIndexer.COURSE_TYPE_IDENTIFIER);
			queryString.append(":");
			queryString.append(courseTypeId.toString());
		}
		
		if(periodId != null && periodId > 0){
			queryString.append(" ");
			queryString.append(DomainIndexer.PERIOD_IDENTIFIER);
			queryString.append(":");
			queryString.append(periodId.toString());
		}
		
		if(onlyOfficial){
			queryString.append(" ");
			queryString.append(DomainIndexer.OFFICIAL_FLAG);
			queryString.append(":");
			queryString.append(String.valueOf(onlyOfficial));
		}
		
		String searchQuery = queryString.toString();
		logger.debug("Extended Search - search query: "+searchQuery);
		
		return this.search(searchQuery);
	}

}
