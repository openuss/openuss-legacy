package org.openuss.web.system.ldap.validator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.ldap.LdapConfigurationService;

/**
 * Validates ChangePasswordUrl for authentication domains.
 * 
 * @author Juergen de Braaf
 * 
 */
@FacesValidator(value="changePasswordUrlValidator")
public class ChangePasswordUrlValidator extends BaseBean implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String changePasswordUrl = (String) value;
		if (StringUtils.isNotEmpty(changePasswordUrl)) {
	    	LdapConfigurationService ldapConfigurationService = (LdapConfigurationService) getBean("ldapConfigurationService");
	    	boolean valid = ldapConfigurationService.isValidURL(new String[]{"http", "https"}, changePasswordUrl);
	    	if (!valid) {
	    		((UIInput)component).setValid(false);
	    		addError(component.getClientId(context), i18n("error_change_password_url_invalid"), null);
	    	}
	    }
	}
}
