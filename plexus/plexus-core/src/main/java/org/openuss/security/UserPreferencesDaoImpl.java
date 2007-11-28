// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;
/**
 * @see org.openuss.security.UserPreferences
 */
public class UserPreferencesDaoImpl
    extends org.openuss.security.UserPreferencesDaoBase
{
    /**
     * @see org.openuss.security.UserPreferencesDao#toUserPreferencesInfo(org.openuss.security.UserPreferences, org.openuss.security.UserPreferencesInfo)
     */
    public void toUserPreferencesInfo(
        org.openuss.security.UserPreferences sourceEntity,
        org.openuss.security.UserPreferencesInfo targetVO)
    {
        // @todo verify behavior of toUserPreferencesInfo
        super.toUserPreferencesInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.security.UserPreferencesDao#toUserPreferencesInfo(org.openuss.security.UserPreferences)
     */
    public org.openuss.security.UserPreferencesInfo toUserPreferencesInfo(final org.openuss.security.UserPreferences entity)
    {
        // @todo verify behavior of toUserPreferencesInfo
        return super.toUserPreferencesInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.security.UserPreferences loadUserPreferencesFromUserPreferencesInfo(org.openuss.security.UserPreferencesInfo userPreferencesInfo)
    {
        if (userPreferencesInfo==null || userPreferencesInfo.getId()==null){
        	return UserPreferences.Factory.newInstance();
        }
    	UserPreferences userPreferences = this.load(userPreferencesInfo.getId());
        if (userPreferences == null)
        {
            userPreferences = UserPreferences.Factory.newInstance();
        }
        return userPreferences;
    }

    
    /**
     * @see org.openuss.security.UserPreferencesDao#userPreferencesInfoToEntity(org.openuss.security.UserPreferencesInfo)
     */
    public org.openuss.security.UserPreferences userPreferencesInfoToEntity(org.openuss.security.UserPreferencesInfo userPreferencesInfo)
    {
        // @todo verify behavior of userPreferencesInfoToEntity
        org.openuss.security.UserPreferences entity = this.loadUserPreferencesFromUserPreferencesInfo(userPreferencesInfo);
        this.userPreferencesInfoToEntity(userPreferencesInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.security.UserPreferencesDao#userPreferencesInfoToEntity(org.openuss.security.UserPreferencesInfo, org.openuss.security.UserPreferences)
     */
    public void userPreferencesInfoToEntity(
        org.openuss.security.UserPreferencesInfo sourceVO,
        org.openuss.security.UserPreferences targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of userPreferencesInfoToEntity
        super.userPreferencesInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}