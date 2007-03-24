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

import org.apache.log4j.Logger;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.Faculty;


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
	 * dao object to create and edit files, is injected by spring
	 */
	public LinkDao linkDao;
	
	
    /**
     * @see org.openuss.docmanagement.DistributionService#addMainFolder(org.openuss.lecture.Enrollment)
     */
    protected void handleAddMainFolder(org.openuss.lecture.Enrollment enrollment)
        throws java.lang.Exception
    {  
		folderDao.buildSystemStructure(DocConstants.DISTRIBUTION, enrollment.getId().toString(),enrollment.getShortcut(), true);
		logger.debug("added enrollment main folder");
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
  		folderDao.buildSystemStructure(DocConstants.DISTRIBUTION, faculty.getId().toString(), faculty.getShortcut(), true);
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
    	linkDao.setLink(link);
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#copyFile(org.openuss.docmanagement.File, org.openuss.docmanagement.Folder)
     */
    protected void handleCopyFile(org.openuss.docmanagement.File file, org.openuss.docmanagement.Folder targetFolder)
        throws java.lang.Exception
    {
    	BigFile bf = fileDao.getFile(file);
    	bf.setPath(targetFolder.getPath());
    	bf.setVisibility(DocRights.EDIT_ASSIST|DocRights.READ_ALL);
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
		if (folder.getSubnodes()!=null) {
			Iterator i = folder.getSubnodes().iterator();
			Resource r;
			while (i.hasNext()) {
				r = (Resource) i.next();
				if (r instanceof File) {
					fileDao.delFile((File) r, delLinks);
				}
				if (r instanceof Folder) {
					handleDelFolder((Folder) r, delLinks);
				}
				if (r instanceof Link){
					linkDao.delLink((Link) r);
				}
			}
		}    	
		folderDao.remove(folder);
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
     * @see org.openuss.docmanagement.DistributionService#getMainFolder(org.openuss.lecture.Enrollment)
     */
    protected org.openuss.docmanagement.Folder handleGetMainFolder(org.openuss.lecture.Enrollment enrollment)
        throws java.lang.Exception
    {
    	Folder fi = new FolderImpl(); 
    	if (enrollment==null) throw new DocManagementException("no enrollment selected");
    	else if (enrollment!=null){
    		try {
				fi = folderDao.getFolder(DocConstants.DISTRIBUTION+"/"+enrollment.getId().toString());
			} catch (PathNotFoundException e) {
				handleAddMainFolder(enrollment);
				return getMainFolder(enrollment);				
			}
    	}
    	return fi;
    }

    /**
     * @see org.openuss.docmanagement.DistributionService#getFacultyFolder(org.openuss.lecture.Faculty)
     */
    protected org.openuss.docmanagement.Folder handleGetFacultyFolder(org.openuss.lecture.Faculty faculty)
        throws java.lang.Exception
    {
    	Folder fi = new FolderImpl(); 
    	if (faculty==null) throw new DocManagementException("no enrollment selected");
    	else if (faculty!=null){
    		try {
				fi = folderDao.getFolder(DocConstants.DISTRIBUTION+"/"+faculty.getId().toString());
			} catch (PathNotFoundException e) {
				handleAddFacultyFolder(faculty);
				return getFacultyFolder(faculty);				
			}
    	}
    	return fi;
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

	@Override
	protected void handleClearEnrollmentTrash(Enrollment enrollment) throws Exception {
		Folder f = getFolder(DocConstants.DISTRIBUTION+"/"+enrollment.getId().toString()+"/"+DocConstants.TRASH_NAME);
		if (f.getSubnodes()!=null) {
			Iterator i = f.getSubnodes().iterator();
			while (i.hasNext()) {
				Resource r = (Resource) i.next();
				if (r instanceof File) {
					fileDao.remove((File) r);
				}
				if (r instanceof Folder) {
					folderDao.remove((Folder) r);
				}
				if (r instanceof Link){
					linkDao.remove((Link)r);
				}
			}
		}		
	}

	@Override
	protected void handleClearFacultyTrash(Faculty faculty) throws Exception {
		Folder f = getFolder(DocConstants.DISTRIBUTION+"/"+faculty.getId().toString()+"/"+DocConstants.TRASH_NAME);
		if (f.getSubnodes()!=null) {
			Iterator i = f.getSubnodes().iterator();
			while (i.hasNext()){
				Resource r = (Resource) i.next();
				if (r instanceof File) {
					fileDao.remove((File) r);				
				}
				if (r instanceof Folder) {
					folderDao.remove((Folder) r);				
				}
				if (r instanceof Link) {
					linkDao.remove((Link) r);				
				}
			}
		}
	}

	public LinkDao getLinkDao() {
		return linkDao;
	}

	public void setLinkDao(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	protected Link handleGetLink(String path) throws Exception {
		return linkDao.getLink(path);
	}

	@Override
	protected void handleChangeLink(Link link) throws Exception {
		linkDao.changeLink(link);
	}

	@Override
	protected void handleDelLink(Link link) throws Exception {
		linkDao.delLink(link);
	}

}