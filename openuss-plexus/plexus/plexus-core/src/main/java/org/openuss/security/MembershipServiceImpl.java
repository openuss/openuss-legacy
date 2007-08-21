// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.lecture.Organisation;

/**
 * @see org.openuss.security.MembershipService
 * @author Ron Haus
 */
public class MembershipServiceImpl extends org.openuss.security.MembershipServiceBase {

	/**
	 * @see org.openuss.security.MembershipService#acceptAspirant(org.openuss.security.Membership,
	 *      org.openuss.security.User, org.openuss.security.MembershipParameters)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleAcceptAspirant(org.openuss.security.Membership membership, org.openuss.security.User user,
			org.openuss.security.MembershipParameters parameters) throws java.lang.Exception {
		// TODO Check Security

		Validate.notNull(user, "MembershipService.handleAcceptAspirant - User cannot be null");
		Validate.notNull(user.getId(), "MembershipService.handleAcceptAspirant - User must have a valid ID");
		Validate.notNull(membership, "MembershipService.handleAcceptAspirant - Membership cannot be null");
		Validate
				.notNull(membership.getId(), "MembershipService.handleAcceptAspirant - Membership must have a valid ID");

		// Remove Aspirant from List of Aspirants
		List aspirants = membership.getAspirants();
		boolean wasFound = aspirants.remove(user);
		if (!wasFound) {
			throw new IllegalArgumentException("MembershipService.handleAcceptAspirant - the User "
					+ user.getUsername() + " has not been an Aspirant");
		}

		// There is no need to check whether the Aspirant is already a Member, since the method addAspirant is taken
		// care of that

		// Add Aspirant to List of Members
		this.addMember(membership, user, parameters);

	}

	/**
	 * @see org.openuss.security.MembershipService#rejectAspirant(org.openuss.security.Membership, org.openuss.security.User,
	 *      org.openuss.security.MembershipParameters)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleRejectAspirant(org.openuss.security.Membership membership, org.openuss.security.User user,
			org.openuss.security.MembershipParameters parameters) throws java.lang.Exception {
		
		// TODO Check Security

		Validate.notNull(user, "MembershipService.handleRejectAspirant - User cannot be null");
		Validate.notNull(user.getId(), "MembershipService.handleRejectAspirant - User must have a valid ID");
		Validate.notNull(membership, "MembershipService.handleRejectAspirant - Membership cannot be null");
		Validate
				.notNull(membership.getId(), "MembershipService.handleRejectAspirant - Membership must have a valid ID");

		// Remove Aspirant from List of Aspirants
		List aspirants = membership.getAspirants();
		boolean wasFound = aspirants.remove(user);
		if (!wasFound) {
			throw new IllegalArgumentException("MembershipService.handleRejectAspirant - the User "
					+ user.getUsername() + " has not been an Aspirant");
		}

	}

	/**
	 * @see org.openuss.security.MembershipService#addMember(org.openuss.security.Membership, java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleAddMember(org.openuss.security.Membership membership, org.openuss.security.User user,
			org.openuss.security.MembershipParameters parameters) throws java.lang.Exception {
		
		// TODO Check Security

		Validate.notNull(user, "MembershipService.handleAddMember - User cannot be null");
		Validate.notNull(user.getId(), "MembershipService.handleAddMember - User must have a valid ID");
		Validate.notNull(membership, "MembershipService.handleAddMember - Membership cannot be null");
		Validate.notNull(membership.getId(), "MembershipService.handleAddMember - Membership must have a valid ID");

		// Check whether the User is already a Member or Aspirant
		List members = membership.getMembers();
		if (members.contains(user)) {
			throw new IllegalArgumentException("MembershipService.handleAddMember - the User " + user.getUsername()
					+ " is already a Member");
		}
		List aspirants = membership.getAspirants();
		if (aspirants.contains(user)) {
			throw new IllegalArgumentException("MembershipService.handleAddMember - the User " + user.getUsername()
					+ " is already an Aspirant");
		}

		// Add User to the List of Members
		members.add(user);

		// TODO Send Email to inform the Members and the new Member
	}

	/**
	 * @see org.openuss.security.MembershipService#addAspirant(org.openuss.security.Membership, java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleAddAspirant(org.openuss.security.Membership membership, org.openuss.security.User user,
			org.openuss.security.MembershipParameters parameters) throws java.lang.Exception {
		
		// TODO Check Security

		Validate.notNull(user, "MembershipService.handleAddAspirant - User cannot be null");
		Validate.notNull(user.getId(), "MembershipService.handleAddAspirant - User must have a valid ID");
		Validate.notNull(membership, "MembershipService.handleAddAspirant - Membership cannot be null");
		Validate.notNull(membership.getId(), "MembershipService.handleAddAspirant - Membership must have a valid ID");

		// Check whether the User is already a Member or Aspirant
		List members = membership.getMembers();
		if (members.contains(user)) {
			throw new IllegalArgumentException("MembershipService.handleAddAspirant - the User " + user.getUsername()
					+ " is already a Member");
		}
		List aspirants = membership.getAspirants();
		if (aspirants.contains(user)) {
			throw new IllegalArgumentException("MembershipService.handleAddAspirant - the User " + user.getUsername()
					+ " is already an Aspirant");
		}

		// Add User to the List of Aspirants
		aspirants.add(user);

		// TODO Send Email to inform the Members and the Aspirant
	}

	/**
	 * @see org.openuss.security.MembershipService#findAllAspirants(org.openuss.security.Membership, org.openuss.security.GroupItem)
	 */
	protected org.openuss.security.Group handleCreateGroup(org.openuss.security.Membership membership, org.openuss.security.Group group)
			throws java.lang.Exception {
		
		Validate.notNull(membership, "MembershipService.handleCreateGroup - Membership cannot be null");
		Validate.notNull(membership.getId(), "MembershipService.handleCreateGroup - Membership must have a valid ID");
		Validate.notNull(group, "MembershipService.handleCreateGroup - Group cannot be null");
		
		Group group2 = this.getSecurityService().createGroup(group.getName(), group.getLabel(), group.getPassword(), group.getGroupType());
		Validate.notNull(group2, "MembershipService.handleCreateGroup - Group couldn't be created");
		Validate.notNull(group2.getId(), "MembershipService.handleCreateGroup - Group couldn't be created");
		
		membership.getGroups().add(group2);
		
		return group2;

	}

	@Override
	protected void handleRemoveGroup(Membership membership, Group group) throws Exception {
		Validate.notNull(group, "MembershipService.handleRemoveGroup - Group cannot be null");
		Validate.notNull(group, "MembershipService.handleRemoveGroup - Membership cannot be null");

		// Remove all Authorities from Group
		List<Authority> authorities = group.getMembers();
		for (Authority authority : authorities) {
			authority.removeGroup(group);
		}

		// Remove the Group from its Membership
		boolean removed = membership.getGroups().remove(group);
		Validate.isTrue(removed, "MembershipService.handleRemoveGroup - Group couldn't be removed");

		// Delete Group
		this.getSecurityService().removeGroup(group);		
	}
}