package org.openuss.docmanagement;

import org.apache.jackrabbit.webdav.io.OutputContext;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class FolderDao extends ResourceDao {

	@Override
	public void spool(OutputContext context) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.ResourceDao#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return true;
	}
}
