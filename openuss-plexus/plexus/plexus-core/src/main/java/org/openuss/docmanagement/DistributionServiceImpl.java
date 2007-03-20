// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.docmanagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

	/**
	 * dao object to create and edit folders, is injected by spring
	 */
	public FolderDao folderDao; 
	
	/**
	 * dao object to create and edit files, is injected by spring
	 */
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
    	//TODO rewrite me
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
    	//TODO implement me
    	FolderImpl folder = null;
        return folder;
    }
    

	public void buildTestStructure() throws Exception{
		folderDao.addTestStructure();		
	}

	public void clearRepository() throws Exception{
		folderDao.clearRepository();		
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

	@Override
	protected void handleDelTempZip(String zipFileName) throws Exception {
        boolean success = (new java.io.File(zipFileName)).delete();
        if (!success) {
        	//delete failed
        	throw new DocManagementException("error deleting temporary file");
        }
	}

	@Override
	protected InputStream handleZipMe(List files, String zipFileName) throws Exception {
		int read = 0;
		InputStream in;
		byte[] data = new byte[1024];
		ZipOutputStream out;
		BigFile bf;
		try{
			out = new ZipOutputStream(new FileOutputStream(zipFileName));
			out.setMethod(ZipOutputStream.DEFLATED);
			Iterator i = files.iterator();
			
			while(i.hasNext()) {
				bf = (BigFile)i.next();
				ZipEntry entry = new ZipEntry(bf.getName());
				in = bf.getFile();			
				out.putNextEntry(entry);
				while((read = in.read(data, 0, 1024)) != -1)
					out.write(data, 0, read);
				out.closeEntry(); 
				in.close();
			}
			out.close();
			
			return new FileInputStream(zipFileName);
		}catch (FileNotFoundException e){
			throw new DocManagementException(e.getMessage());
		}catch (IOException e) {
			throw new DocManagementException(e.getMessage());
		}			
	}



}