package org.openuss.repository;

import junit.framework.Test;
import junit.framework.TestSuite;

public class RepositoryTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.repository");
		//$JUnit-BEGIN$
		suite.addTestSuite(RepositoryServiceIntegrationTest.class);
		suite.addTestSuite(RepositoryFileDaoTest.class);
		//$JUnit-END$
		return suite;
	}

}
