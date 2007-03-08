package org.openuss.docmanagement;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class FolderDao extends ResourceDao {

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.ResourceDao#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return true;
	}
}
