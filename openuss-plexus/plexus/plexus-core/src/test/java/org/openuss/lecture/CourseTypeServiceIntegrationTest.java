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
		testUtility.createUniquePeriodInDB();
		
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
		
		assertNotNull(institute.getCourseTypes());
		assertEquals(1, institute.getCourseTypes().size());
		assertEquals(courseTypeInfo.getName(), institute.getCourseTypes().get(0).getName());
		
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
		
		//Create CourseType without Courses
		CourseType courseType = testUtility.createUniqueCourseTypeInDB();
		Long courseTypeId = courseType.getId();
		assertNotNull(courseTypeId);
		Institute institute = courseType.getInstitute();
		int sizeBefore = institute.getCourseTypes().size();
	
		//Create CourseType with Courses
		Course course = testUtility.createUniqueCourseInDB();
		CourseType courseType2 = course.getCourseType();
		Long courseTypeId2 = courseType2.getId();
		assertNotNull(courseTypeId2);
		Institute institute2 = courseType2.getInstitute();
		int sizeBefore2 = institute2.getCourseTypes().size();
		
		flush();
		
		//Remove CourseType2
		this.getCourseTypeService().removeCourseType(courseType.getId());
		this.getCourseTypeService().removeCourseType(courseType2.getId());
		
		CourseTypeDao courseTypeDao = (CourseTypeDao) this.getApplicationContext().getBean("courseTypeDao");
		CourseType courseTypeTest = courseTypeDao.load(courseTypeId);
		assertNull(courseTypeTest);
		assertEquals(sizeBefore-1, institute.getCourseTypes().size());
		
		CourseType courseTypeTest2 = courseTypeDao.load(courseTypeId2);
		assertNull(courseTypeTest2);
		assertEquals(sizeBefore2-1, institute2.getCourseTypes().size());
		
		logger.info("----> END access to removeCourseType test");
	}
	
	public void testUpdateCourseType () {
		logger.info("----> BEGIN access to updateCourseType test");
		
		//Create CourseType
		CourseType courseType = testUtility.createUniqueCourseTypeInDB();
		assertNotNull(courseType);
		
		flush();
		
		CourseTypeInfo courseTypeInfo = new CourseTypeInfo();
		courseTypeInfo.setId(courseType.getId());
		courseTypeInfo.setName(testUtility.unique("name"));
		courseTypeInfo.setDescription(testUtility.unique("description"));
		courseTypeInfo.setShortcut(testUtility.unique("shortcut"));
		courseTypeInfo.setInstituteId(courseType.getInstitute().getId());
		
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
	
//	public void testIsNoneExistingCourseTypeShortcut () {
//		logger.debug("----> BEGIN access to isNoneExistingCourseTypeShortcut test <---- ");
//		
//		//Create Secure Context
//		testUtility.createUserSecureContext();
//		
//		// Create CourseTypes
//		CourseType courseType1= testUtility.createUniqueCourseTypeInDB();
//		CourseType courseType2= testUtility.createUniqueCourseTypeInDB();
//		flush();
//		
//		// Test
//		CourseTypeDao courseTypeDao = (CourseTypeDao) this.getApplicationContext().getBean("courseTypeDao");
//		Boolean result = this.getCourseTypeService().isNoneExistingCourseTypeShortcut(
//				courseTypeDao.toCourseTypeInfo(courseType1), courseType1.getShortcut());
//		assertNotNull(result);
//		assertTrue(result);
//		
//		result = this.getCourseTypeService().isNoneExistingCourseTypeShortcut(
//				courseTypeDao.toCourseTypeInfo(courseType1), testUtility.unique("shortcut"));
//		assertNotNull(result);
//		assertTrue(result);
//		
//		result = this.getCourseTypeService().isNoneExistingCourseTypeShortcut(
//				courseTypeDao.toCourseTypeInfo(courseType1), courseType2.getShortcut());
//		assertNotNull(result);
//		assertFalse(result);
//		
//		logger.debug("----> END access to isNoneExistingCourseTypeShortcut test <---- ");
//	}
	
	public void testIsNoneExistingCourseTypeName () {
		logger.debug("----> BEGIN access to isNoneExistingCourseTypeName test <---- ");
		
		//Create Secure Context
		testUtility.createUserSecureContext();
		
		// Create CourseTypes
		CourseType courseType1= testUtility.createUniqueCourseTypeInDB();
		CourseType courseType2= testUtility.createUniqueCourseTypeInDB();
		flush();
		
		// Test
		CourseTypeDao courseTypeDao = (CourseTypeDao) this.getApplicationContext().getBean("courseTypeDao");
		Boolean result = this.getCourseTypeService().isNoneExistingCourseTypeName(
				courseTypeDao.toCourseTypeInfo(courseType1), courseType1.getName());
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getCourseTypeService().isNoneExistingCourseTypeName(
				courseTypeDao.toCourseTypeInfo(courseType1), testUtility.unique("name"));
		assertNotNull(result);
		assertTrue(result);
		
		result = this.getCourseTypeService().isNoneExistingCourseTypeName(
				courseTypeDao.toCourseTypeInfo(courseType1), courseType2.getName());
		assertNotNull(result);
		assertFalse(result);
		
		logger.debug("----> END access to isNoneExistingCourseTypeName test <---- ");
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	
}