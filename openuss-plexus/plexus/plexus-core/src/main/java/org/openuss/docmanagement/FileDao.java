package org.openuss.docmanagement;

import org.apache.jackrabbit.webdav.io.OutputContext;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class FileDao extends ResourceDao {

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.ResourceDao#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return false;
	}

	@Override
	public void spool(OutputContext context) {
		// TODO Auto-generated method stub
		
	}
}
