package org.openuss.web.lecture.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.User;
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.UserDnPatternInfo;
import org.openuss.web.Constants;

@FacesValidator(value="userDnPatternUniqueValidator")
public class UserDnPatternUniqueValidator extends BaseBean implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String userDnPatternName = (String) value;
		UserDnPatternInfo container = new UserDnPatternInfo();
	    container.setName(userDnPatternName);
	    UserDnPatternInfo userDnPatternInfo = (UserDnPatternInfo) getBean(Constants.USERDNPATTERN_INFO);
	    if (userDnPatternInfo.getName()!=null && userDnPatternInfo.getName().equals(userDnPatternName)) {
	    	//Update of AuthenticationDomain -> Name can stay the same
	    	((UIInput)component).setValid(true);
	    }
	    else if (StringUtils.isNotEmpty(userDnPatternName)) {
	    		LdapConfigurationService ldapConfigurationService = (LdapConfigurationService) getBean("ldapConfigurationService");
	    		boolean unique = ldapConfigurationService.isValidUserDnPattern(userDnPatternInfo);
	    		if (!unique) {
	    			((UIInput)component).setValid(false);
	    			addError(component.getClientId(context), i18n("error_userdnpattern_already_exists"), null);
	    		}
	    	 }		
	}
}
