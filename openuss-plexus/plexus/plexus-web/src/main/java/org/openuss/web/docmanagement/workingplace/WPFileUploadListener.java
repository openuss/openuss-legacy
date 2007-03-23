package org.openuss.web.docmanagement.workingplace;

import java.io.IOException;
import java.sql.Timestamp;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.openuss.docmanagement.BigFile;
import org.openuss.docmanagement.BigFileImpl;

/**
 * ValueChangeListener for File Upload
 */
public class WPFileUploadListener implements ValueChangeListener {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WPFileUploadListener.class);

	private Object getObjectFromContext(String expression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
        ValueBinding valueBinding = facesContext.getApplication().createValueBinding(expression);
        return valueBinding.getValue(facesContext);
	}
	
	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
		logger.debug("FileUpload event fired");
		UploadedFile uploadedFile = (UploadedFile) event.getNewValue();
		WPFileController wPFileController = (WPFileController) getObjectFromContext("#{wPFileController}");
		if (uploadedFile != null) {			
			wPFileController.setFile(uploadedFile2File(uploadedFile, wPFileController.file.getPath()));
		}
		else if (uploadedFile == null) {
			wPFileController.setFile(null);			
		}
		logger.debug("FileUpload event handled");
	}
	
	private BigFile uploadedFile2File(UploadedFile uf, String path){
		try {
			BigFile bf = new BigFileImpl(
					null, 
					new Timestamp(System.currentTimeMillis()),
					uf.getSize(),
					null,
					uf.getContentType(),
					clearIeFileName(uf.getName()),
					path,
					null,
					1,
					0,
					uf.getInputStream(), "");
			return bf;
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;		
	}

	private String clearIeFileName(String name){
		int ie = name.lastIndexOf("\\");
		if (!(ie==-1)){
			name = name.substring(ie+1);
		}
		return name;
	}

}
