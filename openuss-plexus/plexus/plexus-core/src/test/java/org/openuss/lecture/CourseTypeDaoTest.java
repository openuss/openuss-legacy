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
	private Institute institute;
	
	private InstituteDao instituteDao;
	
	public CourseTypeDaoTest () {
		setDefaultRollback(false);
	}
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		institute = testUtility.createUniqueInstituteInDB();
		flush();
	}
	
	@Override
	protected void onTearDownInTransaction() throws Exception {
		//testUtility.removePersistInstituteAndDefaultUser();
	}
	
	public void testCourseTypeDao() {
		// create a courseType
		CourseType courseType = new CourseTypeImpl();
		courseType.setName("courseTypename"+System.currentTimeMillis());
		courseType.setShortcut("shortcut"+System.currentTimeMillis());
		
		// associate courseType with institute
		institute.add(courseType);
		//courseType.setInstitute(institute);
		
		// persist
		assertNull(courseType.getId());
		instituteDao.update(institute);
		flush();
		
		assertNotNull(courseType.getId());

		
		// load 
		CourseType s = courseTypeDao.load(courseType.getId());
		assertEquals(courseType.getId(),s.getId());
		
		// update
		s.setDescription("description");
		courseTypeDao.update(s);
		flush();

		// remove courseType from institute
		institute.remove(s);
		instituteDao.update(institute);
		flush();
	}

	public void testToCourseTypeInfo () {
		
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
	
	public void testCourseTypeInfoToEntity () {
		
		//Create CoursTypeInfo
		CourseTypeInfo courseTypeInfo = new CourseTypeInfo();
		courseTypeInfo.setShortcut(testUtility.unique("shortcut"));
		courseTypeInfo.setName(testUtility.unique("name"));
		courseTypeInfo.setDescription(testUtility.unique("description"));
		courseTypeInfo.setInstituteId(testUtility.createUniqueInstituteInDB().getId());
		
		//Test
		CourseType courseType = this.getCourseTypeDao().courseTypeInfoToEntity(courseTypeInfo);
		assertNotNull(courseType);
		assertEquals(courseTypeInfo.getId(), courseType.getId());
		assertEquals(courseTypeInfo.getName(), courseType.getName());
		assertEquals(courseTypeInfo.getShortcut(), courseType.getShortcut());
		assertEquals(courseTypeInfo.getDescription(), courseType.getDescription());
		assertEquals(courseTypeInfo.getInstituteId(), courseType.getInstitute().getId());
		
	}
	
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



}