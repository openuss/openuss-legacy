// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;
/**
 * @see org.openuss.security.ldap.UserDnPatternSet
 */
public class UserDnPatternSetDaoImpl
    extends org.openuss.security.ldap.UserDnPatternSetDaoBase
{
    /**
     * @see org.openuss.security.ldap.UserDnPatternSetDao#toUserDnPatternSetInfo(org.openuss.security.ldap.UserDnPatternSet, org.openuss.security.ldap.UserDnPatternSetInfo)
     */
    public void toUserDnPatternSetInfo(
        org.openuss.security.ldap.UserDnPatternSet sourceEntity,
        org.openuss.security.ldap.UserDnPatternSetInfo targetVO)
    {      
        super.toUserDnPatternSetInfo(sourceEntity, targetVO);
//        targetVO.setId(sourceEntity.getId());
//        targetVO.setName(sourceEntity.getName());
    }


    /**
     * @see org.openuss.security.ldap.UserDnPatternSetDao#toUserDnPatternSetInfo(org.openuss.security.ldap.UserDnPatternSet)
     */
    public org.openuss.security.ldap.UserDnPatternSetInfo toUserDnPatternSetInfo(final org.openuss.security.ldap.UserDnPatternSet entity)
    {
         
        return super.toUserDnPatternSetInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.security.ldap.UserDnPatternSet loadUserDnPatternSetFromUserDnPatternSetInfo(org.openuss.security.ldap.UserDnPatternSetInfo userDnPatternSetInfo)
    {        
//        throw new java.lang.UnsupportedOperationException("org.openuss.security.ldap.loadUserDnPatternSetFromUserDnPatternSetInfo(org.openuss.security.ldap.UserDnPatternSetInfo) not yet implemented.");

        org.openuss.security.ldap.UserDnPatternSet userDnPatternSet = this.load(userDnPatternSetInfo.getId());
        if (userDnPatternSet == null)
        {
            userDnPatternSet = org.openuss.security.ldap.UserDnPatternSet.Factory.newInstance();
        }
        return userDnPatternSet;        
    }

    
    /**
     * @see org.openuss.security.ldap.UserDnPatternSetDao#userDnPatternSetInfoToEntity(org.openuss.security.ldap.UserDnPatternSetInfo)
     */
    public org.openuss.security.ldap.UserDnPatternSet userDnPatternSetInfoToEntity(org.openuss.security.ldap.UserDnPatternSetInfo userDnPatternSetInfo)
    {
        org.openuss.security.ldap.UserDnPatternSet entity = this.loadUserDnPatternSetFromUserDnPatternSetInfo(userDnPatternSetInfo);
        this.userDnPatternSetInfoToEntity(userDnPatternSetInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.security.ldap.UserDnPatternSetDao#userDnPatternSetInfoToEntity(org.openuss.security.ldap.UserDnPatternSetInfo, org.openuss.security.ldap.UserDnPatternSet)
     */
    public void userDnPatternSetInfoToEntity(
        org.openuss.security.ldap.UserDnPatternSetInfo sourceVO,
        org.openuss.security.ldap.UserDnPatternSet targetEntity,
        boolean copyIfNull) {    	
        super.userDnPatternSetInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}