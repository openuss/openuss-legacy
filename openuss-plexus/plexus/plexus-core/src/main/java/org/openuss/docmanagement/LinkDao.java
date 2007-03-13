package org.openuss.docmanagement;

import javax.jcr.Node;
import javax.jcr.Repository;

import org.apache.jackrabbit.webdav.io.OutputContext;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class LinkDao extends ResourceDao {
	
	private Repository repository;


	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
