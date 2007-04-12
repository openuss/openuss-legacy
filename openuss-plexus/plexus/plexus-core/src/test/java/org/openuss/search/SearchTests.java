package org.openuss.search;

import junit.framework.Test;
import junit.framework.TestSuite;

public class SearchTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.search");
		//$JUnit-BEGIN$
		suite.addTestSuite(IndexEventDaoTest.class);
		suite.addTestSuite(LastEventProcessedDaoTest.class);
		suite.addTestSuite(IndexerServiceIntegrationTest.class);
		//$JUnit-END$
		return suite;
	}

}