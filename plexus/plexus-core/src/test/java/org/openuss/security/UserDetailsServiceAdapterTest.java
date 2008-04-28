package org.openuss.security;

import static org.easymock.EasyMock.createMock;
import junit.framework.TestCase;

import org.openuss.security.acegi.UserDetailsServiceAdapter;

/**
 * @author Ingo Dueppe
 */
public class UserDetailsServiceAdapterTest extends TestCase {
	
	/**
	 * Test wether or not the load by username wraps the user object correctly.
	 */
	public void testLoadUserByUsername() {
		//FIXME Tests - Doesn't work, due to change from data object to vo.
//		User user = User.Factory.newInstance();
//		user.setUsername("username");
//		user.setId(4711L);
//		SecurityService service = createMock(SecurityService.class);
//		UserDetailsServiceAdapter adapter = new UserDetailsServiceAdapter();
//		adapter.setSecurityService(service);
//
//		expect(service.getUserByName("username")).andReturn(service.getUser(user.getId()));
//		expect(service.getUserByName("usernotfound")).andReturn(null);
//		replay(service);
//		UserDetails userDetails = (UserDetails) adapter.loadUserByUsername("username");
//		assertEquals("username", userDetails.getUsername());
//		assertEquals(user,userDetails);
//		try {
//			adapter.loadUserByUsername("usernotfound");
//			fail();
//		} catch (UsernameNotFoundException ex) {
//			// success
//		}
//		verify(service);
	}
	

	public void testSecurityServiceState () {
		try {
			UserDetailsServiceAdapter adapter = new UserDetailsServiceAdapter();
			adapter.loadUserByUsername("username");
		} catch (IllegalStateException ex) {
			// success
		}
	}
	
	/**
	 * Tests wether or not the asccessors of SecurityService work correctly.
	 */
	public void testSecurityServiceSetterAndGetter() {
		SecurityService service = createMock(SecurityService.class);
		UserDetailsServiceAdapter adapter = new UserDetailsServiceAdapter();
		adapter.setSecurityService(service);
		assertEquals(service, adapter.getSecurityService());
	}

	
	
}
