package org.openuss.search.lucene;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Searcher;
import org.springmodules.lucene.index.factory.SimpleLuceneIndexReader;
import org.springmodules.lucene.search.factory.LuceneSearcher;

public class LuceneIndexReader extends SimpleLuceneIndexReader {

	public LuceneIndexReader(IndexReader indexReader) {
		super(indexReader);
	}

	@Override
	public LuceneSearcher createSearcher() {
		return super.createSearcher();
	}

	@Override
	public Searcher createNativeSearcher() {
		return super.createNativeSearcher();
	}
	
	
	

}
