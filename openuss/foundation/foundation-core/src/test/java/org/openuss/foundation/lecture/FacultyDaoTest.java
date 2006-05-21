// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.foundation.lecture;

import org.springframework.dao.DataAccessException;

/**
 * JUnit Test for Spring Hibernate FacultyDao class.
 * @see org.openuss.foundation.lecture.FacultyDao
 */
public class FacultyDaoTest extends FacultyDaoTestBase {
	
	public void testFacultyDaoCreate() {
		Faculty faculty = createTestFaculty();
		assertNull(faculty.getId());
		facultyDao.create(faculty);
		assertNotNull(faculty.getId());
	}
	
	public void testUniqueShortcut() {
		Faculty faculty = createTestFaculty();
		
		assertNull(faculty.getId());
		facultyDao.create(faculty);
		assertNotNull(faculty.getId());
		
		Faculty faculty2 = createTestFaculty();
		assertNull(faculty2.getId());
		facultyDao.create(faculty2);
		assertNotNull(faculty2.getId());

		try {
			setComplete();
			endTransaction();
			fail();
		} catch (DataAccessException e) {
			// success - unique constraint  
		}
	}

	private Faculty createTestFaculty() {
//		String className = FacultyBase.Factory.class.getName();
//		System.out.println(className);
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setName("name");
		faculty.setShortcut("shortcut");
		faculty.setOwner("owner");
		return faculty;
	}
	
}