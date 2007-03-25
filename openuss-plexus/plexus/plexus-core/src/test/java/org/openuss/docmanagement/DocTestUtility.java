package org.openuss.docmanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.jcr.NamespaceException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import org.springmodules.jcr.JcrSessionFactory;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.compact.CompactNodeTypeDefReader;
import org.apache.log4j.Logger;


public class DocTestUtility {
	public FileDao fileDao;

	public FolderDao folderDao;

	public LinkDao linkDao;

	public ExamAreaDao examAreaDao;

	public WorkingPlaceDao workingPlaceDao;

	public JcrSessionFactory jcrSessionFactory;
	
	public static final Logger logger = Logger.getLogger(DocTestUtility.class);

	public void setFolder(Folder folder, Node root)
			throws PathNotFoundException, RepositoryException {
		String path = folder.getPath();
		if (path.startsWith("/"))path = path.substring(1);
		if (path != "")	root = root.getNode(path);
		if (!root.hasNode(folder.getName())) {
			root.addNode(folder.getName(), DocConstants.DOC_FOLDER);
			root = root.getNode(folder.getName());
			root.setProperty(DocConstants.PROPERTY_MESSAGE, folder.getMessage());
			root.setProperty(DocConstants.PROPERTY_VISIBILITY, folder.getVisibility());
		}
	}

	public void setUp() {
		try {
			Session session = jcrSessionFactory.getSession();
			
			// add namesspace
			NamespaceRegistry nsr = session.getWorkspace()
					.getNamespaceRegistry();
			try{
				nsr.registerNamespace(DocConstants.NAMESPACE_PREFIX,
					DocConstants.NAMESPACE_URI);
			} catch (NamespaceException e){
				// do nothing
			}
			NodeTypeManagerImpl ntm = (NodeTypeManagerImpl) session
					.getWorkspace().getNodeTypeManager();
		
			try {
				FileReader fileReader;
				fileReader = new FileReader("ntd.cnd");
				

				// Create a CompactNodeTypeDefReader
				CompactNodeTypeDefReader cndReader;

				cndReader = new CompactNodeTypeDefReader(
				fileReader, "ntd.cnd");
						// Get the List of NodeTypeDef objects
				List ntdList = cndReader.getNodeTypeDefs();

				// Acquire the NodeTypeRegistry
				NodeTypeRegistry ntreg = ntm.getNodeTypeRegistry();

				// Loop through the prepared NodeTypeDefs
				for (Iterator i = ntdList.iterator(); i.hasNext();) {

					// Get the NodeTypeDef...
					NodeTypeDef ntd = (NodeTypeDef) i.next();

					// ...and register it
					ntreg.registerNodeType(ntd);
				}
			} catch (Exception e) {				
			}
			// ntr.registerNodeType(arg0)
			
			
			
			Folder dist = new FolderImpl("Main distribution folder",
					DocConstants.DISTRIBUTION, "", null, DocRights.READ_OWNER
							| DocRights.EDIT_OWNER);
			Folder exam = new FolderImpl("Main exam area folder",
					DocConstants.EXAMAREA, "", null, DocRights.READ_OWNER
							| DocRights.EDIT_OWNER);
			Folder wp = new FolderImpl("Main workingplace folder",
					DocConstants.WORKINGPLACE, "", null, DocRights.READ_OWNER
							| DocRights.EDIT_OWNER);

			setFolder(dist, session.getRootNode());
			setFolder(exam, session.getRootNode());
			setFolder(wp, session.getRootNode());
			session.save();
		} catch (RepositoryException e) {
			logger.error("", e);
		}
	}

	public void addEnrollment(long id){
		try {
			Session session = jcrSessionFactory.getSession();
			Node node = session.getRootNode().getNode(DocConstants.DISTRIBUTION);
			node.addNode((new Long(id)).toString(), DocConstants.DOC_FOLDER);
			node = node.getNode((new Long(id)).toString());
			node.setProperty(DocConstants.PROPERTY_MESSAGE, "");
			node.setProperty(DocConstants.PROPERTY_VISIBILITY, DocRights.READ_ALL|DocRights.EDIT_ALL);
			session.save();
			node.addNode(DocConstants.TRASH_NAME, DocConstants.DOC_FOLDER);
			node = node.getNode(DocConstants.TRASH_NAME);
			node.setProperty(DocConstants.PROPERTY_MESSAGE, "");
			node.setProperty(DocConstants.PROPERTY_VISIBILITY, DocRights.READ_ASSIST|DocRights.EDIT_ASSIST);
			session.save();
		} catch (RepositoryException e) {
			logger.error("", e);
		}
	}

	public void addFile(){
		try {
			Session session = jcrSessionFactory.getSession();		
			Node node = session.getRootNode().getNode(DocConstants.DISTRIBUTION+"/1");
			node.setProperty(DocConstants.PROPERTY_MESSAGE, "testMessage");
			Calendar c = new GregorianCalendar();
			c.setTimeInMillis(1);
			node.setProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME, c);
			node.setProperty(DocConstants.PROPERTY_VISIBILITY, 1);
			// nt:resource Knoten, der die eigentlich Datei enthaelt
			node.addNode(DocConstants.JCR_CONTENT, DocConstants.NT_RESOURCE);
			node = node.getNode(DocConstants.JCR_CONTENT);
			byte[] a = { 1, 2, 3, 4 };
			node.setProperty(DocConstants.JCR_DATA, new java.io.ByteArrayInputStream(a));
			node.setProperty(DocConstants.JCR_MIMETYPE, "testMime");
			Calendar c2 = new GregorianCalendar();
			c2.setTimeInMillis(2);
			node.setProperty(DocConstants.JCR_LASTMODIFIED, c2);
			session.save();
		} catch (RepositoryException e) {
			logger.error("", e);
		}
	}	
	
	public void tearDown() {
		try {
			Session session = jcrSessionFactory.getSession();		
			Node root = session.getRootNode();
			Node node;
			node = root.getNode(DocConstants.DISTRIBUTION);
			node.remove();
			node = root.getNode(DocConstants.EXAMAREA);
			node.remove();
			node = root.getNode(DocConstants.WORKINGPLACE);
			node.remove();	
			session.save();
		} catch (RepositoryException e) {
			logger.error("", e);
		}
	}

	public ExamAreaDao getExamAreaDao() {
		return examAreaDao;
	}

	public void setExamAreaDao(ExamAreaDao examAreaDao) {
		this.examAreaDao = examAreaDao;
	}

	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public FolderDao getFolderDao() {
		return folderDao;
	}

	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
	}

	public LinkDao getLinkDao() {
		return linkDao;
	}

	public void setLinkDao(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	public WorkingPlaceDao getWorkingPlaceDao() {
		return workingPlaceDao;
	}

	public void setWorkingPlaceDao(WorkingPlaceDao workingPlaceDao) {
		this.workingPlaceDao = workingPlaceDao;
	}

	public JcrSessionFactory getJcrSessionFactory() {
		return jcrSessionFactory;
	}

	public void setJcrSessionFactory(JcrSessionFactory jcrSessionFactory) {
		this.jcrSessionFactory = jcrSessionFactory;
	}

}