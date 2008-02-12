// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

import org.openuss.lecture.AccessType;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Period;
import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate GroupService class.
 * @see org.openuss.groups.GroupService
 */
public class GroupServiceIntegrationTest extends GroupServiceIntegrationTestBase {
	
	public void testCreateUserGroup () {
		logger.debug("----> BEGIN access to create test <---- ");
		
		//Synchronize with DB
		flush();

		// Create UserGroupInfo
		UserGroupInfo groupInfo = new UserGroupInfo();
		groupInfo.setAccessType(GroupAccessType.OPEN);
		groupInfo.setCalendar(true);
		groupInfo.setChat(true);
		User user = testUtility.createUniqueUserInDB();
		groupInfo.setCreator(user.getId());
		groupInfo.setDescription("A UserGroup");
		groupInfo.setDocuments(true);
		groupInfo.setForum(true);
		groupInfo.setName("UserGroup");
		groupInfo.setPassword(null);
		groupInfo.setShortcut("group");
		
		// Test
		Long groupId = this.getGroupService().createUserGroup(groupInfo, user.getId());
		assertNotNull(groupId);		

		UserGroupDao groupDao = (UserGroupDao) this.getApplicationContext().getBean("userGroupDao");
		UserGroup groupTest = groupDao.load(groupId);
		assertNotNull(groupTest);
		assertEquals(groupInfo.getAccessType(), groupTest.getAccessType());
		assertEquals(groupInfo.getDescription(), groupTest.getDescription());
		assertEquals(groupInfo.getCreator(), groupTest.getCreator().getId());
		assertEquals(groupInfo.getName(), groupTest.getName());
		assertEquals(groupInfo.getPassword(), groupTest.getPassword());
		assertEquals(groupInfo.getShortcut(), groupTest.getShortcut());
		assertEquals(groupInfo.isCalendar(), groupTest.getCalendar().booleanValue());
		assertEquals(groupInfo.isChat(), groupTest.getChat().booleanValue());
		assertEquals(groupInfo.isDocuments(), groupTest.getDocuments().booleanValue());
		assertEquals(groupInfo.isForum(), groupTest.getForum().booleanValue());
		assertEquals(groupInfo.isNewsletter(), groupTest.getNewsletter().booleanValue());
		
		logger.debug("----> END access to create test <---- ");
	}
	
}