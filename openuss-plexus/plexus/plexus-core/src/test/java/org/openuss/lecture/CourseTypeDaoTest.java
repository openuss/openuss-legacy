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
		institute = testUtility.createPersistInstituteWithDefaultUser();
		commit();
	}
	
	@Override
	protected void onTearDownInTransaction() throws Exception {
		testUtility.removePersistInstituteAndDefaultUser();
	}
	
	public void testCourseTypeDao() {
		// create a courseType
		CourseType courseType = new CourseTypeImpl();
		courseType.setName("courseTypename"+System.currentTimeMillis());
		courseType.setShortcut("shortcut"+System.currentTimeMillis());
		
		// associate courseType with institute
		institute.add(courseType);
		courseType.setInstitute(institute);
		
		// persist
		assertNull(courseType.getId());
		instituteDao.update(institute);
		assertNotNull("course type has id", courseType.getId());
		flush();
		
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