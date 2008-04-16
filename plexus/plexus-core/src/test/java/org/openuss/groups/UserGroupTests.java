package org.openuss.groups;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Lutz D. Kramer
 */
public class UserGroupTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.groups");
		//$JUnit-BEGIN$
		suite.addTestSuite(UserGroupDaoTest.class);
		suite.addTestSuite(GroupServiceIntegrationTest.class);
		suite.addTestSuite(GroupNewsletterServiceIntegrationTest.class);
		return suite;
	}

}
