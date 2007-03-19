// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.docmanagement;

import java.util.List;
import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.LoginException;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Node;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.log4j.Logger;


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
	
	public FolderDao folderDao; 
	
	public FileDao fileDao;
	
    /**
     * @see org.openuss.docmanagement.DistributionService#addMainFolder(org.openuss.lecture.Enrollment)
     */
    protected void handleAddMainFolder(org.openuss.lecture.Enrollment enrollment)
        throws java.lang.Exception
    {  
    		Folder folder = folderDao.getFolder(DocConstants.DISTRIBUTION);    		
    		//add faculty main folder to distribution part of repository
    		FolderImpl enrollmentMain = new FolderImpl(enrollment.getShortcut(), enrollment.getId().toString(), folder.getPath(), null, DocRights.READ_ALL|DocRights.EDIT_ALL);
    		folderDao.setFolder(enrollmentMain);	
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#addFolder(org.openuss.docmanagement.Folder)
     */
    protected void handleAddFolder(org.openuss.docmanagement.Folder newFolder)
        throws java.lang.Exception
    {
    	folderDao.setFolder(newFolder);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#addFacultyFolder(org.openuss.lecture.Faculty)
     */
    protected void handleAddFacultyFolder(org.openuss.lecture.Faculty faculty)
        throws java.lang.Exception
    {
      try{ 
    	//if distribution folder does not exist create it
    	Folder folder;
		try{
			folder = folderDao.getFolder(DocConstants.DISTRIBUTION);
		} catch (Exception e){
			//distribution folder does not exist    			
			FolderImpl dist = new FolderImpl("Distribution main directory", DocConstants.DISTRIBUTION, "", null, DocRights.READ_ALL|DocRights.EDIT_ALL);
			folderDao.setFolder(dist);
		}
		folder = folderDao.getFolder(DocConstants.DISTRIBUTION);
		
		//add faculty main folder to distribution part of repository
		FolderImpl enrollmentMain = new FolderImpl(faculty.getShortcut(), faculty.getId().toString(), folder.getPath(), null, DocRights.READ_ALL|DocRights.EDIT_ALL);
		folderDao.setFolder(enrollmentMain);
		
	} catch (Exception e) {
		// TODO check if exception have to be caught here, or in weblayer
		logger.error(e);
	}
        
    }


    /**
     * @see org.openuss.docmanagement.DistributionService#changeFolder(org.openuss.docmanagement.Folder, java.lang.String, int)
     */
    protected void handleChangeFolder(org.openuss.docmanagement.Folder folder, boolean old)
        throws java.lang.Exception
    {
    	if (old) folderDao.changeFolder(folder);
    	else if (!old) folderDao.setFolder(folder);    	
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#setVisibility(org.openuss.docmanagement.Node, int)
     */
    protected void handleSetVisibility(Resource resource, int visibility)
        throws java.lang.Exception
    {
        Session session = login();
        Node n  = session.getNodeByUUID(resource.getId());
        n.setProperty("visibility", visibility);
        logout(session);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#addFile(org.openuss.docmanagement.BigFile, org.openuss.docmanagement.Folder)
     */
    protected void handleAddFile(org.openuss.docmanagement.BigFile file)
        throws java.lang.Exception
    {
    	fileDao.setFile(file);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#addLink(org.openuss.docmanagement.Link, org.openuss.docmanagement.Folder)
     */
    protected void handleAddLink(org.openuss.docmanagement.Link link)
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
    	//TODO think about moving method into fileDao to use workspace.copy()
    	BigFile bf = fileDao.getFile(file);
    	bf.setPath(targetFolder.getPath());
    	fileDao.setFile(bf);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#addSharedFile(org.openuss.docmanagement.BigFile, org.openuss.lecture.Faculty)
     */
    protected void handleAddSharedFile(org.openuss.docmanagement.BigFile file, org.openuss.lecture.Faculty faculty)
        throws java.lang.Exception
    {
    	file.setPath(folderDao.getFolder(DocConstants.DISTRIBUTION+"/"+faculty.getId().toString()).getPath());
    	fileDao.setFile(file);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#delNode(org.openuss.docmanagement.Node)
     */
    protected void handleDelFile(org.openuss.docmanagement.File file, boolean delLinks)
        throws java.lang.Exception
    {
    	fileDao.delFile(file, delLinks);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#delSharedFile(org.openuss.docmanagement.File, boolean)
     */
    protected void handleDelFolder(org.openuss.docmanagement.Folder folder, boolean delLinks)
        throws java.lang.Exception
    {	

    }


    /**
     * @see org.openuss.docmanagement.DistributionService#getFiles(org.openuss.docmanagement.Folder)
     */
    protected List handleGetFiles(Folder folder)
        throws java.lang.Exception
    {
    	return null;
    }


    /**
     * @see org.openuss.docmanagement.DistributionService#getFile(org.openuss.docmanagement.File)
     */
    protected org.openuss.docmanagement.BigFile handleGetFile(org.openuss.docmanagement.File file)
        throws java.lang.Exception
    {
		return fileDao.getFile(file);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#getSharedFiles(org.openuss.lecture.Faculty)
     */
    protected List handleGetSharedFiles(org.openuss.lecture.Faculty faculty)
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
    	//TODO change to enrollment folder
    	Folder fi = new FolderImpl(); 
    	if (enrollment==null) fi = folderDao.getFolder("test");
    	else if (enrollment!=null) fi = folderDao.getFolder(DocConstants.DISTRIBUTION+"/"+enrollment.getId().toString());
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
    

	public void buildTestStructure() throws Exception{
		folderDao.addTestStructure();		
	}

	public void clearRepository() throws Exception{
		folderDao.clearRepository();		
	}
	

	private Session login() throws LoginException, RepositoryException {
		Session session = repository.login(new SimpleCredentials(
				username, password.toCharArray()));
		return session;
	}

	private void logout(Session session) throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException {
		try{
			session.save();
		} catch (Exception e){
			logger.error("Fehler:",e);
		}
		session.logout();
	}

	public Repository getRepository() {
		return repository;
	}
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public FolderDao getFolderDao() {
		return folderDao;
	}

	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
	}

	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	@Override
	protected File handleGetFile(String path) throws Exception {
		return fileDao.getFile(path);
	}

	@Override
	protected Folder handleGetFolder(String path) throws Exception {
		return folderDao.getFolder(path);
	}

	@Override
	protected void handleChangeFile(BigFile file, boolean old) throws Exception {
    	if (old) fileDao.changeFile(file);
    	else if (!old) fileDao.setFile(file);    	
	}


}