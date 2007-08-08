// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.Date;


/**
 * JUnit Test for Spring Hibernate ApplicationDao class.
 * @see org.openuss.lecture.ApplicationDao
 */
public class ApplicationDaoTest extends ApplicationDaoTestBase {
	
	public void testApplicationDaoCreate() {
		Application application = Application.Factory.newInstance();
		application.setApplicationDate(new Date());
		application.setConfirmed(false);
		assertNull(application.getId());
		applicationDao.create(application);
		assertNotNull(application.getId());
	}
}
