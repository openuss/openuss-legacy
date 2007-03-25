package org.openuss.web.docmanagement.examarea;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.openuss.web.docmanagement.AbstractFileUploadListener;

/**
 * ValueChangeListener for File Upload
 */
public class ExamFileUploadListener extends AbstractFileUploadListener implements ValueChangeListener {	

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExamFileUploadListener.class);
	
	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
		logger.debug("FileUpload event fired");
		UploadedFile uploadedFile = (UploadedFile) event.getNewValue();
		ExamFileController examFileController = (ExamFileController) getObjectFromContext("#{examFileController}");
		if (uploadedFile != null) {			
			examFileController.setFile(uploadedFile2File(uploadedFile, examFileController.file.getPath()));
		}
		else if (uploadedFile == null) {
			examFileController.setFile(null);			
		}
		logger.debug("FileUpload event handled");
	}

}
