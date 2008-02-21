// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;
/**
 * @see org.openuss.security.ldap.RoleAttributeKey
 */
public class RoleAttributeKeyDaoImpl
    extends org.openuss.security.ldap.RoleAttributeKeyDaoBase
{
    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#toRoleAttributeKeyInfo(org.openuss.security.ldap.RoleAttributeKey, org.openuss.security.ldap.RoleAttributeKeyInfo)
     */
    public void toRoleAttributeKeyInfo(
        org.openuss.security.ldap.RoleAttributeKey sourceEntity,
        org.openuss.security.ldap.RoleAttributeKeyInfo targetVO)
    {
        // @todo verify behavior of toRoleAttributeKeyInfo
        super.toRoleAttributeKeyInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#toRoleAttributeKeyInfo(org.openuss.security.ldap.RoleAttributeKey)
     */
    public org.openuss.security.ldap.RoleAttributeKeyInfo toRoleAttributeKeyInfo(final org.openuss.security.ldap.RoleAttributeKey entity)
    {
        // @todo verify behavior of toRoleAttributeKeyInfo
        return super.toRoleAttributeKeyInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.security.ldap.RoleAttributeKey loadRoleAttributeKeyFromRoleAttributeKeyInfo(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyInfo)
    {
        // @todo implement loadRoleAttributeKeyFromRoleAttributeKeyInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.security.ldap.loadRoleAttributeKeyFromRoleAttributeKeyInfo(org.openuss.security.ldap.RoleAttributeKeyInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.security.ldap.RoleAttributeKey roleAttributeKey = this.load(roleAttributeKeyInfo.getId());
        if (roleAttributeKey == null)
        {
            roleAttributeKey = org.openuss.security.ldap.RoleAttributeKey.Factory.newInstance();
        }
        return roleAttributeKey;
        */
    }

    
    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#roleAttributeKeyInfoToEntity(org.openuss.security.ldap.RoleAttributeKeyInfo)
     */
    public org.openuss.security.ldap.RoleAttributeKey roleAttributeKeyInfoToEntity(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyInfo)
    {
        // @todo verify behavior of roleAttributeKeyInfoToEntity
        org.openuss.security.ldap.RoleAttributeKey entity = this.loadRoleAttributeKeyFromRoleAttributeKeyInfo(roleAttributeKeyInfo);
        this.roleAttributeKeyInfoToEntity(roleAttributeKeyInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.security.ldap.RoleAttributeKeyDao#roleAttributeKeyInfoToEntity(org.openuss.security.ldap.RoleAttributeKeyInfo, org.openuss.security.ldap.RoleAttributeKey)
     */
    public void roleAttributeKeyInfoToEntity(
        org.openuss.security.ldap.RoleAttributeKeyInfo sourceVO,
        org.openuss.security.ldap.RoleAttributeKey targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of roleAttributeKeyInfoToEntity
        super.roleAttributeKeyInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}