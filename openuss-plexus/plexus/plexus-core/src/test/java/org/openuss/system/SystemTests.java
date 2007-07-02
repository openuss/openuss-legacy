package org.openuss.system;

import junit.framework.Test;
import junit.framework.TestSuite;

public class SystemTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.system");
		//$JUnit-BEGIN$
		suite.addTestSuite(SystemPropertyDaoTest.class);
		suite.addTestSuite(SystemServiceIntegrationTest.class);
		//$JUnit-END$
		return suite;
	}

}
