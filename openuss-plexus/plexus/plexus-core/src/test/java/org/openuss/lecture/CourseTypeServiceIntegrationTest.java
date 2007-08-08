// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate CourseTypeService class.
 * @see org.openuss.lecture.CourseTypeService
 * @author Florian Dondorf
 */
public class CourseTypeServiceIntegrationTest extends CourseTypeServiceIntegrationTestBase {

	private TestUtility testUtility;
	
	public void testCreateCourseType () {
		
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

	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	
}