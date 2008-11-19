package org.openuss.course.newsletter;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CourseNewsletterTests{

		public static Test suite() {
			TestSuite suite = new TestSuite("Test for org.openuss.newsletter");
			//$JUnit-BEGIN$
			suite.addTestSuite(CourseNewsletterServiceIntegrationTest.class);
			//$JUnit-END$
			return suite;
		}
}