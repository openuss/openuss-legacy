package org.openuss.docmanagement;

import javax.jcr.Repository;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class ExamAreaDao extends FolderDao {
	
	private Repository repository;

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	
}
