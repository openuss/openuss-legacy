package org.openuss.web.docmanagement;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;

public class ExceptionHandler{
	
	public static final Logger exceptionLogger = Logger.getLogger(ExceptionHandler.class); 
	
	public void handleNotAFolderException(NotAFolderException e) {
		exceptionLogger.error(e.getMessage(), e);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void handlePathNotFoundException(PathNotFoundException e) {
		exceptionLogger.error(e.getMessage(), e);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
		exceptionLogger.error(e.getMessage(), e);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void handleNotAFileException(NotAFileException e) {
		exceptionLogger.error(e.getMessage(), e);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void handleDocManagementException(DocManagementException e) {
		exceptionLogger.error(e.getMessage(), e);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}