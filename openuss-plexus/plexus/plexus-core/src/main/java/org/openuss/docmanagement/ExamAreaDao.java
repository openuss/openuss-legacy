package org.openuss.docmanagement;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import org.apache.log4j.Logger;
import org.springmodules.jcr.JcrSessionFactory;

public class ExamAreaDao extends ResourceDao{
	
	public static final Logger logger = Logger.getLogger(ExamAreaDao.class);
	
	private JcrSessionFactory sessionFactory;
	
	private Session session;
	
	public FileDao fileDao;
	
	
	public void setDeadline(Timestamp time, String path) throws DocManagementException{
		try {
			if (path.startsWith("/")) path = path.substring(1);
			Node node = session.getRootNode().getNode(path);
			Calendar c = new GregorianCalendar();
			c.setTimeInMillis(time.getTime());
			node.setProperty(DocConstants.PROPERTY_DEADLINE, c);
			session.save();		
		} catch (RepositoryException e) {
			throw new DocManagementException("Unknown Error occured");
		}		
	}
	
	public ExamArea getExamArea(String path) throws PathNotFoundException, DocManagementException{
		ExamArea eai = new ExamAreaImpl();
		try {
			Node node = session.getRootNode();
			if (path==null) throw new NotAFolderException("resource at path: '' is not a folder");
			if (path.length() != 0)
				if (path.startsWith("/"))path = path.substring(1);
					try{
						node = node.getNode(path);
					}
					catch (javax.jcr.PathNotFoundException e1){
						throw new org.openuss.docmanagement.PathNotFoundException("folder does not exist");
					}
			if (!node.isNodeType(DocConstants.DOC_FOLDER)){
				throw new NotAFolderException("resource at path  '"+ path + "' is not a folder");
			}
			eai.setDeadline(new Timestamp(node.getProperty(DocConstants.PROPERTY_DEADLINE).getDate().getTimeInMillis()));
			eai.setId(node.getUUID());
			eai.setName("");
			eai.setPath(node.getPath());
			eai.setVisibility((int) node.getProperty(DocConstants.PROPERTY_VISIBILITY).getLong());
					
			setSubFiles(node, eai);		
			session.save();
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
		String filePath;
		while (ni.hasNext()) {
			n = ni.nextNode();
			if (!n.getName().startsWith("jcr:")) {
				if (n.isNodeType(DocConstants.DOC_FILE)) {
					filePath = n.getPath();						
					if (filePath.startsWith("/")) filePath = filePath.substring(1);
					v.add(getFileDao().getFile(filePath));
				}
			}
		}
		if (v.size() > 0)
		eai.setSubnodes(v);
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
