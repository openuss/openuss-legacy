// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;
/**
 * @see org.openuss.security.UserProfile
 */
public class UserProfileDaoImpl
    extends org.openuss.security.UserProfileDaoBase
{
    /**
     * @see org.openuss.security.UserProfileDao#toUserProfileInfo(org.openuss.security.UserProfile, org.openuss.security.UserProfileInfo)
     */
    public void toUserProfileInfo(
        org.openuss.security.UserProfile sourceEntity,
        org.openuss.security.UserProfileInfo targetVO)
    {
        // @todo verify behavior of toUserProfileInfo
        super.toUserProfileInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.security.UserProfileDao#toUserProfileInfo(org.openuss.security.UserProfile)
     */
    public org.openuss.security.UserProfileInfo toUserProfileInfo(final org.openuss.security.UserProfile entity)
    {
        // @todo verify behavior of toUserProfileInfo
        return super.toUserProfileInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.security.UserProfile loadUserProfileFromUserProfileInfo(org.openuss.security.UserProfileInfo userProfileInfo)
    {
    	if (userProfileInfo == null || userProfileInfo.getId() == null){
    		return UserProfile.Factory.newInstance();
    	}
        UserProfile userProfile = this.load(userProfileInfo.getId());
        if (userProfile == null)
        {
            userProfile = UserProfile.Factory.newInstance();
        }
        return userProfile;
    }

    
    /**
     * @see org.openuss.security.UserProfileDao#userProfileInfoToEntity(org.openuss.security.UserProfileInfo)
     */
    public org.openuss.security.UserProfile userProfileInfoToEntity(org.openuss.security.UserProfileInfo userProfileInfo)
    {
        // @todo verify behavior of userProfileInfoToEntity
        org.openuss.security.UserProfile entity = this.loadUserProfileFromUserProfileInfo(userProfileInfo);
        this.userProfileInfoToEntity(userProfileInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.security.UserProfileDao#userProfileInfoToEntity(org.openuss.security.UserProfileInfo, org.openuss.security.UserProfile)
     */
    public void userProfileInfoToEntity(
        org.openuss.security.UserProfileInfo sourceVO,
        org.openuss.security.UserProfile targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of userProfileInfoToEntity
        super.userProfileInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}