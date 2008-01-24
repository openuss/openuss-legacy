package org.openuss.wiki;

import junit.framework.Test;
import junit.framework.TestSuite;

public class WikiTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.wiki");
		//$JUnit-BEGIN$
		suite.addTestSuite(WikiSiteDaoTest.class);
		suite.addTestSuite(WikiServiceIntegrationTest.class);
		//$JUnit-END$
		return suite;
	}

}
