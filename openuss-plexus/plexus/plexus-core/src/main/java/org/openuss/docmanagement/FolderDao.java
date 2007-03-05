package org.openuss.docmanagement;

import java.util.Collection;

import javax.jcr.Session;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class FolderDao extends ResourceDao implements Folder {
	public FolderDao(ResourceLocator locator, Session session) {
		// TODO
		super(locator, session);		
	}

	public Collection<Resource> getSubnodes() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSubnodes(Collection<Resource> subnodes) {
		// TODO Auto-generated method stub
		
	}

}
