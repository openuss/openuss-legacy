package org.openuss.web.upload;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Destroy;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.repository.RepositoryFile;
import org.openuss.repository.RepositoryService;


/**
 * This class manage all temporary uploaded files and secures that they will delete 
 * if the user session ends.
 * @author Ingo Dueppe
 */
@Bean (name="uploadFileManager", scope=Scope.SESSION)
@View
public class UploadFileManager extends BaseBean{
	private static final Logger logger = Logger.getLogger(UploadFileManager.class);

	private static final long serialVersionUID = -842372090394813149L;
	
	private List<RepositoryFile> files;
	
	@Property (value="#{repositoryService}")
	transient private RepositoryService repository;
	
	public UploadFileManager() {
		files = new ArrayList<RepositoryFile>();
	}
	
	@Destroy
	public void destroy() {
		logger.debug("destroying temporary files");
		for (RepositoryFile file : files) {
			repository.removeFile(file);			
		}
	}
	
	/**
	 * Register a temporary file to be deleted from the repository if the session ends
	 * @param file
	 */
	public void registerFile(RepositoryFile file) {
		if (file != null) {
			if (logger.isDebugEnabled())
				logger.debug("adding file "+file.getFileName());
			files.add(file);
		}
	}
	
	/**
	 * Unregister a temporary file from automatical deletion if the session ends
	 * @param file
	 */
	public void unregisterFile(RepositoryFile file) {
		if (file != null) {
			files.remove(file);
		}
	}
	
	/**
	 * Removes the file from the associated repository
	 * @param file
	 */
	public void removeFile(RepositoryFile file) {
		if (file != null) {
			// check if the file is covered by the upload manager 
			if (files.contains(file)) {
				repository.removeFile(file);
			}
			unregisterFile(file);
		}
	}

	public RepositoryService getRepository() {
		return repository;
	}

	public void setRepository(RepositoryService repository) {
		this.repository = repository;
	}

}
