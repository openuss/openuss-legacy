// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openuss.lecture.Organisation;

/**
 * @see org.openuss.security.MembershipService
 * @author Ron Haus, Florian Dondorf
 */
public class MembershipServiceImpl extends org.openuss.security.MembershipServiceBase {

	/**
	 * @see org.openuss.security.MembershipService#setOwner(java.lang.Long, java.lang.Long)
	 */
	protected void handleSetOwner(java.lang.Long organisationId, java.lang.Long userId) throws java.lang.Exception {
		// TODO Check Security - only admin and owner must be allowed to change the owner
		User owner = this.getUserDao().load(userId);
		if (owner == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleSetOwner - no user found corresponding to the id " + userId);
		}

		Organisation organisation = this.getOrganisationDao().load(organisationId);
		if (organisation == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleSetOwner - no organisation found corresponding to the id "
							+ organisationId);
		}

		organisation.setOwner(owner);
	}

	/**
	 * @see org.openuss.security.MembershipService#findOwner(java.lang.Long)
	 */
	protected org.openuss.security.UserInfo handleFindOwner(java.lang.Long organisationId) throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		if (organisation == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleSetOwner - no organisation found corresponding to the id "
							+ organisationId);
		}

		return getUserDao().toUserInfo(organisation.getOwner());
	}

	/**
	 * @see org.openuss.security.MembershipService#acceptAspirant(java.lang.Long, java.lang.Long)
	 */
	protected void handleAcceptAspirant(java.lang.Long organisationId, java.lang.Long userId)
			throws java.lang.Exception {
		// TODO Check Security - only admin, owner and members must be allowed to accept aspirants

		User aspirant = this.getUserDao().load(userId);
		if (aspirant == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleAcceptAspirant - no user found corresponding to the id " + userId);
		}

		Organisation organisation = this.getOrganisationDao().load(organisationId);
		if (organisation == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleAcceptAspirant - no organisation found corresponding to the id "
							+ organisationId);
		}

		// Remove Aspirant from List of Aspirants
		List aspirants = organisation.getAspirants();
		boolean wasFound = aspirants.remove(aspirant);
		if (!wasFound) {
			throw new IllegalArgumentException("MembershipService.handleAcceptAspirant - the user "
					+ aspirant.getUsername() + " has not been an Aspirant");
		}

		// There is no need to check whether the Aspirant is already a Member or Owner, since the method addAspirant
		// is taken care of that
		
		// Add Aspirant to List of Members
		List members = organisation.getMembers();
		members.add(aspirant);
		
		// To inform the aspirant of his "upgrade" is the responibility of the web layer

	}

	/**
	 * @see org.openuss.security.MembershipService#rejectAspirant(java.lang.Long, java.lang.Long)
	 */
	protected void handleRejectAspirant(java.lang.Long organisationId, java.lang.Long userId)
			throws java.lang.Exception {

	}

	/**
	 * @see org.openuss.security.MembershipService#addMember(java.lang.Long, java.lang.Long)
	 */
	protected void handleAddMember(java.lang.Long organisationId, java.lang.Long userId) throws java.lang.Exception {
		// TODO Check Security - only admin, owner and members must be allowed to add members
	}

	/**
	 * @see org.openuss.security.MembershipService#addAspirant(java.lang.Long, java.lang.Long)
	 */
	protected void handleAddAspirant(java.lang.Long organisationId, java.lang.Long userId) throws java.lang.Exception {
		// TODO Check Security - only admin, owner and members must be allowed to add aspirants

		User user = this.getUserDao().load(userId);
		if (user == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleAddAspirant - no user found corresponding to the id " + userId);
		}

		Organisation organisation = this.getOrganisationDao().load(organisationId);
		if (organisation == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleAddAspirant - no organisation found corresponding to the id "
							+ organisationId);
		}

		// Check whether the User is already a Member, Aspirant or the Owner
		if (organisation.getOwner().equals(user)) {
			throw new IllegalArgumentException("MembershipService.handleAddAspirant - the User " + user.getUsername()
					+ " is already the Owner");
		}
		List members = organisation.getMembers();
		if (members.contains(user)) {
			throw new IllegalArgumentException("MembershipService.handleAddAspirant - the User " + user.getUsername()
					+ " is already a Member");
		}
		List aspirants = organisation.getAspirants();
		if (aspirants.contains(user)) {
			throw new IllegalArgumentException("MembershipService.handleAddAspirant - the User " + user.getUsername()
					+ " is already an Aspirant");
		}

		// Add User to the List of Aspirants
		aspirants.add(user);

	}

	/**
	 * @see org.openuss.security.MembershipService#findAllMembers(java.lang.Long)
	 */
	protected java.util.List handleFindAllMembers(java.lang.Long organisationId) throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		if (organisation == null) {
			throw new IllegalArgumentException(
					"MembershipService.FindAllMembers - no organisation found corresponding to the id "
							+ organisationId);
		}
		List members = organisation.getMembers();
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
	protected java.util.List handleFindAllAspirants(java.lang.Long organisationId) throws java.lang.Exception {
		Organisation organisation = this.getOrganisationDao().load(organisationId);
		if (organisation == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleFindAllAspirants - no organisation found corresponding to the id "
							+ organisationId);
		}

		List aspirants = organisation.getAspirants();
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