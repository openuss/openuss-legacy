package org.openuss.web.system.ldap;

import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the roleAttributeKey maintenance.
 * 
 * @author Peter Schuh
 * @author Christian Grelle
 * @author Ingo Dueppe
 * 
 */

@Bean(name = Constants.LDAP_ROLEATTRIBUTEKEY_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapRoleAttributeKeyRegistrationController extends AbstractLdapPage {

	@Property(value = "#{roleAttributeKeyInfo}")
	private RoleAttributeKeyInfo roleAttributeKeyInfo;

	/**
	 * Refreshing roleAttributeKey VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing roleAttributeKey session object");
		if (roleAttributeKeyInfo != null) {
			if (roleAttributeKeyInfo.getId() != null) {
				roleAttributeKeyInfo = ldapConfigurationService.getRoleAttributeKeyById(roleAttributeKeyInfo.getId());
			} else {
				roleAttributeKeyInfo = (RoleAttributeKeyInfo) getSessionBean(Constants.ROLEATTRIBUTEKEY_INFO);
			}
		}

		setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, roleAttributeKeyInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing roleAttributeKey session object");
		refreshRoleAttributeKeySet();
		if (roleAttributeKeyInfo == null || roleAttributeKeyInfo.getId() == null) {			
			addError(i18n("message_ldap_roleattributekey_no_roleattributekey_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshRoleAttributeKeySet() {
		if (roleAttributeKeyInfo != null) {
			if (roleAttributeKeyInfo.getId() != null) {
				roleAttributeKeyInfo = ldapConfigurationService.getRoleAttributeKeyById(roleAttributeKeyInfo.getId());
				setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, roleAttributeKeyInfo);
			}
		}
	}

	public RoleAttributeKeyInfo getRoleAttributeKeyInfo() {
		return roleAttributeKeyInfo;
	}

	public void setRoleAttributeKeyInfo(RoleAttributeKeyInfo roleAttributeKeyInfo) {
		this.roleAttributeKeyInfo = roleAttributeKeyInfo;
	}
	
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