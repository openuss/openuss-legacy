package org.openuss.security.acegi;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.openuss.security.User;
import org.openuss.security.UserImpl;
import org.openuss.security.SecurityService;
import org.springframework.dao.DataAccessException;

public class UserDetailsServiceAdapter implements UserDetailsService {

	private SecurityService securityService;

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		if (securityService == null)
			throw new IllegalStateException("Adapter is not associated to an SecurityService object!");
		User user = securityService.getUserByName(username);
		if (user == null)
			throw new UsernameNotFoundException("Username not found!");
		return (UserImpl) user;
	}
}
