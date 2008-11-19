package org.openuss.security.acl;

/**
 * <p>
 * 
 * @{inheritDoc </p>
 */
public interface Permission {

	public org.openuss.security.acl.PermissionPK getPermissionPk();

	public void setPermissionPk(org.openuss.security.acl.PermissionPK permissionPk);

	/**
	 * <p>
	 * 
	 * @{inheritDoc </p>
	 */
	public Integer getMask();

	public void setMask(Integer mask);

	/**
	 * Constructs new instances of {@link org.openuss.security.acl.Permission}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static Permission.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static Permission.Factory getFactory() {
			if (factory == null) {
				factory = (Permission.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.security.acl.Permission.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract Permission createPermission();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.acl.Permission}.
		 */
		public static org.openuss.security.acl.Permission newInstance() {
			return getFactory().createPermission();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.security.acl.Permission}, taking all possible
		 * properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.security.acl.Permission newInstance(Integer mask) {
			final org.openuss.security.acl.Permission entity = getFactory().createPermission();
			entity.setMask(mask);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}