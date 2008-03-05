package org.openuss.braincontest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class BrainContestTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.braincontest");
		//$JUnit-BEGIN$
		//suite.addTestSuite(BrainContestServiceIntegrationTest.class);
		suite.addTestSuite(BrainContestDaoTest.class);
		suite.addTestSuite(AnswerDaoTest.class);
		//$JUnit-END$
		return suite;
	}

}
