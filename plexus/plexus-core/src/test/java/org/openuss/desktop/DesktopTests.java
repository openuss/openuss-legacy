package org.openuss.desktop;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DesktopTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.desktop");
		//$JUnit-BEGIN$
		suite.addTestSuite(DesktopService2IntegrationTest.class);
		suite.addTestSuite(DesktopDaoTest.class);
		//$JUnit-END$
		return suite;
	}

}
