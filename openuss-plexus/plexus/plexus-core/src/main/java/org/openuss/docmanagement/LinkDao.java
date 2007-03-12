package org.openuss.docmanagement;

import javax.jcr.Repository;

import org.apache.jackrabbit.webdav.io.OutputContext;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class LinkDao extends ResourceDao {
	
	private Repository repository;

	@Override
	public void spool(OutputContext context) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.ResourceDao#isCollection()
	 */
	@Override
	public boolean isCollection() {
		// TODO abhängig vom Ziel des Links
		return false;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
