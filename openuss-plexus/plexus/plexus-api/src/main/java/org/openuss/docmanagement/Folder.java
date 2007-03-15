package org.openuss.docmanagement;

import java.util.Collection;

public interface Folder extends Resource {

	public Collection<Resource> getSubnodes();

	public void setSubnodes(Collection<Resource> subnodes);

}