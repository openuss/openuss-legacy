package org.openuss.chat;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ChatTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.chattests");
		//$JUnit-BEGIN$
		suite.addTestSuite(ChatUserDaoTest.class);
		suite.addTestSuite(ChatRoomDaoTest.class);
		suite.addTestSuite(ChatMessageDaoTest.class);
		suite.addTestSuite(ChatServiceIntegrationTest.class);
		//$JUnit-END$
		return suite;
	}

}
