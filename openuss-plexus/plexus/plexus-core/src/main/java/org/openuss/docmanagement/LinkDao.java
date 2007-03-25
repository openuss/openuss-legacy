package org.openuss.docmanagement;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;
import org.apache.log4j.Logger;
import org.springmodules.jcr.JcrSessionFactory;

public class LinkDao extends ResourceDao {
	
	private JcrSessionFactory sessionFactory;
	
	public static final Logger logger = Logger.getLogger(LinkDao.class);
	
	/**
	 * repository object injected by spring
	 */
	private Session session;
	
	/**
	 * fileDao object injected by spring
	 */
	private FileDao fileDao;

	
	/**
	 * @param link
	 */
	public void setLink(Link link) throws ResourceAlreadyExistsException, DocManagementException{
		try{
			Node node = session.getRootNode();
			String path = link.getPath();
			if (path.startsWith("/")) path = path.substring(1);		
			node = node.getNode(path);
			if (node.hasNode(link.getName())){
				session.save();
				throw new ResourceAlreadyExistsException("That name already exists in folder ");
			}
			node = node.addNode(link.getName(), DocConstants.DOC_LINK);
			node.setProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME, link.getDistributionDate().getTime());
			node.setProperty(DocConstants.PROPERTY_MESSAGE, link.getMessage());
			String targetPath = link.getTarget().getPath(); 
			if (targetPath.startsWith("/")) targetPath = targetPath.substring(1);
			Node target = session.getRootNode().getNode(targetPath);			
			node.setProperty(DocConstants.PROPERTY_REFERENCE, target);
			node.setProperty(DocConstants.PROPERTY_VISIBILITY, link.getVisibility());			
			session.save();
		} catch (RepositoryException e){
			throw new DocManagementException("RepositoryException occured");
		}
	}
	
	/**
	 * method to get a link object from a given path
	 * @param path
	 * @return
	 * @throws DocManagementException
	 */
	public Link getLink(String path) throws DocManagementException{
		try {
			Node node = session.getRootNode();
			if (path.startsWith("/")) path = path.substring(1);
			node = node.getNode(path);
			Link link = new LinkImpl();
			link.setCreated(new Timestamp(node.getProperty(DocConstants.JCR_CREATED).getDate().getTimeInMillis()));
			link.setDistributionDate(new Timestamp(node.getProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME).getDate().getTimeInMillis()));
			link.setMessage(node.getProperty(DocConstants.PROPERTY_MESSAGE).getString());
			link.setName(node.getName());
			link.setPath(node.getPath());
			Node target = node.getProperty(DocConstants.PROPERTY_REFERENCE).getNode();			
			link.setTarget(fileDao.getFile(target.getPath()));
			link.setVisibility((int)node.getProperty(DocConstants.PROPERTY_VISIBILITY).getLong());
			// get viewers of linked file
			node = node.getProperty(DocConstants.PROPERTY_REFERENCE).getNode();
			ArrayList<String> viewed = new ArrayList<String>();
			if (node.hasProperty(DocConstants.PROPERTY_VIEWED)){				
			    Property viewers = node.getProperty(DocConstants.PROPERTY_VIEWED);
			    try {
			      viewed.add(viewers.getString());
			    } catch (ValueFormatException e) {
			      Value[] viewerValues = viewers.getValues();
			      for (Value c : viewerValues) {
			    	  viewed.add(c.getString());
			      }
			    }	
			}
			link.setViewed(viewed.toArray(new String[0]));			
			session.save();
			return link;
		} catch (RepositoryException e) {
			throw new DocManagementException("Repository Exception occured");
		}
	}
	
	/**
	 * method to move a link to trash folder
	 * @param link
	 * @throws DocManagementException
	 */
	public void delLink(Link link) throws DocManagementException{
		try {
			Node node = session.getRootNode();
			String path = link.getPath();
			if (path.startsWith("/")) path = path.substring(1);
			node = node.getNode(path);
			move2trash(node,0);
			session.save();
		} catch (RepositoryException e) {
			throw new DocManagementException("RepositoryException occured");
		}
	}
	
	/**
	 * method to delete a link permanently for use to empty trash folder
	 * @param link
	 * @throws DocManagementException
	 */
	public void remove(Link link) throws DocManagementException{
		try{			
			Node node = session.getRootNode();
			String path = link.getPath();
			if (path.startsWith("/")) path = path.substring(1);
			node = node.getNode(path);
			node.remove();
			session.save();
		} catch (RepositoryException e) {
			throw new DocManagementException("RepositoryException occured");
		}			
	}

	/**
	 * convenience method to move a node to current trash folder
	 * @param node
	 * @param i
	 * @throws ItemExistsException
	 * @throws PathNotFoundException
	 * @throws VersionException
	 * @throws ConstraintViolationException
	 * @throws LockException
	 * @throws RepositoryException
	 */
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

	/**
	 * convenience method to get path to current trash folder
	 * @param path
	 * @return
	 */
	private String getPathToTrash(String path) {
		if (path.startsWith("/")) path = path.substring(1);
		String area = path.substring(0,path.indexOf("/"));
		String id = path.substring(path.indexOf("/")+1);
		id = id.substring(0,id.indexOf("/"));
		String trash = "/"+area+"/"+id+"/"+DocConstants.TRASH_NAME;
		return trash;
	}
	
	public void changeLink(Link link) throws DocManagementException{
		try{
			Node node = session.getRootNode();
			String path = link.getPath();
			if (path.startsWith("/")) path = path.substring(1);
			node = node.getNode(path);
			node.setProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME, link.getDistributionDate().getTime());
			node.setProperty(DocConstants.PROPERTY_MESSAGE, link.getMessage());
			node.setProperty(DocConstants.PROPERTY_VISIBILITY, link.getVisibility());
			if (!node.getPath().equals(node.getParent().getPath() + "/" + link.getName())) {
				if (node.getParent().hasNode(link.getName())) {
					session.save();
					throw new ResourceAlreadyExistsException("A File with that name already exists!");
				}
				session.move(node.getPath(), node.getParent().getPath() + "/"+ link.getName());
			}	
			session.save();	
		} catch (RepositoryException e) {
			throw new DocManagementException("Repository Exception occured");
		}		
	}

	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public JcrSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(JcrSessionFactory sessionFactory) {
		if (sessionFactory!=null) {
			try{
				setSession(sessionFactory.getSession());
			}catch (RepositoryException e){
				logger.error("",e);
			}
		}
		this.sessionFactory = sessionFactory;
	}
}
