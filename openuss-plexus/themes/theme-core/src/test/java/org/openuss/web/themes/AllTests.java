package org.openuss.web.themes;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.web.themes");
		// $JUnit-BEGIN$
		suite.addTestSuite(ThemeBeanTest.class);
		suite.addTestSuite(ThemeHotDeployListenerTest.class);
		suite.addTestSuite(ThemeManageBeanTest.class);
		// $JUnit-END$
		return suite;
	}

	public static void main(String[] args) {
		suite();
	}

}
