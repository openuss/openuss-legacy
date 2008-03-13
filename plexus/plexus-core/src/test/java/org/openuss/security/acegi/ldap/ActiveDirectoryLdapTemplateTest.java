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
import org.acegisecurity.ldap.LdapEntryMapper;


/**
 * Tests <code>ActiveDirectoryLdapTemplate</code> class.
 * The only difference for accessing a Microsoft Active Directory service using a <code>DirContext</code> 
 * is located within the private <code>getRelativeName</code> method. 
 * So we just test this method here.
 * 
 * @author Peter Schuh
 *
 */
public class ActiveDirectoryLdapTemplateTest extends TestCase {

	public void testRetrieveUser() {		
		// Set up
		InitialDirContextFactory dirContextFactory = createStrictMock(InitialDirContextFactory.class);
		DirContext ctx = createStrictMock(DirContext.class);
		LdapEntryMapper mapper = createStrictMock(LdapEntryMapper.class);
		String rootDn = "dc=mydomain,dc=org";
		String relativeDn = "cn=tester,ou=myunit"; 
		String fullDn = relativeDn+","+rootDn;
		Attributes attributes = new BasicAttributes();
		Object obj = new Object();
		try {
		// Define behaviour
		expect(dirContextFactory.newInitialDirContext()).andReturn(ctx);
		expect(ctx.getNameInNamespace()).andReturn(rootDn);
		expect(ctx.getAttributes(relativeDn, null)).andReturn(attributes);
		ctx.close();
		expect(mapper.mapAttributes(fullDn, attributes)).andReturn(obj);
		replay(dirContextFactory);
		replay(ctx);
		replay(mapper);
		} catch (NamingException ne) {
			throw new RuntimeException(ne.getMessage(),ne);
        }
		// Do the test
		ActiveDirectoryLdapTemplate activeDirectoryLdapTemplate = new ActiveDirectoryLdapTemplate(dirContextFactory);
		activeDirectoryLdapTemplate.retrieveEntry(fullDn, mapper, null);
		verify(mapper);
		verify(ctx);
		verify(dirContextFactory);
	}
	
	public void testRetrieveUserEmptyRootDn() {		
		// Set up
		InitialDirContextFactory dirContextFactory = createStrictMock(InitialDirContextFactory.class);
		DirContext ctx = createStrictMock(DirContext.class);
		LdapEntryMapper mapper = createStrictMock(LdapEntryMapper.class);
		String rootDn = "";
		String relativeDn = "cn=tester,ou=myunit"; 
		String fullDn = relativeDn+","+rootDn;
		Attributes attributes = new BasicAttributes();
		Object obj = new Object();
		try {
		// Define behaviour
		expect(dirContextFactory.newInitialDirContext()).andReturn(ctx);
		expect(ctx.getNameInNamespace()).andReturn(rootDn);
		expect(ctx.getAttributes(fullDn, null)).andReturn(attributes);
		ctx.close();
		expect(mapper.mapAttributes(fullDn, attributes)).andReturn(obj);
		replay(dirContextFactory);
		replay(ctx);
		replay(mapper);
		} catch (NamingException ne) {
			throw new RuntimeException(ne.getMessage(),ne);
        }
		// Do the test
		ActiveDirectoryLdapTemplate activeDirectoryLdapTemplate = new ActiveDirectoryLdapTemplate(dirContextFactory);
		activeDirectoryLdapTemplate.retrieveEntry(fullDn, mapper, null);
		verify(mapper);
		verify(ctx);
		verify(dirContextFactory);
	}
	
	public void testRetrieveUserRootDnEqualsFullDn() {		
		// Set up
		InitialDirContextFactory dirContextFactory = createStrictMock(InitialDirContextFactory.class);
		DirContext ctx = createStrictMock(DirContext.class);
		LdapEntryMapper mapper = createStrictMock(LdapEntryMapper.class);
		String rootDn = "dc=mydomain,dc=org";
		String relativeDn = "";		
		String fullDn = rootDn;
		Attributes attributes = new BasicAttributes();
		Object obj = new Object();
		try {
		// Define behaviour
		expect(dirContextFactory.newInitialDirContext()).andReturn(ctx);
		expect(ctx.getNameInNamespace()).andReturn(rootDn);
		expect(ctx.getAttributes(relativeDn, null)).andReturn(attributes);
		ctx.close();
		expect(mapper.mapAttributes(fullDn, attributes)).andReturn(obj);
		replay(dirContextFactory);
		replay(ctx);
		replay(mapper);
		} catch (NamingException ne) {
			throw new RuntimeException(ne.getMessage(),ne);
        }
		// Do the test
		ActiveDirectoryLdapTemplate activeDirectoryLdapTemplate = new ActiveDirectoryLdapTemplate(dirContextFactory);
		activeDirectoryLdapTemplate.retrieveEntry(fullDn, mapper, null);
		verify(mapper);
		verify(ctx);
		verify(dirContextFactory);
	}
}