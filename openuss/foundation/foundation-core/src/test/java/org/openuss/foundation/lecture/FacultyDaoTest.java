// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.foundation.lecture;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * JUnit Test for Spring Hibernate FacultyDao class.
 * @see org.openuss.foundation.lecture.FacultyDao
 */
public class FacultyDaoTest extends AbstractTransactionalDataSourceSpringContextTests {
	
	private FacultyDao facultyDao;
	
	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}

	public void testFacultyDaoInjection() {
		assertNotNull(facultyDao);
	}

	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml",
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-beans.xml", 
			"classpath*:beanRefFactory"};
	}

}