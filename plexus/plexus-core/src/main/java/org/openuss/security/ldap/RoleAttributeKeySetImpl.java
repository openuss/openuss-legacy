// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.List;

/**
 * @see org.openuss.security.ldap.RoleAttributeKeySet
 * @author Damian Kemner
 */
public class RoleAttributeKeySetImpl
    extends org.openuss.security.ldap.RoleAttributeKeySetBase
	implements org.openuss.security.ldap.RoleAttributeKeySet
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 7970279538996851415L;

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeySet#addRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKey)
     */
    public void addRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKey roleAttribute)
    {
    	if (roleAttribute != null) {
    		List<RoleAttributeKey> keys = getRoleAttributeKeys();
        	if (!keys.contains(roleAttribute)) {
        		keys.add(roleAttribute);
        	}
    	}
    }

    /**
     * @see org.openuss.security.ldap.RoleAttributeKeySet#removeAttributeKey(org.openuss.security.ldap.RoleAttributeKey)
     */
    public void removeAttributeKey(org.openuss.security.ldap.RoleAttributeKey roleAttributeKey)
    {
    	if (roleAttributeKey != null) {
    		List<RoleAttributeKey> keys = getRoleAttributeKeys();
        	if (keys.contains(roleAttributeKey)) {
        		keys.remove(roleAttributeKey);
        	}
    	}
    }

	@Override
	public void removeRoleAttributeKey(RoleAttributeKey roleAttributeKey) {
		removeAttributeKey(roleAttributeKey);
	}
    
    

}