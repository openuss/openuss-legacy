package org.openuss.migration.legacy.dao;

import org.hibernate.ScrollableResults;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class LegacyDaoImplTest extends AbstractTransactionalDataSourceSpringContextTests {

	private LegacyDao legacyDao;
	

	public void testLoadAllAssistants() {
		ScrollableResults results = legacyDao.loadAllAssistants();
		results.close();
	}

	public void testLoadAllStudents() {
		ScrollableResults results = legacyDao.loadAllStudents();
		results.close();
	}

	public void testLoadAllInstitutes() {
		ScrollableResults results = legacyDao.loadAllStudents();
		results.close();
	}

	public void testLoadAllAssistantEnrollments() {
		ScrollableResults results = legacyDao.loadAllAssistantEnrollments();
		results.close();
	}

	public void testLoadAllAssistantFaculty() {
		ScrollableResults results = legacyDao.loadAllAssistantFaculty();
		results.close();
	}

	public void testLoadAllStudentEnrollments() {
		ScrollableResults results = legacyDao.loadAllStudentEnrollments();
		results.close();
	}

	public void testLoadAllStudentFaculty() {
		ScrollableResults results = legacyDao.loadAllStudentFaculty();
		results.close();
	}

	public void testLoadAllStudentSubject() {
		ScrollableResults results = legacyDao.loadAllStudentSubject();
		results.close();
	}

	public void testLoadAllFacultyInformation() {
		ScrollableResults results = legacyDao.loadAllFacultyInformation();
		results.close();
	}

	public void testLoadAllEnrollmentInformation() {
		ScrollableResults results = legacyDao.loadAllEnrollmentInformation();
		results.close();
	}

	public void testLoadAllLectures() {
		ScrollableResults results = legacyDao.loadAllLectures();
		results.close();
	}

	public void testLoadAllEnrollmentAccessList() {
		ScrollableResults results = legacyDao.loadAllEnrollmentAccessList();
		results.close();
	}

	public LegacyDao getLegacyDao() {
		return legacyDao;
	}

	public void setLegacyDao(LegacyDao legacyDao) {
		this.legacyDao = legacyDao;
	}
	
	protected String[] getConfigLocations() {
		return new String[] {
			"classpath*:applicationContext-legacy.xml",
			"classpath*:applicationContext-legacyDataSource.xml"
		};
	}

}
