package org.openuss.newsletter;

import junit.framework.Test;
import junit.framework.TestSuite;

public class NewsletterTests{

		public static Test suite() {
			TestSuite suite = new TestSuite("Test for org.openuss.newsletter");
			//$JUnit-BEGIN$
			suite.addTestSuite(MailDaoTest.class);
			suite.addTestSuite(NewsletterDaoTest.class);
			suite.addTestSuite(NewsletterServiceIntegrationTest.class);
			suite.addTestSuite(SubscriberDaoTest.class);
			//$JUnit-END$
			return suite;
		}
}