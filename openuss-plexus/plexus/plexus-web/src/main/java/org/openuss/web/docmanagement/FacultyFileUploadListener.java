package org.openuss.web.docmanagement;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;

/**
 * ValueChangeListener for File Upload
 */
public class FacultyFileUploadListener extends AbstractFileUploadListener implements ValueChangeListener {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FacultyFileUploadListener.class);
	
	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
		logger.debug("FileUpload event fired");
		UploadedFile uploadedFile = (UploadedFile) event.getNewValue();
		FacultyFileController facultyFileController = (FacultyFileController) getObjectFromContext("#{facultyFileController}");
		if (uploadedFile != null) {			
			facultyFileController.setFile(uploadedFile2File(uploadedFile, facultyFileController.file.getPath()));
		}
		else if (uploadedFile == null) {
			facultyFileController.setFile(null);			
		}
		logger.debug("FileUpload event handled");
	}
}
