package org.openuss.mail;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MailTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.mail");
		//$JUnit-BEGIN$
		suite.addTestSuite(MailingJobDaoTest.class);		
		suite.addTestSuite(MailServiceIntegrationTest.class);
		suite.addTestSuite(MailToSendDaoTest.class);
		suite.addTestSuite(MailMessageDaoTest.class);
		suite.addTestSuite(TemplateModelDaoTest.class);
		//$JUnit-END$
		return suite;
	}

}