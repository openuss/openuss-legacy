package org.openuss.security;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openuss.security.acl.AclPermissionIntegrationTest;
import org.openuss.security.acl.ObjectIdentityDaoTest;
import org.openuss.security.acl.PermissionDaoTest;

public class SecurityTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.security");
		//$JUnit-BEGIN$
		suite.addTestSuite(GroupDaoTest.class);
		suite.addTestSuite(GroupImplTest.class);
		suite.addTestSuite(SecurityServiceIntegrationTest.class);
		suite.addTestSuite(UserContactDaoTest.class);
		suite.addTestSuite(UserDaoTest.class);
		suite.addTestSuite(UserDetailsServiceAdapterTest.class);
		suite.addTestSuite(UserImplTest.class);
		suite.addTestSuite(UserPreferencesDaoTest.class);
		suite.addTestSuite(UserProfileDaoTest.class);
		// ACL package
		suite.addTestSuite(AclPermissionIntegrationTest.class);
		suite.addTestSuite(ObjectIdentityDaoTest.class);
		suite.addTestSuite(PermissionDaoTest.class);
		//$JUnit-END$
		return suite;
	}

}