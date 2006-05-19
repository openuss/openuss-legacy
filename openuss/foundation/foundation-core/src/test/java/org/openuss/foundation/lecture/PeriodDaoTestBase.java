// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.foundation.lecture;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.apache.log4j.Logger;


/**
 * JUnit Test for Spring Hibernate PeriodDao class.
 * @see org.openuss.foundation.lecture.PeriodDao
 */
public abstract class PeriodDaoTestBase extends AbstractTransactionalDataSourceSpringContextTests {
	
	protected static final Logger logger = Logger.getLogger(PeriodDaoTest.class);

	protected PeriodDao periodDao;
	
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