package org.openuss.web.docmanagement;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.shale.tiger.register.FacesValidator;


/**
 * Validator checks wether the resourceName is valid
 */
@FacesValidator(value="checkResourceNameValidator")
public class DistributionValidators extends ExceptionHandler implements Validator {
	public static final String allowed ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789äöüßÄÖÜ-_. ";	
	
	public boolean checkResource(String resource){		
		if (resource==null) return false;
		if (resource.length()==0) return false;
		if (resource.endsWith(".")) return false;
		if (resource.endsWith(" ")) return false;
		if (resource.startsWith(" ")) return false;
		for (int i = 0; i < resource.length(); i++){
			if (allowed.indexOf(resource.charAt(i))==-1) return false;
			if (resource.charAt(i)=='.') if (resource.charAt(i+1)=='.') return false;
			if (resource.charAt(i)==' ') if (resource.charAt(i+1)==' ') return false;
		}
		return true;	
	}
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String resourceName = (String) value;
		if (!checkResource(resourceName)){
			((UIInput)component).setValid(false);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resource name not valid!" ,null);
			context.addMessage(null, msg);
		}
	}
}
