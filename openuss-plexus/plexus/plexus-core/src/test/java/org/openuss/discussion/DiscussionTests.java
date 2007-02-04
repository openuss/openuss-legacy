package org.openuss.discussion;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DiscussionTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.discussion");
		//$JUnit-BEGIN$
		suite.addTestSuite(ForumDaoTest.class);
		suite.addTestSuite(AttachmentDaoTest.class);
		suite.addTestSuite(FormulaDaoTest.class);
		suite.addTestSuite(DiscussionDaoTest.class);
		suite.addTestSuite(DiscussionWatchDaoTest.class);
		suite.addTestSuite(TopicDaoTest.class);
		//$JUnit-END$
		return suite;
	}

}
