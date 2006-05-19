package org.openuss.foundation.lecture;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.foundation.lecture");
		// $JUnit-BEGIN$
		suite.addTestSuite(EnrollmentDaoTest.class);
		suite.addTestSuite(SubjectDaoTest.class);
		suite.addTestSuite(FacultyDaoTest.class);
		suite.addTestSuite(PeriodDaoTest.class);
		// $JUnit-END$
		return suite;
	}

	public static void main(String[] args) {
		suite();
	}

}
