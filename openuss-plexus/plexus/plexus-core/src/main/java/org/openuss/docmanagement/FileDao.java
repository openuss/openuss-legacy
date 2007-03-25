package org.openuss.docmanagement;

import javax.jcr.Node;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.LoginException;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import org.apache.log4j.Logger;
import org.springmodules.jcr.JcrSessionFactory;

public class FileDao extends ResourceDao {

	private JcrSessionFactory sessionFactory;
	
	private Session session;

	public static Logger logger = Logger.getLogger(FileDao.class);

	public MailSending mailSending;


	
	/**
	 * getFile method retrieves a file object from given path and returns it
	 * 
	 * @param path
	 * @return
	 * @throws NotAFileException
	 * @throws LoginException
	 * @throws RepositoryException
	 */
	public File getFile(String path) throws NotAFileException,
			DocManagementException {
		FileImpl file;
		try {
			Node node = session.getRootNode();
			if (path.startsWith("/"))path = path.substring(1);
			node = node.getNode(path);
			if (!node.isNodeType(DocConstants.DOC_FILE)) {
				session.save();
				throw new NotAFileException("Not a file");
			}
			file = node2FileImpl(session, node);
			session.save();
		} catch (NotAFileException e) {
			throw e;
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new DocManagementException("RepositoryException occured");
		}
		return file;
	}

