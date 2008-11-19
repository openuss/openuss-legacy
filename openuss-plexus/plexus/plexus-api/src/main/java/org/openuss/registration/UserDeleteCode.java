package org.openuss.registration;

/**
 * 
 */
public interface UserDeleteCode extends org.openuss.registration.ActivationCode, org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public String getCode();

	public void setCode(String code);

	/**
	 * Modified
	 * 
	 */
	public org.openuss.security.User getUser();

	public void setUser(org.openuss.security.User user);

	/**
	 * Constructs new instances of
	 * {@link org.openuss.registration.UserDeleteCode}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static UserDeleteCode.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static UserDeleteCode.Factory getFactory() {
			if (factory == null) {
				factory = (UserDeleteCode.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.registration.UserDeleteCode.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract UserDeleteCode createUserDeleteCode();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.registration.UserDeleteCode}.
		 */
		public static org.openuss.registration.UserDeleteCode newInstance() {
			return getFactory().createUserDeleteCode();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.registration.UserDeleteCode}, taking all required
		 * and/or read-only properties as arguments.
		 */
		public static org.openuss.registration.UserDeleteCode newInstanceByIdentifier(Long id) {
			final org.openuss.registration.UserDeleteCode entity = getFactory().createUserDeleteCode();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.registration.UserDeleteCode}, taking all possible
		 * properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.registration.UserDeleteCode newInstance(String code, java.sql.Timestamp createdAt,
				org.openuss.security.User user) {
			final org.openuss.registration.UserDeleteCode entity = getFactory().createUserDeleteCode();
			entity.setCode(code);
			entity.setCreatedAt(createdAt);
			entity.setUser(user);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}