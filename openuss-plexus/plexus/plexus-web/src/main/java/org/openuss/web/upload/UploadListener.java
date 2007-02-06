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
			logger.debug("file name " + uploadedFile.getName());
			logger.debug("file type " + uploadedFile.getContentType());
			logger.debug("file size " + uploadedFile.getSize());

			logger.debug("source " + event.getSource());
			logger.debug("component " + event.getComponent());
			
			try {
				saveUploadedFile(uploadedFile);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	public void saveUploadedFile(UploadedFile uploadedFile) throws IOException {
		RepositoryService repository = (RepositoryService) getBean("repositoryService");
		RepositoryFile file = RepositoryFile.Factory.newInstance();
		
		String fileName = extractFileName(uploadedFile.getName());
		
		file.setName(fileName);
		file.setFileName(fileName);
		file.setContentType(uploadedFile.getContentType());
		file.setFileSize((int)uploadedFile.getSize());
		file.setInputStream(uploadedFile.getInputStream());
		
		repository.saveFile(file);
		UploadFileManager fileManager = (UploadFileManager) getBean("uploadFileManager");
		fileManager.registerFile(file);
		
		// store last uploaded file reference in session
		setSessionBean(Constants.UPLOADED_FILE, file);
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