	/**
	 * convenience method, which extract the information of a node and return a fileImpl object representation
	 * @param session
	 * @param node
	 * @return
	 * @throws RepositoryException
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws UnsupportedRepositoryOperationException
	 * @throws AccessDeniedException
	 * @throws ItemExistsException
	 * @throws ConstraintViolationException
	 * @throws InvalidItemStateException
	 * @throws VersionException
	 * @throws LockException
	 * @throws NoSuchNodeTypeException
	 */
	private FileImpl node2FileImpl(Session session, Node node) throws RepositoryException, ValueFormatException, PathNotFoundException, UnsupportedRepositoryOperationException, AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, VersionException, LockException, NoSuchNodeTypeException {
		FileImpl file;
		String owner = "";
		if (node.hasProperty(DocConstants.PROPERTY_OWNER))
			owner = node.getProperty(DocConstants.PROPERTY_OWNER).getString();
		Timestamp distributionTime = new Timestamp(node.getProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME).getDate().getTimeInMillis());
		Timestamp lastModification = new Timestamp(node.getNode(DocConstants.JCR_CONTENT).getProperty(DocConstants.JCR_LASTMODIFIED).getDate().getTimeInMillis()); 
		file = new FileImpl(
				distributionTime,
				node.getUUID(), 
				lastModification,
				node.getNode(DocConstants.JCR_CONTENT).getProperty(DocConstants.JCR_DATA).getLength(),
				node.getProperty(DocConstants.PROPERTY_MESSAGE).getString(),
				node.getNode(DocConstants.JCR_CONTENT).getProperty(DocConstants.JCR_MIMETYPE).getString(),
				node.getName(),
				node.getPath(), 
				null, 
				1, 
				((int) node.getProperty(DocConstants.PROPERTY_VISIBILITY).getLong()), 
				owner,
				extractViewers(node).toArray(new String[0]), 
				"");
		if (node.hasProperty(DocConstants.JCR_CREATED)){
			file.setCreated(new Timestamp(node.getProperty(DocConstants.JCR_CREATED).getDate().getTimeInMillis()));
		}
		return file;
	}

	/**
	 * extracts all viewers of this node and returns a array list of usernames
	 * @param node
	 * @return
	 * @throws RepositoryException
	 * @throws PathNotFoundException
	 * @throws ValueFormatException
	 */
	private ArrayList<String> extractViewers(Node node) throws RepositoryException, PathNotFoundException, ValueFormatException {
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
		return viewed;
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
		BigFileImpl fi = new BigFileImpl();
		try {
			String path = file.getPath();
			if (path.startsWith("/")) path = path.substring(1);
			Node node = session.getRootNode().getNode(path);
			fi = node2BigFileImpl(node);
			//old versions have no jcr:created property 
			if (node.hasProperty(DocConstants.JCR_CREATED)){
				fi.setCreated(new Timestamp(node.getProperty(DocConstants.JCR_CREATED).getDate().getTimeInMillis()));
			}
			// visbility changes only can be saved, if nodes are not versionable, or every view would create a new version 
			if (getAreaType(node).equals(DocConstants.DISTRIBUTION)){
				handleVisibilityChanges(file, session, fi, node);			
			}
			session.save();
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new DocManagementException("RepositoryException occured");
		}
		return fi;
	}

	/**
	 * if file is in a distribution file, system keeps information, if user has viewed a file or not. this is handled by this method
	 * @param file
	 * @param session
	 * @param fi
	 * @param node
	 * @throws RepositoryException
	 * @throws PathNotFoundException
	 * @throws ValueFormatException
	 */
	private void handleVisibilityChanges(File file, Session session, BigFileImpl fi, Node node) throws RepositoryException, PathNotFoundException, ValueFormatException {
		//get list of user, who have viewed the document
		if (file.getViewer()==null) file.setViewer("");
		ArrayList<String> viewed = new ArrayList<String>();
		Value[] viewerValues=null;
		if (node.hasProperty(DocConstants.PROPERTY_VIEWED)){				
		    Property viewers = node.getProperty(DocConstants.PROPERTY_VIEWED);
		    try {
		      viewed.add(viewers.getString());
		    } catch (ValueFormatException e) {
		      viewerValues = viewers.getValues();
		      for (Value c : viewerValues) {
		    	  viewed.add(c.getString());
		      }
		    }	
		}
		fi.setViewed(viewed.toArray(new String[0]));
		//check if user has viewed document, if not, add him to list
		Iterator i = viewed.iterator();
		boolean hasViewed = false;				
		while (i.hasNext()){
			if (((String)i.next()).equals(file.getViewer())) hasViewed = true;
		}
		if (!hasViewed){
			ValueFactory vf = session.getValueFactory();					
			Value v = vf.createValue(file.getViewer());					
			ArrayList<Value> valueList = new ArrayList<Value>();
			if (viewerValues!=null){
				for (int j = 0; j < viewerValues.length; j++) valueList.add(viewerValues[j]);
			}
			valueList.add(v);
			Value[] v2 = valueList.toArray(new Value[0]);
			node.setProperty(DocConstants.PROPERTY_VIEWED, v2);					
		}		
	}

	/**
	 * convenience method which returns the BigFileImpl representation of give node
	 * @param node
	 * @return
	 * @throws RepositoryException
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws UnsupportedRepositoryOperationException
	 */
	private BigFileImpl node2BigFileImpl(Node node) throws RepositoryException, ValueFormatException, PathNotFoundException, UnsupportedRepositoryOperationException {
		BigFileImpl fi;
		String owner = "";
		if (node.hasProperty(DocConstants.PROPERTY_OWNER))
			owner = node.getProperty(DocConstants.PROPERTY_OWNER)
					.getString();
		fi = new BigFileImpl(
				new Timestamp(node.getProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME).getDate().getTimeInMillis()), 
				node.getUUID(),
				new Timestamp(node.getNode(DocConstants.JCR_CONTENT).getProperty(DocConstants.JCR_LASTMODIFIED).getDate().getTimeInMillis()), 
				node.getNode(DocConstants.JCR_CONTENT).getProperty(DocConstants.JCR_DATA).getLength(), 
				node.getProperty(DocConstants.PROPERTY_MESSAGE).getString(),
				node.getNode(DocConstants.JCR_CONTENT).getProperty(DocConstants.JCR_MIMETYPE).getString(), 
				node.getName(),
				node.getPath(), 
				null,
				1, 
				((int) node.getProperty(DocConstants.PROPERTY_VISIBILITY).getLong()), 
				node.getNode(DocConstants.JCR_CONTENT).getProperty(DocConstants.JCR_DATA).getStream(),
				owner);
		return fi;
	}

	/**
	 * setFile method persists the file represented by BigFile object
	 * 
	 * @param file
	 * @throws RepositoryException
	 * @throws LoginException
	 * @throws ResourceAlreadyExistsException
	 * @throws Exception
	 */
	public void setFile(BigFile file) throws ResourceAlreadyExistsException,
			DocManagementException {
		if (systemFolder(file.getPath()+"/"+file.getName())) throw new SystemFolderException("Systemfolders cannot be edited, files in trash-folder cannot be edited!");
		try {
			String path = file.getPath();
			if (path.startsWith("/"))
				path = path.substring(1);
			Node node = session.getRootNode().getNode(path);
			String areaType = getAreaType(node);
			if (areaType == DocConstants.DISTRIBUTION) {
				setDistributionFile(node, file);
			}
			if (areaType == DocConstants.EXAMAREA) {
				setExamAreaFile(node, file);
			}
			if (areaType == DocConstants.WORKINGPLACE) {
				setWorkingPlaceFile(node, file);
			}
			String id = path.substring(path.indexOf("/")+1);
			if (id.indexOf("/")!=-1) id = id.substring(0, id.indexOf("/"));
			mailSending.triggerMails(new Long(id),((file.getVisibility()&DocRights.READ_ALL)>0));
			session.save();
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new DocManagementException("RepositoryException occured");
		}
	}
	
	/**
	 * convenience method, which sets given file to given node
	 * 
	 * @param node
	 * @param file
	 * @throws NoSuchNodeTypeException
	 * @throws VersionException
	 * @throws ConstraintViolationException
	 * @throws LockException
	 * @throws AccessDeniedException
	 * @throws ItemExistsException
	 * @throws InvalidItemStateException
	 * @throws UnsupportedRepositoryOperationException
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 * @throws DocManagementException 
	 */
	private void setWorkingPlaceFile(Node node, BigFile file)
			throws NoSuchNodeTypeException, VersionException,
			ConstraintViolationException, LockException, AccessDeniedException,
			ItemExistsException, InvalidItemStateException,
			UnsupportedRepositoryOperationException, ValueFormatException,
			PathNotFoundException, RepositoryException, DeadlineException, DocManagementException {				
		if (node.hasNode(file.getName())) {
			node = node.getNode(file.getName());
			if (!node.isNodeType(DocConstants.MIX_VERSIONABLE)) {
				node.addMixin(DocConstants.MIX_VERSIONABLE);
				node.getNode(DocConstants.JCR_CONTENT).addMixin(
						DocConstants.MIX_VERSIONABLE);
				node.getSession().save();
				node.checkin();
				node.getSession().save();
			}
			node.checkout();
			node.setProperty(DocConstants.PROPERTY_OWNER, file.getOwner());
			writeDocFileProperties(node, file);
			node.getSession().save();
			node.checkin();
			return;
		}
		node.addNode(file.getName(), DocConstants.DOC_FILE);
		node = node.getNode(file.getName());
		node.setProperty(DocConstants.PROPERTY_OWNER, file.getOwner());
		writeDocFile(node, file);
	}	
	
	

	/**
	 * convenience method, which sets given file to given node
	 * 
	 * @param node
	 * @param file
	 * @throws NoSuchNodeTypeException
	 * @throws VersionException
	 * @throws ConstraintViolationException
	 * @throws LockException
	 * @throws AccessDeniedException
	 * @throws ItemExistsException
	 * @throws InvalidItemStateException
	 * @throws UnsupportedRepositoryOperationException
	 * @throws ValueFormatException
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 * @throws DocManagementException 
	 */
	private void setExamAreaFile(Node node, BigFile file)
			throws NoSuchNodeTypeException, VersionException,
			ConstraintViolationException, LockException, AccessDeniedException,
			ItemExistsException, InvalidItemStateException,
			UnsupportedRepositoryOperationException, ValueFormatException,
			PathNotFoundException, RepositoryException, DeadlineException, DocManagementException {
		//check for deadline
		if (!node.hasProperty(DocConstants.PROPERTY_DEADLINE)) throw new DocManagementException ("no deadline found");
		if (node.getProperty(DocConstants.PROPERTY_DEADLINE).getDate().getTimeInMillis()<System.currentTimeMillis())
			throw new DeadlineException("Deadline reached, no more submissions are possible!");		
		if (node.hasNode(file.getName())) {
			node = node.getNode(file.getName());
			if (!node.getProperty(DocConstants.PROPERTY_OWNER).getString().equals(file.getOwner()))
					throw new DocManagementException("Filename is already used, choose a new name!");
			if (!node.isNodeType(DocConstants.MIX_VERSIONABLE)) {
				node.addMixin(DocConstants.MIX_VERSIONABLE);
				node.getNode(DocConstants.JCR_CONTENT).addMixin(
						DocConstants.MIX_VERSIONABLE);
				node.getSession().save();
				node.checkin();
				node.getSession().save();
			}
			node.checkout();
			node.setProperty(DocConstants.PROPERTY_OWNER, file.getOwner());
			writeDocFileProperties(node, file);
			node.getSession().save();
			node.checkin();
			return;
		}
		node.addNode(file.getName(), DocConstants.DOC_FILE);
		node = node.getNode(file.getName());
		node.setProperty(DocConstants.PROPERTY_OWNER, file.getOwner());
		writeDocFile(node, file);
	}

	/**
	 * convenience method, which sets given file to given node
	 * 
	 * @param node
	 * @param file
	 * @throws ResourceAlreadyExistsException
	 * @throws DocManagementException 
	 */
	private void setDistributionFile(Node node, BigFile file)
			throws ResourceAlreadyExistsException, DocManagementException {
		try {
			if (node.hasNode(file.getName())) {
				throw new ResourceAlreadyExistsException("File already exists!");
			}
			// nt:File Knoten
			node.addNode(file.getName(), DocConstants.DOC_FILE);
			node = node.getNode(file.getName());
			writeDocFile(node, file);
		} catch (LoginException e1) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e1) {
			throw new DocManagementException("RepositoryException occured");
		}

	}

	/**
	 * convenience method which actually write the properties of nt:file and
	 * jcr:content nodes
	 * 
	 * @param node
	 * @param file
	 * @throws ValueFormatException
	 * @throws VersionException
	 * @throws LockException
	 * @throws ConstraintViolationException
	 * @throws RepositoryException
	 * @throws ItemExistsException
	 * @throws PathNotFoundException
	 * @throws NoSuchNodeTypeException
	 */
	private void writeDocFileProperties(Node node, BigFile file)
			throws ValueFormatException, VersionException, LockException,
			ConstraintViolationException, RepositoryException,
			ItemExistsException, PathNotFoundException, NoSuchNodeTypeException {
		node.setProperty(DocConstants.PROPERTY_MESSAGE, file.getMessage());
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(file.getDistributionTime().getTime());
		node.setProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME, c);
		node.setProperty(DocConstants.PROPERTY_VISIBILITY, file.getVisibility());
		// nt:resource Knoten, der die eigentlich Datei enthaelt
		node = node.getNode(DocConstants.JCR_CONTENT);
		node.setProperty(DocConstants.JCR_DATA, file.getFile());
		node.setProperty(DocConstants.JCR_MIMETYPE, file.getMimeType());
		Calendar c2 = new GregorianCalendar();
		c2.setTimeInMillis(file.getLastModification().getTime());
		node.setProperty(DocConstants.JCR_LASTMODIFIED, c2);
	}

	/**
	 * convenience method which actually write the nt:file and jcr:content nodes
	 * 
	 * @param node
	 * @param file
	 * @throws ValueFormatException
	 * @throws VersionException
	 * @throws LockException
	 * @throws ConstraintViolationException
	 * @throws RepositoryException
	 * @throws ItemExistsException
	 * @throws PathNotFoundException
	 * @throws NoSuchNodeTypeException
	 */
	private void writeDocFile(Node node, BigFile file)
			throws ValueFormatException, VersionException, LockException,
			ConstraintViolationException, RepositoryException,
			ItemExistsException, PathNotFoundException, NoSuchNodeTypeException {
		node.setProperty(DocConstants.PROPERTY_MESSAGE, file.getMessage());
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(file.getDistributionTime().getTime());
		node.setProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME, c);
		node.setProperty(DocConstants.PROPERTY_VISIBILITY, file
						.getVisibility());
		if ((file.getOwner()!=null)&&(file.getOwner()!="")) node.setProperty(DocConstants.PROPERTY_OWNER, file.getOwner());
		// nt:resource Knoten, der die eigentlich Datei enthaelt
		node.addNode(DocConstants.JCR_CONTENT, DocConstants.NT_RESOURCE);
		node = node.getNode(DocConstants.JCR_CONTENT);
		node.setProperty(DocConstants.JCR_DATA, file.getFile());
		node.setProperty(DocConstants.JCR_MIMETYPE, file.getMimeType());
		Calendar c2 = new GregorianCalendar();
		c2.setTimeInMillis(file.getLastModification().getTime());
		node.setProperty(DocConstants.JCR_LASTMODIFIED, c2);
	}

	/**
	 * Change the given file object
	 * 
	 * @param file
	 * @throws DocManagementException
	 * @throws LoginException
	 * @throws RepositoryException
	 */
	public void changeFile(BigFile file) throws ResourceAlreadyExistsException, SystemFolderException, 
			DocManagementException {
		try {
			if (systemFolder(file.getPath())) throw new SystemFolderException("Systemfolders cannot be edited, files in trash-folder cannot be edited!");
			Node node = session.getRootNode();
			String path = file.getPath();
			if (path.startsWith("/")) path = path.substring(1);
			if (path != "")	node = node.getNode(path);
			// nt:File
			node.setProperty(DocConstants.PROPERTY_MESSAGE, file.getMessage());
			Calendar c = new GregorianCalendar();
			c.setTimeInMillis(file.getDistributionTime().getTime());
			node.setProperty(DocConstants.PROPERTY_DISTRIBUTIONTIME, c);
			node.setProperty(DocConstants.PROPERTY_VISIBILITY, file.getVisibility());
			//delete viewed property
			if (node.hasProperty(DocConstants.PROPERTY_VIEWED)) node.setProperty(DocConstants.PROPERTY_VIEWED,new Value[0]);	
			// nt:resource
			node = node.getNode(DocConstants.JCR_CONTENT);
			Calendar c2 = new GregorianCalendar();
			c2.setTimeInMillis(file.getLastModification().getTime());
			node.setProperty(DocConstants.JCR_LASTMODIFIED, c2);
			session.save();
			// if nodename has changed, move node
			node = node.getParent();
			if (!node.getPath().equals(
					node.getParent().getPath() + "/" + file.getName())) {
				if (node.getParent().hasNode(file.getName())) {
					session.save();
					throw new ResourceAlreadyExistsException(
							"A File with that name already exists!");
				}
				session.move(node.getPath(), node.getParent().getPath() + "/"+ file.getName());
			}
			String id = path.substring(path.indexOf("/")+1);
			if (id.indexOf("/")!=-1) id = id.substring(0, id.indexOf("/"));
			mailSending.triggerMails(new Long(id),((file.getVisibility()&DocRights.READ_ALL)>0));			
			session.save();
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new DocManagementException("RepositoryException occured");
		}
	}

	/**
	 * delFile method deletes given file
	 * 
	 * @param file
	 * @param delLinks
	 *            if delLinks is true, links to given file are deleted, if
	 *            delLinks is false, links are changed to copies of file
	 * @throws RepositoryException
	 * @throws LoginException
	 * @throws ResourceAlreadyExistsException
	 * @throws NotAFileException
	 * @throws Exception
	 */
	public void delFile(File file, boolean delLinks) throws NotAFileException,
			ResourceAlreadyExistsException, SystemFolderException, DocManagementException {
		try {
			if (systemFolder(file.getPath())) throw new SystemFolderException("Systemfolders cannot be edited, files in trash-folder cannot be edited!");
			String path = file.getPath();
			if (path.startsWith("/"))
				path = path.substring(1);
			Node node = session.getRootNode().getNode(path);
			String areaType = getAreaType(node);
			if (areaType == DocConstants.DISTRIBUTION) {
				delDistributionFile(node, delLinks);
			}
			if (areaType == DocConstants.WORKINGPLACE) {
				move2trash(node, 0);				
			}
			if (areaType == DocConstants.EXAMAREA) {
				// no support for delete planned
			}
			session.save();

		} catch (SystemFolderException e){
			throw e;
		} catch (NotAFileException e) {
			throw new DocManagementException("LoginException occured");
		} catch (ResourceAlreadyExistsException e) {
			throw new DocManagementException("LoginException occured");
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new DocManagementException("RepositoryException occured");
		}
	}

	/**
	 * convenience method, which deletes a file in distribution part
	 * 
	 * @param node
	 * @param delLinks
	 * @throws RepositoryException
	 * @throws NotAFileException
	 * @throws ResourceAlreadyExistsException
	 */
	private void delDistributionFile(Node node, boolean delLinks)
			throws NotAFileException, ResourceAlreadyExistsException,
			DocManagementException {
		try {
			if (delLinks) {
				PropertyIterator pi = node.getReferences();
				Node n;				
				while (pi.hasNext()) {
					n = pi.nextProperty().getParent();
					n.remove();
				}
				move2trash(node, 0);
			} else if (!delLinks) {
				PropertyIterator pi = node.getReferences();
				Node n;
				Node parent;
				File f = new FileImpl();
				f = getFile(node.getPath());
				while (pi.hasNext()) {					
					n = pi.nextProperty().getParent();
					parent = n.getParent();
					n.remove();
					setDistributionFile(parent, getFile(f));
				}
				move2trash(node, 0);
			}
		} catch (LoginException e) {
			throw new DocManagementException("LoginException occured");
		} catch (RepositoryException e) {
			throw new DocManagementException("RepositoryException occured");
		}
	}

	/**
	 * convenience method, which moves a node to trashfolder
	 * @param node
	 * @param i
	 * @throws ItemExistsException
	 * @throws PathNotFoundException
	 * @throws VersionException
	 * @throws ConstraintViolationException
	 * @throws LockException
	 * @throws RepositoryException
	 */
	private void move2trash(Node node, int i) throws ItemExistsException,
			PathNotFoundException, VersionException,
			ConstraintViolationException, LockException, RepositoryException {
		// first try
		if (i == 0) {
			try {
				node.getSession().move(node.getPath(),
						getPathToTrash(node.getPath()) + "/" + node.getName());
			} catch (ItemExistsException e) {
				move2trash(node, i + 1);
			}
		}
		// higher tries -> add number to filename to prevent 2 items having the
		// same name in a folder
		else if (i > 0) {
			try {
				node.getSession().move(
						node.getPath(),
						getPathToTrash(node.getPath()) + "/" + node.getName()
								+ (new Integer(i)).toString());
			} catch (ItemExistsException e) {
				move2trash(node, i + 1);
			}
		}
	}

	/**
	 * convenience method, which returns the path, to current trash folder
	 * @param path
	 * @return
	 */
	private String getPathToTrash(String path) {
		if (path.startsWith("/"))
			path = path.substring(1);
		String area = path.substring(0, path.indexOf("/"));
		String id = path.substring(path.indexOf("/") + 1);
		id = id.substring(0, id.indexOf("/"));
		String trash = "/" + area + "/" + id + "/" + DocConstants.TRASH_NAME;
		logger.debug("path to trash is = ");
		return trash;
	}

	/**
	 * returns the areaType of the given node.
	 * 
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
			return "";
		} catch (RepositoryException e) {
			throw new DocManagementException("RepositoryException occured");
		}
	}

	/**
	 * deletes a file permanently - only for use to empty trash folder
	 * 
	 * @param file
	 * @throws PathNotFoundException
	 * @throws DocManagementException
	 */
	public void remove(File file) throws PathNotFoundException,
			DocManagementException {
		try {
			String path = file.getPath();
			if (path.startsWith("/"))
				path = path.substring(1);
			Node node = session.getRootNode().getNode(path);
			node.remove();
			session.save();
		} catch (javax.jcr.PathNotFoundException e) {
			throw new PathNotFoundException("Path Not found");
		} catch (RepositoryException e) {
			throw new DocManagementException("RepositoryException occured");
		}
	}
	/**
	 * returns the owner of a file
	 * @param file
	 * @return
	 * @throws PathNotFoundException
	 * @throws DocManagementException
	 */
	public String getOwner(File file) throws PathNotFoundException,
			DocManagementException {
		String owner = null;
		try {
			String path = file.getPath();
			if (path.startsWith("/"))
				path = path.substring(1);
			Node node = session.getRootNode().getNode(path);
			if (node.hasProperty(DocConstants.PROPERTY_OWNER))
				owner = node.getProperty(DocConstants.PROPERTY_OWNER)
						.getString();
			session.save();
			if (owner != null)
				return owner;
			return "";
		} catch (javax.jcr.PathNotFoundException e) {
			throw new PathNotFoundException("Path Not found");
		} catch (RepositoryException e) {
			throw new DocManagementException("RepositoryException occured");
		}
	}

	/**
	 * returns a complete list of versions of the file, from oldest to newest
	 * @param file
	 * @return
	 * @throws DocManagementException
	 */
	@SuppressWarnings("unchecked")
	public List getVersions(File file) throws DocManagementException {
		List l = new ArrayList();
		try {
			String path = file.getPath();
			if (path.startsWith("/")) path = path.substring(1);
			Node node = session.getRootNode().getNode(path);
			if (!node.isNodeType(DocConstants.MIX_VERSIONABLE)) {
				l.add(file);
				return l;
			}
			VersionHistory history = node.getVersionHistory();
			int versionnumber = 0;
			File f = new FileImpl();
			for (VersionIterator it = history.getAllVersions(); it.hasNext();) {
				Version version = (Version) it.next();
				NodeIterator it2 = version.getNodes("jcr:frozenNode");
				if (it2.hasNext()) {
					node = it2.nextNode();
					// ignore first stub version 
					if (versionnumber > 0){
						f = node2FileImpl(session, node);
						f.setName("");
						f.setVersion(versionnumber);
						l.add(f);
					}
					versionnumber++;
				}
			}
			session.save();
			return l;
		} catch (RepositoryException e) {
			throw new DocManagementException(
					"Exception retrieving file versions");
		}
	}


	public MailSending getMailSending() {
		return mailSending;
	}

	public void setMailSending(MailSending mailSending) {
		this.mailSending = mailSending;
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
