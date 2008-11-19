package org.openuss.security.ldap;

import java.util.Set;

/**
 * 
 */
public interface UserDnPattern extends org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public Long getId();

	public void setId(Long id);

	/**
     * 
     */
	public String getName();

	public void setName(String name);

	/**
	 * Modified
	 * 
	 */
	public Set<org.openuss.security.ldap.LdapServer> getLdapServers();

	public void setLdapServers(Set<org.openuss.security.ldap.LdapServer> ldapServers);

	/**
	 * Constructs new instances of
	 * {@link org.openuss.security.ldap.UserDnPattern}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static UserDnPattern.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static UserDnPattern.Factory getFactory() {
			if (factory == null) {
				factory = (UserDnPattern.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.security.ldap.UserDnPattern.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract UserDnPattern createUserDnPattern();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.UserDnPattern}.
		 */
		public static org.openuss.security.ldap.UserDnPattern newInstance() {
			return getFactory().createUserDnPattern();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.UserDnPattern}, taking all required
		 * and/or read-only properties as arguments.
		 */
		public static org.openuss.security.ldap.UserDnPattern newInstanceByIdentifier(Long id) {
			final org.openuss.security.ldap.UserDnPattern entity = getFactory().createUserDnPattern();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.UserDnPattern}, taking all required
		 * and/or read-only properties as arguments.
		 */
		public static org.openuss.security.ldap.UserDnPattern newInstance(String name) {
			final org.openuss.security.ldap.UserDnPattern entity = getFactory().createUserDnPattern();
			entity.setName(name);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.UserDnPattern}, taking all possible
		 * properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.security.ldap.UserDnPattern newInstance(String name,
				Set<org.openuss.security.ldap.LdapServer> ldapServers) {
			final org.openuss.security.ldap.UserDnPattern entity = getFactory().createUserDnPattern();
			entity.setName(name);
			entity.setLdapServers(ldapServers);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}