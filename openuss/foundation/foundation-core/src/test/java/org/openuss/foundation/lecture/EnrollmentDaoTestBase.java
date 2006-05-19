// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.foundation.lecture;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.apache.log4j.Logger;


/**
 * JUnit Test for Spring Hibernate EnrollmentDao class.
 * @see org.openuss.foundation.lecture.EnrollmentDao
 */
public abstract class EnrollmentDaoTestBase extends AbstractTransactionalDataSourceSpringContextTests {
	
	protected static final Logger logger = Logger.getLogger(EnrollmentDaoTest.class);

	protected EnrollmentDao enrollmentDao;
	
	public EnrollmentDao getEnrollmentDao() {
		return enrollmentDao;
	}

	public void setEnrollmentDao(EnrollmentDao enrollmentDao) {
		this.enrollmentDao = enrollmentDao;
	}

	public void testEnrollmentDaoInjection() {
		assertNotNull(enrollmentDao);
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml",
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-beans.xml", 
			"classpath*:beanRefFactory"};
	}
}