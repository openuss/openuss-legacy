// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.List;


/**
 * JUnit Test for Spring Hibernate UserDnPatternSetDao class.
 * @see org.openuss.security.ldap.UserDnPatternSetDao
 */
public class UserDnPatternSetDaoTest extends UserDnPatternSetDaoTestBase {
	
	public void testUserDnPatternSetDaoCreate() {
		
		UserDnPattern userDnPattern = UserDnPattern.Factory.newInstance();
		userDnPattern.setName("CN");
		List<UserDnPattern> userDnPatterns = new ArrayList<UserDnPattern>();
		userDnPatterns.add(userDnPattern);
		
		UserDnPatternSet userDnPatternSet = UserDnPatternSet.Factory.newInstance();
		userDnPatternSet.setName("user dn pattern test ");	
		userDnPatternSet.setUserDnPatterns(userDnPatterns);
		
		
		assertNull(userDnPatternSet.getId());
		userDnPatternSetDao.create(userDnPatternSet);
		assertNotNull(userDnPatternSet.getId());
	}
}
