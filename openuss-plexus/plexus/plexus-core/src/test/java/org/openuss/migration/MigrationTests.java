package org.openuss.migration;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MigrationTests{
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.migration");
		//$JUnit-BEGIN$
		suite.addTestSuite(MigrationServiceIntegrationTest.class);
		//$JUnit-END$
		return suite;
	}
}