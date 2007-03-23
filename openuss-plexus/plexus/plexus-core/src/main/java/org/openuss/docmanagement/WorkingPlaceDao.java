package org.openuss.docmanagement;

import javax.jcr.Repository;


/**
 * @author David Ullrich
 * @version 0.5
 */
public class WorkingPlaceDao extends ResourceDao {
	
	/**
	 * FileDao object for file operations on repository, injected by spring
	 */
	public FileDao fileDao;
	
	/**
	 * FolderDao object for folder operations on repository, injected by spring	  
	 */
	public FolderDao folderDao;

	private Repository repository;


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

	public FolderDao getFolderDao() {
		return folderDao;
	}

	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
	}
}
