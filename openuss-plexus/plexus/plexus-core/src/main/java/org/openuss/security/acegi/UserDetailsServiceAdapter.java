package org.openuss.security.acegi;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.security.UserInfoDetails;
import org.springframework.dao.DataAccessException;

/**
 * FIXME this class produce package cycle!  
 * 
 * @author Ingo Dueppe
 *
 */
public class UserDetailsServiceAdapter implements UserDetailsService {

	private SecurityService securityService;

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		if (securityService == null) {
			throw new IllegalStateException("Adapter is not associated to an SecurityService object!");
		}

		UserInfo userInfo = securityService.getUserByName(username);
		if (userInfo == null) {
			throw new UsernameNotFoundException("Username not found!");
		}
		return (UserInfoDetails) getSecurityService().getUserInfoDetails(userInfo);
		
	}

}
