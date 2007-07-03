// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntityImpl.vsl in in openuss/tools/andromda/templates.
//
package org.openuss.lecture;

/**
 * @see org.openuss.lecture.Organisation
 */
public abstract class OrganisationImpl
    extends org.openuss.lecture.OrganisationBase
	implements org.openuss.lecture.Organisation
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -1208147996320435736L;
    
    public org.openuss.security.User getOwner() {
    	return this.getMembership().getOwner();
    }
    
    public void setOwner(org.openuss.security.User owner) {
    	if (owner == null) {
    		throw new IllegalArgumentException("Organisation.setOwner - 'owner' can not be null");
    	}
    	this.getMembership().setOwner(owner);
    }
}