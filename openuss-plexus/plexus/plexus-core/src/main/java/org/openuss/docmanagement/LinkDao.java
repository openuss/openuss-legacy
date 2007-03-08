package org.openuss.docmanagement;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class LinkDao extends ResourceDao {

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.ResourceDao#isCollection()
	 */
	@Override
	public boolean isCollection() {
		// TODO abhängig vom Ziel des Links
		return false;
	}
}
