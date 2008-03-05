// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.List;


/**
 * JUnit Test for Spring Hibernate LdapServerDao class.
 * @see org.openuss.security.ldap.LdapServerDao
 */
public class LdapServerDaoTest extends LdapServerDaoTestBase {
	
	private UserDnPatternSetDao userDnPatternSetDao;
	
	public void testLdapServerDaoCreate() {
		LdapServer ldapServer = LdapServer.Factory.newInstance();
		ldapServer.setProviderUrl(" ");
		ldapServer.setPort(389);
		ldapServer.setRootDn("DC=uni-muenster,DC=de");
		ldapServer.setAuthenticationType("DIGEST-MD5");
		ldapServer.setManagerDn("manger_user");
		ldapServer.setManagerPassword("manager_pswd");
		ldapServer.setUseConnectionPool(false);
		ldapServer.setUseLdapContext(false);
		ldapServer.setDescription("WWU Ldap Server");
		ldapServer.setLdapServerType(LdapServerType.ACTIVE_DIRECTORY);
		ldapServer.setEnabled(true);
		
		UserDnPattern userDnPattern = UserDnPattern.Factory.newInstance();
		userDnPattern.setName("CN");
		List<UserDnPattern> userDnPatterns = new ArrayList<UserDnPattern>();
		userDnPatterns.add(userDnPattern);
		
		UserDnPatternSet userDnPatternSet = UserDnPatternSet.Factory.newInstance();
		userDnPatternSet.setName("user dn pattern test ");	
		userDnPatternSet.setUserDnPatterns(userDnPatterns);
		ldapServer.setUserDnPatternSet(userDnPatternSet);
		
		assertNull(userDnPatternSet.getId());
		userDnPatternSetDao.create(userDnPatternSet);
		assertNotNull(userDnPatternSet.getId());
		
		/*
		assertNull(ldapServer.getId());
		ldapServerDao.create(ldapServer);
		assertNotNull(ldapServer.getId());
		*/
	}
	
	public void setUserDPatternSetDao(UserDnPatternSetDao userDnPatternSetDao) {
		this.userDnPatternSetDao = userDnPatternSetDao;
	}
}
