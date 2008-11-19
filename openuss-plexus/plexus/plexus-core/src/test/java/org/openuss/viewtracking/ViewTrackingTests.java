package org.openuss.viewtracking;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ViewTrackingTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.viewtracking");
		//$JUnit-BEGIN$
		suite.addTestSuite(DomainViewStateDaoTest.class);
		suite.addTestSuite(TrackingServiceIntegrationTest.class);
		//$JUnit-END$
		return suite;
	}

}
