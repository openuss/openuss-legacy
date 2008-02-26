package openuss.spring.services.impl;

import openuss.spring.resources.User;
import openuss.spring.services.AuthenticationService;

/**
 * AuthenticationServiceImpl.
 * Internal service for user authentication.
 * @author Carsten Fiedler
 */
public class AuthenticationServiceImpl implements AuthenticationService {
	private User user;
	
    /**
     * Authenticates user.
     * @param <code>String</code> username
     * @param <code>String</code> password
     * @return <code>boolean</code> true if user exists
     */
	public boolean authenticateUser(String username, String password) {
		boolean userExists = false;
		if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
			userExists = true;
		}
		return userExists;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}