package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * Abstract class which can be used to derive backing beans for attributeMapping
 * overview views
 * 
 * @author Christian Grelle
 * @author Peter Schuh
 * 
 */
public abstract class AbstractLdapAttributeMappingsOverviewPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractLdapRoleAttributeKeysOverviewPage.class);	

	@Property(value = "#{"+Constants.LDAP_ATTRIBUTEMAPPING_REGISTRATION_CONTROLLER+"}")
	protected LdapAttributeMappingRegistrationController attributeMappingRegistrationController;
	
	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;

	@Prerender
	public void prerender() throws Exception {
	}
	
	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}

	public LdapAttributeMappingRegistrationController getAttributeMappingRegistrationController() {
		return attributeMappingRegistrationController;
	}

	public void setAttributeMappingRegistrationController(
			LdapAttributeMappingRegistrationController attributeMappingRegistrationController) {
		this.attributeMappingRegistrationController = attributeMappingRegistrationController;
	}

}