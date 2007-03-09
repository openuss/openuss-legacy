package org.openuss.docmanagement;

import javax.jcr.Node;

import org.apache.jackrabbit.webdav.io.OutputContext;

/**
 * @author David Ullrich
 * @version 0.5
 */
public abstract class ResourceDao {
	protected Node representedNode;
	
	public boolean exists() {
		return (representedNode != null);
	}

	public abstract boolean isCollection();
	
	public abstract void spool(OutputContext context);
}
