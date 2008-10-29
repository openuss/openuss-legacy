package org.openuss.registration;

/**
 * 
 */
public interface ActivationCode extends org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public Long getId();

	public void setId(Long id);

	/**
     * 
     */
	public java.sql.Timestamp getCreatedAt();

	public void setCreatedAt(java.sql.Timestamp createdAt);

	/**
	 * Constructs new instances of
	 * {@link org.openuss.registration.ActivationCode}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static ActivationCode.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static ActivationCode.Factory getFactory() {
			if (factory == null) {
				factory = (ActivationCode.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.registration.ActivationCode.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract ActivationCode createActivationCode();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.registration.ActivationCode}.
		 */
		public static org.openuss.registration.ActivationCode newInstance() {
			return getFactory().createActivationCode();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.registration.ActivationCode}, taking all required
		 * and/or read-only properties as arguments.
		 */
		public static org.openuss.registration.ActivationCode newInstanceByIdentifier(Long id) {
			final org.openuss.registration.ActivationCode entity = getFactory().createActivationCode();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.registration.ActivationCode}, taking all possible
		 * properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.registration.ActivationCode newInstance(java.sql.Timestamp createdAt) {
			final org.openuss.registration.ActivationCode entity = getFactory().createActivationCode();
			entity.setCreatedAt(createdAt);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}