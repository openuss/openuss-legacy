package org.openuss.commands;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CommandTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.commands");
		//$JUnit-BEGIN$
		suite.addTestSuite(CommandDaoTest.class);
		suite.addTestSuite(CommandServiceIntegrationTest.class);
		suite.addTestSuite(LastProcessedCommandDaoTest.class);
		suite.addTestSuite(ClusterCommandProcessorTest.class);
		//$JUnit-END$
		return suite;
	}

}
