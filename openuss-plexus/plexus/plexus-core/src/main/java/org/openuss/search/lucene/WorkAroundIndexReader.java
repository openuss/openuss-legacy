package org.openuss.search.lucene;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Searcher;
import org.openuss.system.SystemServiceException;
import org.springmodules.lucene.index.factory.SimpleLuceneIndexReader;
import org.springmodules.lucene.search.factory.LuceneSearcher;
import org.springmodules.lucene.search.factory.SimpleSearcherFactory;

/**
 * Workaround class until spring modules will implement the SimpleLuceneIndexReader. 
 * @author Ingo Dueppe
 *
 */
public class WorkAroundIndexReader extends SimpleLuceneIndexReader {
	 
	private SimpleSearcherFactory searcherFactory;

	public WorkAroundIndexReader(IndexReader indexReader, SimpleSearcherFactory searcherFactory) {
		super(indexReader);
		this.searcherFactory = searcherFactory;
	}

	@Override
	public LuceneSearcher createSearcher() {
		try {
			return searcherFactory.getSearcher();
		} catch (IOException e) {
			throw new SystemServiceException("Couldn't create searcher instance",e); 
		}
	}

	@Override
	public Searcher createNativeSearcher() {
		return super.createNativeSearcher();
	}
}
