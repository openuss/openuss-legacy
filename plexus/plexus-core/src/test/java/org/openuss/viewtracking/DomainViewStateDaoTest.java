// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.viewtracking;

import org.openuss.TestUtility;
import org.openuss.security.User;


/**
 * JUnit Test for Spring Hibernate DomainViewStateDao class.
 * @see org.openuss.viewtracking.DomainViewStateDao
 */
public class DomainViewStateDaoTest extends DomainViewStateDaoTestBase {
	
	private TestUtility testUtility;
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public void testViewStateDaoCreate() {
		User user = testUtility.createSecureContext();
		Long id = testUtility.unique();
		DomainViewState domainViewState = new DomainViewStateImpl();
		DomainViewStatePK pk = new DomainViewStatePK();
		pk.setDomainIdentifier(id);
		pk.setUserIdentifier(user.getId());
		domainViewState.setDomainViewStatePk(pk);
		domainViewState.setViewState(ViewState.NEW);
		assertEquals(id, domainViewState.getDomainViewStatePk().getDomainIdentifier());
		domainViewStateDao.create(domainViewState);
		assertEquals(id, domainViewState.getDomainViewStatePk().getDomainIdentifier());
		assertEquals(user.getId(), domainViewState.getDomainViewStatePk().getUserIdentifier());
		assertEquals(ViewState.NEW, domainViewState.getViewState());
		commit();
		domainViewState = domainViewStateDao.load(pk);
		assertEquals(id, domainViewState.getDomainViewStatePk().getDomainIdentifier());
	}

	private void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}
}