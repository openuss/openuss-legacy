package org.openuss.security;

import java.util.List;

/**
 * 
 */
public interface Membership extends org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public Long getId();

	public void setId(Long id);

	/**
	 * Modified
	 * 
	 */
	public List<org.openuss.security.Group> getGroups();

	public void setGroups(List<org.openuss.security.Group> groups);

	/**
	 * Modified
	 * 
	 */
	public List<org.openuss.security.User> getMembers();

	public void setMembers(List<org.openuss.security.User> members);

	/**
	 * Modified
	 * 
	 */
	public List<org.openuss.security.User> getAspirants();

	public void setAspirants(List<org.openuss.security.User> aspirants);

	/**
	 * Constructs new instances of {@link org.openuss.security.Membership}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static Membership.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static Membership.Factory getFactory() {
			if (factory == null) {
				factory = (Membership.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.security.Membership.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract Membership createMembership();

		/**
		 * Constructs a new instance of {@link org.openuss.security.Membership}.
		 */
		public static org.openuss.security.Membership newInstance() {
			return getFactory().createMembership();
		}

		/**
		 * Constructs a new instance of {@link org.openuss.security.Membership},
		 * taking all required and/or read-only properties as arguments.
		 */
		public static org.openuss.security.Membership newInstanceByIdentifier(Long id) {
			final org.openuss.security.Membership entity = getFactory().createMembership();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of {@link org.openuss.security.Membership},
		 * taking all possible properties (except the identifier(s))as
		 * arguments.
		 */
		public static org.openuss.security.Membership newInstance(List<org.openuss.security.Group> groups,
				List<org.openuss.security.User> members, List<org.openuss.security.User> aspirants) {
			final org.openuss.security.Membership entity = getFactory().createMembership();
			entity.setGroups(groups);
			entity.setMembers(members);
			entity.setAspirants(aspirants);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}