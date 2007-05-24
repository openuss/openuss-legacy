package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.openuss.search.DomainIndexer;
import org.springmodules.lucene.search.factory.SearcherFactory;
import org.springmodules.lucene.search.object.SimpleLuceneSearchQuery;

/**
 * Lecture Search
 * @author Ingo Dueppe
 */
public class LectureSearchQuery extends SimpleLuceneSearchQuery implements LectureSearcher {

	private static final Logger logger = Logger.getLogger(LectureSearchQuery.class);
	

	public LectureSearchQuery(SearcherFactory searcherFactory, Analyzer analyzer) {
		super(searcherFactory, analyzer);
	}

	@Override
	protected Query constructSearchQuery(String textToSearch) throws ParseException {
		QueryParser parser = new QueryParser("CONTENT", getTemplate().getAnalyzer());
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

}
