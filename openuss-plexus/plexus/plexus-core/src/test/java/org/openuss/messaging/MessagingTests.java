package org.openuss.messaging;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MessagingTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.messaging");
		//$JUnit-BEGIN$
		suite.addTestSuite(MessageDaoTest.class);
		suite.addTestSuite(MessageJobDaoTest.class);
		suite.addTestSuite(MessageServiceIntegrationTest.class);
		suite.addTestSuite(RecipientDaoTest.class);
		suite.addTestSuite(TemplateMessageDaoTest.class);
		suite.addTestSuite(TemplateParameterDaoTest.class);
		suite.addTestSuite(TextMessageDaoTest.class);
		suite.addTestSuite(MessageSendingCommandTest.class);
		//$JUnit-END$
		return suite;
	}

}
