// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 * 
 * @author Juergen de Braaf
 */
package org.openuss.security.ldap;

import org.openuss.commands.CommandApplicationService;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.search.IndexerApplicationException;

/**
 * @see org.openuss.security.ldap.LdapConfigurationNotifyService
 */
public class LdapConfigurationNotifyServiceImpl extends LdapConfigurationNotifyServiceBase {

	/**
	 * @see org.openuss.security.ldap.LdapConfigurationNotifyService#reconfigure()
	 */
	protected void handleReconfigure() throws java.lang.Exception {
		try {
			// create command
			getCommandService().createEachCommand(new DefaultDomainObject(1L), "ldapConfigDomainCommand", "RECONFIGURE");
		} catch (CommandApplicationService e) {
			throw new IndexerApplicationException(e);
		}
	}

}