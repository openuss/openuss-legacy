package org.openuss.security.acegi.ldap;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import junit.framework.TestCase;

import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsImpl;


/**
 * Tests {@link ExtendedLdapUserDetailsMapper}.
 * Includes all tests for LdapUserDetailsMapper of the Acegi framework.
 *
 * @author Luke Taylor
 * @author Peter Schuh 
 */

public class ExtendedLdapUserDetailsMapperTest extends TestCase {

    public void testMultipleRoleAttributeValuesAreMappedToAuthoritiesWithDefaultGroupRoleAttributeKeyAtTheBeginOfDn() throws Exception {
        ExtendedLdapUserDetailsMapper mapper = new ExtendedLdapUserDetailsMapper();
        mapper.setConvertToUpperCase(false);
        mapper.setRolePrefix("");

        mapper.setRoleAttributes(new String[] {"userRole"});

        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute roleAttribute = new BasicAttribute("userRole");
        roleAttribute.add("cn=X,  ou=myunit,  dc=domain,  dc=org");
        roleAttribute.add("cn=Y, ou=myunit,   dc=domain,   dc=org");
        roleAttribute.add("cn=Z,ou=myunit,  dc=domain,  dc=org");
        attrs.put(roleAttribute);

        LdapUserDetailsImpl.Essence user = (LdapUserDetailsImpl.Essence) mapper.mapAttributes("cn=someName", attrs);

        assertEquals(3, user.getGrantedAuthorities().length);
        assertEquals("X", user.getGrantedAuthorities()[0].getAuthority());
        assertEquals("Y", user.getGrantedAuthorities()[1].getAuthority());
        assertEquals("Z", user.getGrantedAuthorities()[2].getAuthority());
    }

	public void testMultipleRoleAttributeValuesAreMappedToAuthoritiesWithDefaultGroupRoleAttributeKeyAsLastElement() throws Exception {
        ExtendedLdapUserDetailsMapper mapper = new ExtendedLdapUserDetailsMapper();
        mapper.setConvertToUpperCase(false);
        mapper.setRolePrefix("");

        mapper.setRoleAttributes(new String[] {"userRole"});

        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute roleAttribute = new BasicAttribute("userRole");
        roleAttribute.add("cn=X");
        roleAttribute.add("cn=Y");
        roleAttribute.add("cn=Z");
        attrs.put(roleAttribute);

        LdapUserDetailsImpl.Essence user = (LdapUserDetailsImpl.Essence) mapper.mapAttributes("cn=someName", attrs);

        assertEquals(3, user.getGrantedAuthorities().length);
        assertEquals("X", user.getGrantedAuthorities()[0].getAuthority());
        assertEquals("Y", user.getGrantedAuthorities()[1].getAuthority());
        assertEquals("Z", user.getGrantedAuthorities()[2].getAuthority());
    }

    public void testMultipleRoleAttributeValuesAreMappedToAuthoritiesWithGivenGroupRoleAttributeKey() throws Exception {
        ExtendedLdapUserDetailsMapper mapper = new ExtendedLdapUserDetailsMapper();
        mapper.setConvertToUpperCase(false);
        mapper.setRolePrefix("");
        
        mapper.setGroupRoleAttributeKey("ou");

        mapper.setRoleAttributes(new String[] {"userRole"});

        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute roleAttribute = new BasicAttribute("userRole");
        roleAttribute.add("cn=A,ou=X");
        roleAttribute.add("cn=A,ou=Y");
        roleAttribute.add("cn=A,ou=Z");
        attrs.put(roleAttribute);

        LdapUserDetailsImpl.Essence user = (LdapUserDetailsImpl.Essence) mapper.mapAttributes("cn=someName", attrs);

        assertEquals(3, user.getGrantedAuthorities().length);
        assertEquals("X", user.getGrantedAuthorities()[0].getAuthority());
        assertEquals("Y", user.getGrantedAuthorities()[1].getAuthority());
        assertEquals("Z", user.getGrantedAuthorities()[2].getAuthority());
    }

