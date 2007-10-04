// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.ArrayList;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.TestUtility;
import org.springframework.dao.DataAccessException;

/**
 * JUnit Test for Spring Hibernate UserDao class.
 * 
 * @see org.openuss.security.UserDao
 * @author Ron Haus, Ingo Dueppe
 */
public class UserDaoTest extends UserDaoTestBase {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(UserDaoTest.class);
	
	private GroupDao groupDao;
	
	private TestUtility testUtility;
	
	public GroupDao getGroupDao() {
		return groupDao;
	}
	
	@Override
	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
		testUtility.createUniqueUserInDB();
	}
	
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	public void testGroupDaoInjection() {
		assertNotNull(groupDao);
	}

	public void testUserDaoCreate() {
		// Create a User
		UserPreferences userPreferences = UserPreferences.Factory.newInstance();
		userPreferences.setLocale("de");
		userPreferences.setTheme("plexus");
		userPreferences.setTimezone(TimeZone.getDefault().getID());
		
		UserContact userContact = UserContact.Factory.newInstance();
		userContact.setFirstName("Unique");
		userContact.setLastName("User");
		userContact.setAddress("Leonardo Campus 5");
		userContact.setCity("M�nster");
		userContact.setCountry("Germany");
		userContact.setPostcode("48149");
		
		User user = User.Factory.newInstance();
		user.setUsername(testUtility.unique("username"));
		user.setPassword("masterkey");
		user.setEmail(testUtility.unique("openuss")+"@e-learning.uni-muenster.de");
		user.setEnabled(true);
		user.setAccountExpired(false);
		user.setCredentialsExpired(false);
		user.setAccountLocked(false);
		
		user.setPreferences(userPreferences);
		user.setContact(userContact);
		user.setGroups(new ArrayList<Group>());
		
		userDao.create(user);
		assertNotNull(user.getId());
		setComplete();
		endTransaction();
		startNewTransaction();

		userDao.remove(user);
		setComplete();
		endTransaction();
	}
	
	public void testGetPassword() {
		// Create a User
		UserPreferences userPreferences = UserPreferences.Factory.newInstance();
		userPreferences.setLocale("de");
		userPreferences.setTheme("plexus");
		userPreferences.setTimezone(TimeZone.getDefault().getID());
		
		UserContact userContact = UserContact.Factory.newInstance();
		userContact.setFirstName("Unique");
		userContact.setLastName("User");
		userContact.setAddress("Leonardo Campus 5");
		userContact.setCity("M�nster");
		userContact.setCountry("Germany");
		userContact.setPostcode("48149");
		
		User user = User.Factory.newInstance();
		user.setUsername(testUtility.unique("username"));
		user.setEmail(testUtility.unique("openuss")+"@e-learning.uni-muenster.de");
		user.setEnabled(true);
		user.setAccountExpired(false);
		user.setCredentialsExpired(false);
		user.setAccountLocked(false);
		
		user.setPreferences(userPreferences);
		user.setContact(userContact);
		user.setGroups(new ArrayList<Group>());
		
		user.setPassword("password");
		
		userDao.create(user);
		
		String password = userDao.getPassword(user.getId());
		assertEquals(user.getPassword(), password);
	}
	
	public void testUniqueUsername() {
		logger.info("---> BEGIN unique username test");
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user.getId());
		User userClone = testUtility.createUniqueUserInDB();
		userClone.setUsername(user.getUsername());
		userDao.create(userClone);
		assertNotNull(userClone.getId());
		
		try {
			setComplete();
			endTransaction();
			fail();
		} catch (DataAccessException e) {
			// success - unique constraint  
			// clean up
		}
		logger.info("---> END unique username test");
	}

	public void testUserRolesRelationship() {
		logger.info("---> BEGIN user roles relationships test");

		Group adminRole = Group.Factory.newInstance("ROLE_ADMIN",GroupType.ROLE);
		groupDao.create(adminRole);
		assertNotNull(adminRole.getId());
		
		User user = testUtility.createUniqueUserInDB();
		user.addGroup(adminRole);
		adminRole.addMember(user);
		
		userDao.create(user);
		assertNotNull(user.getId());
		
		Long id = user.getId();
		user = null;
		user = userDao.load(id);
		assertEquals(id, user.getId());
		assertEquals(1, user.getGroups().size());
		assertTrue(user.getGroups().contains(adminRole));
		
		logger.info("---> END user roles relationships test");
		
	}
	
	public void testFindUserByUsername() {
		logger.info("---> BEGIN find user by username");
		
		User user = testUtility.createUniqueUserInDB();
		assertNotNull(user.getId());
		
		User found = userDao.findUserByUsername(StringUtils.swapCase(user.getUsername()));
		assertNotNull(found);
		assertEquals(user, found);
		
		found = userDao.findUserByUsername("this cannot be found");
		assertNull(found);
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

}