package org.openuss.enrollment.mailinglist;

import junit.framework.Test;
import junit.framework.TestSuite;

public class EnrollmentMailinglistTests{

		public static Test suite() {
			TestSuite suite = new TestSuite("Test for org.openuss.mailinglist");
			//$JUnit-BEGIN$
			suite.addTestSuite(EnrollmentMailingListServiceIntegrationTest.class);
			//$JUnit-END$
			return suite;
		}
}