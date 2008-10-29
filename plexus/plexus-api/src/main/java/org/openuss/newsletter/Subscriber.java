package org.openuss.newsletter;

/**
 * 
 */
public interface Subscriber {

	public org.openuss.newsletter.SubscriberPK getSubscriberPk();

	public void setSubscriberPk(org.openuss.newsletter.SubscriberPK subscriberPk);

	/**
     * 
     */
	public boolean isBlocked();

	public void setBlocked(boolean blocked);

	/**
	 * Constructs new instances of {@link org.openuss.newsletter.Subscriber}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static Subscriber.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static Subscriber.Factory getFactory() {
			if (factory == null) {
				factory = (Subscriber.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.newsletter.Subscriber.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract Subscriber createSubscriber();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.newsletter.Subscriber}.
		 */
		public static org.openuss.newsletter.Subscriber newInstance() {
			return getFactory().createSubscriber();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.newsletter.Subscriber}, taking all possible
		 * properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.newsletter.Subscriber newInstance(boolean blocked) {
			final org.openuss.newsletter.Subscriber entity = getFactory().createSubscriber();
			entity.setBlocked(blocked);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}