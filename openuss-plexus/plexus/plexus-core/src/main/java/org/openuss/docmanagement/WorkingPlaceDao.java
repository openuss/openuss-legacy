package org.openuss.docmanagement;

public class WorkingPlaceDao extends ResourceDao {
	
	/**
	 * FileDao object for file operations on repository, injected by spring
	 */
	public FileDao fileDao;
	
	/**
	 * FolderDao object for folder operations on repository, injected by spring	  
	 */
	public FolderDao folderDao;

	public WorkingPlace getWorkingPlace(String path) throws PathNotFoundException, NotAFolderException, NotAFileException, DocManagementException{
		WorkingPlace wp = new WorkingPlaceImpl();
		Folder f = folderDao.getFolder(path);
		wp.setCreated(f.getCreated());
		wp.setId(f.getId());
		wp.setMessage(f.getMessage());
		wp.setName(f.getName());
		wp.setPath(f.getPath());
		wp.setSubnodes(f.getSubnodes());
		wp.setVisibility(f.getVisibility());
		return wp;
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
}
