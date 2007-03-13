package org.openuss.docmanagement;

import java.util.Vector;
import java.util.Collection;

import javax.jcr.LoginException;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
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

	private Repository repository;

	public static Logger logger = Logger.getLogger(FolderDao.class);

	private FileDao fileDao;

	public Folder getFolder(String path) throws Exception {
		FolderImpl fi;
		try {
			Session session = login(repository);
			Node node = session.getRootNode();
			if (!(path.length() == 0))
				try{
					node = node.getNode(path);
				} catch (PathNotFoundException e){
					throw new Exception("Path not found");
				}
			if (!node.isNodeType(DocConstants.NT_FOLDER))
				throw new Exception("not a folder");

			fi = new FolderImpl(node.getUUID(), node.getProperty(
					DocConstants.PROPERTY_MESSAGE).getString(), node.getName(),
					node.getPath(), null, // TODO add subnodes
					(int) node.getProperty(DocConstants.PROPERTY_VISIBILITY)
							.getLong());

			Vector<Resource> v = new Vector<Resource>();
			NodeIterator ni = node.getNodes();
			Node n;
			String newPath = "";
			while (ni.hasNext()) {
				n = ni.nextNode();
				if (!n.getName().startsWith("jcr:")) {
					if (n.isNodeType(DocConstants.NT_FOLDER)) {
						if (path != "")
							newPath = path + "/" + n.getName();
						if (path == "")
							newPath = n.getName(); // path in root
						v.add(getFolder(newPath));
					}
					if (n.isNodeType(DocConstants.NT_FILE)) {
						v.add(fileDao.getFile(n.getPath()));
					}
					// TODO add links
				}
			}
			if (v.size() > 0)
				fi.setSubnodes(v);
			// TODO differ between Folder, Files and Links
			logout(session);
			return fi;

		} catch (LoginException e) {
			logger.error("Login Exception: ", e);
		} catch (RepositoryException e) {
			logger.error("Repository Exception: ", e);
		}

		return null;
	}

	public void setFolder(Folder folder) throws Exception {
		// setting of whole structure possible?
		try {		
			Session session = login(repository);
			Node node = session.getRootNode();
			if (folder.getPath()!="") node = node.getNode(folder.getPath());
			try{
				node.getNode(folder.getName());
			}
			catch (PathNotFoundException e){
				//should occur
				node.addNode(folder.getName(), DocConstants.NT_FOLDER);
				node = node.getNode(folder.getName());
				node.addMixin(DocConstants.MIX_REFERENCEABLE); 
				node.setProperty(DocConstants.PROPERTY_MESSAGE, folder.getMessage());
				node.setProperty(DocConstants.PROPERTY_VISIBILITY, folder.getVisibility());				
				logout(session);
			}
			throw new Exception ("Folder already exists");
				
		} catch (LoginException e) {
			logger.error("Login Exception: ", e);	
		} catch (RepositoryException e) {
			logger.error("Repository Exception: ", e);
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

}
