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
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.web.Constants;

/**
 * Validates uniqueness of RoleAttributeKeys
 * 
 * @author Peter Schuh
 * 
 */
@FacesValidator(value = "roleAttributeKeyUniqueValidator")
public class RoleAttributeKeyUniqueValidator extends BaseBean implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String roleAttributeKeyName = (String) value;
		RoleAttributeKeyInfo dto = new RoleAttributeKeyInfo();
		dto.setName(roleAttributeKeyName);
		RoleAttributeKeyInfo roleAttributeKeyInfo = (RoleAttributeKeyInfo) getBean(Constants.ROLEATTRIBUTEKEY_INFO);
		if (roleAttributeKeyInfo.getName() != null && roleAttributeKeyInfo.getName().equals(roleAttributeKeyName)) {
			// Update of RoleAttributeKey -> Name can stay the same
			((UIInput) component).setValid(true);
		} else if (StringUtils.isNotEmpty(roleAttributeKeyName)) {
			LdapConfigurationService ldapConfigurationService = (LdapConfigurationService) getBean("ldapConfigurationService");
			boolean unique = ldapConfigurationService.isValidRoleAttributeKey(dto);
			if (!unique) {
				((UIInput) component).setValid(false);
				addError(component.getClientId(context), i18n("error_userdnpattern_already_exists"), null);
			}
		}
	}
}