package org.openuss.search.lucene;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Searcher;
import org.springmodules.lucene.index.factory.SimpleLuceneIndexReader;
import org.springmodules.lucene.search.factory.LuceneSearcher;

/**
 * Workaround class until spring modules will implement the SimpleLuceneIndexReader. 
 * @author Ingo Dueppe
 *
 */
public class WorkAroundIndexReader extends SimpleLuceneIndexReader {
	
	private LuceneSearcher luceneSearcher;

	public WorkAroundIndexReader(IndexReader indexReader, LuceneSearcher luceneSearcher) {
		super(indexReader);
		this.luceneSearcher = luceneSearcher;
	}

	@Override
	public LuceneSearcher createSearcher() {
		return luceneSearcher;
	}

	@Override
	public Searcher createNativeSearcher() {
		return super.createNativeSearcher();
	}
}
