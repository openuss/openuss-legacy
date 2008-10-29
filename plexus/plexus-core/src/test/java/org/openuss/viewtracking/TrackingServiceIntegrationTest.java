package org.openuss.viewtracking;

import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;

/**
 * JUnit Test for Spring Hibernate TrackingService class.
 * @see org.openuss.viewtracking.TrackingService
 */
public class TrackingServiceIntegrationTest extends TrackingServiceIntegrationTestBase {
	
	private TestUtility testUtility;

	public void testViewState() {
		testUtility.createUserSecureContext();
		DefaultDomainObject defaultDomainObject = new DefaultDomainObject(TestUtility.unique());
		
		assertEquals(ViewState.NEW, trackingService.getViewState(defaultDomainObject));
		
		trackingService.setViewState(ViewState.MODIFIED, defaultDomainObject);
		
		assertEquals(ViewState.MODIFIED, trackingService.getViewState(defaultDomainObject));

		trackingService.setRead(defaultDomainObject);
		assertEquals(ViewState.READ, trackingService.getViewState(defaultDomainObject));

		trackingService.setNew(defaultDomainObject);
		assertEquals(ViewState.NEW, trackingService.getViewState(defaultDomainObject));
		
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
}