package org.openuss.security;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openuss.security.acl.AclPermissionIntegrationTest;
import org.openuss.security.acl.ObjectIdentityDaoTest;
import org.openuss.security.acl.PermissionDaoTest;
import org.openuss.security.registration.ActivationCodeDaoTest;
import org.openuss.security.registration.RegistrationServiceIntegrationTest;

public class SecurityTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.security");
		//$JUnit-BEGIN$
		suite.addTestSuite(AuthorityDaoTest.class);
		suite.addTestSuite(GroupDaoTest.class);
		suite.addTestSuite(GroupImplTest.class);
		suite.addTestSuite(SecurityServiceIntegrationTest.class);
		suite.addTestSuite(UserContactDaoTest.class);
		suite.addTestSuite(UserDaoTest.class);
		suite.addTestSuite(UserDetailsServiceAdapterTest.class);
		suite.addTestSuite(UserImplTest.class);
		suite.addTestSuite(UserPreferencesDaoTest.class);
		suite.addTestSuite(UserProfileDaoTest.class);
		// acl package
		suite.addTestSuite(AclPermissionIntegrationTest.class);
		suite.addTestSuite(ObjectIdentityDaoTest.class);
		suite.addTestSuite(PermissionDaoTest.class);
		// registration package
		suite.addTestSuite(ActivationCodeDaoTest.class);
		suite.addTestSuite(RegistrationServiceIntegrationTest.class);
		//$JUnit-END$
		return suite;
	}

}
