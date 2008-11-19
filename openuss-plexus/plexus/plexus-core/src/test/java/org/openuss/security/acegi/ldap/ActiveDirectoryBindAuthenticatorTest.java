package org.openuss.security.acegi.ldap;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;

import junit.framework.TestCase;

import org.acegisecurity.ldap.InitialDirContextFactory;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsMapper;

/**
 * Test for <code>ActiveDirectoryBindAuthenticator</code>
 * Compared to the <BindAuthenticator> only one parameter passing within the <code>bindWithDn</code> 
 * method was changed to be able to access a Microsoft Active Directoy service.
 * This method is tested here.
 * 
 * @author Peter Schuh
 *
 */
public class ActiveDirectoryBindAuthenticatorTest extends TestCase {

	public void testBindWithDn() {		
		// Set up
		InitialDirContextFactory dirContextFactory = createStrictMock(InitialDirContextFactory.class);
		DirContext ctx = createStrictMock(DirContext.class);
		String roleAttribute = "memberOf";
		String rootDn = "dc=mydomain,dc=org";
		String relativeDn = "cn=tester,ou=myunit"; 
		String fullDn = relativeDn+","+rootDn;
		String rolePrefix = "ROLE_";
		String role = "ADMINISTRATOR";
		String username = "tester";
		String password = "secret";
		LdapUserDetailsMapper ldapUserDetailsMapper = new LdapUserDetailsMapper();
		ldapUserDetailsMapper.setRoleAttributes(new String[]{roleAttribute});
		ldapUserDetailsMapper.setRolePrefix(rolePrefix);
		Attributes attributes = new BasicAttributes();
		attributes.put(roleAttribute, role);
		try {
		// Define behaviour
		expect(dirContextFactory.getRootDn()).andReturn(rootDn);
		expect(dirContextFactory.newInitialDirContext(username, password)).andReturn(ctx);
		expect(ctx.getNameInNamespace()).andReturn(rootDn);
		expect(ctx.getAttributes(relativeDn, null)).andReturn(attributes);
		ctx.close();
		replay(dirContextFactory);
		replay(ctx);
		} catch (NamingException ne) {
			throw new RuntimeException(ne.getMessage(),ne);
        }
		// Do the test
		ActiveDirectoryBindAuthenticator activeDirectoryBindAuthenticator = new ActiveDirectoryBindAuthenticator(dirContextFactory);
		activeDirectoryBindAuthenticator.setUserDetailsMapper(ldapUserDetailsMapper);
		activeDirectoryBindAuthenticator.setUserDnPatterns(new String[]{fullDn});
		LdapUserDetails user = activeDirectoryBindAuthenticator.authenticate(username, password); 
		verify(ctx);
		verify(dirContextFactory);
		assertEquals(rolePrefix+role, user.getAuthorities()[0].getAuthority());
	}
}