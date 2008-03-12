package org.openuss.web.system;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.UserDnPatternInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * Abstract class which can be used to derive backing beans for userDnPatterns
 * overview views
 * 
 * 
 */
public abstract class AbstractLdapUserDnPatternsOverviewPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractLdapUserDnPatternsOverviewPage.class);


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
}