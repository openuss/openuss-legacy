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
public class AuthenticationDomainDaoImpl extends AuthenticationDomainDaoBase {
	/**
	 * @see org.openuss.security.ldap.AuthenticationDomainDao#toAuthenticationDomainInfo(org.openuss.security.ldap.AuthenticationDomain,
	 *      org.openuss.security.ldap.AuthenticationDomainInfo)
	 */
	public void toAuthenticationDomainInfo(AuthenticationDomain sourceEntity,AuthenticationDomainInfo targetVO) {
		super.toAuthenticationDomainInfo(sourceEntity, targetVO);

	}

	/**
	 * @see org.openuss.security.ldap.AuthenticationDomainDao#toAuthenticationDomainInfo(org.openuss.security.ldap.AuthenticationDomain)
	 */
	public AuthenticationDomainInfo toAuthenticationDomainInfo(final AuthenticationDomain entity) {

		AuthenticationDomainInfo authenticationDomainInfo = super.toAuthenticationDomainInfo(entity);

		// set AttributeMapping Id
		if (entity.getAttributeMapping() != null) {
			authenticationDomainInfo.setAttributeMappingId(entity.getAttributeMapping().getId());
		}

		// set LdapServer Ids
		List<Long> ldapServerIds = new ArrayList<Long>();
		Set<LdapServer> ldapServerList = entity.getLdapServers();
		for (LdapServer ldapServer : ldapServerList) {
			ldapServerIds.add(ldapServer.getId());
		}
		authenticationDomainInfo.setLdapServerIds(ldapServerIds);

		return authenticationDomainInfo;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private AuthenticationDomain loadAuthenticationDomainFromAuthenticationDomainInfo(AuthenticationDomainInfo authenticationDomainInfo) {
		AuthenticationDomain authenticationDomain = null;
		if (authenticationDomainInfo.getId() != null) {
			authenticationDomain = this.load(authenticationDomainInfo.getId());
		}
		if (authenticationDomain == null) {
			authenticationDomain = AuthenticationDomain.Factory.newInstance();
		}
		return authenticationDomain;

	}

	/**
	 * @see org.openuss.security.ldap.AuthenticationDomainDao#authenticationDomainInfoToEntity(org.openuss.security.ldap.AuthenticationDomainInfo)
	 */
	public AuthenticationDomain authenticationDomainInfoToEntity(AuthenticationDomainInfo authenticationDomainInfo) {
		AuthenticationDomain entity = this.loadAuthenticationDomainFromAuthenticationDomainInfo(authenticationDomainInfo);
		this.authenticationDomainInfoToEntity(authenticationDomainInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.security.ldap.AuthenticationDomainDao#authenticationDomainInfoToEntity(org.openuss.security.ldap.AuthenticationDomainInfo,
	 *      org.openuss.security.ldap.AuthenticationDomain)
	 */
	public void authenticationDomainInfoToEntity(AuthenticationDomainInfo sourceVO,
		AuthenticationDomain targetEntity, boolean copyIfNull) {
		super.authenticationDomainInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}