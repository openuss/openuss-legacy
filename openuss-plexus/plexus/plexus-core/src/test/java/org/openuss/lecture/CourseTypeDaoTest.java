// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate CourseTypeDao class.
 * @see org.openuss.lecture.CourseTypeDao
 */
public class CourseTypeDaoTest extends CourseTypeDaoTestBase {
	
	private TestUtility testUtility;
	private Faculty faculty;
	
	private FacultyDao facultyDao;
	
	public CourseTypeDaoTest () {
		setDefaultRollback(false);
	}
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		faculty = testUtility.createPersistFacultyWithDefaultUser();
		commit();
	}
	
	@Override
	protected void onTearDownInTransaction() throws Exception {
		testUtility.removePersistFacultyAndDefaultUser();
	}
	
	public void testCourseTypeDao() {
		// create a courseType
		CourseType courseType = new CourseTypeImpl();
		courseType.setName("courseTypename"+System.currentTimeMillis());
		courseType.setShortcut("shortcut"+System.currentTimeMillis());
		
		// associate courseType with faculty
		faculty.add(courseType);
		courseType.setFaculty(faculty);
		
		// persist
		assertNull(courseType.getId());
		facultyDao.update(faculty);
		assertNotNull(courseType.getId());
		commit();
		
		// load 
		CourseType s = courseTypeDao.load(courseType.getId());
		assertEquals(courseType.getId(),s.getId());
		
		// update
		s.setDescription("description");
		courseTypeDao.update(s);
		commit();

		// remove courseType from faculty
		faculty.remove(s);
		facultyDao.update(faculty);
		commit();
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}



}