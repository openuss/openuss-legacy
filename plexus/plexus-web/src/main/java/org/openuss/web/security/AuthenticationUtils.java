package org.openuss.web.security;

import org.acegisecurity.AccountExpiredException;
import org.acegisecurity.Authentication;
import org.acegisecurity.CredentialsExpiredException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.LockedException;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.userdetails.UserDetails;
import org.openuss.security.SecurityConstants;
import org.openuss.security.User;

public class AuthenticationUtils {
	
	public static void checkLocallyAllowanceToLogin(User user) throws Exception {
		if (!user.isEnabled()) throw new DisabledException("authentication_error_account_disabled");
		if (user.isAccountLocked()) throw new LockedException("authentication_error_account_locked");
		if (user.isCredentialsExpired()) throw new CredentialsExpiredException("authentication_error_password_expired");
		if (user.isAccountExpired()) throw new AccountExpiredException("authentication_error_account_expired");		
	}
	
	public static String generateCentralUserLoginName(String authenticationDomainName, String username) {
		return SecurityConstants.USERNAME_DOMAIN_DELIMITER+SecurityConstants.USERNAME_DOMAIN_DELIMITER+authenticationDomainName+SecurityConstants.USERNAME_DOMAIN_DELIMITER+username;
	}
	
	
	/**
     * From Acegi-Framework:  
     * Creates a successful <code>Authentication</code> object.<p>Protected so subclasses can override.</p>
     *  <p>Subclasses will usually store the original credentials the user supplied (not salted or encoded
     * passwords) in the returned <code>Authentication</code> object.</p>
     *
     * @param principal that should be the principal in the returned object (defined by the {@link
     *        #isForcePrincipalAsString()} method)
     * @param authentication that was presented to the provider for validation
     * @param user that was loaded by the implementation
     *
     * @return the successful authentication token
     *  
     */
    public static Authentication createSuccessAuthentication(Object principal, Authentication authentication,
        UserDetails user) {
        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal,
                authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());

        return result;
    }
}
