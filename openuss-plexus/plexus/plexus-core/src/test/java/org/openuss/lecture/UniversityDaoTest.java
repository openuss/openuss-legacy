// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;


/**
 * JUnit Test for Spring Hibernate UniversityDao class.
 * @see org.openuss.lecture.UniversityDao
 */
public class UniversityDaoTest extends UniversityDaoTestBase {
	
	public void testUniversityDaoCreate() {
		University university = new UniversityImpl();
		assertNull(university.getId());
		universityDao.create(university);
		assertNotNull(university.getId());
	}
}