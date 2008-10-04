package org.openuss.web.system.ldap;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the attributeMapping maintenance.
 * 
 * @author Peter Schuh
 * @author Christian Grelle
 * @author Ingo Dueppe
 * 
 */

@Bean(name = Constants.LDAP_ATTRIBUTEMAPPING_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapAttributeMappingRegistrationController extends AbstractLdapPage {

	@Property(value = "#{attributeMappingInfo}")
	private AttributeMappingInfo attributeMappingInfo;

	/**
	 * Refreshing attributeMapping VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing attributeMapping session object");
		if (attributeMappingInfo != null) {
			if (attributeMappingInfo.getId() != null) {
				attributeMappingInfo = ldapConfigurationService.getAttributeMappingById(attributeMappingInfo.getId());
			} else {
				attributeMappingInfo = (AttributeMappingInfo) getSessionBean(Constants.ATTRIBUTEMAPPING_INFO);				
			}
		}
		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMappingInfo);
	}

	@Prerender
	public void prerender() {
		logger.debug("prerender - refreshing attributeMapping session object");
		refreshAttributeMapping();
		if (attributeMappingInfo == null || attributeMappingInfo.getId() == null) {
			addError(i18n("message_ldap_attributemapping_no_attributemapping_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshAttributeMapping() {
		if (attributeMappingInfo != null) {
			if (attributeMappingInfo.getId() != null) {
				attributeMappingInfo = ldapConfigurationService.getAttributeMappingById(attributeMappingInfo.getId());
				setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMappingInfo);
			}
		}
	}

	public String start() {
		logger.debug("start registration process");
		attributeMappingInfo = new AttributeMappingInfo();		
		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMappingInfo);
		return Constants.LDAP_ATTRIBUTEMAPPING_EDIT_PAGE;
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
		List<?> selectedIds = (List<?>)valueChangeEvent.getNewValue();
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
	
	public AttributeMappingInfo getAttributeMappingInfo() {
		return attributeMappingInfo;
	}

	public void setAttributeMappingInfo(AttributeMappingInfo attributeMappingInfo) {
		this.attributeMappingInfo = attributeMappingInfo;
	}	
	
}