package org.openuss.search;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
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
	public List<DomainResult> search(String textToSearch, Long resultTypeId, Long universityId,
			Long departmentId, Long instituteId, Long courseTypeId,
			boolean onlyOfficial, boolean onlyInTitle) {
		// TODO Auto-generated method stub
		return null;
	}

}
