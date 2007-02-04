package org.openuss.security;

public class SecurityTestUtils {

	public static User createDefaultUser() {
		User user = User.Factory.newInstance();
		user.setUsername("username");
		user.setPassword("password");
		user.setEmail("email");
		user.setEnabled(true);
		user.setAccountExpired(true);
		user.setCredentialsExpired(true);
		user.setAccountLocked(true);
		return user;
	}

	
}
