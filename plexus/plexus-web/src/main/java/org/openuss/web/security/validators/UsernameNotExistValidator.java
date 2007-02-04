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
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * Validator checks wether the username exist or not.
 *  
 * @author Ingo Dueppe
 */
@FacesValidator(value="usernameNotExistValidator")
public class UsernameNotExistValidator extends BaseBean implements Validator {
	
	private static final long serialVersionUID = -3002419073055652642L;

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String username = (String) value;
		if (StringUtils.isNotEmpty(username)) {
			SecurityService service = (SecurityService) getBean("securityService");
			// TODO user should be defined by attribute of the validator
			User user = (User) getSessionBean(Constants.USER);
			boolean unique = service.isValidUserName(user, username);
			if (!unique) {
				((UIInput)component).setValid(false);
				addError(component.getClientId(context), i18n("error_username_already_exists"), null);
			}
		}
	}
}
