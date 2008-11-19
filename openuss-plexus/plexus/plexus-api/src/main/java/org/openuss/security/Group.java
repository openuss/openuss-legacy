package org.openuss.security;

import java.util.List;

/**
 * @author Ingo Dueppe
 */
public interface Group extends org.openuss.security.Authority, org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public String getLabel();

	public void setLabel(String label);

	/**
     * 
     */
	public org.openuss.security.GroupType getGroupType();

	public void setGroupType(org.openuss.security.GroupType groupType);

	/**
     * 
     */
	public String getPassword();

	public void setPassword(String password);

	/**
	 * Modified
	 * 
	 */
	public List<org.openuss.security.Authority> getMembers();

	public void setMembers(List<org.openuss.security.Authority> members);

	/**
	 * <p>
	 * Removes the given user from the user list of the authority.
	 * </p>
	 */
	public abstract void removeMember(org.openuss.security.Authority authority);

	/**
	 * <p>
	 * Add the given user to the user list of the authority.
	 * </p>
	 */
	public abstract void addMember(org.openuss.security.Authority authority);

	/**
	 * Constructs new instances of {@link org.openuss.security.Group}.
	 */
	public static abstract class Factory {

		/**
		 * Singleton instance of the concrete factory
		 */
		private static Group.Factory factory = null;

		/**
		 * Singleton method to obtain an instance of the concrete factory
		 */
		private static Group.Factory getFactory() {
			if (factory == null) {
				factory = (Group.Factory) org.openuss.api.utilities.FactoryFinder
						.find("org.openuss.security.Group.Factory");
			}
			return factory;
		}

		/**
		 * Abstract factory method for the concrete product.
		 */
		public abstract Group createGroup();

		/**
		 * Constructs a new instance of {@link org.openuss.security.Group}.
		 */
		public static org.openuss.security.Group newInstance() {
			return getFactory().createGroup();
		}

		/**
		 * Constructs a new instance of {@link org.openuss.security.Group},
		 * taking all required and/or read-only properties as arguments.
		 */
		public static org.openuss.security.Group newInstanceByIdentifier(Long id) {
			final org.openuss.security.Group entity = getFactory().createGroup();
			entity.setId(id);
			return entity;
		}

		/**
		 * Constructs a new instance of {@link org.openuss.security.Group},
		 * taking all required and/or read-only properties as arguments.
		 */
		public static org.openuss.security.Group newInstance(org.openuss.security.GroupType groupType, String name) {
			final org.openuss.security.Group entity = getFactory().createGroup();
			entity.setGroupType(groupType);
			entity.setName(name);
			return entity;
		}

		/**
		 * Constructs a new instance of {@link org.openuss.security.Group},
		 * taking all possible properties (except the identifier(s))as
		 * arguments.
		 */
		public static org.openuss.security.Group newInstance(String label, org.openuss.security.GroupType groupType,
				String password, String name, List<org.openuss.security.Authority> members) {
			final org.openuss.security.Group entity = getFactory().createGroup();
			entity.setLabel(label);
			entity.setGroupType(groupType);
			entity.setPassword(password);
			entity.setName(name);
			entity.setMembers(members);
			return entity;
		}
	}

	// Interface HibernateEntity.vsl merge-point
}