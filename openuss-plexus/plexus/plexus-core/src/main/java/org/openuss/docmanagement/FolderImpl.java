package org.openuss.docmanagement;

import java.util.Collection;

public class FolderImpl extends ResourceImpl implements Folder{
	
	public FolderImpl(){		
	}
	
	public FolderImpl(String id, String message, String name, String path, Collection<Resource> subnodes, int visibility){
		this.setId(id);
		this.setMessage(message);
		this.setName(name);
		this.setPath(path);
		this.setSubnodes(subnodes);
		this.setVisibility(visibility);
	}
	
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