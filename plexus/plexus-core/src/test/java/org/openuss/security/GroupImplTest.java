package org.openuss.security;

import static org.easymock.EasyMock.createMock;

import java.util.Collection;

import junit.framework.TestCase;

/**
 * 
 * @author Ingo Düppe
 */
public class GroupImplTest extends TestCase {
	
	public void testGetAuthority() {
		GroupImpl role = new GroupImpl();
		role.setName("role_name");
		assertEquals("role_name", role.getAuthority());
	}
	
	public void testAddMember() {
		Group group = Group.Factory.newInstance();
		User user = createMock(User.class);
		assertEquals(0, group.getMembers().size());
		group.addMember(user);
		// check user
		Collection<Authority> members = group.getMembers();
		assertEquals(1, members.size());
		assertEquals(user, members.iterator().next());
		// remove not existing users
		group.removeMember(User.Factory.newInstance());
		group.removeMember(null);
		// check again
		members = group.getMembers();
		assertEquals(1, members.size());
		assertEquals(user, members.iterator().next());
		// remove user
		group.removeMember(user);
		assertEquals(0, group.getMembers().size());
	}

	
	
}
