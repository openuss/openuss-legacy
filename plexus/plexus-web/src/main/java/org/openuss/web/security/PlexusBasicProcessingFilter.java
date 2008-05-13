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
			// Retrieve username
			LdapUserDetails ldapUserDetails = (LdapUserDetails)authResult.getPrincipal();
			String username = "";
			try {
				String authenticationDomainName = ((String) ldapUserDetails.getAttributes().get(AttributeMappingKeys.AUTHENTICATIONDOMAINNAME_KEY).get());
				username = ((String) ldapUserDetails.getAttributes().get(AttributeMappingKeys.USERNAME_KEY).get());
				username = AuthenticationUtils.generateCentralUserLoginName(authenticationDomainName, username);				
			} catch (NamingException ne) {
			    // do nothing
			}
			if (StringUtils.isNotBlank(username)) {
				UserInfo user = securityService.getUserByName(username);
				if (user != null) {
					// OpenUSS user profile found  
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

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}
