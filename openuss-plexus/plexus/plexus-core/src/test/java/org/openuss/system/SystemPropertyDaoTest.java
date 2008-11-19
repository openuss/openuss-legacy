// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.system;


/**
 * JUnit Test for Spring Hibernate SystemPropertyDao class.
 * @see org.openuss.system.SystemPropertyDao
 */
public class SystemPropertyDaoTest extends SystemPropertyDaoTestBase {
	
	public void testSystemPropertyDaoCreate() {
		SystemProperty systemProperty = new SystemPropertyImpl();
		systemProperty.setName(" ");
		systemProperty.setValue(" ");
		assertNull(systemProperty.getId());
		systemPropertyDao.create(systemProperty);
		assertNotNull(systemProperty.getId());
	}
}