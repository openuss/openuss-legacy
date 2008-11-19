package org.openuss.statistics;

import junit.framework.Test;
import junit.framework.TestSuite;

public class StatisticsTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.statistics");
		//$JUnit-BEGIN$
		suite.addTestSuite(OnlineSessionDaoTest.class);
		suite.addTestSuite(OnlineStatisticServiceIntegrationTest.class);
		suite.addTestSuite(SystemStatisticDaoTest.class);
		//$JUnit-END$
		return suite;
	}

}
