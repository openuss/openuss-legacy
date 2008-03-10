package org.openuss.web.lecture.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.security.ldap.UserDnPatternInfo;

@FacesValidator(value="roleAttributeKeyUniqueValidator")
public class RoleAttributeKeyUniqueValidator extends BaseBean implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String roleAttributeKey = (String) value;
		RoleAttributeKeyInfo roleAttributeKeyInfo = new RoleAttributeKeyInfo();
	    roleAttributeKeyInfo.setName(roleAttributeKey);
		if (StringUtils.isNotEmpty(roleAttributeKey)) {
			LdapConfigurationService ldapConfigurationService = (LdapConfigurationService) getBean("ldapConfigurationService");
			boolean unique = ldapConfigurationService.isValidRoleAttributeKey(roleAttributeKeyInfo);
			if (!unique) {
         		((UIInput)component).setValid(false);
				addError(component.getClientId(context), i18n("error_userdnpattern_already_exists"), null);
			}
		}
	}
}