package org.openuss.docmanagement;

import java.util.Collection;

public class FolderImpl extends NodeImpl implements Folder{
	
	public Collection<Node> subnodes;

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Folder#getSubnodes()
	 */
	public Collection<Node> getSubnodes() {
		return subnodes;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.Folder#setSubnodes(java.util.Collection)
	 */
	public void setSubnodes(Collection<Node> subnodes) {
		this.subnodes = subnodes;
	}
	
	
}