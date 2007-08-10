// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.List;

import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate CourseTypeService class.
 * @see org.openuss.lecture.CourseTypeService
 * @author Florian Dondorf
 */
public class CourseTypeServiceIntegrationTest extends CourseTypeServiceIntegrationTestBase {

	private TestUtility testUtility;
	
	public void testCreateCourseType () {
		logger.info("----> BEGIN access to createCourseType test");
		
		//Create Institute
		Institute institute = testUtility.createUniqueInstituteInDB();
		
		//Create Period
		Period period = testUtility.createUniquePeriodInDB();
		
		//Create CourseInfo
		CourseTypeInfo courseTypeInfo = new CourseTypeInfo();
		courseTypeInfo.setName(testUtility.unique("name"));
		courseTypeInfo.setDescription("description");
		courseTypeInfo.setInstituteId(institute.getId());
		courseTypeInfo.setShortcut(testUtility.unique("testC"));
		
		//Test
		Long courseTypeId = this.getCourseTypeService().create(courseTypeInfo);
		assertNotNull(courseTypeId);
		
		CourseTypeDao courseTypeDao = (CourseTypeDao) this.getApplicationContext().getBean("courseTypeDao");
		CourseType courseType = courseTypeDao.load(courseTypeId);
		assertNotNull(courseType);
		assertEquals(courseTypeInfo.getInstituteId(), courseType.getInstitute().getId());
		assertEquals(courseTypeInfo.getName(), courseType.getName());
		assertEquals(courseTypeInfo.getShortcut(), courseType.getShortcut());
		assertEquals(courseTypeInfo.getDescription(), courseType.getDescription());
		
		logger.info("----> END access to createCourseType test");
	}

	public void testFindCourseType () {
		logger.info("----> BEGIN access to findCourseType test");
		
		//Create CourseType
		CourseType courseType = testUtility.createUniqueCourseTypeInDB();
		flush();
		assertNotNull(courseType.getId());
		
		//Test
		CourseTypeInfo courseTypeInfo = this.getCourseTypeService().findCourseType(courseType.getId());
		assertNotNull(courseTypeInfo);
		assertEquals(courseType.getName(), courseTypeInfo.getName());
		assertEquals(courseType.getDescription(), courseTypeInfo.getDescription());
		assertEquals(courseType.getId(), courseTypeInfo.getId());
		assertEquals(courseType.getInstitute().getId(), courseTypeInfo.getInstituteId());
		assertEquals(courseType.getShortcut(), courseTypeInfo.getShortcut());
		
		logger.info("----> END access to findCourseType test");
	}
	
	public void testFindCourseTypesByInstitute () {
		logger.info("----> BEGIN access to findCourseTypesByInstitute test");
		
		//Create Institutes
		Institute institute1 = testUtility.createUniqueInstituteInDB();
		Institute institute2 = testUtility.createUniqueInstituteInDB();
		
		//Create CourseTypes
		CourseType courseType1 = testUtility.createUniqueCourseTypeInDB();
		courseType1.setInstitute(institute1);
		institute1.getCourseTypes().add(courseType1);
		
		CourseType courseType2 = testUtility.createUniqueCourseTypeInDB();
		courseType2.setInstitute(institute2);
		institute2.getCourseTypes().add(courseType2);
		
		CourseType courseType3 = testUtility.createUniqueCourseTypeInDB();
		courseType3.setInstitute(institute1);
		institute1.getCourseTypes().add(courseType3);
		
		CourseType courseType4 = testUtility.createUniqueCourseTypeInDB();
		courseType4.setInstitute(institute2);
		institute2.getCourseTypes().add(courseType4);
		
		CourseType courseType5 = testUtility.createUniqueCourseTypeInDB();
		courseType5.setInstitute(institute1);
		institute1.getCourseTypes().add(courseType5);
		
		flush();
		
		//Test
		List<CourseTypeInfo> foundCourseTypes = this.getCourseTypeService().findCourseTypesByInstitute(institute1.getId());
		assertNotNull(foundCourseTypes);
		assertEquals(3, foundCourseTypes.size());
		
		foundCourseTypes = this.getCourseTypeService().findCourseTypesByInstitute(institute2.getId());
		assertNotNull(foundCourseTypes);
		assertEquals(2, foundCourseTypes.size());
		
		logger.info("----> END access to findCourseTypesByInstitutes test");
	}
	
	public void testRemoveCourseType () {
		logger.info("----> BEGIN access to removeCourseType test");
		
		//Create Courses
		Course course1 = testUtility.createUniqueCourseInDB();
		Course course2 = testUtility.createUniqueCourseInDB();
		Course course3 = testUtility.createUniqueCourseInDB();
		
		//Create CourseType
		CourseType courseType = testUtility.createUniqueCourseTypeInDB();
		courseType.getCourses().add(course1);
		courseType.getCourses().add(course2);
		courseType.getCourses().add(course3);
	
		flush();
		
		//Test
		assertNotNull(courseType.getId());
		this.getCourseTypeService().removeCourseType(courseType.getId());
		
		CourseTypeDao courseTypeDao = (CourseTypeDao) this.getApplicationContext().getBean("courseTypeDao");
		CourseType courseTypeTest = courseTypeDao.load(courseType.getId());
		assertNull(courseTypeTest);
		
		CourseDao courseDao = (CourseDao) this.getApplicationContext().getBean("courseDao");
		Course courseTest1 = courseDao.load(course1.getId());
		assertNull(courseTest1);
		
		Course courseTest2 = courseDao.load(course2.getId());
		assertNull(courseTest2);
		
		Course courseTest3 = courseDao.load(course3.getId());
		assertNull(courseTest3);
		
		logger.info("----> END access to removeCourseType test");
	}
	
	public void testUpdateCourseType () {
		logger.info("----> BEGIN access to updateCourseType test");
		
		//Create CourseType
		CourseType courseType = testUtility.createUniqueCourseTypeInDB();
		flush();
		
		//Test
		assertNotNull(courseType);
		CourseTypeInfo courseTypeInfo = this.getCourseTypeService().findCourseType(courseType.getId());
		assertNotNull(courseTypeInfo);
		
		courseTypeInfo.setName(testUtility.unique("name"));
		courseTypeInfo.setDescription(testUtility.unique("description"));
		courseTypeInfo.setShortcut(testUtility.unique("shortcut"));
		courseTypeInfo.setInstituteId(testUtility.createUniqueInstituteInDB().getId());
		
		this.getCourseTypeService().update(courseTypeInfo);
		flush();
		
		CourseTypeDao courseTypeDao = (CourseTypeDao) this.getApplicationContext().getBean("courseTypeDao");
		CourseType courseTypeTest = courseTypeDao.load(courseTypeInfo.getId());
		assertNotNull(courseTypeTest);
		assertEquals(courseTypeInfo.getId(), courseTypeTest.getId());
		assertEquals(courseTypeInfo.getName(), courseTypeTest.getName());
		assertEquals(courseTypeInfo.getDescription(), courseTypeTest.getDescription());
		assertEquals(courseTypeInfo.getShortcut(), courseTypeTest.getShortcut());
		assertEquals(courseTypeInfo.getInstituteId(), courseTypeTest.getInstitute().getId());
		
		logger.info("----> END access to updateCourseType test");
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	
}