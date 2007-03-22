package org.openuss.docmanagement;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * @author David Ullrich
 * @version 0.5
 */
public class ExamAreaDao extends FolderDao {
	
	private Repository repository;

	public FileDao fileDao;
	
	public void setDeadline(ExamArea ea) throws DocManagementException{
		try {
			Session session = login(repository);
			String path = ea.getPath();
			if (path.startsWith("/")) path = path.substring(1);
			Node node = session.getRootNode().getNode(path);
			Calendar c = new GregorianCalendar();
			c.setTimeInMillis(ea.getDeadline().getTime());
			node.setProperty(DocConstants.PROPERTY_DEADLINE, c);
			logout(session);		
		} catch (RepositoryException e) {
			throw new DocManagementException("Unknown Error occured");
		}		
	}
	
	public ExamArea getExamArea(String path) throws PathNotFoundException, DocManagementException{
		ExamArea eai = new ExamAreaImpl();
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
			eai.setDeadline(new Timestamp(node.getProperty(DocConstants.PROPERTY_DEADLINE).getDate().getTimeInMillis()));
			eai.setId(node.getUUID());
			eai.setName("");
			eai.setPath(node.getPath());
			eai.setVisibility((int) node.getProperty(DocConstants.PROPERTY_VISIBILITY).getLong());
					
			setSubFiles(node, eai);		
			logout(session);
		} catch (javax.jcr.PathNotFoundException e) {
			throw new PathNotFoundException("Path not found");
		} catch (RepositoryException e){
			throw new DocManagementException("Unknown error occured");
		}
		return eai;
	}

	private void setSubFiles(Node node, ExamArea eai) throws RepositoryException, NotAFileException, DocManagementException {
		Vector<Resource> v = new Vector<Resource>();
		NodeIterator ni = node.getNodes();
		Node n;
		String newPath = "";
		String filePath;
		while (ni.hasNext()) {
			n = ni.nextNode();
			if (!n.getName().startsWith("jcr:")) {
				if (n.isNodeType(DocConstants.DOC_FILE)) {
					filePath = n.getPath();						
					if (filePath.startsWith("/")) filePath = filePath.substring(1);
					v.add(fileDao.getFile(filePath));
				}
			}
		}
		if (v.size() > 0)
		eai.setSubnodes(v);
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
