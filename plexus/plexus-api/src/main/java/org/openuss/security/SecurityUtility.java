package org.openuss.security;

/**
 * 
 * @author Ingo Dueppe
 *
 */
public class SecurityUtility {
	
	private static String prefix = SecurityConstants.USERNAME_DOMAIN_DELIMITER+SecurityConstants.USERNAME_DOMAIN_DELIMITER;
	private static String delimiter = SecurityConstants.USERNAME_DOMAIN_DELIMITER;

	/**
	 * Converts username and domain name to <code>\\${domain}\${username}</code>
	 * @param domain
	 * @param username
	 * @return username with domain prefix
	 */
	public static String toUsername(String domain, String username) {
		return prefix+domain+delimiter+username;		
	}
}
