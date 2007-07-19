// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;


/**
 * @see org.openuss.lecture.OrganisationService
 * @author Ron Haus
 */
public class OrganisationServiceImpl
    extends org.openuss.lecture.OrganisationServiceBase
{

    /**
     * @see org.openuss.lecture.OrganisationService#addMember(java.lang.Long, java.lang.Long)
     */
    protected void handleAddMember(java.lang.Long organisationId, java.lang.Long userId)
        throws java.lang.Exception
    {
    	Organisation organisation = this.getOrganisationDao().load(organisationId);
		if (organisation == null) {
			throw new IllegalArgumentException(
					"MembershipService.handleAddMember - no Organisation found corresponding to the ID " + organisationId);
		}
		
		this.getMembershipService().addMember(organisation.getMembership().getId(), userId, null);

    }

    /**
     * @see org.openuss.lecture.OrganisationService#addAspirant(java.lang.Long, java.lang.Long)
     */
    protected void handleAddAspirant(java.lang.Long organisationId, java.lang.Long userId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAddAspirant(java.lang.Long organisationId, java.lang.Long userId)
        throw new java.lang.UnsupportedOperationException("org.openuss.lecture.OrganisationService.handleAddAspirant(java.lang.Long organisationId, java.lang.Long userId) Not implemented!");
    }

    /**
     * @see org.openuss.lecture.OrganisationService#findAllMembers(java.lang.Long)
     */
    protected java.util.List handleFindAllMembers(java.lang.Long organisationId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindAllMembers(java.lang.Long organisationId)
        return null;
    }

    /**
     * @see org.openuss.lecture.OrganisationService#findAllAspirants(java.lang.Long)
     */
    protected java.util.List handleFindAllAspirants(java.lang.Long organisationId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindAllAspirants(java.lang.Long organisationId)
        return null;
    }

    /**
     * @see org.openuss.lecture.OrganisationService#acceptAspirant(java.lang.Long, java.lang.Long)
     */
    protected void handleAcceptAspirant(java.lang.Long organisationId, java.lang.Long userId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAcceptAspirant(java.lang.Long organisationId, java.lang.Long userId)
        throw new java.lang.UnsupportedOperationException("org.openuss.lecture.OrganisationService.handleAcceptAspirant(java.lang.Long organisationId, java.lang.Long userId) Not implemented!");
    }

    /**
     * @see org.openuss.lecture.OrganisationService#rejectAspirant(java.lang.Long, java.lang.Long)
     */
    protected void handleRejectAspirant(java.lang.Long organisationId, java.lang.Long userId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRejectAspirant(java.lang.Long organisationId, java.lang.Long userId)
        throw new java.lang.UnsupportedOperationException("org.openuss.lecture.OrganisationService.handleRejectAspirant(java.lang.Long organisationId, java.lang.Long userId) Not implemented!");
    }

}