package org.openuss.groups;

import junit.framework.Test;
import junit.framework.TestSuite;

public class UserGroupTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.lecture");
		//$JUnit-BEGIN$
		suite.addTestSuite(UserGroupDaoTest.class);
		suite.addTestSuite(GroupServiceIntegrationTest.class);
		return suite;
	}

}
