package org.openuss.security.acegi.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsImpl;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExtendedLdapUserDetailsMapper extends LdapUserDetailsMapper {

    //~ Instance fields ================================================================================================
    private final Log logger = LogFactory.getLog(ExtendedLdapUserDetailsMapper.class);
    private String passwordAttributeName = "userPassword";
    private String rolePrefix = "ROLE_";
    private String[] roleAttributes = null;
    private boolean convertToUpperCase = true;
    
	private String groupRoleAttributeKey = null;

    //~ Methods ========================================================================================================

    @Override
	public Object mapAttributes(String dn, Attributes attributes)
        throws NamingException {
        LdapUserDetailsImpl.Essence essence = new LdapUserDetailsImpl.Essence();

        essence.setDn(dn);
        essence.setAttributes(attributes);

        Attribute passwordAttribute = attributes.get(passwordAttributeName);

        if (passwordAttribute != null) {
            essence.setPassword(mapPassword(passwordAttribute));
        }

        // Map the roles
        for (int i = 0; (roleAttributes != null) && (i < roleAttributes.length); i++) {
            Attribute roleAttribute = attributes.get(roleAttributes[i]);

            if (roleAttribute == null) {
                logger.debug("Couldn't read role attribute '" + roleAttributes[i] + "' for user " + dn);
                continue;
            }

            NamingEnumeration attributeRoles = roleAttribute.getAll();

            while (attributeRoles.hasMore()) {
                GrantedAuthority authority = createAuthority(attributeRoles.next());

                if (authority != null) {
                    essence.addAuthority(authority);
                } else {
                    logger.debug("Failed to create an authority value from attribute with Id: "
                            + roleAttribute.getID());
                }
            }
        }

        return essence;
    }

	
}
