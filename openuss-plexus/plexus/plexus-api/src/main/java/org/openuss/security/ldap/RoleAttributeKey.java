package org.openuss.security.ldap;

import java.util.List;

/**
 * 
 */
public interface RoleAttributeKey extends org.openuss.foundation.DomainObject {

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
	public List<org.openuss.security.ldap.AttributeMapping> getAttributeMappings();

	public void setAttributeMappings(List<org.openuss.security.ldap.AttributeMapping> attributeMappings);

	/**
	 * Constructs new instances of
	 * {@link org.openuss.security.ldap.RoleAttributeKey}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static RoleAttributeKey.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static RoleAttributeKey.Factory getFactory() {
			if (factory == null) {
				factory = (RoleAttributeKey.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.security.ldap.RoleAttributeKey.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract RoleAttributeKey createRoleAttributeKey();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.RoleAttributeKey}.
		 */
		public static org.openuss.security.ldap.RoleAttributeKey newInstance() {
			return getFactory().createRoleAttributeKey();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.RoleAttributeKey}, taking all
		 * required and/or read-only properties as arguments.
		 */
		public static org.openuss.security.ldap.RoleAttributeKey newInstanceByIdentifier(Long id) {
			final org.openuss.security.ldap.RoleAttributeKey entity = getFactory().createRoleAttributeKey();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.RoleAttributeKey}, taking all
		 * required and/or read-only properties as arguments.
		 */
		public static org.openuss.security.ldap.RoleAttributeKey newInstance(String name) {
			final org.openuss.security.ldap.RoleAttributeKey entity = getFactory().createRoleAttributeKey();
			entity.setName(name);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.RoleAttributeKey}, taking all
		 * possible properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.security.ldap.RoleAttributeKey newInstance(String name,
				List<org.openuss.security.ldap.AttributeMapping> attributeMappings) {
			final org.openuss.security.ldap.RoleAttributeKey entity = getFactory().createRoleAttributeKey();
			entity.setName(name);
			entity.setAttributeMappings(attributeMappings);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}