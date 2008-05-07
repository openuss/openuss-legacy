package org.openuss.security.acegi.ldap;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsMapper;

public class ExtendedLdapUserDetailsMapper extends LdapUserDetailsMapper {

    //~ Instance fields ================================================================================================
    private String rolePrefix = "ROLE_";
    private boolean convertToUpperCase = true;
    
	private String groupRoleAttributeKey = "cn";

    //~ Methods ========================================================================================================

	
    /**
     * Retrieves a Role from a Role DN  according to the groupRoleAttributeKey. Default is Common Name (CN) of a Role DN.
     */
    
    protected GrantedAuthority createAuthority(Object roleDn) {
        String dn = null;
    	String role = null;        
    	if (roleDn instanceof String) {
        	dn = (String) roleDn;
        	dn = dn.replaceAll("\\s+","");
        	role = dn;
        	
    		dn = dn.toUpperCase();
    		int startindex = dn.indexOf(groupRoleAttributeKey.toUpperCase());
    		int endindex = dn.length();    		
    		if (startindex > -1) {
    			startindex = startindex + groupRoleAttributeKey.length()+1;
    			// If GroupRoleAttributeKey is not at the end of the DN
    			if (dn.indexOf(",", startindex) > -1)
    				endindex = dn.indexOf(",", startindex);    			
    		}
    		// GroupRoleAttribute not found within Role DN -> Return full roleDN
    		else { 
    			startindex = 0;
    		}
    		
    		role = role.substring(startindex,endindex);    		
      	
            if (convertToUpperCase) {
                role = role.toUpperCase();
            }
            return new GrantedAuthorityImpl(rolePrefix + role);
        }
        return null;
    }

	public String getGroupRoleAttributeKey() {
		return groupRoleAttributeKey;
	}

	public void setGroupRoleAttributeKey(String groupRoleAttributeKey) {
		if (groupRoleAttributeKey != null && !groupRoleAttributeKey.equals(""))
			this.groupRoleAttributeKey = groupRoleAttributeKey;
	}

	public String getRolePrefix() {
		return rolePrefix;
	}

	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	public boolean getConvertToUpperCase() {
		return convertToUpperCase;
	}

	public void setConvertToUpperCase(boolean convertToUpperCase) {
		this.convertToUpperCase = convertToUpperCase;
	}	
}
