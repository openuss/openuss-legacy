package org.openuss.messaging;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MessagingTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.messaging");
		//$JUnit-BEGIN$
		suite.addTestSuite(MessageDaoTest.class);
		suite.addTestSuite(MessageDaoTest.class);
		//$JUnit-END$
		return suite;
	}

}
