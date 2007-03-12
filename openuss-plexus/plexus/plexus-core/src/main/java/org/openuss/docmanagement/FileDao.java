package org.openuss.docmanagement;

import java.util.Calendar;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.nodetype.ConstraintViolationException;

import org.apache.log4j.Logger;

import org.apache.jackrabbit.webdav.io.OutputContext;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class FileDao extends ResourceDao {
	
	private Repository repository;
	
	public static Logger logger = Logger.getLogger(FileDao.class);

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.ResourceDao#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return false;
	}
	
	public BigFile getFile(File file){
		Session session;
		try {
			session = login(repository);
			Node node = session.getNodeByUUID(file.getId());
			//fill FileObject
			logout(session);		
		} catch (ConstraintViolationException e){
			logger.error("ConstraintViolationException: ",e);
		} catch (LoginException e) {
			//should never happen (repository access through master account)
			logger.error("Login error in FileDao", e);
		} catch (RepositoryException e) {
			logger.error("Repository Exception: ",e);
		}
		return null;		
	}

	public File getFileInfoByPath(String path){
		return null;
	}

	public File getFileInfoById(String id){
		return null;
	}
	
	public void setFile(BigFile file) throws Exception{
		try {
			Session session = login(repository);
			Node node = session.getRootNode().getNode(file.getPath());
			String areaType = getAreaType(node);
			if (areaType==DocConstants.DISTRIBUTION){
				setDistributionFile(node, file);
			}
			if (areaType==DocConstants.EXAMAREA){
			}
			if (areaType==DocConstants.WORKINGPLACE){
			}

			logout(session);
		} catch (LoginException e) {
			//should never happen (repository access through master account)
			logger.error("Login error in FileDao", e);			
		} catch (RepositoryException e) {
			logger.error("Repository Exception: ",e);			
		}
	}
	
	private void setDistributionFile(Node node, BigFile file) throws Exception{
		try{
			Node test = node.getNode(file.getName());
			//only reached if Node with same name exists
			//TODO change to self-written exception
			throw new Exception("File already exists");
		} catch (PathNotFoundException e){
			//should occur!
	    	// nt:File Knoten
			node.addNode(file.getName(), "nt:file");
			node = node.getNode(file.getName()); 
			node.addMixin("mix:referenceable");
			//TODO all attributes set?
			// nt:resource Knoten, der die eigentlich Datei enthaelt
			node.addNode("jcr:content", "nt:resource");
			node = node.getNode("jcr:content");
			node.addMixin("mix:referenceable");			
			node.setProperty(DocConstants.PROPERTY_MESSAGE, file.getMessage());
			node.setProperty("jcr:data", file.getFile());
			node.setProperty("jcr:mimeType", file.getMimeType());	
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(file.getLastModification().getTime());
			node.setProperty("jcr:lastModified", c); 
		} catch (RepositoryException e){
			logger.error("RepositoryException: ", e);
		}
	}
	
	private String getAreaType(Node node){
		//TODO implement me
		//possible methods: recursive, path analysing, ...?
		return DocConstants.DISTRIBUTION;
	}

	@Override
	public void spool(OutputContext context) {
		// TODO Auto-generated method stub
		
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
