package org.openuss.feed;

import junit.framework.Test;
import junit.framework.TestSuite;

public class FeedTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.feed");
		//$JUnit-BEGIN$
		suite.addTestSuite(FeedServiceIntegrationTest.class);
		//$JUnit-END$
		return suite;
	}

}
