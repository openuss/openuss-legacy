// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.List;

/**
 * @see org.openuss.security.ldap.LdapServer
 */
public class LdapServerDaoImpl extends LdapServerDaoBase {
	/**
	 * @see org.openuss.security.ldap.LdapServerDao#toLdapServerInfo(org.openuss.security.ldap.LdapServer,
	 *      org.openuss.security.ldap.LdapServerInfo)
	 */
	public void toLdapServerInfo(LdapServer sourceEntity, LdapServerInfo targetVO) {
		super.toLdapServerInfo(sourceEntity, targetVO);
	}

	/**
	 * @see org.openuss.security.ldap.LdapServerDao#toLdapServerInfo(org.openuss.security.ldap.LdapServer)
	 */
	public LdapServerInfo toLdapServerInfo(final LdapServer entity) {
		LdapServerInfo ldapServerInfo = super.toLdapServerInfo(entity);

		// set AuthenticationDomain Id
		ldapServerInfo.setAuthenticationDomainId(entity.getAuthenticationDomain().getId());

		// set UserDnPattern Ids
		List<Long> userDnPatternIdList = new ArrayList<Long>();
		List<UserDnPattern> userDnPatternList = entity.getUserDnPatterns();
		for (UserDnPattern userDnPattern : userDnPatternList) {
			userDnPatternIdList.add(userDnPattern.getId());
		}
		ldapServerInfo.setUserDnPatternIds(userDnPatternIdList);

		return ldapServerInfo;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private LdapServer loadLdapServerFromLdapServerInfo(LdapServerInfo ldapServerInfo) {
		LdapServer ldapServer = null;
		if (ldapServerInfo.getId() != null) {
			ldapServer = this.load(ldapServerInfo.getId());
		}
		if (ldapServer == null) {
			ldapServer = LdapServer.Factory.newInstance();
		}
		return ldapServer;

	}

	/**
	 * @see org.openuss.security.ldap.LdapServerDao#ldapServerInfoToEntity(org.openuss.security.ldap.LdapServerInfo)
	 */
	public LdapServer ldapServerInfoToEntity(LdapServerInfo ldapServerInfo) {
		LdapServer entity = this.loadLdapServerFromLdapServerInfo(ldapServerInfo);
		this.ldapServerInfoToEntity(ldapServerInfo, entity, true);

		return entity;
	}

	/**
	 * @see org.openuss.security.ldap.LdapServerDao#ldapServerInfoToEntity(org.openuss.security.ldap.LdapServerInfo,
	 *      org.openuss.security.ldap.LdapServer)
	 */
	public void ldapServerInfoToEntity(LdapServerInfo sourceVO, LdapServer targetEntity, boolean copyIfNull) {
		super.ldapServerInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}