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

/**
 * Validator checks wether the username exist or not.
 *  
 * @author Ingo Dueppe
 */
@FacesValidator(value="usernameExistValidator")
public class UsernameExistValidator extends BaseBean implements Validator {
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String username = (String) value;
		if (StringUtils.isNotEmpty(username)) {
			SecurityService service = (SecurityService) getBean("securityService");
			boolean notExist = service.isValidUserName(null, username);
			if (notExist) {
				if (component instanceof UIInput) {
					((UIInput)component).setValid(false);
				}
				addError(component.getClientId(context), i18n("error_username_does_not_exists"), null);
			}
		}
	}
}
