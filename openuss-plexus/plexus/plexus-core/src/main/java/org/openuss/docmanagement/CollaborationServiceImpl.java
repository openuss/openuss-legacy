// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.docmanagement;

import java.util.Iterator;
import java.util.List;

import org.openuss.lecture.Enrollment;

/**
 * @see org.openuss.docmanagement.CollaborationService
 */
public class CollaborationServiceImpl
    extends org.openuss.docmanagement.CollaborationServiceBase
{

	/**
	 * dao obect for file operations on repository, injected by spring
	 */
	public FileDao fileDao;
	
	/**
	 * dao obect for folder operations on repository, injected by spring
	 */
	public FolderDao folderDao;
	
	/**
	 * dao obect for working place operations on repository, injected by spring
	 */
	public WorkingPlaceDao workingPlaceDao;
	
	
	@Override
	protected void handleAddFile(BigFile file) throws Exception {
		fileDao.setFile(file);		
	}

	@Override
	protected void handleAddWorkingPlace(Enrollment enrollment) throws Exception {
		folderDao.buildSystemStructure(DocConstants.WORKINGPLACE, enrollment.getId().toString(),enrollment.getShortcut(), true);
	}

	@Override
	protected void handleClearTrash(Enrollment enrollment) throws Exception {
		Folder f = getFolder(DocConstants.WORKINGPLACE+"/"+enrollment.getId().toString()+"/"+DocConstants.TRASH_NAME);
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
			}
		}				
	}

	@Override
	protected void handleDelFile(File filename) throws Exception {
		fileDao.delFile(filename, true);
	}

	@Override
	protected BigFile handleGetFile(File file) throws Exception {
		return fileDao.getFile(file);
	}

	@Override
	protected WorkingPlace handleGetWorkingPlace(Enrollment enrollment) throws Exception {
		try{
			return workingPlaceDao.getWorkingPlace("/"+DocConstants.WORKINGPLACE+"/"+enrollment.getId().toString());
		}
		catch (PathNotFoundException e){
			handleAddWorkingPlace(enrollment);
			return workingPlaceDao.getWorkingPlace("/"+DocConstants.WORKINGPLACE+"/"+enrollment.getId().toString());
		}
		
	}

	@Override
	protected void handleAddFolder(Folder folder) throws Exception {
		folderDao.setFolder(folder);
	}

	@Override
	protected void handleChangeFile(BigFile file) throws Exception {
		fileDao.changeFile(file);		
	}

	@Override
	protected void handleChangeFolder(Folder folder) throws Exception {
		folderDao.changeFolder(folder);
	}

	@Override
	protected void handleDelFolder(Folder folder) throws Exception {
		if (folder.getSubnodes()!=null) {
			Iterator i = folder.getSubnodes().iterator();
			Resource r;
			while (i.hasNext()) {
				r = (Resource) i.next();
				if (r instanceof File) {
					fileDao.delFile((File) r, true);
				}
				if (r instanceof Folder) {
					handleDelFolder((Folder) r);
				}
			}
		}    	
		folderDao.remove(folder);
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
	protected List handleGetVersions(File file) throws Exception {
		return fileDao.getVersions(file);
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

	public WorkingPlaceDao getWorkingPlaceDao() {
		return workingPlaceDao;
	}

	public void setWorkingPlaceDao(WorkingPlaceDao workingPlaceDao) {
		this.workingPlaceDao = workingPlaceDao;
	}

    

}