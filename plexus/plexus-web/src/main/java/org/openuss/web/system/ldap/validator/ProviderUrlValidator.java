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
 * Validates ProviderUrl.
 * 
 * @author Peter Schuh
 *
 */
@FacesValidator(value="providerUrlValidator")
public class ProviderUrlValidator extends BaseBean implements Validator {
	

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		boolean valid = false;
		
		String providerUrl = (String) value;
		
	    if (StringUtils.isNotEmpty(providerUrl)) {
//	    	localhost for testing is allowed
	    	if(providerUrl.contains("localhost")) {
	    		valid = true;
	    	} else {
	    		LdapConfigurationService ldapConfigurationService = (LdapConfigurationService) getBean("ldapConfigurationService");
		    	valid = ldapConfigurationService.isValidURL(new String[]{"ldap", "ldaps"}, providerUrl);	    		
	    	}
	    	if (!valid) {
	    			((UIInput)component).setValid(false);
	    			addError(component.getClientId(context), i18n("error_provider_url_invalid"), null);
	    	}
	    }
	}
}