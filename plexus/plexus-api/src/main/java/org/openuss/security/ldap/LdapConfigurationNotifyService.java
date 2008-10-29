package org.openuss.security.ldap;

/**
 * <p>
 * Create command in order to notify other servers about ldap server
 * configuration changing.
 * </p>
 */
public interface LdapConfigurationNotifyService {

	/**
     * 
     */
	public void reconfigure();

}
