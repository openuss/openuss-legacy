// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import org.acegisecurity.acl.AclManager;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.openuss.TestUtility;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.University;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.security.acl.ObjectIdentity;
import org.openuss.security.acl.Permission;

/**
 * JUnit Test for Spring Hibernate SecurityService class.
 * 
 * @see org.openuss.security.SecurityService
 * @author Ingo Dueppe
 * @author Ron Haus
 */
public class SecurityServiceIntegrationTest extends SecurityServiceIntegrationTestBase {

	private AclManager aclManager;
	
	public void testSaveLoadAndRemoveUser() {
		User user = testUtility.createUniqueUserInDB();
		User user2 = securityService.getUserObject(securityService.getUserByName(user.getUsername()));
		assertEquals(user, user2);

		securityService.removeUser(getSecurityService().getUser(user.getId()));
	}

	public void testIsNonExistingUserName() {
		User user = testUtility.createUniqueUserInDB();

		assertTrue(securityService.isValidUserName(null, testUtility.unique("Name")));
		assertFalse(securityService.isValidUserName(null, user.getUsername()));
		assertTrue(securityService.isValidUserName(getSecurityService().getUser(user.getId()), user.getUsername()));

	}

	public void testPermission() {
		User user = testUtility.createUniqueUserInDB();
		TestBean bean = new TestBean(TestUtility.unique(), "test get permission");
		securityService.createObjectIdentity(bean, null);
		securityService.setPermissions(user, bean, LectureAclEntry.INSTITUTE_OWN);
		flush();
		Permission found = securityService.getPermissions(user, bean);
		assertNotNull(found);
		securityService.removePermission(user, bean);
//		flush();
		assertNull(securityService.getPermissions(user, bean));
	}
	
	public void testRemovingObjectIdentity() {
		User user = testUtility.createUniqueUserInDB();
		TestBean bean = new TestBean(TestUtility.unique(), "test removing objectidentity");
		ObjectIdentity objectIdentity = securityService.createObjectIdentity(bean, null);
		securityService.setPermissions(user, bean, LectureAclEntry.READ);
		flush();
		assertNotNull(securityService.getPermissions(user, bean));
		assertNotNull(objectIdentity);
		assertNotNull(objectIdentity.getId());
		commit();
		securityService.removeObjectIdentity(bean);
		commit();
		try {
			securityService.getPermissions(user, bean);
			fail("SecurityServiceException expected");
		} catch (SecurityServiceException sse) {
			assertNotNull(sse);
		}
	}

	public void testAclGrants() {

		Group group = securityService.createGroup(testUtility.unique("test-group"), "label", "password",
				GroupType.ADMINISTRATOR);
		User user = testUtility.createUniqueUserInDB();
		securityService.addAuthorityToGroup(user, group);
		Object parent = new TestBean(TestUtility.unique(), "parent");
		Object child = new TestBean(TestUtility.unique(), "child");
		securityService.createObjectIdentity(parent, null);
		securityService.createObjectIdentity(child, parent);
		securityService.setPermissions(group, parent, LectureAclEntry.ASSIST);

		final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user
				.getUsername(), "[Protected]", ((UserImpl) user).getAuthorities());
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authRequest);

		AcegiUtils.setAclManager(aclManager);
		assertTrue(AcegiUtils.hasPermission(child, new Integer[] { LectureAclEntry.ASSIST }));
	}

	public void testRemoveAllPermissions() {
		logger.info("----> BEGIN access to removeAllPermissions test");

		// Create University with a Group (with Users) and Permissions
		University university = testUtility.createUniqueUniversityInDB();
		Group group1 = university.getMembership().getGroups().get(0);
		
		Permission found = securityService.getPermissions(group1, university);
		assertNotNull(found);

		// Add a 2nd Group (with Users) and Permissions
		User user2 = testUtility.createUniqueUserInDB();
		Group group2 = Group.Factory.newInstance();
		group2.setName("UNIVERSITY_" + university.getId() + "_ADMINS2");
		group2.setLabel("autogroup_administrator_label");
		group2.setGroupType(GroupType.ADMINISTRATOR);
		MembershipService membershipService = (MembershipService) applicationContext.getBean("membershipService");
		group2 = membershipService.createGroup(university.getMembership(), group2);
		membershipService.addMember(university.getMembership(), user2);
		securityService.addAuthorityToGroup(user2, group2);
		securityService.setPermissions(group2, university, LectureAclEntry.UNIVERSITY_ADMINISTRATION);
		
		Permission found2 = securityService.getPermissions(group2, university);
		assertNotNull(found2);
		
		// Synchronize with Database
		flush();
		
		// Remove All Permissions
		securityService.removeAllPermissions(university);
		
		// Synchronize with Database
		flush();
		
		// Test
		Permission foundB = securityService.getPermissions(group1, university); Permission found2B =
		securityService.getPermissions(user2, university);
		assertNull(foundB);
		assertNull(found2B);

		logger.info("----> END access to removeAllPermissions test");
	}

	public AclManager getAclManager() {
		return aclManager;
	}

	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}

	public static class TestBean {
		private Long id;
		private String name;

		public TestBean(Long id, String name) {
			this.id = id;
			this.name = name;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return "TestBean@" + name + " id " + id;
		}
	}

}
