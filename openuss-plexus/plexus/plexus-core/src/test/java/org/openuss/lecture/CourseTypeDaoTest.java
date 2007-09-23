// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate CourseTypeDao class.
 * 
 * @see org.openuss.lecture.CourseTypeDao
 * @author Ron Haus
 */
public class CourseTypeDaoTest extends CourseTypeDaoTestBase {

	private TestUtility testUtility;
	private InstituteDao instituteDao;

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public void testCourseTypeDao() {
		// Create Institute
		Institute institute = testUtility.createUniqueInstituteInDB();

		// Create a CourseType
		CourseType courseType = new CourseTypeImpl();
		courseType.setName(testUtility.unique("courseTypename"));
		courseType.setShortcut(testUtility.unique("shortcut"));

		// associate CourseType with Institute
		institute.add(courseType);

		// persist
		assertNull(courseType.getId());
		instituteDao.update(institute);
		flush();
		assertNotNull(courseType.getId());


		// load
		CourseType courseType2 = courseTypeDao.load(courseType.getId());
		assertEquals(courseType.getId(), courseType2.getId());

		// update
		courseType2.setDescription("description");
		courseTypeDao.update(courseType2);
		flush();

		// remove CourseType from Institute
		institute.remove(courseType2);
		instituteDao.update(institute);
		flush();
	}

	public void testToCourseTypeInfo() {

		// Create a complete CourseType
		CourseType courseType = testUtility.createUniqueCourseTypeInDB();
		courseType.setName(testUtility.unique("name"));
		courseType.setDescription(testUtility.unique("description"));
		courseType.setShortcut(testUtility.unique("shortcut"));
		courseType.setInstitute(testUtility.createUniqueInstituteInDB());
		courseType.getCourses().add(testUtility.createUniqueCourseInDB());

		// Test
		CourseTypeInfo courseTypeInfo = this.getCourseTypeDao().toCourseTypeInfo(courseType);
		assertNotNull(courseTypeInfo);
		assertEquals(courseType.getName(), courseTypeInfo.getName());
		assertEquals(courseType.getDescription(), courseTypeInfo.getDescription());
		assertEquals(courseType.getShortcut(), courseTypeInfo.getShortcut());
		assertEquals(courseType.getInstitute().getId(), courseTypeInfo.getInstituteId());
	}

	public void testCourseTypeInfoToEntity() {

		// Create CoursTypeInfo
		CourseTypeInfo courseTypeInfo = new CourseTypeInfo();
		courseTypeInfo.setShortcut(testUtility.unique("shortcut"));
		courseTypeInfo.setName(testUtility.unique("name"));
		courseTypeInfo.setDescription(testUtility.unique("description"));
		courseTypeInfo.setInstituteId(testUtility.createUniqueInstituteInDB().getId());

		// Test
		CourseType courseType = this.getCourseTypeDao().courseTypeInfoToEntity(courseTypeInfo);
		assertNotNull(courseType);
		assertEquals(courseTypeInfo.getId(), courseType.getId());
		assertEquals(courseTypeInfo.getName(), courseType.getName());
		assertEquals(courseTypeInfo.getShortcut(), courseType.getShortcut());
		assertEquals(courseTypeInfo.getDescription(), courseType.getDescription());
		assertEquals(courseTypeInfo.getInstituteId(), courseType.getInstitute().getId());

	}

}
