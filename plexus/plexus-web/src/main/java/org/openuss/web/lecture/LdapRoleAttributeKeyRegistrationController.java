package org.openuss.web.lecture;

import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the roleAttributeKey maintenance.
 * 
 * @author Peter Schuh
 * @author Christian Grelle
 * 
 */

@Bean(name = Constants.LDAP_ROLEATTRIBUTEKEY_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapRoleAttributeKeyRegistrationController extends AbstractLdapRoleAttributeKeyPage {

	
	private static final Logger logger = Logger.getLogger(LdapRoleAttributeKeyRegistrationController.class);

	public String start() {
		logger.debug("start registration process");
		
		roleAttributeKeyInfo = new RoleAttributeKeyInfo();
		setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, roleAttributeKeyInfo);
		
		return Constants.LDAP_ROLEATTRIBUTEKEY_REGISTRATION_STEP1_PAGE;
	}

	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getAllRoleAttributeKeys() {
		return ldapConfigurationService.getAllRoleAttributeKeys();
	}

	public String register() {
		ldapConfigurationService.createRoleAttributeKey(roleAttributeKeyInfo);
		return Constants.LDAP_ROLEATTRIBUTEKEY_PAGE;
	}
	
	public String save() {
		ldapConfigurationService.saveRoleAttributeKey(roleAttributeKeyInfo);
		return Constants.LDAP_ROLEATTRIBUTEKEY_PAGE;
	}
	
}