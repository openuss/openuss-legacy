package org.openuss.security.ldap;

import java.util.List;

/**
 * 
 */
public interface LdapServer extends org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public Long getId();

	public void setId(Long id);

	/**
     * 
     */
	public String getProviderUrl();

	public void setProviderUrl(String providerUrl);

	/**
     * 
     */
	public Integer getPort();

	public void setPort(Integer port);

	/**
     * 
     */
	public String getRootDn();

	public void setRootDn(String rootDn);

	/**
     * 
     */
	public String getAuthenticationType();

	public void setAuthenticationType(String authenticationType);

	/**
     * 
     */
	public String getManagerDn();

	public void setManagerDn(String managerDn);

	/**
     * 
     */
	public String getManagerPassword();

	public void setManagerPassword(String managerPassword);

	/**
     * 
     */
	public Boolean getUseConnectionPool();

	public void setUseConnectionPool(Boolean useConnectionPool);

	/**
     * 
     */
	public Boolean getUseLdapContext();

	public void setUseLdapContext(Boolean useLdapContext);

	/**
     * 
     */
	public String getDescription();

	public void setDescription(String description);

	/**
     * 
     */
	public org.openuss.security.ldap.LdapServerType getLdapServerType();

	public void setLdapServerType(org.openuss.security.ldap.LdapServerType ldapServerType);

	/**
     * 
     */
	public boolean isEnabled();

	public void setEnabled(boolean enabled);

	/**
	 * Modified
	 * 
	 */
	public List<org.openuss.security.ldap.UserDnPattern> getUserDnPatterns();

	public void setUserDnPatterns(List<org.openuss.security.ldap.UserDnPattern> userDnPatterns);

	/**
	 * Modified
	 * 
	 */
	public org.openuss.security.ldap.AuthenticationDomain getAuthenticationDomain();

	public void setAuthenticationDomain(org.openuss.security.ldap.AuthenticationDomain authenticationDomain);

	/**
	 * Constructs new instances of {@link org.openuss.security.ldap.LdapServer}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static LdapServer.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static LdapServer.Factory getFactory() {
			if (factory == null) {
				factory = (LdapServer.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.security.ldap.LdapServer.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract LdapServer createLdapServer();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.LdapServer}.
		 */
		public static org.openuss.security.ldap.LdapServer newInstance() {
			return getFactory().createLdapServer();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.LdapServer}, taking all required
		 * and/or read-only properties as arguments.
		 */
		public static org.openuss.security.ldap.LdapServer newInstanceByIdentifier(Long id) {
			final org.openuss.security.ldap.LdapServer entity = getFactory().createLdapServer();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.LdapServer}, taking all required
		 * and/or read-only properties as arguments.
		 */
		public static org.openuss.security.ldap.LdapServer newInstance(String providerUrl, Integer port, String rootDn,
				String authenticationType, Boolean useConnectionPool, Boolean useLdapContext, String description,
				org.openuss.security.ldap.LdapServerType ldapServerType, boolean enabled,
				List<org.openuss.security.ldap.UserDnPattern> userDnPatterns,
				org.openuss.security.ldap.AuthenticationDomain authenticationDomain) {
			final org.openuss.security.ldap.LdapServer entity = getFactory().createLdapServer();
			entity.setProviderUrl(providerUrl);
			entity.setPort(port);
			entity.setRootDn(rootDn);
			entity.setAuthenticationType(authenticationType);
			entity.setUseConnectionPool(useConnectionPool);
			entity.setUseLdapContext(useLdapContext);
			entity.setDescription(description);
			entity.setLdapServerType(ldapServerType);
			entity.setEnabled(enabled);
			entity.setUserDnPatterns(userDnPatterns);
			entity.setAuthenticationDomain(authenticationDomain);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.LdapServer}, taking all possible
		 * properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.security.ldap.LdapServer newInstance(String providerUrl, Integer port, String rootDn,
				String authenticationType, String managerDn, String managerPassword, Boolean useConnectionPool,
				Boolean useLdapContext, String description, org.openuss.security.ldap.LdapServerType ldapServerType,
				boolean enabled, List<org.openuss.security.ldap.UserDnPattern> userDnPatterns,
				org.openuss.security.ldap.AuthenticationDomain authenticationDomain) {
			final org.openuss.security.ldap.LdapServer entity = getFactory().createLdapServer();
			entity.setProviderUrl(providerUrl);
			entity.setPort(port);
			entity.setRootDn(rootDn);
			entity.setAuthenticationType(authenticationType);
			entity.setManagerDn(managerDn);
			entity.setManagerPassword(managerPassword);
			entity.setUseConnectionPool(useConnectionPool);
			entity.setUseLdapContext(useLdapContext);
			entity.setDescription(description);
			entity.setLdapServerType(ldapServerType);
			entity.setEnabled(enabled);
			entity.setUserDnPatterns(userDnPatterns);
			entity.setAuthenticationDomain(authenticationDomain);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}