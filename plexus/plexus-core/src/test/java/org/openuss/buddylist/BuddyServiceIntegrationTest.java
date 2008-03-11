// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 * @author Ralf Plattfaut
 */
package org.openuss.buddylist;

import java.util.*;

import org.acegisecurity.acl.AclManager;
import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.*;

/**
 * JUnit Test for Spring Hibernate BuddyService class.
 * @see org.openuss.buddylist.BuddyService
 */
public class BuddyServiceIntegrationTest extends BuddyServiceIntegrationTestBase {
	private AclManager aclManager;
	
	private SecurityService securityService;
	
	private BuddyDao buddyDao;
	
	private TagDao tagDao;
	
	private UserDao userDao;

	@Override
	protected void onSetUpInTransaction() throws Exception {
		AcegiUtils.setAclManager(aclManager);
		testUtility.createUserSecureContext();
		super.onSetUpInTransaction();
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
	 * @throws BuddyApplicationException 
	 */
	public void testBuddyList() throws BuddyApplicationException{
		org.openuss.security.User user2 = userDao.create("user2", "asdf", "asdf@asdf.com", true, false, false, false, new Date());
		UserInfo user2Info = new UserInfo();
		userDao.toUserInfo(user2, user2Info);

			buddyService.addBuddy(user2Info);

//		assertEquals(0, buddyService.getBuddyList().size());
		List<Buddy> buddys = buddyDao.findByUser(user2);
		assertEquals(1, buddys.size());
		Buddy buddy = buddys.get(0);
		buddy.setAuthorized(true);
		assertEquals(1, buddyService.getBuddyList().size());
		try {
			buddyService.deleteBuddy(buddyDao.toBuddyInfo(buddy));
		} catch (Exception e) {
			fail();
		}
		assertEquals(0, buddyService.getBuddyList().size());
		}
	
	public void testTagAndBuddyList(){
		User user2 = userDao.create("user2", "asdf", "asdf@asdf.com", true, false, false, false, new Date());
		User user3 = userDao.create("user3", "asdf", "asdf@asdf.com", true, false, false, false, new Date());
		user2.setFirstName("firstname of user2");
		UserInfo user2Info = new UserInfo();
		UserInfo user3Info = new UserInfo();
		userDao.toUserInfo(user2, user2Info);
		userDao.toUserInfo(user3, user3Info);
		try{
		buddyService.addBuddy(user2Info);
		buddyService.addBuddy(user3Info);
//		assertEquals(0, buddyService.getBuddyList().size());
		Buddy buddy2 = (Buddy)buddyDao.findByUser(user2).get(0);
		Buddy buddy3 = (Buddy)buddyDao.findByUser(user3).get(0);
		buddy2.setAuthorized(true);
		buddy3.setAuthorized(true);
		assertEquals(2, buddyService.getBuddyList().size());
		assertEquals(0, buddyService.getAllUsedTags().size());
		buddyService.addTag(buddyDao.toBuddyInfo(buddy2), "soccer");
		assertEquals(1, buddyService.getAllUsedTags().size());
		buddyService.addTag(buddyDao.toBuddyInfo(buddy3), "soCCer");
		assertEquals(1, buddyService.getAllUsedTags().size());
		assertEquals(1, buddyDao.load(buddy2.getId()).getTags().size());
		assertEquals(1, buddyDao.load(buddy3.getId()).getTags().size());
		buddyService.addTag(buddyDao.toBuddyInfo(buddy2), "soccER");
		assertEquals(1, buddyService.getAllUsedTags().size());
		assertEquals(1, buddyDao.load(buddy2.getId()).getTags().size());
		buddyService.addTag(buddyDao.toBuddyInfo(buddy2), "tennis");
		assertEquals(2, buddyDao.load(buddy2.getId()).getTags().size());
		assertEquals(2, buddyService.getBuddyList().size());		
		//delete Tag
		buddyService.deleteTag(buddyDao.toBuddyInfo(buddy2), "soccer");
		assertEquals(2, buddyService.getAllUsedTags().size());
		assertEquals(1, buddyDao.load(buddy2.getId()).getTags().size());
		assertEquals(1, buddyDao.load(buddy3.getId()).getTags().size());
		//delete Buddy
		buddyService.deleteBuddy(buddyDao.toBuddyInfo(buddy2));
		assertEquals(1, buddyService.getAllUsedTags().size());
		assertEquals(1, buddyDao.load(buddy3.getId()).getTags().size());
		assertEquals(1, buddyService.getBuddyList().size());
		} catch(BuddyApplicationException e1){
			fail();
		}
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}