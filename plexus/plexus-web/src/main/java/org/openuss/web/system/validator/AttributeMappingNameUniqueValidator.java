package org.openuss.web.system.validator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.RoleAttributeKeyInfo;

/**
 * Validates uniqueness of MappingNames
 * 
 * @author Peter Schuh
 *
 */
@FacesValidator(value="attributeMappingNameUniqueValidator")
public class AttributeMappingNameUniqueValidator extends BaseBean implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String attributeMappingName = (String) value;
		AttributeMappingInfo attributeMappingInfo = new AttributeMappingInfo();
	    attributeMappingInfo.setMappingName(attributeMappingName);
		if (StringUtils.isNotEmpty(attributeMappingName)) {
			LdapConfigurationService ldapConfigurationService = (LdapConfigurationService) getBean("ldapConfigurationService");
			boolean unique = false;
			if (!unique) {
         		((UIInput)component).setValid(false);
				addError(component.getClientId(context), i18n("error_userdnpattern_already_exists"), null);
			}
		}
	}
}