// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntityImpl.vsl in in openuss/tools/andromda/templates.
//
package org.openuss.security.ldap;

/**
 * @see org.openuss.security.ldap.AuthenticationDomain
 * @author Damian Kemner
 */
public class AuthenticationDomainImpl
    extends org.openuss.security.ldap.AuthenticationDomainBase
	implements org.openuss.security.ldap.AuthenticationDomain
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -318235353669895799L;
    
    public void addServer(org.openuss.security.ldap.LdapServer ldapServer) {
    	if (ldapServer != null) {
    		getLdapServers().add(ldapServer);
    	}
    }
    
    public void removeServer(org.openuss.security.ldap.LdapServer ldapServer) {
    	if (ldapServer != null) {
    		getLdapServers().remove(ldapServer);
    	}
    }

}