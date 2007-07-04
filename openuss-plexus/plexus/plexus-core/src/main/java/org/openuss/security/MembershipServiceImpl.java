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
		
		UserInfo ownerInfo = this.getUserDao().toUserInfo(organisation.getOwner());
		
		return ownerInfo;
	}

}