// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @see org.openuss.security.ldap.AuthenticationDomain
 */
public class AuthenticationDomainDaoImpl
    extends org.openuss.security.ldap.AuthenticationDomainDaoBase
{
    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#toAuthenticationDomainInfo(org.openuss.security.ldap.AuthenticationDomain, org.openuss.security.ldap.AuthenticationDomainInfo)
     */
    public void toAuthenticationDomainInfo(
        org.openuss.security.ldap.AuthenticationDomain sourceEntity,
        org.openuss.security.ldap.AuthenticationDomainInfo targetVO)
    {
        super.toAuthenticationDomainInfo(sourceEntity, targetVO);
        
    }


    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#toAuthenticationDomainInfo(org.openuss.security.ldap.AuthenticationDomain)
     */
    public org.openuss.security.ldap.AuthenticationDomainInfo toAuthenticationDomainInfo(final org.openuss.security.ldap.AuthenticationDomain entity) {
    	
    	AuthenticationDomainInfo authenticationDomainInfo = super.toAuthenticationDomainInfo(entity);
    	
//    	set AttributeMapping Id
    	if(entity.getAttributeMapping() != null) {
    		authenticationDomainInfo.setAttributeMappingId(entity.getAttributeMapping().getId());	
    	}
    	 
//    	set LdapServer Ids
    	List<Long> ldapServerIds = new ArrayList<Long>();
    	Set<LdapServer> ldapServerList = entity.getLdapServers();
    	for (LdapServer ldapServer : ldapServerList) {
    		ldapServerIds.add(ldapServer.getId());			
		}
    	authenticationDomainInfo.setLdapServerIds(ldapServerIds);
    	
    	return authenticationDomainInfo;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.security.ldap.AuthenticationDomain loadAuthenticationDomainFromAuthenticationDomainInfo(org.openuss.security.ldap.AuthenticationDomainInfo authenticationDomainInfo)
    {
        
//        throw new java.lang.UnsupportedOperationException("org.openuss.security.ldap.loadAuthenticationDomainFromAuthenticationDomainInfo(org.openuss.security.ldap.AuthenticationDomainInfo) not yet implemented.");

        org.openuss.security.ldap.AuthenticationDomain authenticationDomain = this.load(authenticationDomainInfo.getId());
        if (authenticationDomain == null)
        {
            authenticationDomain = org.openuss.security.ldap.AuthenticationDomain.Factory.newInstance();
        }
        return authenticationDomain;

    }

    
    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#authenticationDomainInfoToEntity(org.openuss.security.ldap.AuthenticationDomainInfo)
     */
    public org.openuss.security.ldap.AuthenticationDomain authenticationDomainInfoToEntity(org.openuss.security.ldap.AuthenticationDomainInfo authenticationDomainInfo)
    {
        org.openuss.security.ldap.AuthenticationDomain entity = this.loadAuthenticationDomainFromAuthenticationDomainInfo(authenticationDomainInfo);
        this.authenticationDomainInfoToEntity(authenticationDomainInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.security.ldap.AuthenticationDomainDao#authenticationDomainInfoToEntity(org.openuss.security.ldap.AuthenticationDomainInfo, org.openuss.security.ldap.AuthenticationDomain)
     */
    public void authenticationDomainInfoToEntity(
        org.openuss.security.ldap.AuthenticationDomainInfo sourceVO,
        org.openuss.security.ldap.AuthenticationDomain targetEntity,
        boolean copyIfNull)
    {
        super.authenticationDomainInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}