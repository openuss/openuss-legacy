package org.openuss.web.system;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the attributeMapping maintenance.
 * 
 * @author Peter Schuh
 * @author Christian Grelle
 * 
 */

@Bean(name = Constants.LDAP_ATTRIBUTEMAPPING_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapAttributeMappingRegistrationController extends AbstractLdapAttributeMappingPage {

	
	private static final Logger logger = Logger.getLogger(LdapAttributeMappingRegistrationController.class);
	
	public String start() {
		logger.debug("start registration process");
		
		attributeMappingInfo = new AttributeMappingInfo();		
		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMappingInfo);
		
		return Constants.LDAP_ATTRIBUTEMAPPING_REGISTRATION_STEP1_PAGE;
	}
	
	public List<SelectItem> getAllRoleAttributeKeys() {
		List<SelectItem> roleAttributeKeyItems = new ArrayList<SelectItem>();
		List<RoleAttributeKeyInfo> roleAttributeKeys = ldapConfigurationService.getAllRoleAttributeKeys();
		for (RoleAttributeKeyInfo roleAttributeKey : roleAttributeKeys) {
				Long id = roleAttributeKey.getId();
				roleAttributeKeyItems.add(new SelectItem(id, roleAttributeKey.getName()));
		}	
		return roleAttributeKeyItems;
	}

	
	public void processValueChange(ValueChangeEvent valueChangeEvent) {
		ArrayList selectedIds = (ArrayList)valueChangeEvent.getNewValue();
		attributeMappingInfo.setRoleAttributeKeyIds(selectedIds);
	}				

	public String register() {
		ldapConfigurationService.createAttributeMapping(attributeMappingInfo);
		return Constants.LDAP_ATTRIBUTEMAPPING_PAGE;
	}
	
	public String save() {
		ldapConfigurationService.saveAttributeMapping(attributeMappingInfo);
		return Constants.LDAP_ATTRIBUTEMAPPING_PAGE;
	}
}