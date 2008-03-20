package org.openuss.web.security.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;

/**
 * Validator checks wether a user with the e-mail-adress exist or not.
 *  
 * @author Lutz Kramer
 */
@FacesValidator(value="userEmailExistValidator")
public class UserEmailExistValidator extends BaseBean implements Validator {
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String useremail = (String) value;
		if (StringUtils.isNotEmpty(useremail)) {
			SecurityService service = (SecurityService) getBean("securityService");
			boolean notExist = service.isValidUserName(null, useremail);
			if (notExist) {
				UserInfo user = service.isNonExistingEmailAddress(null, useremail);
				if (user == null){
					((UIInput)component).setValid(false);
					addError(component.getClientId(context), i18n("error_email_does_not_exists"), null);
				}	
			}
		}
	}
}
