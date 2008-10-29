// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.system;

/**
 * JUnit Test for Spring Hibernate SystemService class.
 * @see org.openuss.system.SystemService
 */
public class SystemServiceIntegrationTest extends SystemServiceIntegrationTestBase {
	
	public void testSystemIdendity () {
		long id = systemService.getInstanceIdentity();
		logger.info("system id "+id);
		assertNotSame(1L, id);
	}
	
}