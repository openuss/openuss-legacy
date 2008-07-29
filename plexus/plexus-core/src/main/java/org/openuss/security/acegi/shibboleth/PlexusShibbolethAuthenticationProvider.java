package org.openuss.security.acegi.shibboleth;

import javax.naming.NamingException;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationServiceException;
import org.acegisecurity.adapters.PrincipalAcegiUserToken;
import org.acegisecurity.userdetails.UserDetails;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetails;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetailsImpl;
import org.openuss.migration.CentralUserData;
import org.openuss.migration.CentralUserDataImpl;
import org.openuss.migration.UserMigrationUtility;
import org.openuss.security.SecurityDomainUtility;
import org.openuss.security.UserInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

/**
 * @author Peter Schuh
 *
 */
public class PlexusShibbolethAuthenticationProvider extends ShibbolethAuthenticationProvider {
	private UserMigrationUtility userMigrationUtility;
	
	@Override
	protected void doAfterPropertiesSet() throws Exception {
		super.doAfterPropertiesSet();
		Assert.notNull(this.userMigrationUtility, "A UserMigrationUtility must be set");
	}
	
	@Override
	protected boolean isAlreadyMigrated(UserDetails user, Authentication authentication) {
		return ((UserInfo)user).isCentralUser();
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
    protected void migrate(UserDetails user, Authentication authentication) {
		getUserMigrationUtility().migrate((UserInfo)user, mapShibbolethUserAttributesToCentralUserData(authentication));
		markUserAsMigratedOne(user, authentication);
    }

	
	/**
	 * Sets a marker for a migrated user. This marker is used within frontend to decide whether to show an appropriate message.
	 * @param user
	 * @param authentication
	 */
	private void markUserAsMigratedOne(UserDetails user, Authentication authentication) {
		((ShibbolethUserDetails)authentication.getDetails()).getAttributes().put(SecurityDomainUtility.USER_MIGRATION_INDICATOR_KEY, new Boolean(true));
	}
	
	@Override
	protected boolean reconcile(UserDetails user, Authentication authentication) {
		return getUserMigrationUtility().reconcile((UserInfo)user, mapShibbolethUserAttributesToCentralUserData(authentication), false);
	}

	private CentralUserData mapShibbolethUserAttributesToCentralUserData(
			Authentication authentication) {
		CentralUserData centralUserData = new CentralUserDataImpl();
		try {
			centralUserData.setUsername(generateUsernameFromAuthentication(authentication));
			centralUserData.setFirstName((String)((ShibbolethUserDetails)authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.FIRSTNAME_KEY).get());
			centralUserData.setLastName((String)((ShibbolethUserDetails)authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.LASTNAME_KEY).get());
			centralUserData.setEmail((String)((ShibbolethUserDetails)authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.EMAIL_KEY).get());
			centralUserData.setAuthenticationDomainName((String)((ShibbolethUserDetails)authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINNAME_KEY).get());
			centralUserData.setAuthenticationDomainId((Long)((ShibbolethUserDetails)authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINID_KEY).get());
		} catch (NamingException e) {
			// do nothing
		}
		return centralUserData;
	}

	public UserMigrationUtility getUserMigrationUtility() {
		return userMigrationUtility;
	}

	public void setUserMigrationUtility(UserMigrationUtility userMigrationUtility) {
		this.userMigrationUtility = userMigrationUtility;
	}

}
