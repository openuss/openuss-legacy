package org.openuss.security.acegi;

import junit.framework.TestCase;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

public class UserInfoDetailsAdapterTest extends TestCase {

	public void testGetAuthorities() {
		UserInfoDetailsAdapter details = createUserDetailsObject();

		assertEquals("username", details.getUsername());
		assertEquals("password", details.getPassword());
		
		GrantedAuthority[] authorities = details.getAuthorities();
		assertNotNull(authorities);
		assertEquals(3, authorities.length);
		
		assertTrue(authorities[0].getAuthority().equals("ROLE_USER"));
		assertTrue(authorities[1].getAuthority().equals("ROLE_ADMIN"));
		assertTrue(authorities[2].getAuthority().equals("SECURE"));
		assertFalse(authorities[2].getAuthority().equals("ROLE_ADMIN"));
		
		assertTrue(authorities[1].equals(authorities[1]));
		
		DomainObject domainObject = new DefaultDomainObject(120L);
		assertTrue(details.equals(domainObject));
	}

	private UserInfoDetailsAdapter createUserDetailsObject() {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(120L);
		userInfo.setUsername("username");
		userInfo.setPassword("password");
		return new UserInfoDetailsAdapter(userInfo,new String[]{"ROLE_USER","ROLE_ADMIN","SECURE"});
	}
	
	/**
	 * 
	 */
	public void testEquals() {
		UserDetails details = createUserDetailsObject();
		
		User user = User.Factory.newInstance();
		user.setId(120L);
		
		assertEquals(user, details);
		assertEquals(details, user);
	}

}
