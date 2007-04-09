package org.openuss.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.springmodules.lucene.search.factory.SearcherFactory;
import org.springmodules.lucene.search.object.SimpleLuceneSearchQuery;

public class LectureSearchQuery extends SimpleLuceneSearchQuery {
	

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
		return document.get("IDENTIFIER") + " " + document.get("TYPE") + " " + document.get("NAME");
	}

}
