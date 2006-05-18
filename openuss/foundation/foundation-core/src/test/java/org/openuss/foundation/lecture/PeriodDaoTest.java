// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.foundation.lecture;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * JUnit Test for Spring Hibernate PeriodDao class.
 * @see org.openuss.foundation.lecture.PeriodDao
 */
public class PeriodDaoTest extends AbstractTransactionalDataSourceSpringContextTests {
	
	private PeriodDao periodDao;
	
	public PeriodDao getPeriodDao() {
		return periodDao;
	}

	public void setPeriodDao(PeriodDao periodDao) {
		this.periodDao = periodDao;
	}

	public void testPeriodDaoInjection() {
		assertNotNull(periodDao);
	}

	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml",
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-beans.xml", 
			"classpath*:beanRefFactory"};
	}

}