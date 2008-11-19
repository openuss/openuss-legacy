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
import org.openuss.web.Constants;

/**
 * Validator checks wether the emailadress is unique or not.
 *  
 * @author Sebastian Roekens
 */
@FacesValidator(value="uniqueEmailValidator")
public class UniqueEmailValidator extends BaseBean implements Validator {
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String email = (String) value;
		if (StringUtils.isNotEmpty(email)) {
			SecurityService service = (SecurityService) getBean("securityService");
			UserInfo user = (UserInfo) getSessionBean(Constants.USER);
			boolean unique = (service.isNonExistingEmailAddress(user, email)==null);
			if (!unique) {
				if (component instanceof UIInput) {
					((UIInput)component).setValid(false);
				}
				addError(component.getClientId(context), i18n("error_email_already_exists"), null);
			}
		}
	}
}
