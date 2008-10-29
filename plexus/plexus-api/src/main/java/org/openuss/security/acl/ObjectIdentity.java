package org.openuss.security.acl;

import java.util.Set;

/**
 * 
 */
public interface ObjectIdentity extends org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public Long getId();

	public void setId(Long id);

	/**
	 * Modified
	 * 
	 */
	public org.openuss.security.acl.ObjectIdentity getParent();

	public void setParent(org.openuss.security.acl.ObjectIdentity parent);

	/**
	 * Modified
	 * 
	 */
	public Set<org.openuss.security.acl.ObjectIdentity> getChild();

	public void setChild(Set<org.openuss.security.acl.ObjectIdentity> child);

	/**
	 * Modified
	 * 
	 */
	public Set<org.openuss.security.acl.Permission> getPermissions();

	public void setPermissions(Set<org.openuss.security.acl.Permission> permissions);

	/**
     * 
     */
	public abstract void addPermission(org.openuss.security.acl.Permission permission);

	/**
     * 
     */
	public abstract void removePermission(org.openuss.security.acl.Permission permission);

	/**
	 * Constructs new instances of
	 * {@link org.openuss.security.acl.ObjectIdentity}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static ObjectIdentity.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static ObjectIdentity.Factory getFactory() {
			if (factory == null) {
				factory = (ObjectIdentity.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.security.acl.ObjectIdentity.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract ObjectIdentity createObjectIdentity();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.acl.ObjectIdentity}.
		 */
		public static org.openuss.security.acl.ObjectIdentity newInstance() {
			return getFactory().createObjectIdentity();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.acl.ObjectIdentity}, taking all required
		 * and/or read-only properties as arguments.
		 */
		public static org.openuss.security.acl.ObjectIdentity newInstanceByIdentifier(Long id) {
			final org.openuss.security.acl.ObjectIdentity entity = getFactory().createObjectIdentity();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.acl.ObjectIdentity}, taking all possible
		 * properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.security.acl.ObjectIdentity newInstance(
				org.openuss.security.acl.ObjectIdentity parent, Set<org.openuss.security.acl.ObjectIdentity> child,
				Set<org.openuss.security.acl.Permission> permissions) {
			final org.openuss.security.acl.ObjectIdentity entity = getFactory().createObjectIdentity();
			entity.setParent(parent);
			entity.setChild(child);
			entity.setPermissions(permissions);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}