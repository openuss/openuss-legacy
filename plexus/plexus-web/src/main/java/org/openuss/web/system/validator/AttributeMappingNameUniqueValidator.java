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
import org.openuss.web.Constants;

/**
 * Validates uniqueness of attributeMappingNames
 * 
 * @author Peter Schuh
 *
 */
@FacesValidator(value="attributeMappingNameUniqueValidator")
public class AttributeMappingNameUniqueValidator extends BaseBean implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String attributeMappingName = (String) value;
		AttributeMappingInfo dto = new AttributeMappingInfo();
	    dto.setMappingName(attributeMappingName);
	    AttributeMappingInfo attributeMappingInfo = (AttributeMappingInfo) getBean(Constants.ATTRIBUTEMAPPING_INFO);
	    if (attributeMappingInfo.getMappingName()!=null && attributeMappingInfo.getMappingName().equals(attributeMappingName)) {
	    	//Update of AttributeMapping -> Name can stay the same
	    	((UIInput)component).setValid(true);
	    }
	    else if (StringUtils.isNotEmpty(attributeMappingName)) {
	    		LdapConfigurationService ldapConfigurationService = (LdapConfigurationService) getBean("ldapConfigurationService");
	    		boolean unique = ldapConfigurationService.isValidAttributeMappingName(dto);
	    		if (!unique) {
	    			((UIInput)component).setValid(false);
	    			addError(component.getClientId(context), i18n("error_attributemappingname_already_exists"), null);
	    		}
	    	 }		
	}
}