package org.openuss.lecture;

import junit.framework.Test;
import junit.framework.TestSuite;

public class LectureTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.openuss.lecture");
		//$JUnit-BEGIN$
		suite.addTestSuite(UniversityIndexingAspectTest.class);
		suite.addTestSuite(DepartmentIndexingAspectTest.class);
		suite.addTestSuite(InstituteIndexingAspectTest.class);
		suite.addTestSuite(InstituteIndexerTest.class);
		suite.addTestSuite(CourseIndexingAspectTest.class);
		suite.addTestSuite(UniversityDaoTest.class);
		suite.addTestSuite(PeriodDaoTest.class);
		suite.addTestSuite(DepartmentDaoTest.class);
		suite.addTestSuite(InstituteDaoTest.class);
		suite.addTestSuite(CourseTypeDaoTest.class);
		suite.addTestSuite(CourseDaoTest.class);
		suite.addTestSuite(CourseMemberDaoTest.class);
		suite.addTestSuite(UniversityServiceIntegrationTest.class);
		suite.addTestSuite(DepartmentServiceIntegrationTest.class);
		suite.addTestSuite(InstituteServiceIntegrationTest.class);
		suite.addTestSuite(CourseTypeServiceIntegrationTest.class);
		suite.addTestSuite(CourseServiceIntegrationTest.class);
		//suite.addTestSuite(LectureServiceIntegrationTest.class);
		//suite.addTestSuite(LectureServiceTest.class);
		//$JUnit-END$
		return suite;
	}

}
