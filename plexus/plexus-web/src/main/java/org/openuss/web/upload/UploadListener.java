package org.openuss.web.upload;

import java.io.IOException;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.repository.RepositoryFile;
import org.openuss.repository.RepositoryService;
import org.openuss.web.Constants;

/**
 * ValueChangeListener for File Upload
 * 
 * @author Ingo Dueppe
 */
public class UploadListener extends BaseBean implements ValueChangeListener {
	private static final long serialVersionUID = 7200670022689284313L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UploadListener.class);

	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
		logger.debug("file uploaded");
		UploadedFile uploadedFile = (UploadedFile) event.getNewValue();
		if (uploadedFile != null) {
			logFileInfo(uploadedFile);
			logValueChangeEvent(event);
			try {
				saveUploadedFile(uploadedFile);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	private void logValueChangeEvent(ValueChangeEvent event) {
		logger.debug("source " + event.getSource());
		logger.debug("component " + event.getComponent());
	}

	private void logFileInfo(UploadedFile uploadedFile) {
		logger.debug("file name " + uploadedFile.getName());
		logger.debug("file type " + uploadedFile.getContentType());
		logger.debug("file size " + uploadedFile.getSize());
	}

	public void saveUploadedFile(UploadedFile uploadedFile) throws IOException {
		RepositoryService repository = (RepositoryService) getBean("repositoryService");
		RepositoryFile file = RepositoryFile.Factory.newInstance();
		
		copyProperties(file, uploadedFile);
		
		repository.saveFile(file);
		uploadedFile.getInputStream().close();
		
		UploadFileManager fileManager = (UploadFileManager) getBean("uploadFileManager");
		fileManager.registerFile(file);
		
		// store last uploaded file reference in session
		setSessionBean(Constants.UPLOADED_FILE, file);
	}

	private void copyProperties(RepositoryFile toFile, UploadedFile fromFile) throws IOException {
		String fileName = extractFileName(fromFile.getName());
		
		toFile.setName(fileName);
		toFile.setFileName(fileName);
		toFile.setContentType(fromFile.getContentType());
		toFile.setFileSize((int)fromFile.getSize());
		toFile.setInputStream(fromFile.getInputStream());
	}

	public String extractFileName(String fileName) {
		String[] separators = new String[]{"\\","/"};
		int index = StringUtils.lastIndexOfAny(fileName, separators);
		if (index > -1) {
			fileName = fileName.substring(index+1);
			logger.debug("extracted filename "+fileName);
		}
		return fileName;
	}

}
