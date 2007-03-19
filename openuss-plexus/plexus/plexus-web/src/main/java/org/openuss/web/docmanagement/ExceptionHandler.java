package org.openuss.web.docmanagement;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;

public class ExceptionHandler{
	
	public void handleNotAFolderException(NotAFolderException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void handlePathNotFoundException(PathNotFoundException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void handleNotAFileException(NotAFileException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void handleDocManagementException(DocManagementException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}