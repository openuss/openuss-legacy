package org.openuss.security;

import org.apache.commons.lang.StringUtils;

/**
 * Helperclass to convert between <code>${domain}\${username}</code>
 * 
 * @author Ingo Dueppe
 *
 */
public class SecurityDomainUtility {
	public static final String USERNAME_DOMAIN_DELIMITER = "\\";
	public static final String USER_MIGRATION_INDICATOR_KEY = "USER_AUTOMATICALLY_MIGRATED";
	public static final String SHIBBOLETH_USER_INDICATOR_KEY = "WAS_SHIBBOLETH_USER";
	
	private static String delimiter = USERNAME_DOMAIN_DELIMITER;

	/**
	 * Converts username and domain name to <code>${domain}\${username}</code>
	 * @param domain - name of the domain
	 * @param username - username without domain
	 * @return username with domain prefix
	 */
	public static String toUsername(String domain, String username) {
		return domain+delimiter+username;		
	}

	/**
	 * Extract the domain name from a <code>domain\\username</code> pattern.
	 * @param username - username containing optionally the domain  
	 * @return domain name or null
	 */
	public static String extractDomain(String username) {
		if (StringUtils.indexOf(username, USERNAME_DOMAIN_DELIMITER) > -1) {
			return StringUtils.substringBefore(username, SecurityDomainUtility.USERNAME_DOMAIN_DELIMITER);
		} else {
			return null;
		}
		
	}
	
	/**
	 * Extract the username from a <code>domain\\username</code> pattern.
	 * @param username - username containing optionally the domain
	 * @return username
	 */
	public static String extractUsername(String username) {
		if (StringUtils.indexOf(username, USERNAME_DOMAIN_DELIMITER) > -1) {
			return StringUtils.substringAfter(username, SecurityDomainUtility.USERNAME_DOMAIN_DELIMITER);
		} else {
			return username;
		}
	}
	
	/**
	 * Checks whether the username contains a domain name or not.
	 * @param username
	 * @return true if username is a full qualified username
	 */
	public static boolean containsDomain(String username) {
		return StringUtils.indexOf(username, USERNAME_DOMAIN_DELIMITER) > -1;
	}

}
