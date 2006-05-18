// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.foundation.lecture;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * JUnit Test for Spring Hibernate SubjectDao class.
 * @see org.openuss.foundation.lecture.SubjectDao
 */
public class SubjectDaoTest extends AbstractTransactionalDataSourceSpringContextTests {
	
	private SubjectDao subjectDao;
	
	public SubjectDao getSubjectDao() {
		return subjectDao;
	}

	public void setSubjectDao(SubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	public void testSubjectDaoInjection() {
		assertNotNull(subjectDao);
	}

	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml",
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-beans.xml", 
			"classpath*:beanRefFactory"};
	}

}