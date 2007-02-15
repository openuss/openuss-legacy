package org.openuss.docmanagement;

import java.util.Collection;

public class Folder extends Node{
	
	public Collection<Node> subnodes;

	public Collection<Node> getSubnodes() {
		return subnodes;
	}

	public void setSubnodes(Collection<Node> subnodes) {
		this.subnodes = subnodes;
	}
	
	
}