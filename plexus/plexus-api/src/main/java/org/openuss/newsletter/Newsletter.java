package org.openuss.newsletter;

import java.util.Set;

/**
 * 
 */
public interface Newsletter extends org.openuss.foundation.DomainObject {

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
     * 
     */
	public Long getDomainIdentifier();

	public void setDomainIdentifier(Long domainIdentifier);

	/**
	 * Modified
	 * 
	 */
	public Set<org.openuss.newsletter.Subscriber> getSubscribers();

	public void setSubscribers(Set<org.openuss.newsletter.Subscriber> subscribers);

	/**
	 * Modified
	 * 
	 */
	public Set<org.openuss.newsletter.Mail> getMailings();

	public void setMailings(Set<org.openuss.newsletter.Mail> mailings);

	/**
	 * Constructs new instances of {@link org.openuss.newsletter.Newsletter}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static Newsletter.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static Newsletter.Factory getFactory() {
			if (factory == null) {
				factory = (Newsletter.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.newsletter.Newsletter.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract Newsletter createNewsletter();

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.newsletter.Newsletter}.
		 */
		public static org.openuss.newsletter.Newsletter newInstance() {
			return getFactory().createNewsletter();
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.newsletter.Newsletter}, taking all required and/or
		 * read-only properties as arguments.
		 */
		public static org.openuss.newsletter.Newsletter newInstanceByIdentifier(Long id) {
			final org.openuss.newsletter.Newsletter entity = getFactory().createNewsletter();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.newsletter.Newsletter}, taking all required and/or
		 * read-only properties as arguments.
		 */
		public static org.openuss.newsletter.Newsletter newInstance(String name, Long domainIdentifier) {
			final org.openuss.newsletter.Newsletter entity = getFactory().createNewsletter();
			entity.setName(name);
			entity.setDomainIdentifier(domainIdentifier);
			return entity;
		}

		/**
		 * Constructs a new instance of
		 * {@link org.openuss.newsletter.Newsletter}, taking all possible
		 * properties (except the identifier(s))as arguments.
		 */
		public static org.openuss.newsletter.Newsletter newInstance(String name, Long domainIdentifier,
				Set<org.openuss.newsletter.Subscriber> subscribers, Set<org.openuss.newsletter.Mail> mailings) {
			final org.openuss.newsletter.Newsletter entity = getFactory().createNewsletter();
			entity.setName(name);
			entity.setDomainIdentifier(domainIdentifier);
			entity.setSubscribers(subscribers);
			entity.setMailings(mailings);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}