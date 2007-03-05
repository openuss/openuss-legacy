package org.openuss.docmanagement;

import java.util.Collection;

public interface Folder extends Resource {

	public abstract Collection<Resource> getSubnodes();

	public abstract void setSubnodes(Collection<Resource> subnodes);

}