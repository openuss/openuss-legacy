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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuss.security.AttributeMappingKeys;
import org.openuss.security.SecurityConstants;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserImpl;

/**
* Enables loading of local user profiles for centrally authenticated users, e. g. LDAP users.
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
			// Retrieve username
			LdapUserDetails ldapUserDetails = (LdapUserDetails)authResult.getPrincipal();
			String username = "";
			try {
				String authenticationDomainName = ((String) ldapUserDetails.getAttributes().get(AttributeMappingKeys.AUTHENTICATIONDOMAINNAME_KEY).get());
				username = ((String) ldapUserDetails.getAttributes().get(AttributeMappingKeys.USERNAME_KEY).get());
				username = SecurityConstants.USERNAME_DOMAIN_DELIMITER+authenticationDomainName+SecurityConstants.USERNAME_DOMAIN_DELIMITER+username;				
			} catch (NamingException ne) {
			    // do nothing
			}
			if (username!=null && !username.equals("")) {
				User user = securityService.getUserByName(username);
				if (user!=null) {
					// OpenUSS user profile found  
					logger.debug("Replacing LDAP-Principal +"+authResult.getPrincipal()+" with "+user.getUsername());
					UserImpl principal = (UserImpl) user;
					UserDetails userDetails = principal;
					Authentication authentication = new PrincipalAcegiUserToken(null,user.getUsername(),"[protected]",userDetails.getAuthorities(),principal);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}
