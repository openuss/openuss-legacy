package org.openuss.aop;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AopTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.braincontest");
		//$JUnit-BEGIN$
		suite.addTestSuite(BookmarkingAspectTest.class);
		suite.addTestSuite(MailSenderAspectTest.class);
		//$JUnit-END$
		return suite;
	}

}