    public void testMultipleRoleAttributeValuesAreMappedToAuthoritiesWithNullGroupRoleAttributeKey() throws Exception {
        ExtendedLdapUserDetailsMapper mapper = new ExtendedLdapUserDetailsMapper();
        mapper.setConvertToUpperCase(false);
        mapper.setRolePrefix("");
        
        mapper.setGroupRoleAttributeKey(null);

        mapper.setRoleAttributes(new String[] {"userRole"});

        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute roleAttribute = new BasicAttribute("userRole");
        roleAttribute.add("X");
        roleAttribute.add("Y");
        roleAttribute.add("Z");
        attrs.put(roleAttribute);

        LdapUserDetailsImpl.Essence user = (LdapUserDetailsImpl.Essence) mapper.mapAttributes("cn=someName", attrs);

        assertEquals(3, user.getGrantedAuthorities().length);
        assertEquals("X", user.getGrantedAuthorities()[0].getAuthority());
        assertEquals("Y", user.getGrantedAuthorities()[1].getAuthority());
        assertEquals("Z", user.getGrantedAuthorities()[2].getAuthority());
    }
    
    public void testMultipleRoleAttributeValuesAreMappedToAuthoritiesWithEmptyGroupRoleAttributeKey() throws Exception {
        ExtendedLdapUserDetailsMapper mapper = new ExtendedLdapUserDetailsMapper();
        mapper.setConvertToUpperCase(false);
        mapper.setRolePrefix("");
        
        mapper.setGroupRoleAttributeKey("");

        mapper.setRoleAttributes(new String[] {"userRole"});

        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute roleAttribute = new BasicAttribute("userRole");
        roleAttribute.add("X");
        roleAttribute.add("Y");
        roleAttribute.add("Z");
        attrs.put(roleAttribute);

        LdapUserDetailsImpl.Essence user = (LdapUserDetailsImpl.Essence) mapper.mapAttributes("cn=someName", attrs);

        assertEquals(3, user.getGrantedAuthorities().length);
        assertEquals("X", user.getGrantedAuthorities()[0].getAuthority());
        assertEquals("Y", user.getGrantedAuthorities()[1].getAuthority());
        assertEquals("Z", user.getGrantedAuthorities()[2].getAuthority());
    }
// Tests from LdapUserDetailsMapperTests
    
    public void testMultipleRoleAttributeValuesAreMappedToAuthorities() throws Exception {
        ExtendedLdapUserDetailsMapper mapper = new ExtendedLdapUserDetailsMapper();
        mapper.setConvertToUpperCase(false);
        mapper.setRolePrefix("");

        mapper.setRoleAttributes(new String[] {"userRole"});

        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute roleAttribute = new BasicAttribute("userRole");
        roleAttribute.add("X");
        roleAttribute.add("Y");
        roleAttribute.add("Z");
        attrs.put(roleAttribute);

        LdapUserDetailsImpl.Essence user = (LdapUserDetailsImpl.Essence) mapper.mapAttributes("cn=someName", attrs);

        assertEquals(3, user.getGrantedAuthorities().length);
    }

    /**
     * SEC-303. Non-retrieved role attribute causes NullPointerException
     */
    public void testNonRetrievedRoleAttributeIsIgnored() throws Exception {
        ExtendedLdapUserDetailsMapper mapper = new ExtendedLdapUserDetailsMapper();

        mapper.setRoleAttributes(new String[] {"userRole", "nonRetrievedAttribute"});

        BasicAttributes attrs = new BasicAttributes();
        attrs.put(new BasicAttribute("userRole", "x"));

        LdapUserDetailsImpl.Essence user = (LdapUserDetailsImpl.Essence) mapper.mapAttributes("cn=someName", attrs);

        assertEquals(1, user.getGrantedAuthorities().length);
        assertEquals("ROLE_X", user.getGrantedAuthorities()[0].getAuthority());
    }

    public void testNonStringRoleAttributeIsIgnoredByDefault() throws Exception {
        ExtendedLdapUserDetailsMapper mapper = new ExtendedLdapUserDetailsMapper();

        mapper.setRoleAttributes(new String[] {"userRole"});

        BasicAttributes attrs = new BasicAttributes();
        attrs.put(new BasicAttribute("userRole", new GrantedAuthorityImpl("X")));

        LdapUserDetailsImpl.Essence user = (LdapUserDetailsImpl.Essence) mapper.mapAttributes("cn=someName", attrs);

        assertEquals(0, user.getGrantedAuthorities().length);
    }

    public void testPasswordAttributeIsMappedCorrectly() throws Exception {
        ExtendedLdapUserDetailsMapper mapper = new ExtendedLdapUserDetailsMapper();

        mapper.setPasswordAttributeName("myappsPassword");
        BasicAttributes attrs = new BasicAttributes();
        attrs.put(new BasicAttribute("myappsPassword", "mypassword".getBytes()));

        LdapUserDetails user =
                ((LdapUserDetailsImpl.Essence) mapper.mapAttributes("cn=someName", attrs)).createUserDetails();

        assertEquals("mypassword", user.getPassword());
    }
}
