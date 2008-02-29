// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;

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
	protected void handleAcceptAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)
			throws java.lang.Exception {
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
		this.addMember(membership, user);

	}

	/**
	 * @see org.openuss.security.MembershipService#rejectAspirant(org.openuss.security.Membership,
	 *      org.openuss.security.User, org.openuss.security.MembershipParameters)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleRejectAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)
			throws java.lang.Exception {

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
	protected void handleAddMember(org.openuss.security.Membership membership, org.openuss.security.User user)
			throws java.lang.Exception {

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
	 * @see org.openuss.security.MembershipService#removeMember(org.openuss.security.Membership, java.lang.Long)
	 */
	protected void handleRemoveMember(Membership membership, User user) throws Exception {
		// TODO Check Security

		Validate.notNull(user, "MembershipService.handleRemoveMember - User cannot be null");
		Validate.notNull(user.getId(), "MembershipService.handleRemoveMember - User must have a valid ID");
		Validate.notNull(membership, "MembershipService.handleRemoveMember - Membership cannot be null");
		Validate.notNull(membership.getId(), "MembershipService.handleRemoveMember - Membership must have a valid ID");


		//check if user is last administrator of membership
		Set<User> members = new HashSet<User>();
		for (Group group: membership.getGroups()){
			if (group.getGroupType().equals(GroupType.ADMINISTRATOR)){
				for (Authority groupMember : group.getMembers()){
					if (groupMember instanceof UserImpl){
						members.add((UserImpl) groupMember);
					}
				}
			}
		}     //size == 0 should not occur
		if ((members.size()==0)||(members.size()==1&&members.iterator().next().getId().equals(user.getId()))) {
			throw new IllegalArgumentException("MembershipService.handleRemoveMember - the User " + user.getUsername()
					+ " couldn't be removed.");
		}
		
		//Remove User from Membership Groups
		for (Group group : membership.getGroups()){
			getSecurityService().removeAuthorityFromGroup(user, group);
		}
		// Remove User from the List of Members
		membership.getMembers().remove(user);

		// TODO Send Email to inform the Members and the removed Member

	}

	/**
	 * @see org.openuss.security.MembershipService#addAspirant(org.openuss.security.Membership, java.lang.Long)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void handleAddAspirant(org.openuss.security.Membership membership, org.openuss.security.User user)
			throws java.lang.Exception {

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
	 * @see org.openuss.security.MembershipService#findAllAspirants(org.openuss.security.Membership,
	 *      org.openuss.security.GroupItem)
	 */
	protected org.openuss.security.Group handleCreateGroup(org.openuss.security.Membership membership,
			org.openuss.security.Group group) throws java.lang.Exception {

		Validate.notNull(membership, "MembershipService.handleCreateGroup - Membership cannot be null");
		Validate.notNull(membership.getId(), "MembershipService.handleCreateGroup - Membership must have a valid ID");
		Validate.notNull(group, "MembershipService.handleCreateGroup - Group cannot be null");

		Group group2 = this.getSecurityService().createGroup(group.getName(), group.getLabel(), group.getPassword(),
				group.getGroupType());
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

	@Override
	protected void handleClearMembership(Membership membership) throws Exception {
		Validate.notNull(membership, "MembershipService.handleClearMembership - Membership cannot be null");

		// Remove Groups
		if (membership.getGroups() != null) {
			List<Group> groupsNew = new ArrayList<Group>();
			for (Group group : membership.getGroups()) {
				groupsNew.add(group);
			}
			for (Group group : groupsNew) {
				this.removeGroup(membership, group);
			}
			groupsNew.clear();
			membership.getGroups().clear();
		} else {
			membership.setGroups(new ArrayList<Group>());
		}

		// Remove Aspirants
		if (membership.getAspirants() != null) {
			membership.getAspirants().clear();
		} else {
			membership.setAspirants(new ArrayList<User>());
		}

		// Remove Members
		if (membership.getMembers() != null) {
			membership.getMembers().clear();
		} else {
			membership.setMembers(new ArrayList<User>());
		}
	}
}