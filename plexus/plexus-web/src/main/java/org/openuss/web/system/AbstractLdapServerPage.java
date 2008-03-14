package org.openuss.web.system;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.LdapServerInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract AttribtuteMapping Page
 * @author Juergen de Braaf
 * @author Christian Grelle
 * @author Peter Schuh
 */
public abstract class AbstractLdapServerPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLdapServerPage.class);
	
	@Property(value = "#{ldapServerInfo}")
	protected LdapServerInfo ldapServerInfo;

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;


	/**
	 * Refreshing attributeMapping VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing ldapServerInfo session object");
		if (ldapServerInfo != null) {
			if (ldapServerInfo.getId() != null) {
				ldapServerInfo = ldapConfigurationService.getLdapServerById(ldapServerInfo.getId());
			} else {
				ldapServerInfo = (LdapServerInfo) getSessionBean(Constants.LDAPSERVER_INFO);				
			}
		}
		setSessionBean(Constants.LDAPSERVER_INFO, ldapServerInfo);
	}

	@Prerender
	public void prerender() {
		logger.debug("prerender - refreshing ldapServerInfo session object");
		refreshLdapServerInfo();
		if (ldapServerInfo == null || ldapServerInfo.getId() == null) {
			addError(i18n("message_ldap_ldapserver_no_ldapserver_selected"));
			redirect(Constants.LDAP_SERVER_PAGE);
		}
	}

	private void refreshLdapServerInfo() {
		if (ldapServerInfo != null) {
			if (ldapServerInfo.getId() != null) {
				ldapServerInfo = ldapConfigurationService.getLdapServerById(ldapServerInfo.getId());
				setSessionBean(Constants.LDAPSERVER_INFO, ldapServerInfo);
			}
		}
	}

	public LdapServerInfo getLdapServerInfoInfo() {
		return ldapServerInfo;
	}

	public void setLdapServerInfo(LdapServerInfo ldapServerInfo) {
		this.ldapServerInfo = ldapServerInfo;
	}
	
	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(
			LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}
	
}