package org.openuss.security.ldap;

import java.util.List;
import java.util.Set;

/**
 * 
 */
public interface AttributeMapping extends org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public Long getId();

	public void setId(Long id);

	/**
     * 
     */
	public String getMappingName();

	public void setMappingName(String mappingName);

	/**
     * 
     */
	public String getUsernameKey();

	public void setUsernameKey(String usernameKey);

	/**
     * 
     */
	public String getFirstNameKey();

	public void setFirstNameKey(String firstNameKey);

	/**
     * 
     */
	public String getLastNameKey();

	public void setLastNameKey(String lastNameKey);

	/**
     * 
     */
	public String getEmailKey();

	public void setEmailKey(String emailKey);

	/**
     * 
     */
	public String getGroupRoleAttributeKey();

	public void setGroupRoleAttributeKey(String groupRoleAttributeKey);

	/**
	 * Modified
	 * 
	 */
	public List<org.openuss.security.ldap.RoleAttributeKey> getRoleAttributeKeys();

	public void setRoleAttributeKeys(List<org.openuss.security.ldap.RoleAttributeKey> roleAttributeKeys);

	/**
	 * Modified
	 * 
	 */
	public Set<org.openuss.security.ldap.AuthenticationDomain> getAuthenticationDomains();

	public void setAuthenticationDomains(Set<org.openuss.security.ldap.AuthenticationDomain> authenticationDomains);

	/**
	 * Constructs new instances of
	 * {@link org.openuss.security.ldap.AttributeMapping}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static AttributeMapping.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static AttributeMapping.Factory getFactory() {
			if (factory == null) {
				factory = (AttributeMapping.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.security.ldap.AttributeMapping.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract AttributeMapping createAttributeMapping();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.AttributeMapping}.
		 */
		public static org.openuss.security.ldap.AttributeMapping newInstance() {
			return getFactory().createAttributeMapping();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.AttributeMapping}, taking all
		 * required and/or read-only properties as arguments.
		 */
		public static org.openuss.security.ldap.AttributeMapping newInstanceByIdentifier(Long id) {
			final org.openuss.security.ldap.AttributeMapping entity = getFactory().createAttributeMapping();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.AttributeMapping}, taking all
		 * required and/or read-only properties as arguments.
		 */
		public static org.openuss.security.ldap.AttributeMapping newInstance(String mappingName, String usernameKey,
				String firstNameKey, String lastNameKey, String emailKey, String groupRoleAttributeKey,
				List<org.openuss.security.ldap.RoleAttributeKey> roleAttributeKeys) {
			final org.openuss.security.ldap.AttributeMapping entity = getFactory().createAttributeMapping();
			entity.setMappingName(mappingName);
			entity.setUsernameKey(usernameKey);
			entity.setFirstNameKey(firstNameKey);
			entity.setLastNameKey(lastNameKey);
			entity.setEmailKey(emailKey);
			entity.setGroupRoleAttributeKey(groupRoleAttributeKey);
			entity.setRoleAttributeKeys(roleAttributeKeys);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.ldap.AttributeMapping}, taking all
		 * possible properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.security.ldap.AttributeMapping newInstance(String mappingName, String usernameKey,
				String firstNameKey, String lastNameKey, String emailKey, String groupRoleAttributeKey,
				List<org.openuss.security.ldap.RoleAttributeKey> roleAttributeKeys,
				Set<org.openuss.security.ldap.AuthenticationDomain> authenticationDomains) {
			final org.openuss.security.ldap.AttributeMapping entity = getFactory().createAttributeMapping();
			entity.setMappingName(mappingName);
			entity.setUsernameKey(usernameKey);
			entity.setFirstNameKey(firstNameKey);
			entity.setLastNameKey(lastNameKey);
			entity.setEmailKey(emailKey);
			entity.setGroupRoleAttributeKey(groupRoleAttributeKey);
			entity.setRoleAttributeKeys(roleAttributeKeys);
			entity.setAuthenticationDomains(authenticationDomains);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}