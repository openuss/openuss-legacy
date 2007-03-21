package org.openuss.docmanagement;

import javax.jcr.Node;

import java.sql.Timestamp;
import java.util.Calendar;


import javax.jcr.ItemExistsException;
import javax.jcr.LoginException;
import javax.jcr.PathNotFoundException;
import javax.jcr.PropertyIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.log4j.Logger;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class FileDao extends ResourceDao {

	private Repository repository;

	public static Logger logger = Logger.getLogger(FileDao.class);

	/**
	 * getFile method retrieves a file object from given path and returns it
	 * 
	 * @param path
	 * @return
	 * @throws NotAFileException
	 * @throws LoginException
	 * @throws RepositoryException
	 */
	public File getFile(String path) throws NotAFileException, DocManagementException {
		Session session;
		FileImpl file;
		try {
			session = login(repository);
		Node node = session.getRootNode();
		if (path.startsWith("/")) path = path.substring(1);
		node = node.getNode(path);
		if (!node.isNodeType(DocConstants.DOC_FILE))
			throw new NotAFileException("Not a file");
		file = new FileImpl(new Timestamp(node.getProperty(
				DocConstants.PROPERTY_DISTRIBUTIONTIME).getDate().getTimeInMillis()), node.getUUID(), new Timestamp(node.getNode(
				DocConstants.JCR_CONTENT).getProperty(
				DocConstants.JCR_LASTMODIFIED).getDate().getTimeInMillis()),
				0, node.getProperty(DocConstants.PROPERTY_MESSAGE).getString(),
				node.getNode(DocConstants.JCR_CONTENT).getProperty(
						DocConstants.JCR_MIMETYPE).getString(), node.getName(),
				node.getPath(), null, 1, ((int) node.getProperty(
						DocConstants.PROPERTY_VISIBILITY).getLong()));
		file.setCreated(new Timestamp(node
				.getProperty(DocConstants.JCR_CREATED).getDate().getTimeInMillis()));
		logout(session);
		} catch (NotAFileException e) {
			throw e;
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new  DocManagementException("RepositoryException occured");
		}
		return file;
	}

	/**
	 * getFile method returns BigFile according to given file object
	 * 
	 * @param file
	 * @return
	 * @throws DocManagementException 
	 * @throws LoginException
	 * @throws RepositoryException
	 */
	public BigFile getFile(File file) throws DocManagementException {
		Session session;
		BigFileImpl fi = new BigFileImpl();
		try {
			session = login(repository);
		Node node = session.getNodeByUUID(file.getId());
		FileImpl pred = new FileImpl();
		fi = new BigFileImpl(new Timestamp(node.getProperty(
				DocConstants.PROPERTY_DISTRIBUTIONTIME).getDate().getTimeInMillis()), node.getUUID(), new Timestamp(node.getNode(
				DocConstants.JCR_CONTENT).getProperty(
				DocConstants.JCR_LASTMODIFIED).getDate().getTimeInMillis()),
				0, node.getProperty(DocConstants.PROPERTY_MESSAGE).getString(),
				node.getNode(DocConstants.JCR_CONTENT).getProperty(
						DocConstants.JCR_MIMETYPE).getString(), node.getName(),
				node.getPath(), pred, 1, ((int) node.getProperty(
						DocConstants.PROPERTY_VISIBILITY).getLong()), node
						.getNode(DocConstants.JCR_CONTENT).getProperty(
								DocConstants.JCR_DATA).getStream());
		fi.setCreated(new Timestamp(node.getProperty(DocConstants.JCR_CREATED)
				.getDate().getTimeInMillis()));
		logout(session);
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new  DocManagementException("RepositoryException occured");
		}
		return fi;
	}

	/**
	 * setFile method persists the file represented by BigFile object
	 * @param file
	 * @throws RepositoryException 
	 * @throws LoginException 
	 * @throws ResourceAlreadyExistsException 
	 * @throws Exception
	 */
	public void setFile(BigFile file) throws ResourceAlreadyExistsException, DocManagementException {
		try {
			Session session = login(repository);
			String path = file.getPath();
			if (path.startsWith("/"))
				path = path.substring(1);
			Node node = session.getRootNode().getNode(path);
			String areaType = getAreaType(node);
			if (areaType == DocConstants.DISTRIBUTION) {
				setDistributionFile(node, file);
			}
			if (areaType == DocConstants.EXAMAREA) {
			}
			if (areaType == DocConstants.WORKINGPLACE) {
			}
			logout(session);
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new  DocManagementException("RepositoryException occured");
		}			
	}

	/**
	 * convenience method, which sets given file to given node
	 * @param node
	 * @param file
	 * @throws ResourceAlreadyExistsException 
	 * @throws RepositoryException 
	 * @throws ConstraintViolationException 
	 * @throws VersionException 
	 * @throws LockException 
	 * @throws NoSuchNodeTypeException 
	 * @throws PathNotFoundException 
	 * @throws ItemExistsException 
	 * @throws Exception
	 */
	private void setDistributionFile(Node node, BigFile file) throws ResourceAlreadyExistsException, DocManagementException{ 
		try {
			if (node.hasNode(file.getName())) throw new ResourceAlreadyExistsException("File already exists");
			// nt:File Knoten
			node.addNode(file.getName(), DocConstants.DOC_FILE);
			node = node.getNode(file.getName());
			node.setProperty(DocConstants.PROPERTY_MESSAGE, file.getMessage());
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(file.getDistributionTime().getTime());
			node.setProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME, c);
			node.setProperty(DocConstants.PROPERTY_VISIBILITY, file
					.getVisibility());
				// nt:resource Knoten, der die eigentlich Datei enthaelt
			node.addNode(DocConstants.JCR_CONTENT, DocConstants.NT_RESOURCE);
			node = node.getNode(DocConstants.JCR_CONTENT);
			node.setProperty(DocConstants.JCR_DATA, file.getFile());
			node.setProperty(DocConstants.JCR_MIMETYPE, file.getMimeType());
			c.setTimeInMillis(file.getLastModification().getTime());
			node.setProperty(DocConstants.JCR_LASTMODIFIED, c);
		} catch (LoginException e1) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e1) {
			throw new  DocManagementException("RepositoryException occured");
		}
		
	}

	/**
	 * Change the given file object 
	 * @param file
	 * @throws DocManagementException 
	 * @throws LoginException
	 * @throws RepositoryException
	 */
	public void changeFile(BigFile file) throws DocManagementException  {
		try {
			Session session = login(repository);
			Node node = session.getRootNode();
			String path = file.getPath();
			if (path.startsWith("/"))
				path = path.substring(1);
			if (path != "")
				node = node.getNode(path);

			// nt:File
			node.setProperty(DocConstants.PROPERTY_MESSAGE, file.getMessage());
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(file.getDistributionTime().getTime());
			node.setProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME, c);
			node
					.setProperty(DocConstants.PROPERTY_VISIBILITY, file
							.getVisibility());

			// nt:resource
			node = node.getNode(DocConstants.JCR_CONTENT);
			c.setTimeInMillis(file.getLastModification().getTime());
			node.setProperty(DocConstants.JCR_LASTMODIFIED, c);

			session.save();
			// if nodename has changed, move node
			node = node.getParent();
			if (!node.getPath().equals(
					node.getParent().getPath() + "/" + file.getName()))
				session.move(node.getPath(), node.getParent().getPath() + "/"
						+ file.getName());
			logout(session);
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new  DocManagementException("RepositoryException occured");
		}
	}

	/**
	 * delFile method deletes given file
	 * @param file
	 * @param delLinks if delLinks is true, links to given file are deleted, if delLinks is false, links are changed to copies of file
	 * @throws RepositoryException 
	 * @throws LoginException 
	 * @throws ResourceAlreadyExistsException 
	 * @throws NotAFileException 
	 * @throws Exception
	 */
	public void delFile(File file, boolean delLinks) throws NotAFileException, ResourceAlreadyExistsException, DocManagementException{
		try {
			Session session = login(repository);
			String path = file.getPath();
			if (path.startsWith("/"))
				path = path.substring(1);
			Node node = session.getRootNode().getNode(path);
			String areaType = getAreaType(node);
			if (areaType == DocConstants.DISTRIBUTION) {
				delDistributionFile(node, delLinks);
			}
			if (areaType == DocConstants.EXAMAREA) {
			}
			if (areaType == DocConstants.WORKINGPLACE) {
			}
			logout(session);
		
		} catch (NotAFileException e) {
			throw new DocManagementException("LoginException occured");
		} catch (ResourceAlreadyExistsException e) {
			throw new DocManagementException("LoginException occured");
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new  DocManagementException("RepositoryException occured");
		}
	}

	/**
	 * convenience method, which deletes a file in distribution part
	 * @param node
	 * @param delLinks
	 * @throws RepositoryException 
	 * @throws NotAFileException 
	 * @throws ResourceAlreadyExistsException 
	 */
	private void delDistributionFile(Node node, boolean delLinks) throws NotAFileException, ResourceAlreadyExistsException, DocManagementException {
		try {
			if (delLinks) {
				PropertyIterator pi = node.getReferences();
				Node n;
				while (pi.hasNext()) {
					n = pi.nextProperty().getNode();
					n.remove();
				}
				move2trash(node,0);			
			} else if (!delLinks) {
				PropertyIterator pi = node.getReferences();
				Node n;
				Node parent;
				File f = new FileImpl();
				f = getFile(node.getPath());		
				while (pi.hasNext()) {
					n = pi.nextProperty().getNode();
					parent = n.getParent();
					n.remove();
					setDistributionFile(parent, getFile(f));
				}
				move2trash(node,0);
			}
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new  DocManagementException("RepositoryException occured");
		}
	}
	
	private void move2trash(Node node, int i) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, RepositoryException{
		//first try
		if (i==0) {
			try{
				node.getSession().move(node.getPath(), getPathToTrash(node.getPath())+"/"+node.getName());		
			} catch (ItemExistsException e){
				move2trash(node, i+1);
			}			
		}
		//higher tries -> add number to filename to prevent 2 items having the same name in a folder
		else if (i>0){
			try{
				node.getSession().move(node.getPath(), getPathToTrash(node.getPath())+"/"+node.getName()+(new Integer(i)).toString());
			} catch (ItemExistsException e){
				move2trash(node, i+1);
			}			
		}
	}

	private String getPathToTrash(String path) {
		if (path.startsWith("/")) path = path.substring(1);
		String area = path.substring(0,path.indexOf("/"));
		String id = path.substring(path.indexOf("/")+1);
		id = id.substring(0,id.indexOf("/"));
		String trash = "/"+area+"/"+id+"/"+DocConstants.TRASH_NAME;
		logger.debug("path to trash is = ");
		return trash;
	}

	/**
	 * returns the areaType of the given node. 
	 * @param node
	 * @return areaType, which can be distribution, exam area or working place
	 * @throws DocManagementException 
	 * @throws RepositoryException 
	 */
	private String getAreaType(Node node) throws DocManagementException {
		try {
			String path = "";
			path = node.getPath();
			if (path.startsWith("/"))
				path = path.substring(1);
			if (path.startsWith(DocConstants.DISTRIBUTION))
				return DocConstants.DISTRIBUTION;
			if (path.startsWith(DocConstants.EXAMAREA))
				return DocConstants.EXAMAREA;
			if (path.startsWith(DocConstants.WORKINGPLACE))
				return DocConstants.WORKINGPLACE;
			return null;
		} catch (RepositoryException e) {
			throw new  DocManagementException("RepositoryException occured");
		}
	}
	
	/**
	 * deletes a file permanently - only for use to empty trash folder
	 * @param file
	 * @throws PathNotFoundException
	 * @throws DocManagementException
	 */
	public void remove(File file) throws PathNotFoundException, DocManagementException{
		try {
			Session session = login(repository);
			String path = file.getPath();
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
}
