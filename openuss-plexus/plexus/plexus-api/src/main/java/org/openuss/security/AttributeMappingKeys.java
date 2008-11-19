package org.openuss.security;

/**
 * Defines the global constants for the mapping of attributes. It defines
 * OpenUSS-specific attribute keys/attribute IDs, where to store and internally
 * search for attribute values, that have been retrieved from directory
 * services, e. g. LDAP.
 * <ol>
 * <li>USERNAME_KEY</li>
 * <li>FIRSTNAME_KEY</li>
 * <li>LASTNAME_KEY</li>
 * <li>EMAIL_KEY</li>
 * <li>AUTHENTICATIONDOMAINID_KEY</li>
 * </ol>
 * 
 * @author Peter Schuh
 */

public class AttributeMappingKeys {

	public static final String USERNAME_KEY = "OPENUSS_USERNAME_KEY";
	public static final String FIRSTNAME_KEY = "OPENUSS_FIRSTNAME_KEY";
	public static final String LASTNAME_KEY = "OPENUSS_LASTNAME_KEY";
	public static final String EMAIL_KEY = "OPENUSS_EMAIL_KEY";
	public static final String AUTHENTICATIONDOMAINID_KEY = "OPENUSS_AUTHENTICATIONDOMAINID_KEY";
	public static final String AUTHENTICATIONDOMAINNAME_KEY = "OPENUSS_AUTHENTICATIONDOMAINNAME_KEY";
}
