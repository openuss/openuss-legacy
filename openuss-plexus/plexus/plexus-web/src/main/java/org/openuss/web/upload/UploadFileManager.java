package org.openuss.web.upload;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Destroy;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.FileInfo;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.web.Constants;


/**
 * This class manage all temporary uploaded files and secures that they will be deleted 
 * if the user session ends.
 * @author Ingo Dueppe
 */
@Bean (name=Constants.UPLOAD_FILE_MANAGER, scope=Scope.SESSION)
@View
public class UploadFileManager extends BaseBean implements Serializable{
	
	private static final Logger logger = Logger.getLogger(UploadFileManager.class);

	private static final long serialVersionUID = -842372090394813149L;
	
	private Stack<UploadedDocument> files;
	
	private Map<FileInfo, UploadedDocument> fileInfo2Documents;
	
	public UploadFileManager() {
		files = new Stack<UploadedDocument>();
		fileInfo2Documents = new HashMap<FileInfo, UploadedDocument>();
	}
	
	@Destroy
	public void destroy() {
		logger.debug("destroying temporary files");
		for (UploadedDocument document : files) {
			document.remove();
		}
		files.clear();
	}
	
	/**
	 * Register a temporary file to be deleted from the repository if the session ends
	 * @param file
	 */
	public void registerDocument(UploadedDocument document) {
		Validate.notNull(document, "Parameter document must not be null!");
		
		logger.debug("Adding document "+document.getFileName()+" to upload manager");
		files.push(document);
	}
	
	/**
	 * Removes the file from the associated repository
	 * @param file
	 */
	public void removeDocument(UploadedDocument document) {
		Validate.notNull(document, "Parameter document must not be null!");
		setSessionBean(Constants.UPLOADED_FILE, null);
		files.remove(document);
		document.remove();
	}
	
	public void remove(FileInfo fileInfo) {
		Validate.notNull(fileInfo, "Parameter fileInfo must not be null!");
		UploadedDocument documents = fileInfo2Documents.get(fileInfo);
		if (documents != null) {
			fileInfo2Documents.remove(fileInfo);
			registerDocument(documents);
		}
	}
	
	public UploadedDocument getLastUploaded() {
		if (files.isEmpty()) {
			return null;
		} else {
			return files.peek();
		}
	}
	
	public FileInfo lastUploadAsFileInfo() throws IOException {
		if (files.isEmpty()) {
			return null;
		}
		UploadedDocument document = files.peek();
		if (document != null) {
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileName(document.getFileName());
			fileInfo.setExtension(extension(document.getFileName()));

			fileInfo.setContentType(document.getContentType());
			fileInfo.setFileSize(document.getFileSize());
			fileInfo.setInputStream(document.getInputStream());
			fileInfo2Documents.put(fileInfo, document);
			return fileInfo;
		} else {
			return null;
		}
	}
	
	private String extension(String fileName) {
		if (fileName != null) {
			int index = fileName.lastIndexOf('.');
			return fileName.substring(index+1);
		} else {
			return "";
		}
	}
	
}
