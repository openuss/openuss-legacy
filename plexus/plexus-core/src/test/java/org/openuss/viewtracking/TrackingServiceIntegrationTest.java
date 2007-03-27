// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.viewtracking;

import org.openuss.DomainObject;
import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate TrackingService class.
 * @see org.openuss.viewtracking.TrackingService
 */
public class TrackingServiceIntegrationTest extends TrackingServiceIntegrationTestBase {
	
	private TestUtility testUtility;

	public void testViewState() {
		testUtility.createSecureContext();
		DomainObject domainObject = new DomainObject(testUtility.unique());
		
		assertEquals(ViewState.NEW, trackingService.getViewState(domainObject));
		
		trackingService.setViewState(ViewState.MODIFIED, domainObject);
		
		assertEquals(ViewState.MODIFIED, trackingService.getViewState(domainObject));

		trackingService.setRead(domainObject);
		assertEquals(ViewState.READ, trackingService.getViewState(domainObject));

		trackingService.setNew(domainObject);
		assertEquals(ViewState.NEW, trackingService.getViewState(domainObject));
		
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
}