// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.List;

/**
 * @see org.openuss.security.ldap.UserDnPatternSet
 * @author Damian Kemner
 *  */
public class UserDnPatternSetImpl
    extends org.openuss.security.ldap.UserDnPatternSetBase
	implements org.openuss.security.ldap.UserDnPatternSet
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -8546191146661012281L;

    /**
     * @see org.openuss.security.ldap.UserDnPatternSet#addUserDnPattern(org.openuss.security.ldap.UserDnPattern)
     */
    public void addUserDnPattern(org.openuss.security.ldap.UserDnPattern userDnPattern)
    {
    	if (userDnPattern != null) {
    		List<UserDnPattern> patterns = getUserDnPatterns();
        	if (!patterns.contains(userDnPattern)) {
        		patterns.add(userDnPattern);
        	}
    	}
    }

    /**
     * @see org.openuss.security.ldap.UserDnPatternSet#removeUserDnPattern(org.openuss.security.ldap.UserDnPattern)
     */
    public void removeUserDnPattern(org.openuss.security.ldap.UserDnPattern userDnPattern)
    {
    	if (userDnPattern != null) {
    		List<UserDnPattern> patterns = getUserDnPatterns();
        	if (patterns.contains(userDnPattern)) {
        		patterns.remove(userDnPattern);
        	}
    	}
    }
    

}