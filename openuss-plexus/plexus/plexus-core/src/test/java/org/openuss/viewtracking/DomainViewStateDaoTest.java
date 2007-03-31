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
		DomainViewState domainViewState = createDomainViewState(id, user.getId(), ViewState.NEW);
		
		assertEquals(id, domainViewState.getDomainViewStatePk().getDomainIdentifier());
		domainViewStateDao.create(domainViewState);
		assertEquals(id, domainViewState.getDomainViewStatePk().getDomainIdentifier());
		assertEquals(user.getId(), domainViewState.getDomainViewStatePk().getUserIdentifier());
		assertEquals(ViewState.NEW, domainViewState.getViewState());
		commit();
		domainViewState = domainViewStateDao.load(domainViewState.getDomainViewStatePk());
		assertEquals(id, domainViewState.getDomainViewStatePk().getDomainIdentifier());
		
	}
	
	public void testUpdateAllAndRemoveBy() {
		Long domainId1 = testUtility.unique();
		Long domainId2 = testUtility.unique();
		Long user1 = testUtility.unique();
		Long user2 = testUtility.unique();
		
		DomainViewState viewState1 = createDomainViewState(domainId1, user1, ViewState.NEW);
		DomainViewState viewState2 = createDomainViewState(domainId1, user2, ViewState.NEW);
		DomainViewState viewState3 = createDomainViewState(domainId2, user2, ViewState.NEW);
		
		domainViewStateDao.create(viewState1);
		domainViewStateDao.create(viewState2);
		domainViewStateDao.create(viewState3);
		
		commit();
		
		domainViewStateDao.updateAllToModified(domainId1);
		
		commit();
		
		DomainViewState loaded1 = domainViewStateDao.load(createPK(domainId1,user1));
		assertEquals(ViewState.MODIFIED, loaded1.getViewState());
		DomainViewState loaded2 = domainViewStateDao.load(createPK(domainId1,user2));
		assertEquals(ViewState.MODIFIED, loaded2.getViewState());
		DomainViewState loaded3 = domainViewStateDao.load(createPK(domainId2,user2));
		assertEquals(ViewState.NEW, loaded3.getViewState());
		
		domainViewStateDao.removeAllByDomain(domainId1);
		
		commit();
		
		assertNull(domainViewStateDao.load(createPK(domainId1,user1)));
		assertNull(domainViewStateDao.load(createPK(domainId1,user2)));
		assertNotNull(domainViewStateDao.load(createPK(domainId2,user2)));
		
		domainViewStateDao.removeAllByDomain(domainId2);
	}
	
	private DomainViewState createDomainViewState(Long domainId, Long userId, ViewState state) {
		DomainViewStatePK pk = createPK(domainId, userId);
		
		DomainViewState viewstate = DomainViewState.Factory.newInstance();
		viewstate.setDomainViewStatePk(pk);
		viewstate.setViewState(state);
		return viewstate;
	}

	private DomainViewStatePK createPK(Long domainId, Long userId) {
		DomainViewStatePK pk = new DomainViewStatePK();
		pk.setDomainIdentifier(domainId);
		pk.setUserIdentifier(userId);
		return pk;
	}

	private void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}
}