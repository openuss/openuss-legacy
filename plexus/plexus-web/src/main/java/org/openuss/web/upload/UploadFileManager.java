package org.openuss.web.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Destroy;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.FileInfo;
import org.openuss.framework.web.jsf.controller.BaseBean;


/**
 * This class manage all temporary uploaded files and secures that they will be deleted 
 * if the user session ends.
 * @author Ingo Dueppe
 */
@Bean (name="uploadFileManager", scope=Scope.SESSION)
@View
public class UploadFileManager extends BaseBean{
	private static final Logger logger = Logger.getLogger(UploadFileManager.class);

	private static final long serialVersionUID = -842372090394813149L;
	
	private Map<FileInfo, File> files;
	
	public UploadFileManager() {
		files = new HashMap<FileInfo, File>();
	}
	
	@Destroy
	public void destroy() {
		logger.debug("destroying temporary files");
		for (Map.Entry<FileInfo, File> entry: files.entrySet()) {
			FileInfo info = entry.getKey();
			File file = entry.getValue();
			
			IOUtils.closeQuietly(info.getInputStream());
			
			if (!file.delete()) {
				logger.error("couldn't delete tempory file "+ file.getAbsolutePath() +" for "+info.getName());
				entry.getValue().deleteOnExit();
			}
		}
	}
	
	/**
	 * Register a temporary file to be deleted from the repository if the session ends
	 * @param file
	 */
	public void registerFile(FileInfo fileInfo, File tempFile) {
		Validate.notNull(fileInfo, "Parameter fileInfo must not be null!");
		Validate.notNull(tempFile, "Parameter tempFile must not be null!");
		
		logger.debug("Adding file "+fileInfo.getFileName()+" to upload manager");
		files.put(fileInfo, tempFile);
	}
	
	/**
	 * Removes the file from the associated repository
	 * @param file
	 */
	public void removeFile(FileInfo fileInfo) {
		Validate.notNull(fileInfo, "Parameter fileInfo must not be null!");
		
		File file = files.get(fileInfo);
		if (file != null && !file.delete()) {
			logger.error("couldn't delete tempory file "+ file.getAbsolutePath() +" for "+fileInfo.getName());
			file.deleteOnExit();
		}
	}
}
