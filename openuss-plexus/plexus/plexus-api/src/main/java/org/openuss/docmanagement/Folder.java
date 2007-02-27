package org.openuss.docmanagement;

import java.util.Collection;

public interface Folder {

	public abstract Collection<Node> getSubnodes();

	public abstract void setSubnodes(Collection<Node> subnodes);

}