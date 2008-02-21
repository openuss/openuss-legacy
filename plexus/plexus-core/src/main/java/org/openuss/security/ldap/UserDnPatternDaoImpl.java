// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;
/**
 * @see org.openuss.security.ldap.UserDnPattern
 */
public class UserDnPatternDaoImpl
    extends org.openuss.security.ldap.UserDnPatternDaoBase
{
    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#toUserDnPatternInfo(org.openuss.security.ldap.UserDnPattern, org.openuss.security.ldap.UserDnPatternInfo)
     */
    public void toUserDnPatternInfo(
        org.openuss.security.ldap.UserDnPattern sourceEntity,
        org.openuss.security.ldap.UserDnPatternInfo targetVO)
    {
        // @todo verify behavior of toUserDnPatternInfo
        super.toUserDnPatternInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#toUserDnPatternInfo(org.openuss.security.ldap.UserDnPattern)
     */
    public org.openuss.security.ldap.UserDnPatternInfo toUserDnPatternInfo(final org.openuss.security.ldap.UserDnPattern entity)
    {
        // @todo verify behavior of toUserDnPatternInfo
        return super.toUserDnPatternInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.security.ldap.UserDnPattern loadUserDnPatternFromUserDnPatternInfo(org.openuss.security.ldap.UserDnPatternInfo userDnPatternInfo)
    {
        // @todo implement loadUserDnPatternFromUserDnPatternInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.security.ldap.loadUserDnPatternFromUserDnPatternInfo(org.openuss.security.ldap.UserDnPatternInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.security.ldap.UserDnPattern userDnPattern = this.load(userDnPatternInfo.getId());
        if (userDnPattern == null)
        {
            userDnPattern = org.openuss.security.ldap.UserDnPattern.Factory.newInstance();
        }
        return userDnPattern;
        */
    }

    
    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#userDnPatternInfoToEntity(org.openuss.security.ldap.UserDnPatternInfo)
     */
    public org.openuss.security.ldap.UserDnPattern userDnPatternInfoToEntity(org.openuss.security.ldap.UserDnPatternInfo userDnPatternInfo)
    {
        // @todo verify behavior of userDnPatternInfoToEntity
        org.openuss.security.ldap.UserDnPattern entity = this.loadUserDnPatternFromUserDnPatternInfo(userDnPatternInfo);
        this.userDnPatternInfoToEntity(userDnPatternInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.security.ldap.UserDnPatternDao#userDnPatternInfoToEntity(org.openuss.security.ldap.UserDnPatternInfo, org.openuss.security.ldap.UserDnPattern)
     */
    public void userDnPatternInfoToEntity(
        org.openuss.security.ldap.UserDnPatternInfo sourceVO,
        org.openuss.security.ldap.UserDnPattern targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of userDnPatternInfoToEntity
        super.userDnPatternInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}