package org.openuss.security.acegi;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.openuss.security.UserInfo;

import junit.framework.TestCase;

public class UserInfoDetailsAdapterTest extends TestCase {

	public void testGetAuthorities() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername("username");
		userInfo.setPassword("password");
		UserDetails details = new UserInfoDetailsAdapter(userInfo,new String[]{"ROLE_USER","ROLE_ADMIN","SECURE"});

		assertEquals("username", details.getUsername());
		assertEquals("password", details.getPassword());
		
		GrantedAuthority[] authorities = details.getAuthorities();
		assertNotNull(authorities);
		assertEquals(3, authorities.length);
		
		assertTrue(authorities[0].equals("ROLE_USER"));
		assertTrue(authorities[1].equals("ROLE_ADMIN"));
		assertTrue(authorities[2].equals("SECURE"));
		assertFalse(authorities[2].equals("ROLE_ADMIN"));
		
		assertTrue(authorities[1].equals(authorities[1]));
	}

}
