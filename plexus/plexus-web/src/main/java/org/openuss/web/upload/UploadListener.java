package org.openuss.web.upload;

import java.io.IOException;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.web.Constants;

/**
 * ValueChangeListener for File Upload
 * 
 * @author Ingo Dueppe
 */
public class UploadListener extends BaseBean implements ValueChangeListener {
	private static final long serialVersionUID = 7200670022689284313L;

	private static final Logger logger = Logger.getLogger(UploadListener.class);

	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
		logger.debug("process file uploaded event");
		UploadedFile uploadedFile = (UploadedFile) event.getNewValue();
		if (uploadedFile != null) {
			logValueChangeEvent(event);
			UploadedDocument document;
			try {
				document = new UploadedDocument(uploadedFile, event.getSource().toString());
			} catch (IOException e) {
				throw new AbortProcessingException("Creating temp file failed", e);
			}
			getUploadFileManager().registerDocument(document);
			setSessionBean(Constants.UPLOADED_FILE, document);
		}
	}

	private void logValueChangeEvent(ValueChangeEvent event) {
		logger.debug("source " + event.getSource());
		logger.debug("component " + event.getComponent());
	}

	private UploadFileManager getUploadFileManager() {
		return (UploadFileManager) getBean(Constants.UPLOAD_FILE_MANAGER);
	}



}
