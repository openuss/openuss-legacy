package org.openuss.web.security.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.SecurityDomainUtility;

/**
 * Validator checks wether username contains instances of username domain delimiter.
 *  
 * @author Sebastian Roekens
 */
@FacesValidator(value="usernameNotContainsUsernameDomainDelimiterValidator")
public class UsernameNotContainsUsernameDomainDelimiterValidator extends BaseBean implements Validator {
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String username = (String) value;
		if (SecurityDomainUtility.containsDomain(username)) {
			((UIInput)component).setValid(false);
			addError(component.getClientId(context), i18n("error_username_contains_username_domain_delimiter"), null);
		}
	}
}	

