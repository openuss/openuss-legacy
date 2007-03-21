package org.openuss.docmanagement;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.Vector;
import javax.jcr.LoginException;
import javax.jcr.NodeIterator;
import org.openuss.docmanagement.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Node;


import org.apache.log4j.Logger;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class FolderDao extends ResourceDao {
	
	/**
	 * repository object is injected by spring 
	 */
	private Repository repository;

	public static Logger logger = Logger.getLogger(FolderDao.class);

	/**
	 * fileDao object is injected by spring
	 */
	private FileDao fileDao;

	/**
	 * fileDao object is injected by spring
	 */
	private LinkDao linkDao;	
	

	/**
	 * getFolder method which returns the folder object at given path
	 * @param path
	 * @return
	 * @throws NotAFolderException
	 * @throws LoginException
	 * @throws RepositoryException
	 * @throws NotAFileException 
	 * @throws  
	 */
	public Folder getFolder(String path) throws PathNotFoundException, NotAFolderException, NotAFileException, DocManagementException{
		FolderImpl fi;
		try {
			Session session = login(repository);
			Node node = session.getRootNode();
			if (path==null) throw new NotAFolderException("resource at path: '' is not a folder");
			if (path.length() != 0)
				if (path.startsWith("/"))path = path.substring(1);
					try{
						node = node.getNode(path);
					}
					catch (javax.jcr.PathNotFoundException e1){
						logout(session);
						throw new org.openuss.docmanagement.PathNotFoundException("folder does not exist");
					}
			if (!node.isNodeType(DocConstants.DOC_FOLDER)){
				logout(session);
				throw new NotAFolderException("resource at path  '"+ path + "' is not a folder");
			}
			fi = new FolderImpl(
					node.getUUID(),
					node.getProperty(DocConstants.PROPERTY_MESSAGE).getString(),
					node.getName(),
					node.getPath(), 
					null, 
					(int) node.getProperty(DocConstants.PROPERTY_VISIBILITY).getLong());
			fi.setCreated(new Timestamp(node.getProperty(DocConstants.JCR_CREATED).getDate().getTimeInMillis()));
			insertSubnodes(path, fi, node);
			logout(session);
		} catch (PathNotFoundException e){
			throw e;
		} catch (NotAFolderException e){
			throw e;		
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new  DocManagementException("RepositoryException occured");
		}
		return fi;
	}

	/**
	 * convenienceMethod to insert Subnodes
	 * @param path
	 * @param fi
	 * @param node
	 * @throws RepositoryException
	 * @throws PathNotFoundException
	 * @throws NotAFolderException
	 * @throws NotAFileException
	 * @throws DocManagementException
	 */
	private void insertSubnodes(String path, FolderImpl fi, Node node) throws RepositoryException, PathNotFoundException, NotAFolderException, NotAFileException, DocManagementException {
		Vector<Resource> v = new Vector<Resource>();
		NodeIterator ni = node.getNodes();
		Node n;
		String newPath = "";
		String filePath;
		while (ni.hasNext()) {
			n = ni.nextNode();
			if (!n.getName().startsWith("jcr:")) {
				if (n.isNodeType(DocConstants.DOC_FOLDER)) {
					if (path != "")
						newPath = path + "/" + n.getName();
					if (path == "")
						newPath = n.getName(); // path in root
					v.add(getFolder(newPath));
				}
				if (n.isNodeType(DocConstants.DOC_FILE)) {
					filePath = n.getPath();						
					if (filePath.startsWith("/")) filePath = filePath.substring(1);
					v.add(fileDao.getFile(filePath));
				}
				if (n.isNodeType(DocConstants.DOC_LINK)) {
					filePath = n.getPath();						
					if (filePath.startsWith("/")) filePath = filePath.substring(1);
					//if links to folders should be possible, differ here
					v.add(linkDao.getLink(filePath));
				}					
			}
		}
		if (v.size() > 0)
			fi.setSubnodes(v);
	}

	/**
	 * setFolder method, which persists a folder represented by given folder object
	 * @param folder
	 * @throws LoginException
	 * @throws RepositoryException
	 * @throws ResourceAlreadyExistsException
	 */
	public void setFolder(Folder folder) throws ResourceAlreadyExistsException ,DocManagementException{
		try {
			Session session = login(repository);
			Node node = session.getRootNode();
			String path = folder.getPath();
			if (path.startsWith("/")) path = path.substring(1);
			if (path!="") node = node.getNode(path);
			if (node.hasNode(folder.getName())) {
				logout(session);
				throw new ResourceAlreadyExistsException ("Folder already exists");
			}
			node.addNode(folder.getName(), DocConstants.DOC_FOLDER);
			node = node.getNode(folder.getName());
			node.setProperty(DocConstants.PROPERTY_MESSAGE, folder.getMessage());
			node.setProperty(DocConstants.PROPERTY_VISIBILITY, folder.getVisibility());				
			logout(session);

		} catch (ResourceAlreadyExistsException e){
			throw e;
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new  DocManagementException("RepositoryException occured");
		}						

	}
	
	/**
	 * Method changes the folder at give path in folder object to attributes of folder object
	 * @param folder
	 * @throws PathNotFoundException, DocManagementException 
	 * @throws LoginException
	 * @throws RepositoryException
	 */
	public void changeFolder(Folder folder) throws PathNotFoundException, ResourceAlreadyExistsException, DocManagementException  {
		try {
			Session session = login(repository);
			Node node = session.getRootNode();
			if (folder.getPath()!="") node = node.getNode(folder.getPath().substring(1));
			node.setProperty(DocConstants.PROPERTY_MESSAGE, folder.getMessage());
			node.setProperty(DocConstants.PROPERTY_VISIBILITY, folder.getVisibility());
			session.save();
			//if nodename has changed, move node
			if (!node.getPath().equals(node.getParent().getPath() + "/" + folder.getName())){
				if (node.getParent().hasNode(folder.getName())) {
					logout(session);
					throw new ResourceAlreadyExistsException("A Folder with that name already exists!");
				}
				
				session.move(node.getPath(), node.getParent().getPath() + "/" + folder.getName());
			}
			logout(session);
		
		} catch (javax.jcr.PathNotFoundException e) {
			throw new PathNotFoundException("Path not found");		
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new  DocManagementException("RepositoryException occured");
		}
	}
	
		
	//TODO move to RepositoryStartup
	public void buildMainRepositoryStructure(){
		try{
			Folder dist = new FolderImpl("Main distribution folder", DocConstants.DISTRIBUTION, "", null, DocRights.READ_OWNER|DocRights.EDIT_OWNER);
			Folder exam = new FolderImpl("Main exam area folder", DocConstants.EXAMAREA, "", null, DocRights.READ_OWNER|DocRights.EDIT_OWNER);
			Folder wp = new FolderImpl("Main workingplace folder", DocConstants.WORKINGPLACE, "", null, DocRights.READ_OWNER|DocRights.EDIT_OWNER);
			setFolder(dist);
			setFolder(exam);
			setFolder(wp);
		} catch(Exception e){
			logger.error("Exception: ", e);
		}
	}


	/**
	 * deletes a folder permanently - only for use to empty trash folder
	 * @param folder
	 * @throws PathNotFoundException
	 * @throws DocManagementException
	 */
	public void remove(Folder folder) throws PathNotFoundException, DocManagementException{
		try {
			Session session = login(repository);
			String path = folder.getPath();
			if (path.startsWith("/")) path = path.substring(1);
			Node node = session.getRootNode().getNode(path);
			node.remove();
			logout(session);
		} catch (javax.jcr.PathNotFoundException e) {
			throw new PathNotFoundException("Path Not found");
		} catch (RepositoryException e) {
			throw new  DocManagementException("RepositoryException occured");
		}		
	}
	
	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public LinkDao getLinkDao() {
		return linkDao;
	}

	public void setLinkDao(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

}
