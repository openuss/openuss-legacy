package org.openuss.search;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;

/**
 * <p>
 * @deprecated since OpenUss 3.0 RC1.
 * Not necessary for searching.
 * Just used for testing purposes.
 * </p>
 */
public class SearchEngine {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SearchEngine.class);
	
	private String path = System.getProperty("java.io.tmpdir");
	
	private Analyzer analyzer;
	
	public Hits search(String queryString) throws IOException {
		IndexReader reader = IndexReader.open(path);
		
		Searcher searcher = new IndexSearcher(reader);
		
		QueryParser parser = new QueryParser("CONTENT", analyzer);
		
		try {
			Query query = parser.parse(queryString);
			logger.debug("Searching for: "+query.toString("CONTENT"));
			long start = System.currentTimeMillis();
			Hits hits = searcher.search(query);
			long end = System.currentTimeMillis();
			
			logger.debug("searching in "+(end-start));

			for (int i = 0; i < hits.length(); i++ ) {
				logger.debug("doc="+hits.id(i)+" score="+hits.score(i));
				
				Document doc = hits.doc(i);
				logger.debug("Identifier = "+doc.get("IDENTIFIER"));
				logger.debug("Content    = "+doc.get("CONTENT"));
			}
			return hits;
			
		} catch (ParseException e) {
			logger.error(e);
		}
		
		return null;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}
}
