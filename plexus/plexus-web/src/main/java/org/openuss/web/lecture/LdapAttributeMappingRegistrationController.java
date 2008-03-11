package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

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
	
	private List<String> allInitiallySelectableRoleAttributeKeyIds = new Vector<String>();	
	private List<String> initiallySelectedRoleAttributeKeyIds = new Vector<String>();
	
	private List<Long> finallySelectedRoleAttributeKeyIds = new Vector<Long>();

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
				String idAsString = String.valueOf(roleAttributeKey.getId());
				roleAttributeKeyItems.add(new SelectItem(idAsString, roleAttributeKey.getName()));
				allInitiallySelectableRoleAttributeKeyIds.add(idAsString);
			}	
		return roleAttributeKeyItems;
	}


	public void processValueChange(ValueChangeEvent valueChangeEvent) {
	      ArrayList selectedIds = (ArrayList)valueChangeEvent.getNewValue();
	      for (Iterator iterator = selectedIds.iterator(); iterator.hasNext();) {
			String idAsString = (String) iterator.next();
			Long id = Long.valueOf(idAsString);
			finallySelectedRoleAttributeKeyIds.add(id);
			System.out.println(id);
		    logger.info(id);
	      }
	}				

	public String register() {
		attributeMappingInfo.setRoleAttributeKeyIds(finallySelectedRoleAttributeKeyIds);
		ldapConfigurationService.createAttributeMapping(attributeMappingInfo);
		return Constants.LDAP_ATTRIBUTEMAPPING_PAGE;
	}
	
	public String save() {
		attributeMappingInfo.setRoleAttributeKeyIds(finallySelectedRoleAttributeKeyIds);
		ldapConfigurationService.saveAttributeMapping(attributeMappingInfo);
		return Constants.LDAP_ATTRIBUTEMAPPING_PAGE;
	}

	public List<Long> getFinallySelectedRoleAttributeKeyIds() {
		return finallySelectedRoleAttributeKeyIds;
	}

	public void setFinallySelectedRoleAttributeKeyIds(
			List<Long> finallySelectedRoleAttributeKeyIds) {
		this.finallySelectedRoleAttributeKeyIds = finallySelectedRoleAttributeKeyIds;
	}

	public List<String> getAllInitiallySelectableRoleAttributeKeyIds() {
		return allInitiallySelectableRoleAttributeKeyIds;
	}

	public void setAllInitiallySelectableRoleAttributeKeyIds(
			List<String> allInitiallySelectableRoleAttributeKeyIds) {
		this.allInitiallySelectableRoleAttributeKeyIds = allInitiallySelectableRoleAttributeKeyIds;
	}

	public List<String> getInitiallySelectedRoleAttributeKeyIds() {
		return initiallySelectedRoleAttributeKeyIds;
	}

	public void setInitiallySelectedRoleAttributeKeyIds(
			List<String> initiallySelectedRoleAttributeKeyIds) {
		this.initiallySelectedRoleAttributeKeyIds = initiallySelectedRoleAttributeKeyIds;
	}
}
