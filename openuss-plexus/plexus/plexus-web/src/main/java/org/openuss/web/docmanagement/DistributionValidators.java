package org.openuss.web.docmanagement;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class DistributionValidators{
	//FIXME implement me	
	public void checkUser(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		  // you are free to call this method at your choice, it has to be conform with your validator attribute
		  // queue the message in this method, make sure to call component.setValid(false)
		//FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, details);
		//getFacesContext().addMessage(clientId, msg);
	}
}