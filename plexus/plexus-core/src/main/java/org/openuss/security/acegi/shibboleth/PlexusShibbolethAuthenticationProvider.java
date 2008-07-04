package org.openuss.security.acegi.shibboleth;

import javax.naming.NamingException;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationServiceException;
import org.acegisecurity.adapters.PrincipalAcegiUserToken;
import org.acegisecurity.userdetails.UserDetails;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetails;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetailsImpl;
import org.openuss.security.SecurityDomainUtility;
import org.springframework.dao.DataAccessException;

public class PlexusShibbolethAuthenticationProvider extends ShibbolethAuthenticationProvider {

	@Override
	protected boolean isAlreadyMigrated(UserDetails user, Authentication authentication) {
		return SecurityDomainUtility.containsDomain(user.getUsername());
	}
	
	@Override
    protected UserDetails retrieveUser(String username, PrincipalAcegiUserToken authentication) throws AuthenticationException {

        UserDetails loadedUser;

        try {
			String userEmailAddress = (String)((ShibbolethUserDetails) authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.EMAIL_KEY).get();
            // Due to insufficient interface declaration, we had to extend our UserDetailsService implementation, so that it can also load a user by email address.
			// May the Acegi developers extend the interface by a loadUserByEmail or a similar method.
			loadedUser = this.getUserDetailsService().loadUserByUsername(userEmailAddress);
        } catch (DataAccessException repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        } catch (NamingException e) {
        	throw new IllegalArgumentException(e.getMessage(),e);
        }

        if (loadedUser == null) {
            throw new AuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        }

        return loadedUser;
    }

	protected String generateUsernameFromAuthentication(Authentication authentication) {
		String domainName = "";
		String username = "";
		try {
			 domainName = (String)((ShibbolethUserDetails) authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINNAME_KEY).get();
			 username = (String)((ShibbolethUserDetails) authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.USERNAME_KEY).get();
		} catch (NamingException e) {
			throw new IllegalArgumentException(e.getMessage(),e);
		}
		return SecurityDomainUtility.toUsername(domainName, username);
	}

	@Override
	protected boolean reconcile(UserDetails user, Authentication authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}
