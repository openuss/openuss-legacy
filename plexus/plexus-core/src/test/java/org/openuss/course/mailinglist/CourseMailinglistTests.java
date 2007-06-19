package org.openuss.course.mailinglist;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CourseMailinglistTests{

		public static Test suite() {
			TestSuite suite = new TestSuite("Test for org.openuss.mailinglist");
			//$JUnit-BEGIN$
			suite.addTestSuite(CourseMailingListServiceIntegrationTest.class);
			//$JUnit-END$
			return suite;
		}
}