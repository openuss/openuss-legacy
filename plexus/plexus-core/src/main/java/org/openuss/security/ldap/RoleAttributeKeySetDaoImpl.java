// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;
/**
 * @see org.openuss.security.ldap.RoleAttributeKeySet
 */
public class RoleAttributeKeySetDaoImpl
    extends org.openuss.security.ldap.RoleAttributeKeySetDaoBase
{
    /**
     * @see org.openuss.security.ldap.RoleAttributeKeySetDao#toRoleAttributeKeySetInfo(org.openuss.security.ldap.RoleAttributeKeySet, org.openuss.security.ldap.RoleAttributeKeySetInfo)
     */
    public void toRoleAttributeKeySetInfo(
        org.openuss.security.ldap.RoleAttributeKeySet sourceEntity,
        org.openuss.security.ldap.RoleAttributeKeySetInfo targetVO)
    {
        // @todo verify behavior of toRoleAttributeKeySetInfo
        super.toRoleAttributeKeySetInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.security.ldap.RoleAttributeKeySetDao#toRoleAttributeKeySetInfo(org.openuss.security.ldap.RoleAttributeKeySet)
     */
    public org.openuss.security.ldap.RoleAttributeKeySetInfo toRoleAttributeKeySetInfo(final org.openuss.security.ldap.RoleAttributeKeySet entity)
    {
        // @todo verify behavior of toRoleAttributeKeySetInfo
        return super.toRoleAttributeKeySetInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.security.ldap.RoleAttributeKeySet loadRoleAttributeKeySetFromRoleAttributeKeySetInfo(org.openuss.security.ldap.RoleAttributeKeySetInfo roleAttributeKeySetInfo)
    {
        // @todo implement loadRoleAttributeKeySetFromRoleAttributeKeySetInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.security.ldap.loadRoleAttributeKeySetFromRoleAttributeKeySetInfo(org.openuss.security.ldap.RoleAttributeKeySetInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.security.ldap.RoleAttributeKeySet roleAttributeKeySet = this.load(roleAttributeKeySetInfo.getId());
        if (roleAttributeKeySet == null)
        {
            roleAttributeKeySet = org.openuss.security.ldap.RoleAttributeKeySet.Factory.newInstance();
        }
        return roleAttributeKeySet;
        */
    }

    
    /**
     * @see org.openuss.security.ldap.RoleAttributeKeySetDao#roleAttributeKeySetInfoToEntity(org.openuss.security.ldap.RoleAttributeKeySetInfo)
     */
    public org.openuss.security.ldap.RoleAttributeKeySet roleAttributeKeySetInfoToEntity(org.openuss.security.ldap.RoleAttributeKeySetInfo roleAttributeKeySetInfo)
    {
        // @todo verify behavior of roleAttributeKeySetInfoToEntity
        org.openuss.security.ldap.RoleAttributeKeySet entity = this.loadRoleAttributeKeySetFromRoleAttributeKeySetInfo(roleAttributeKeySetInfo);
        this.roleAttributeKeySetInfoToEntity(roleAttributeKeySetInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.security.ldap.RoleAttributeKeySetDao#roleAttributeKeySetInfoToEntity(org.openuss.security.ldap.RoleAttributeKeySetInfo, org.openuss.security.ldap.RoleAttributeKeySet)
     */
    public void roleAttributeKeySetInfoToEntity(
        org.openuss.security.ldap.RoleAttributeKeySetInfo sourceVO,
        org.openuss.security.ldap.RoleAttributeKeySet targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of roleAttributeKeySetInfoToEntity
        super.roleAttributeKeySetInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}