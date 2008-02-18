package org.openuss.security.acegi.ldap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.commands.AbstractDomainCommand;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextException;

/**
 * LdapConfigDomainCommand calls method reconfigure of ConfigurableLdapAuthenticationProvider. 
 *  
 * @author Jürgen de Braaf
 * @author Peter Schuh 
 */
public class LdapConfigDomainCommand extends AbstractDomainCommand implements InitializingBean {

	private static final Logger logger = Logger.getLogger(LdapConfigDomainCommand.class);
	
	private ConfigurableLdapAuthenticationProvider configurableLdapAuthenticationProvider;
	
	/**
	 * {@inheritDoc}
	 */
	public final void execute() {
		
		if (StringUtils.equals("RECONFIGURE", getCommandType())) {
			
//			reconfigure LDAP server configuration
			configurableLdapAuthenticationProvider.reconfigure();
		} 
	}

	public ConfigurableLdapAuthenticationProvider getConfigurableLdapAuthenticationProvider() {
		return configurableLdapAuthenticationProvider;
	}

	public void setConfigurableLdapAuthenticationProvider(ConfigurableLdapAuthenticationProvider configurableLdapAuthenticationProvider) {
		this.configurableLdapAuthenticationProvider = configurableLdapAuthenticationProvider;
	}

	public void afterPropertiesSet() throws Exception {
		if (configurableLdapAuthenticationProvider == null)
			throw new ApplicationContextException("LdapConfigDomainCommand must be associated with a ConfigurableLdapAuthenticationProvider bean!");
	}
	
}
