package org.openuss.docmanagement;

import javax.jcr.Node;
import javax.jcr.Repository;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class WorkingPlaceDao extends FolderDao {
	public WorkingPlaceDao(Node representedNode) {
		super(representedNode);
	}

	private Repository repository;

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
