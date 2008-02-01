// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;

import java.util.Date;

import org.acegisecurity.acl.AclManager;
import org.acegisecurity.userdetails.User;
import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.repository.RepositoryService;
import org.openuss.security.*;

/**
 * JUnit Test for Spring Hibernate BuddyService class.
 * @see org.openuss.buddylist.BuddyService
 */
public class BuddyServiceIntegrationTest extends BuddyServiceIntegrationTestBase {
	private AclManager aclManager;
	
	private SecurityService securityService;
	
	private DefaultDomainObject defaultDomainObject;
	
	private BuddyDao buddyDao;
	
	private TagDao tagDao;
	
	private UserDao userDao;

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
		org.openuss.security.User user1 = userDao.create("user1", "asdf", "asdf@asdf.com", true, false, false, false, new Date());
		org.openuss.security.User user2 = userDao.create("user2", "asdf", "asdf@asdf.com", true, false, false, false, new Date());
		UserInfo user2Info = new UserInfo();
		userDao.toUserInfo(user2, user2Info);
		buddyService.addBuddy(user1, user2Info);
		assertEquals(0, buddyService.getBuddyList(user1).size());
		assertEquals(1, buddyService.getAllOpenRequests(user2).size());
		BuddyInfo buddy2 = (BuddyInfo)buddyService.getAllOpenRequests(user2).get(0);
		buddyService.authorizeBuddyRequest(buddy2, true);
		assertEquals(0, buddyService.getAllOpenRequests(user2).size());
		assertEquals(1, buddyService.getBuddyList(user1).size());
		try {
			buddyService.deleteBuddy(user2, buddy2);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		buddyService.deleteBuddy(user1, buddy2);
		assertEquals(0, buddyService.getBuddyList(user1).size());
		}
	
	public void testTagAndBuddyList(){
		org.openuss.security.User user1 = userDao.create("user1", "asdf", "asdf@asdf.com", true, false, false, false, new Date());
		org.openuss.security.User user2 = userDao.create("user2", "asdf", "asdf@asdf.com", true, false, false, false, new Date());
		org.openuss.security.User user3 = userDao.create("user3", "asdf", "asdf@asdf.com", true, false, false, false, new Date());
		UserInfo user2Info = new UserInfo();
		UserInfo user3Info = new UserInfo();
		userDao.toUserInfo(user2, user2Info);
		userDao.toUserInfo(user3, user3Info);
		buddyService.addBuddy(user1, user2Info);
		buddyService.addBuddy(user1, user3Info);
		assertEquals(0, buddyService.getBuddyList(user1).size());
		BuddyInfo buddy2 = (BuddyInfo)buddyService.getAllOpenRequests(user2).get(0);
		BuddyInfo buddy3 = (BuddyInfo)buddyService.getAllOpenRequests(user3).get(0);
		buddyService.authorizeBuddyRequest(buddy2, true);
		buddyService.authorizeBuddyRequest(buddy3, true);
		assertEquals(0, buddyService.getAllUsedTags(user1).size());
		buddyService.addTag(buddy2, "soccer");
		buddy2 = (BuddyInfo)buddyDao.load(buddyDao.TRANSFORM_BUDDYINFO, buddy2.getId());
		assertEquals(1, buddyService.getAllUsedTags(user1).size());
		//assertEquals(1, buddy2.getTags().size());
		buddyService.addTag(buddy3, "soccer");
		assertEquals(1, buddyService.getAllUsedTags(user1).size());
		//assertEquals(1, buddyDao.load(buddy2.getId()).getallTags().size());
		//assertEquals(1, buddyDao.load(buddy3.getId()).getallTags().size());
		assertEquals(2, buddyService.getBuddyList(user1).size());
		/**userDao.remove(user3);
		assertEquals(buddyService.getBuddyList(user1), 1);
		**/
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}