package org.openuss.group;

import junit.framework.Test;
import junit.framework.TestSuite;

public class GroupTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.messaging");
		//$JUnit-BEGIN$
		suite.addTestSuite(CustomInfoDaoTest.class);
		suite.addTestSuite(CustomInfoValueDaoTest.class);
		suite.addTestSuite(GroupAdminInformationDaoTest.class);
		suite.addTestSuite(GroupContainerDaoTest.class);
		suite.addTestSuite(GroupMemberDaoTest.class);
		suite.addTestSuite(GroupServiceIntegrationTest.class);
		suite.addTestSuite(GroupWishDaoTest.class);
		suite.addTestSuite(WorkingGroupDaoTest.class);
		//$JUnit-END$
		return suite;
	}

}
