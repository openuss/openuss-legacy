// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;
/**
 * @see org.openuss.security.ldap.LdapServer
 */
public class LdapServerDaoImpl
    extends org.openuss.security.ldap.LdapServerDaoBase
{
    /**
     * @see org.openuss.security.ldap.LdapServerDao#toLdapServerInfo(org.openuss.security.ldap.LdapServer, org.openuss.security.ldap.LdapServerInfo)
     */
    public void toLdapServerInfo(
        org.openuss.security.ldap.LdapServer sourceEntity,
        org.openuss.security.ldap.LdapServerInfo targetVO)
    {
        // @todo verify behavior of toLdapServerInfo
        super.toLdapServerInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.security.ldap.LdapServerDao#toLdapServerInfo(org.openuss.security.ldap.LdapServer)
     */
    public org.openuss.security.ldap.LdapServerInfo toLdapServerInfo(final org.openuss.security.ldap.LdapServer entity)
    {
        // @todo verify behavior of toLdapServerInfo
        return super.toLdapServerInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.security.ldap.LdapServer loadLdapServerFromLdapServerInfo(org.openuss.security.ldap.LdapServerInfo ldapServerInfo)
    {
        // @todo implement loadLdapServerFromLdapServerInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.security.ldap.loadLdapServerFromLdapServerInfo(org.openuss.security.ldap.LdapServerInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.security.ldap.LdapServer ldapServer = this.load(ldapServerInfo.getId());
        if (ldapServer == null)
        {
            ldapServer = org.openuss.security.ldap.LdapServer.Factory.newInstance();
        }
        return ldapServer;
        */
    }

    
    /**
     * @see org.openuss.security.ldap.LdapServerDao#ldapServerInfoToEntity(org.openuss.security.ldap.LdapServerInfo)
     */
    public org.openuss.security.ldap.LdapServer ldapServerInfoToEntity(org.openuss.security.ldap.LdapServerInfo ldapServerInfo)
    {
        // @todo verify behavior of ldapServerInfoToEntity
        org.openuss.security.ldap.LdapServer entity = this.loadLdapServerFromLdapServerInfo(ldapServerInfo);
        this.ldapServerInfoToEntity(ldapServerInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.security.ldap.LdapServerDao#ldapServerInfoToEntity(org.openuss.security.ldap.LdapServerInfo, org.openuss.security.ldap.LdapServer)
     */
    public void ldapServerInfoToEntity(
        org.openuss.security.ldap.LdapServerInfo sourceVO,
        org.openuss.security.ldap.LdapServer targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of ldapServerInfoToEntity
        super.ldapServerInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}