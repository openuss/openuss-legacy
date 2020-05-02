package org.openuss.security;

import static org.easymock.classextension.EasyMock.createMock;
import junit.framework.TestCase;

import org.acegisecurity.GrantedAuthority;

/**
 * 
 * @author Ingo Düppe
 */
public class UserImplTest extends TestCase {

	private Group group;
	private UserImpl user;

	@Override
	public void setUp() {
		group = createMock(Group.class);
		user = new UserImpl();
	}
	
	public void testGrantedAuthority() {
		user.addGroup(Group.Factory.newInstance(GroupType.ADMINISTRATOR, "admins"));
		user.addGroup(Group.Factory.newInstance(GroupType.ASSISTANT,"asssistants"));
		GrantedAuthority[] authorities = user.getAuthorities();
		assertEquals(2, authorities.length);
		assertTrue(authorities[0] instanceof Authority);
	}

	/**
	 * Test add and remove role 
	 */
	public void testAddAndRemoveRoles() {
		setUp();
		assertEquals(0, user.getGroups().size());
		user.addGroup(group);
		assertEquals(1, user.getGroups().size());
		// remove different role
		user.removeGroup(createMock(Group.class));
		assertEquals(1, user.getGroups().size());
		// remove role
		user.removeGroup(group);
		assertEquals(0, user.getGroups().size());
	}

	
	/**
	 * Test addRole if it is not initialized before.
	 */
	public void testAddRoles() {
		user.addGroup(group);
		assertEquals(1, user.getGroups().size());
	}

	/**
	 * Test removeRole before initialization.
	 */
	public void testRemoveRoles() {
		user.removeGroup(null);
		user.removeGroup(group);
	}
	
	public void testUserProperties() {
		user.setAccountExpired(true);
		assertFalse(user.isAccountNonExpired());
		user.setAccountLocked(true);
		assertFalse(user.isAccountNonLocked());
		user.setCredentialsExpired(true);
		assertFalse(user.isCredentialsNonExpired());
	}
	
	
}
