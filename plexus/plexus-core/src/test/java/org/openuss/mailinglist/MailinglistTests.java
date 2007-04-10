package org.openuss.mailinglist;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MailinglistTests{

		public static Test suite() {
			TestSuite suite = new TestSuite("Test for org.openuss.mailinglist");
			//$JUnit-BEGIN$
			suite.addTestSuite(MailingListDaoTest.class);
			suite.addTestSuite(MailingListServiceIntegrationTest.class);
			//$JUnit-END$
			return suite;
		}
}