// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.List;

import org.openuss.TestUtility;
import org.springframework.dao.DataAccessException;

/**
 * JUnit Test for Spring Hibernate FacultyDao class.
 * @see org.openuss.lecture.FacultyDao
 */
public class FacultyDaoTest extends FacultyDaoTestBase {
	
	private TestUtility testUtility;
	
	public void testFacultyDaoCreate() {
		Faculty faculty = createTestFaculty();
		assertNull(faculty.getId());
		facultyDao.create(faculty);
		assertNotNull(faculty.getId());
	}
	
	public void testUniqueShortcut() {
		Faculty faculty = createTestFaculty();
		faculty.setShortcut("xxxx");
		assertNull(faculty.getId());
		facultyDao.create(faculty);
		assertNotNull(faculty.getId());
		
		Faculty faculty2 = createTestFaculty();
		faculty2.setShortcut("xxxx");
		assertNull(faculty2.getId());
		facultyDao.create(faculty2);
		assertNotNull(faculty2.getId());

		try {
			setComplete();
			endTransaction();
			fail();
		} catch (DataAccessException e) {
			// success - remove the first one
		}
	}
	
	public void testLoadAllEnabled() {
		Faculty faculty1 = createTestFaculty();
		faculty1.setEnabled(false);
		facultyDao.create(faculty1);

		Faculty faculty2 = createTestFaculty();
		faculty2.setEnabled(true);
		facultyDao.create(faculty2);
		
		setComplete();
		endTransaction();
		
		List faculties = facultyDao.loadAllEnabled();
		assertTrue(faculties.contains(faculty2));
		faculties.contains(faculty1);
		assertFalse(faculties.contains(faculty1));
		
	}

	private Faculty createTestFaculty() {
		LectureBuilder builder = new LectureBuilder().createFaculty(testUtility.createUserInDB());
		return builder.getFaculty();
	}
	

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
}