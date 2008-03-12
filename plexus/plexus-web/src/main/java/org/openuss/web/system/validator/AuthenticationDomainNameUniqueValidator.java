package org.openuss.web.system.validator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.web.Constants;

/**
 * Validates uniqueness of AuthenticationDomainNames
 * 
 * @author Peter Schuh
 *
 */
@FacesValidator(value="authenticationDomainNameUniqueValidator")
public class AuthenticationDomainNameUniqueValidator extends BaseBean implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String authenticationDomainName = (String) value;
		AuthenticationDomainInfo dto = new AuthenticationDomainInfo();
	    dto.setName(authenticationDomainName);
	    AuthenticationDomainInfo authenticationDomainInfo = (AuthenticationDomainInfo) getBean(Constants.AUTHENTICATIONDOMAIN_INFO);
	    if (authenticationDomainInfo.getName()!=null && authenticationDomainInfo.getName().equals(authenticationDomainName)) {
	    	//Update of AuthenticationDomain -> Name can stay the same
	    	((UIInput)component).setValid(true);
	    }
	    else if (StringUtils.isNotEmpty(authenticationDomainName)) {
	    		LdapConfigurationService ldapConfigurationService = (LdapConfigurationService) getBean("ldapConfigurationService");
	    		boolean unique = ldapConfigurationService.isValidAuthenticationDomainName(dto);
	    		if (!unique) {
	    			((UIInput)component).setValid(false);
	    			addError(component.getClientId(context), i18n("error_authenticationdomainname_already_exists"), null);
	    		}
	    	 }		
	}
}
