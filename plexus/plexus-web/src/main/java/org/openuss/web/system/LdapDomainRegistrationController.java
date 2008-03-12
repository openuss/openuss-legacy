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
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the ldap_domain maintenance. 
 * 
 * @author Peter Schuh
 * @author Christian Grelle
 * 
 */

@Bean(name = Constants.LDAP_DOMAIN_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapDomainRegistrationController extends AbstractLdapDomainPage {

	
	private static final Logger logger = Logger.getLogger(LdapDomainRegistrationController.class);
	
	public String start() {
		logger.debug("start registration process");
		
		authenticationDomainInfo = new AuthenticationDomainInfo();
		setSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO, authenticationDomainInfo);
		
		return Constants.LDAP_DOMAIN_REGISTRATION_STEP1_PAGE;
	}


	public List<SelectItem> getAllAttributeMappings() {
		List<SelectItem> attributeMappingItems = new ArrayList<SelectItem>();
		List<AttributeMappingInfo> attributeMappings = ldapConfigurationService.getAllAttributeMappings();
		for (AttributeMappingInfo attributeMapping : attributeMappings) {
				attributeMappingItems.add(new SelectItem(attributeMapping.getId(), attributeMapping.getMappingName()));
		}
		return attributeMappingItems;
	}
	
	public void processValueChange(ValueChangeEvent valueChangeEvent) {
		Long attributeMappingId = (Long) valueChangeEvent.getNewValue();
		authenticationDomainInfo.setAttributeMappingId(attributeMappingId);
	}				
	
	public String register() {
		ldapConfigurationService.createDomain(authenticationDomainInfo);
		return Constants.LDAP_DOMAIN_PAGE;
	}
	
	public String save() {
		ldapConfigurationService.saveDomain(authenticationDomainInfo);
		return Constants.LDAP_DOMAIN_PAGE;
	}	

}
