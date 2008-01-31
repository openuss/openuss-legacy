// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;

import org.acegisecurity.acl.AclManager;
import org.acegisecurity.userdetails.User;
import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.repository.RepositoryService;
import org.openuss.security.*;
import org.openuss.security.externalAuth.*;

/**
 * JUnit Test for Spring Hibernate BuddyService class.
 * @see org.openuss.buddylist.BuddyService
 */
public class BuddyServiceIntegrationTest extends BuddyServiceIntegrationTestBase {
	private AclManager aclManager;
	
	private BuddyService buddyService;
	
	private SecurityService securityService;
	
	private DefaultDomainObject defaultDomainObject;
	
	private BuddyDao buddyDao;
	
	private TagDao tagDao;
	
	private UserDao userDao;
	
	private SecurityDomainDao securityDomainDao;

	@Override
	protected void onSetUpInTransaction() throws Exception {
		AcegiUtils.setAclManager(aclManager);
		testUtility.createUserSecureContext();
		defaultDomainObject = createDomainObject();
		super.onSetUpInTransaction();
	}
	
	private DefaultDomainObject createDomainObject() {
		DefaultDomainObject defaultDomainObject = new DefaultDomainObject(TestUtility.unique());
		securityService.createObjectIdentity(defaultDomainObject, null);
		return defaultDomainObject;
	}

	public void setBuddyService(BuddyService buddyService) {
		this.buddyService = buddyService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void setBuddyDao(BuddyDao buddyDao) {
		this.buddyDao = buddyDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}
	
	/**
	 * This test tries to add a user to another ones buddylist.
	 * The buddylist should be empty.
	 * The second user accepts now the buddy-request of the first one.
	 * The buddylist should now consist of one entry.
	 */
	public void testBuddyList(){
		SecurityDomain openUSS = securityDomainDao.create("openUSS", "OpenUSS");
		org.openuss.security.User user1 = userDao.create(false, false, false, "a@b.com", true, "24", securityDomainDao.create("openUSS", "OpenUSS"), "User1");
		org.openuss.security.User user2 = userDao.create(false, false, false, "b@c.com", true, "asdf", securityDomainDao.create("openUSS", "OpenUSS"), "user2");
		UserInfo user2Info = new UserInfo();
		userDao.toUserInfo(user2, user2Info);
		buddyService.addBuddy(user1, user2Info);
		assertEquals(0, buddyService.getBuddyList(user1).size());
		BuddyInfo buddy2 = (BuddyInfo)buddyService.getAllOpenRequests(user2).get(0);
		buddyService.authorizeBuddyRequest(buddy2, true);
		assertEquals(1, buddyService.getBuddyList(user1).size());
		}
}