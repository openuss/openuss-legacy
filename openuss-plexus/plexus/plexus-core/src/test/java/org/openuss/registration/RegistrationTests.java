package org.openuss.registration;

import junit.framework.Test;
import junit.framework.TestSuite;

public class RegistrationTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.registration");
		//$JUnit-BEGIN$
		suite.addTestSuite(ActivationCodeDaoTest.class);
		suite.addTestSuite(RegistrationServiceIntegrationTest.class);
		suite.addTestSuite(UserActivationCodeDaoTest.class);
		suite.addTestSuite(InstituteActivationCodeDaoTest.class);
		//$JUnit-END$
		return suite;
	}

}
