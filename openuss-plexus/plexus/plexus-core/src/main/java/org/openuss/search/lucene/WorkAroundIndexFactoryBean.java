package org.openuss.search.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springmodules.lucene.index.factory.IndexFactory;
import org.springmodules.lucene.index.factory.SimpleIndexFactory;
import org.springmodules.lucene.search.factory.SimpleSearcherFactory;

/**
 * Simple factory bean to configure a simple factory to manipulate an index.
 * 
 * @author Ingo Dueppe
 * @see org.springmodules.lucene.index.factory.SimpleIndexFactory
 */
public class WorkAroundIndexFactoryBean implements FactoryBean, InitializingBean {
 
	private WorkAroundSimpleIndexFactory indexFactory;
	private SimpleSearcherFactory searcherFactory;
	private boolean resolveLock = false;
	private boolean create = false;
	private Directory directory;
	private Analyzer analyzer;

	/**
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public Object getObject() throws Exception {
		return indexFactory;
	}

	/**
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	public Class getObjectType() {
		return IndexFactory.class;
	}

	/**
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		return true;
	}

	/**
	 * Set the Lucene Directory used by the IndexFactory.
	 */
	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	/**
	 * Return the Lucene Directory used by the IndexFactory.
	 */
	public Directory getDirectory() {
		return directory;
	}

	/**
	 * Set if the index locking must be resolved
	 */
	public void setResolveLock(boolean b) {
		resolveLock = b;
	}

	/**
	 * Return if the index locking must be resolved
	 */
	public boolean isResolveLock() {
		return resolveLock;
	}

	/**
	 * Set if the index must be created if it don't exist
	 */
	public void setCreate(boolean create) {
		this.create = create;
	}

	/**
	 * Return if the index must be created if it don't exist
	 */
	public boolean isCreate() {
		return create;
	}

	/**
	 * Set the Lucene Analyzer used by the IndexFactory.
	 */
	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	/**
	 * Return the Lucene Analyzer used by the IndexFactory.
	 */
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	/**
	 * This method constructs a SimpleIndexFactory instance based on the
	 * configured directory and analyzer.
	 */
	public void afterPropertiesSet() throws Exception {
		if (getDirectory() == null) {
			throw new IllegalArgumentException("directory is required");
		}
		this.indexFactory = new WorkAroundSimpleIndexFactory(getDirectory(), getAnalyzer());
		this.indexFactory.setSearcher(searcherFactory);
		this.indexFactory.setResolveLock(resolveLock);
		this.indexFactory.setCreate(create);
	}

	public SimpleIndexFactory getIndexFactory() {
		return indexFactory;
	}

	public void setIndexFactory(WorkAroundSimpleIndexFactory indexFactory) {
		this.indexFactory = indexFactory;
	}

	public SimpleSearcherFactory getSearcherFactory() {
		return searcherFactory;
	}

	public void setSearcherFactory(SimpleSearcherFactory searcherFactory) {
		this.searcherFactory = searcherFactory;
	}
}
