package org.openuss.security.ldap;

/**
 * @see org.openuss.security.ldap.AuthenticationDomain
 * @author Damian Kemner
 */
public class AuthenticationDomainImpl extends AuthenticationDomainBase implements AuthenticationDomain {

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

	@Override
	public void setName(String name) {
		// not allowed to overwrite AuthenticationDomain name
		if (getName() == null) {
			super.setName(name.toLowerCase());
		}
	}

}