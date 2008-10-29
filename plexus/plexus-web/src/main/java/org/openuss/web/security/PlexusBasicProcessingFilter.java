package org.openuss.web.security;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.adapters.PrincipalAcegiUserToken;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuss.security.AttributeMappingKeys;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.security.acegi.UserInfoDetailsAdapter;

/**
* Enables loading of local user profiles for centrally authenticated users, e. g. LDAP users.
* @author Peter Schuh
*/

public class PlexusBasicProcessingFilter extends ExtendedBasicProcessingFilter {

	private static final Log logger = LogFactory.getLog(PlexusBasicProcessingFilter.class);
	
	private SecurityService securityService;

	public PlexusBasicProcessingFilter() {
		super();
	}

	@Override
    protected void onSuccessfulAuthentication(ServletRequest request, ServletResponse response, Authentication authResult) throws IOException {
		super.onSuccessfulAuthentication(request, response, authResult);
		// Load migrated OpenUSS user profile for authenticated LDAP user
		if (authResult.getPrincipal() instanceof LdapUserDetails) {
			// Retrieve email address.
			LdapUserDetails ldapUserDetails = (LdapUserDetails)authResult.getPrincipal();
			String userEmailAddress = "";
			try {
				userEmailAddress = ((String) ldapUserDetails.getAttributes().get(AttributeMappingKeys.EMAIL_KEY).get());				
			} catch (NamingException ne) {
			    // do nothing
			}
			if (StringUtils.isNotBlank(userEmailAddress)) {
				// Load by email address, due to user could have been migrated by another authentication mechanism, e. g. shibboleth.
				// If the shibboleth mechanism uses a different domain name, user cannot be found by username.
				UserInfo user = securityService.getUserByEmail(userEmailAddress);
				if (user != null) {
					// OpenUSS user profile found  
					if (isUserAllowedToLogin(user)) {
						logger.debug("Replacing LDAP-Principal +"+authResult.getPrincipal()+" with "+user.getUsername());
						
						String[] authorities = securityService.getGrantedAuthorities(user);
						UserDetails userDetails = new UserInfoDetailsAdapter(user, authorities);
						
						Authentication authentication = new PrincipalAcegiUserToken("key",user.getUsername(),"[protected]", userDetails.getAuthorities(),userDetails);
						authentication.setAuthenticated(true);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
		}
	}

	protected boolean isUserAllowedToLogin(UserInfo user) {
		boolean allowed = false;
			if (!user.isAccountExpired() && 
				!user.isAccountLocked() && 
				!user.isCredentialsExpired() && 
				user.isEnabled()) {
				allowed = true;
			}
		return allowed;
	}
	
	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}
