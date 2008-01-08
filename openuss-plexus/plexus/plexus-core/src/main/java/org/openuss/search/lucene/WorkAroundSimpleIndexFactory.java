package org.openuss.search.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.springmodules.lucene.index.LuceneIndexAccessException;
import org.springmodules.lucene.index.factory.LuceneIndexReader;
import org.springmodules.lucene.index.factory.SimpleIndexFactory;
import org.springmodules.lucene.search.factory.SimpleSearcherFactory;

public class WorkAroundSimpleIndexFactory extends SimpleIndexFactory {

	private SimpleSearcherFactory searcherFactory;
	
	public WorkAroundSimpleIndexFactory() {
		super();
	}

	public WorkAroundSimpleIndexFactory(Directory directory, Analyzer analyzer) {
		super(directory, analyzer);
	}

	public WorkAroundSimpleIndexFactory(Directory directory, Analyzer analyzer, SimpleSearcherFactory searcherFactory) {
		super(directory, analyzer);
		this.searcherFactory = searcherFactory;
	}
	
	/**
	 * Contruct a new IndexReader instance based on the directory property. This
	 * instance will be used by the IndexTemplate to get informations about the
	 * index and make delete operations on the index. 
	 * @return a new reader instance on the index
	 * @see org.springmodules.lucene.index.factory.IndexFactory#getIndexReader()
	 */
	public LuceneIndexReader getIndexReader() {
		LuceneIndexReader reader = super.getIndexReader();
		if (reader != null) {
			try {
				reader =  new WorkAroundIndexReader(IndexReader.open(getDirectory()), searcherFactory);
			} catch (IOException ex) {
				throw new LuceneIndexAccessException("Error during opening the reader",ex);			
			}
		}
		return reader;
	}

	public SimpleSearcherFactory getSearcherFactory() {
		return searcherFactory;
	}

	public void setSearcher(SimpleSearcherFactory searcherFactory) {
		this.searcherFactory = searcherFactory;
	}


}
