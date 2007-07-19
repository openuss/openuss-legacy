// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @see org.openuss.security.MembershipService
 * @author Ron Haus, Florian Dondorf
 */
public class MembershipServiceImpl extends org.openuss.security.MembershipServiceBase {


	/**
	 * @see org.openuss.security.MembershipService#acceptAspirant(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings({"unchecked"})
	protected void handleAcceptAspirant(java.lang.Long membershipId, java.lang.Long userId, org.openuss.security.MembershipParameters parameters)
			throws java.lang.Exception {
		// TODO Check Security

		User aspirant = this.getUserDao().load(userId);
		if (aspirant == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleAcceptAspirant - no User found corresponding to the ID " + userId);
		}

		Membership membership = this.getMembershipDao().load(membershipId);
		if (membership == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleAcceptAspirant - no Organisation found corresponding to the ID "
							+ membershipId);
		}

		// Remove Aspirant from List of Aspirants
		List aspirants = membership.getAspirants();
		boolean wasFound = aspirants.remove(aspirant);
		if (!wasFound) {
			throw new IllegalArgumentException("MembershipService.handleAcceptAspirant - the User "
					+ aspirant.getUsername() + " has not been an Aspirant");
		}

		// There is no need to check whether the Aspirant is already a Member, since the method addAspirant
		// is taken care of that
		
		// Add Aspirant to List of Members
		this.addMember(membershipId, userId, parameters);

	}

	/**
	 * @see org.openuss.security.MembershipService#rejectAspirant(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings({"unchecked"})
	protected void handleRejectAspirant(java.lang.Long membershipId, java.lang.Long userId)
			throws java.lang.Exception {
		// TODO Check Security
		
		User aspirant = this.getUserDao().load(userId);
		if (aspirant == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleRejectAspirant - no User found corresponding to the ID " + userId);
		}
		
		Membership membership = this.getMembershipDao().load(membershipId);
		if (membership == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleRejectAspirant - no Membership found corresponding to the ID "
							+ membershipId);
		}
		
		// Remove Aspirant from List of Aspirants
		List aspirants = membership.getAspirants();
		boolean wasFound = aspirants.remove(aspirant);
		if (!wasFound) {
			throw new IllegalArgumentException("MembershipService.handleRejectAspirant - the User "
					+ aspirant.getUsername() + " has not been an Aspirant");
		}
		
	}

	/**
	 * @see org.openuss.security.MembershipService#addMember(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings({"unchecked"})
	protected void handleAddMember(java.lang.Long membershipId, java.lang.Long userId, org.openuss.security.MembershipParameters parameters) throws java.lang.Exception {
		// TODO Check Security
		
		User user = this.getUserDao().load(userId);
		if (user == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleAddMember - no User found corresponding to the ID " + userId);
		}
		
		Membership membership = this.getMembershipDao().load(membershipId);
		if (membership == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleAddMember - no Organisation found corresponding to the ID "
							+ membershipId);
		}
		
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
		
		// TODO Add User to the official Group "Administrators"
		
		// TODO Send Email to inform the Members
	}

	/**
	 * @see org.openuss.security.MembershipService#addAspirant(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings({"unchecked"})
	protected void handleAddAspirant(java.lang.Long membershipId, java.lang.Long userId, org.openuss.security.MembershipParameters parameters) throws java.lang.Exception {
		// TODO Check Security

		User user = this.getUserDao().load(userId);
		if (user == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleAddAspirant - no User found corresponding to the ID " + userId);
		}

		Membership membership = this.getMembershipDao().load(membershipId);
		if (membership == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleAddAspirant - no Membership found corresponding to the ID "
							+ membershipId);
		}

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
		
		// TODO Send Email to inform the Members
	}

	/**
	 * @see org.openuss.security.MembershipService#findAllMembers(java.lang.Long)
	 */
	@SuppressWarnings({"unchecked"})
	protected java.util.List handleFindAllMembers(java.lang.Long membershipId) throws java.lang.Exception {
		Membership membership = this.getMembershipDao().load(membershipId);
		if (membership == null) {
			throw new IllegalArgumentException(
					"MembershipService.FindAllMembers - no Membership found corresponding to the ID "
							+ membershipId);
		}
		List members = membership.getMembers();
		Iterator iterator = members.iterator();

		List memberInfos = new ArrayList(members.size());
		for (int i = 0; i < members.size(); i++) {
			Object member = iterator.next();
			if (member instanceof User) {
				memberInfos.add(this.getUserDao().toUserInfo((User) member));
			}
		}

		return memberInfos;
	}

	/**
	 * @see org.openuss.security.MembershipService#findAllAspirants(java.lang.Long)
	 */
	@SuppressWarnings({"unchecked"})
	protected java.util.List handleFindAllAspirants(java.lang.Long membershipId) throws java.lang.Exception {
		Membership membership = this.getMembershipDao().load(membershipId);
		if (membership == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleFindAllAspirants - no Membership found corresponding to the ID "
							+ membershipId);
		}

		List aspirants = membership.getAspirants();
		Iterator iterator = aspirants.iterator();

		List aspirantInfos = new ArrayList(aspirants.size());
		for (int i = 0; i < aspirants.size(); i++) {
			Object aspirant = iterator.next();
			if (aspirant instanceof User) {
				aspirantInfos.add(this.getUserDao().toUserInfo((User) aspirant));
			}
		}

		return aspirantInfos;
	}
}