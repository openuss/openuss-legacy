package org.openuss.lecture;

import junit.framework.Test;
import junit.framework.TestSuite;

public class LectureTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.lecture");
		//$JUnit-BEGIN$
		suite.addTestSuite(FacultyIndexingAspectTest.class);
		suite.addTestSuite(CourseDaoTest.class);
		suite.addTestSuite(CourseMemberDaoTest.class);
		suite.addTestSuite(CourseServiceIntegrationTest.class);
		suite.addTestSuite(FacultyDaoTest.class);
		suite.addTestSuite(LectureIntegrationTest.class);
		suite.addTestSuite(LectureServiceIntegrationTest.class);
		suite.addTestSuite(LectureServiceTest.class);
		suite.addTestSuite(PeriodDaoTest.class);
		suite.addTestSuite(CourseTypeDaoTest.class);
		suite.addTestSuite(FacultyIndexerTest.class);
		//$JUnit-END$
		return suite;
	}

}
