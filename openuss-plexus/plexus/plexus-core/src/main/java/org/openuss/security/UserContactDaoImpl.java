// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;
/**
 * @see org.openuss.security.UserContact
 */
public class UserContactDaoImpl
    extends org.openuss.security.UserContactDaoBase
{
    /**
     * @see org.openuss.security.UserContactDao#toUserContactInfo(org.openuss.security.UserContact, org.openuss.security.UserContactInfo)
     */
    public void toUserContactInfo(
        org.openuss.security.UserContact sourceEntity,
        org.openuss.security.UserContactInfo targetVO)
    {
        // @todo verify behavior of toUserContactInfo
        super.toUserContactInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.security.UserContactDao#toUserContactInfo(org.openuss.security.UserContact)
     */
    public org.openuss.security.UserContactInfo toUserContactInfo(final org.openuss.security.UserContact entity)
    {
        // @todo verify behavior of toUserContactInfo
        return super.toUserContactInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.security.UserContact loadUserContactFromUserContactInfo(org.openuss.security.UserContactInfo userContactInfo)
    {
    	if (userContactInfo == null || userContactInfo.getId()==null){
    		return UserContact.Factory.newInstance();
    	}
        UserContact userContact = this.load(userContactInfo.getId());
        if (userContact == null)
        {
            userContact = UserContact.Factory.newInstance();
        }
        return userContact;
    }

    
    /**
     * @see org.openuss.security.UserContactDao#userContactInfoToEntity(org.openuss.security.UserContactInfo)
     */
    public org.openuss.security.UserContact userContactInfoToEntity(org.openuss.security.UserContactInfo userContactInfo)
    {
        // @todo verify behavior of userContactInfoToEntity
        org.openuss.security.UserContact entity = this.loadUserContactFromUserContactInfo(userContactInfo);
        this.userContactInfoToEntity(userContactInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.security.UserContactDao#userContactInfoToEntity(org.openuss.security.UserContactInfo, org.openuss.security.UserContact)
     */
    public void userContactInfoToEntity(
        org.openuss.security.UserContactInfo sourceVO,
        org.openuss.security.UserContact targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of userContactInfoToEntity
        super.userContactInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}