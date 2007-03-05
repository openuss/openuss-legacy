package org.openuss.docmanagement;

import java.util.Collection;

public class FolderImpl extends ResourceImpl implements Folder{
	
	public Collection<Resource> subnodes;

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Folder#getSubnodes()
	 */
	public Collection<Resource> getSubnodes() {
		return subnodes;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Folder#setSubnodes(java.util.Collection)
	 */
	public void setSubnodes(Collection<Resource> subnodes) {
		this.subnodes = subnodes;
	}
	
	
}