package org.openuss.web.system.ldap;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.web.BasePage;

/**
 * Abstract page class for LDAP configuration pages
 * 
 * @author Ingo Dueppe
 *
 */
public class AbstractLdapPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractLdapPage.class);
	
	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;

	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}


}
