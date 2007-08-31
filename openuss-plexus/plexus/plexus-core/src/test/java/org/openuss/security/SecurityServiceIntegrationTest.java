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
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.security.acl.Permission;

/**
 * JUnit Test for Spring Hibernate SecurityService class.
 * @see org.openuss.security.SecurityService
 */
/**
 * @author Ingo Dueppe
 *
 */
public class SecurityServiceIntegrationTest extends SecurityServiceIntegrationTestBase {
	
	private AclManager aclManager;
	
	public void testSaveLoadAndRemoveUser() {
		User user = testUtility.createDefaultUser();
		
		securityService.createUser(user);
		assertNotNull(user.getId());
		securityService.saveUser(user);
		
		User user2 = securityService.getUserByName(user.getUsername());
		assertEquals(user, user2);
		
		securityService.removeUser(user);	
	}
	
	public void testIsNonExistingUserName() {
		User user = testUtility.createDefaultUser();
		
		assertTrue(securityService.isValidUserName(null, user.getUsername()));
		securityService.createUser(user);
		assertFalse(securityService.isValidUserName(null, user.getUsername()));
		assertTrue(securityService.isValidUserName(user, user.getUsername()));
		
	}
	
	public void testPermission() {
		User user = testUtility.createUserInDB();
		TestBean bean = new TestBean(testUtility.unique(), "test get permission");
		
		securityService.createObjectIdentity(bean, null);
		securityService.setPermissions(user, bean, LectureAclEntry.INSTITUTE_OWN);

		commit();
		
		Permission found = securityService.getPermissions(user, bean);
		assertNotNull(found);
		securityService.removePermission(user, bean);
		
		commit();

		assertNull(securityService.getPermissions(user, bean));
	}

	public void testAclGrants() {
		User user = testUtility.createDefaultUser();
		
		Group group = securityService.createGroup(testUtility.unique("test-group"), "label", "password", GroupType.ADMINISTRATOR);
		user = securityService.createUser(user);
		securityService.addAuthorityToGroup(user, group);
		Object parent = new TestBean(testUtility.unique(),"parent");
		Object child = new TestBean(testUtility.unique(), "child");
		securityService.createObjectIdentity(parent, null);
		securityService.createObjectIdentity(child, parent);
		securityService.setPermissions(group, parent, LectureAclEntry.ASSIST);
		
		final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), "[Protected]", ((UserImpl)user).getAuthorities());
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authRequest);

		AcegiUtils.setAclManager(aclManager);
		assertTrue(AcegiUtils.hasPermission(child, new Integer[]{LectureAclEntry.ASSIST}));
	}
	
	public AclManager getAclManager() {
		return aclManager;
	}

	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}

	public class TestBean {
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
			return "TestBean@"+name+" id "+id;
		}
	}
	
}
