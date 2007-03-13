package org.openuss.docmanagement;

import java.util.Vector;

import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.Node;


import org.apache.jackrabbit.webdav.io.OutputContext;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class FolderDao extends ResourceDao {

	private Repository repository;
	
	public Folder getFolder(String path) throws Exception{
		FolderImpl fi = new FolderImpl();
		
		Session session = login(repository);
		
		Node node = session.getRootNode();
		if (!(path.length()==0)) node=node.getNode(path);
    	fi.setId(path);
    	fi.setName(path);
    	//TODO set all attributes
		Vector<Resource> v = new Vector<Resource>();
    	NodeIterator ni = node.getNodes();
    	Node n;
    	String newPath="";
    	while (ni.hasNext()){
    		n = ni.nextNode();
    		if (path!="")newPath=path+"/"+n.getName();
    		if (path=="")newPath=n.getName();
    		if (!n.getName().startsWith("jcr:")) v.add(getFolder(newPath));
    	}
    	if (v.size()>0)	fi.setSubnodes(v);		
    	//TODO differ between Folder, Files and Links
    	logout(session);
		return fi;
	}
	
	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	
}
