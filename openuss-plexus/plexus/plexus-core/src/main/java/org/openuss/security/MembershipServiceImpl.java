// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import org.openuss.lecture.Organisation;

/**
 * @see org.openuss.security.MembershipService
 * @author Ron Haus, Florian Dondorf
 */
public class MembershipServiceImpl extends org.openuss.security.MembershipServiceBase {

	/**
	 * @see org.openuss.security.MembershipService#setOwner(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleSetOwner(java.lang.Long organisationId, java.lang.Long userId) throws java.lang.Exception {
		// TODO Check Security - only admin and owner must be allowed to change
		// the owner
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
	 * @see org.openuss.security.MembershipService#acceptAspirant(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleAcceptAspirant(java.lang.Long organisationId, java.lang.Long userId)
			throws java.lang.Exception {

	}

	/**
	 * @see org.openuss.security.MembershipService#rejectAspirant(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleRejectAspirant(java.lang.Long organisationId, java.lang.Long userId)
			throws java.lang.Exception {

	}

	/**
	 * @see org.openuss.security.MembershipService#addMember(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleAddMember(java.lang.Long organisationId, java.lang.Long userId) throws java.lang.Exception {
	}

	/**
	 * @see org.openuss.security.MembershipService#addAspirant(java.lang.Long,
	 *      java.lang.Long)
	 */
	protected void handleAddAspirant(java.lang.Long organisationId, java.lang.Long userId) throws java.lang.Exception {

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

		return organisation.getMembers();
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

		return organisation.getAspirants();
	}
}