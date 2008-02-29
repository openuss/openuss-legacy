package org.openuss.aop;

import org.apache.log4j.Logger;
import org.openuss.security.ldap.LdapConfigurationNotifyService;
import org.openuss.security.ldap.LdapConfigurationNotifyServiceException;

/** 
 * Aspect in order to reconfigure ldap server.
 * 
 * @author Juergen de Braaf
 */
public class LdapConfigurationAspect {

	private static final Logger logger = Logger.getLogger(LdapConfigurationAspect.class);

	private LdapConfigurationNotifyService ldapConfigurationNotifyService;
	

	/**
	 * Reconfigure ldap server by sending a command. 
	 */
	public void reconfigure() {
		logger.info("Starting method reconfigure");
		try {
			ldapConfigurationNotifyService.reconfigure();
			
		} catch (LdapConfigurationNotifyServiceException e) {
			logger.error(e);
		}
	}


	public LdapConfigurationNotifyService getLdapConfigurationNotifyService() {
		return ldapConfigurationNotifyService;
	}


	public void setLdapConfigurationNotifyService(
			LdapConfigurationNotifyService ldapConfigurationNotifyService) {
		this.ldapConfigurationNotifyService = ldapConfigurationNotifyService;
	}

	
}
