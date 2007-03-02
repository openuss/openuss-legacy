// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.docmanagement;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.LoginException;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Node;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFormatException;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.log4j.Logger;

import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeNodeBase;

/**
 * @see org.openuss.docmanagement.DistributionService
 */
public class DistributionServiceImpl
    extends org.openuss.docmanagement.DistributionServiceBase
{

	private static final Logger logger = Logger.getLogger(DistributionServiceImpl.class);

	private String username = "username";
	private String password = "password";
	
	private final String distribution = "distribution";
	
	public Repository repository;
	
    /**
     * @see org.openuss.docmanagement.DistributionService#addMainFolder(org.openuss.lecture.Enrollment)
     */
    protected void handleAddMainFolder(org.openuss.lecture.Enrollment enrollment)
        throws java.lang.Exception
    {
    	try {
			Session session = login();
			Node node = session.getRootNode();       
			//if distribution folder does not exist create it
			if (node.getNode(distribution)==null){
				node.addNode(distribution);
				node.addMixin("mix:referenceable");
			}
			//add faculty main folder to distribution part of repository
			node = node.getNode(distribution);
			node.addNode(enrollment.getId().toString(), "nt:folder");
			node = node.getNode(enrollment.getId().toString());
			node.addMixin("mix:referenceable");
			
			logout(session);
		} catch (Exception e) {
			// TODO check if exception have to be caught here, or in weblayer
			logger.error(e);
		}
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#addFolder(org.openuss.docmanagement.Folder)
     */
    protected void handleAddFolder(org.openuss.docmanagement.Folder targetFolder, org.openuss.docmanagement.Folder newFolder)
        throws java.lang.Exception
    {
    	Session session = login();
    	Node node = session.getNodeByUUID(targetFolder.getId());
    	node.addNode(newFolder.getName(), "nt:folder");
    	node = node.getNode(newFolder.getName());    	
    	node.addMixin("mix:referenceable");
    	node.setProperty("message", newFolder.getMessage());
    	node.setProperty("visibility", DocVisibility.ALL);
    	logout(session);
    	//TODO add subfolders
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#addFacultyFolder(org.openuss.lecture.Faculty)
     */
    protected void handleAddFacultyFolder(org.openuss.lecture.Faculty faculty)
        throws java.lang.Exception
    {
        
    	try {
			Session session = login();
			Node node = session.getRootNode();       
			//if distribution folder does not exist create it
			if (node.getNode(distribution)==null){
				node.addNode(distribution);
				node.addMixin("mix:referenceable");
			}
			//add faculty main folder to distribution part of repository
			node = node.getNode(distribution);
			node.addNode(faculty.getId().toString(), "nt:folder");
			node = node.getNode(faculty.getId().toString());
			node.addMixin("mix:referenceable");
			
			logout(session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
        
    }


    /**
     * @see org.openuss.docmanagement.DistributionService#changeFolder(org.openuss.docmanagement.Folder, java.lang.String, int)
     */
    protected void handleChangeFolder(org.openuss.docmanagement.Folder folder, java.lang.String name, int visibility)
        throws java.lang.Exception
    {
    	//TODO add message to model and method
    	//@todo implement protected void handleChangeFolder(org.openuss.docmanagement.Folder folder, java.lang.String name, int visibility)
        throw new java.lang.UnsupportedOperationException("org.openuss.docmanagement.DistributionService.handleChangeFolder(org.openuss.docmanagement.Folder folder, java.lang.String name, int visibility) Not implemented!");
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#setVisibility(org.openuss.docmanagement.Node, int)
     */
    protected void handleSetVisibility(org.openuss.docmanagement.Node node, int visibility)
        throws java.lang.Exception
    {
        Session session = login();
        Node n  = session.getNodeByUUID(node.getId());
        n.setProperty("visibility", visibility);
        logout(session);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#addFile(org.openuss.docmanagement.BigFile, org.openuss.docmanagement.Folder)
     */
    protected void handleAddFile(org.openuss.docmanagement.BigFile file, org.openuss.docmanagement.Folder folder)
        throws java.lang.Exception
    {
    	Session session = login();
    	Node node = session.getNodeByUUID(folder.getId());

    	// nt:File Knoten
		node.addNode(file.getName(), "nt:file");
		node = node.getNode(file.getName()); 
		node.addMixin("mix:referenceable");
		// nt:resource Knoten, der die eigentlich Datei enthaelt
		node.addNode("jcr:content", "nt:resource");
		node = node.getNode("jcr:content");
		node.addMixin("mix:referenceable");			
		node.setProperty("message", file.getMessage());
		node.setProperty("jcr:data", file.getFile());
		node.setProperty("jcr:mimeType", file.getMimeType());	
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(file.getLastModification().getTime());
		node.setProperty("jcr:lastModified", c); 
		
		logout(session);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#addLink(org.openuss.docmanagement.Link, org.openuss.docmanagement.Folder)
     */
    protected void handleAddLink(org.openuss.docmanagement.Link link, org.openuss.docmanagement.Folder targetFolder)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAddLink(org.openuss.docmanagement.Link link, org.openuss.docmanagement.Folder targetFolder)
        throw new java.lang.UnsupportedOperationException("org.openuss.docmanagement.DistributionService.handleAddLink(org.openuss.docmanagement.Link link, org.openuss.docmanagement.Folder targetFolder) Not implemented!");
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#copyFile(org.openuss.docmanagement.File, org.openuss.docmanagement.Folder)
     */
    protected void handleCopyFile(org.openuss.docmanagement.File file, org.openuss.docmanagement.Folder targetFolder)
        throws java.lang.Exception
    {
        Session session = login();
        
        Workspace ws = session.getWorkspace();        
        //Items are given a new UUID automatically!
        ws.copy(session.getNodeByUUID(file.getId()).getPath(), session.getNodeByUUID(targetFolder.getId()).getPath());    	
        
        logout(session);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#addSharedFile(org.openuss.docmanagement.BigFile, org.openuss.lecture.Faculty)
     */
    protected void handleAddSharedFile(org.openuss.docmanagement.BigFile file, org.openuss.lecture.Faculty faculty)
        throws java.lang.Exception
    {
    	Session session = login();
    	
    	Node node = session.getRootNode();
    	node = node.getNode(distribution+"/"+faculty.getId().toString());

    	// nt:File Knoten
		node.addNode(file.getName(), "nt:file");
		node = node.getNode(file.getName()); 
		node.addMixin("mix:referenceable");
		// nt:resource Knoten, der die eigentlich Datei enthaelt
		node.addNode("jcr:content", "nt:resource");
		node = node.getNode("jcr:content");
		node.addMixin("mix:referenceable");			
		node.setProperty("message", file.getMessage());
		node.setProperty("jcr:data", file.getFile());
		node.setProperty("jcr:mimeType", file.getMimeType());	
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(file.getLastModification().getTime());
		node.setProperty("jcr:lastModified", c); 
		
		logout(session);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#delNode(org.openuss.docmanagement.Node)
     */
    protected void handleDelNode(org.openuss.docmanagement.Node node)
        throws java.lang.Exception
    {
    	Session session = login();
    	
    	Node n = session.getNodeByUUID(node.getId());
    	//TODO what about linked nodes?
    	n.remove();
    	
    	logout(session);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#delSharedFile(org.openuss.docmanagement.File, boolean)
     */
    protected void handleDelSharedFile(org.openuss.docmanagement.File file, boolean delLinks)
        throws java.lang.Exception
    {	
    	Session session = login();
    	Node fileToDelete = session.getNodeByUUID(file.getId());
    	PropertyIterator pi = fileToDelete.getReferences();    	
    	if (delLinks){
    		deleteLinks(pi);
    	}else if (!delLinks){
    		changeLinksToFiles(session, fileToDelete, pi);
    	}
    	
    	logout(session);
    }

	private void deleteLinks(PropertyIterator pi) throws VersionException, LockException, ConstraintViolationException, RepositoryException {
		Property p;
		while (pi.hasNext()){
			p = pi.nextProperty(); 
			p.remove();
		}		
	}

	private void changeLinksToFiles(Session session, Node fileToDelete, PropertyIterator pi) throws ItemNotFoundException, AccessDeniedException, RepositoryException, VersionException, LockException, ConstraintViolationException, PathNotFoundException, ItemExistsException {
		Property p;
		Node link;
		Node parent;
		while(pi.hasNext()){
			p = (Property) pi.nextProperty();
			// Links are saved in a extra Node
			link = p.getParent();
			parent = link.getParent();
			link.remove();
			session.getWorkspace().copy(fileToDelete.getPath(), parent.getPath());
			
		}
	}

    /**
     * @see org.openuss.docmanagement.DistributionService#getFiles(org.openuss.docmanagement.Folder)
     */
    protected java.util.Collection handleGetFiles(org.openuss.docmanagement.Folder folder)
        throws java.lang.Exception
    {
    	Session session = login();
    	
    	Collection<File> files = new Vector<File>();
    	File fi;
    	Node node = session.getNodeByUUID(folder.getId());
    	Node file;
    	NodeIterator ni = node.getNodes();
    	
    	while (ni.hasNext()){
    		file = ni.nextNode();
    		//TODO handle links
    		if (file.isNodeType("nt:file")){
    			// Make File Object
    			fi = generateFile(file);
    			files.add(fi);    			
    		}	
    	}    
    	
    	logout(session);    	
    	return files;
    }

	private File generateFile(Node file) throws UnsupportedRepositoryOperationException, RepositoryException, PathNotFoundException, ValueFormatException {
		FileImpl fi = new FileImpl();
		//TODO check if all properties are set
		//TODO move values like "message" to a class of constants
		fi.setId(file.getUUID());
		fi.setName(file.getName());
		file = file.getNode("jcr:content");
		fi.setMessage(file.getProperty("message").getString());
		fi.setMimeType(file.getProperty("jcr:mimeType").getString());
		fi.setLastModification(new Timestamp(file.getProperty("jcr:lastModified").getDate().getTimeInMillis()));
		return fi;
	}

    /**
     * @see org.openuss.docmanagement.DistributionService#getFile(org.openuss.docmanagement.File)
     */
    protected org.openuss.docmanagement.BigFile handleGetFile(org.openuss.docmanagement.File file)
        throws java.lang.Exception
    {
        Session session = login();
        
        Node f = session.getNodeByUUID(file.getId());
		BigFileImpl bfi;
		bfi = new BigFileImpl();
		//TODO check if all properties are set
		//TODO move values like "message" to a class of constants
		bfi.setId(f.getUUID());
		bfi.setName(f.getName());
		f = f.getNode("jcr:content");
		bfi.setMessage(f.getProperty("message").getString());
		bfi.setMimeType(f.getProperty("jcr:mimeType").getString());
		bfi.setLastModification(new Timestamp(f.getProperty("jcr:lastModified").getDate().getTimeInMillis()));
		//TODO check if temporary save of file is needed, or inputstream is sufficient
		bfi.setFile(f.getProperty("jcr:data").getStream());
		
		logout(session);
		return bfi;
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#getSharedFiles(org.openuss.lecture.Faculty)
     */
    protected java.util.Collection handleGetSharedFiles(org.openuss.lecture.Faculty faculty)
        throws java.lang.Exception
    {
    	//TODO check if method is needed anymore
    	// @todo implement protected org.openuss.docmanagement.File handleGetSharedFiles(org.openuss.lecture.Faculty faculty)
        return null;
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#zipFiles(org.openuss.docmanagement.BigFile)
     */
    protected java.io.InputStream handleZipFiles(org.openuss.docmanagement.BigFile files)
        throws java.lang.Exception
    {
    	//TODO check if method is needed here, or done by servlet
    	// @todo implement protected java.io.InputStream handleZipFiles(org.openuss.docmanagement.BigFile files)
        return null;
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#getMainFolder(org.openuss.lecture.Enrollment)
     */
    protected org.openuss.docmanagement.Folder handleGetMainFolder(org.openuss.lecture.Enrollment enrollment)
        throws java.lang.Exception
    {
    	Session session = login();
    	Node main = session.getRootNode();
    	main = main.getNode(distribution+"/"+enrollment.getId().toString());
    	
    	FolderImpl fi = new FolderImpl();
    	fi.setId(enrollment.getId().toString());
    	fi.setName("main");
    	fi.setVisibility(DocVisibility.ALL);
    	//TODO set subfolder
    	
    	logout(session);
    	return fi;
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#getFacultyFolder(org.openuss.lecture.Faculty)
     */
    protected org.openuss.docmanagement.Folder handleGetFacultyFolder(org.openuss.lecture.Faculty faculty)
        throws java.lang.Exception
    {
    	FolderImpl folder = null;
    	Session session = login();
    	Node node = session.getRootNode();
    	node = node.getNode(distribution+"/"+faculty.getId().toString());
    	if (node == null) throw new Exception ("faculty main Folder does not exist yet!");
    	folder = new FolderImpl();
    	folder.setId(node.getUUID());
    	folder.setName(faculty.getId().toString());
    	//TODO add subnodes
    	logout(session);    	
        return folder;
    }
    

	@Override
	protected TreeNode handleGetTreeData() throws Exception {
		try {
			Session session = login();
			Node root = session.getRootNode();

			TreeNode treeData = traverse(root,session);
			
			logout(session);

			return treeData;
		} catch (Exception e) {			
			logger.error("Fehler in der getTreeData Methode!",e);
		}

		logger.error("Fehler in der getTreeData-Methode");
		return null;
	}


	private TreeNode traverse(Node n, Session s) {
		try {

			if ((!n.getName().startsWith("jcr:"))) {

				String name = n.getName();
				if (name.equals("") || (name == null))
					name = "root";

				TreeNode tnb;
				if (n.isNodeType("mix:referenceable")) {
					tnb = new TreeNodeBase("node", name, !n.hasNodes());
				} else {
					n.addMixin("mix:referenceable");
					s.save();
					tnb = new TreeNodeBase("node", name, !n.hasNodes());
				}

				Node nn;
				if (!n.hasNodes()) {
					return tnb;
				} else if (n.hasNodes()) {
					for (NodeIterator i = n.getNodes(); i.hasNext();) {
						nn = i.nextNode();
						TreeNode temp = traverse(nn, s);
						if (temp != null)
							tnb.getChildren().add(temp);
					}
				}
				return tnb;
			}
		} catch (Exception e) {
			logger.error("Fehler in traverse-Methode",e);
		}
		// wird nur erreicht im fall des jcr:system Knotens
		logger.error("jcr:system oder nullPointerError in traverse-Methode");
		return null;
	}
	

	private Session login() throws LoginException, RepositoryException {
		Session session = repository.login(new SimpleCredentials(
				username, password.toCharArray()));
		return session;
	}

	private void logout(Session session) throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException {
		session.save();
		session.logout();
	}

	public Repository getRepository() {
		return repository;
	}
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}