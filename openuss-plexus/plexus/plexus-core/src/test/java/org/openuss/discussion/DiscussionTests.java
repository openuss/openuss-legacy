package org.openuss.discussion;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DiscussionTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.discussion");
		//$JUnit-BEGIN$
		suite.addTestSuite(DiscussionServiceIntegrationTest.class);
		suite.addTestSuite(DiscussionWatchDaoTest.class);
		suite.addTestSuite(FormulaDaoTest.class);
		suite.addTestSuite(ForumDaoTest.class);
		suite.addTestSuite(ForumWatchDaoTest.class);
		suite.addTestSuite(TopicDaoTest.class);
		suite.addTestSuite(PostDaoTest.class);
		suite.addTestSuite(TopicTest.class);
		//$JUnit-END$
		return suite;
	}

}
