// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import org.acegisecurity.Authentication;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


/**
 * JUnit Test for Spring Hibernate UserDao class.
 * @see org.openuss.security.UserDao
 */
public class LdapTest extends AbstractDependencyInjectionSpringContextTests {
	
	protected static final Logger logger = Logger.getLogger(LdapTest.class);

	protected ProviderManager authManager;	
	
	protected UsernamePasswordAuthenticationToken ldapPswdAuthToken;
	
	public LdapTest() {
		super();
	}
	
	public ProviderManager getAuthManager() {
		return authManager;
	}

	public void setAuthManager(ProviderManager authManager) {
		this.authManager = authManager;
	}	
	
	

	public UsernamePasswordAuthenticationToken getLdapPswdAuthToken() {
		return ldapPswdAuthToken;
	}

	public void setLdapPswdAuthToken(UsernamePasswordAuthenticationToken ldapPswdAuthToken) {
		this.ldapPswdAuthToken = ldapPswdAuthToken;
	}

	public void testLdapPswdAuthTokenInjection() {
		assertNotNull(ldapPswdAuthToken);
	}
	
	public void testUserNameInjection() {
		assertEquals("j_debr01", ldapPswdAuthToken.getPrincipal().toString());
	}
	
	public void testPswdInjection() {
		assertNotSame("", ldapPswdAuthToken.getCredentials().toString());
	}
	
	
	public void testAuthManagerInjection() {
		assertNotNull(authManager);
	}
	
	 public void testNormalUsage() {

	        //UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(ldapUser, ldapPswd);
		 	//UsernamePasswordAuthenticationToken authRequest = ldapPswdAuthToken;
		 	UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("p_schu07", "...");
		 	
		 	Authentication authResult = authManager.authenticate(authRequest);	        
	        
		 	/*
		 	 
		 	assertEquals("j_debr01", authResult.getCredentials());	        
	        
	        
	        
	        UserDetails user = (UserDetails) authResult.getPrincipal();
//	        assertEquals(2, user.getAuthorities().length);
//	        assertEquals("{SHA}nFCebWjxfaLbHHG1Qk5UU4trbvQ=", user.getPassword());
//	        assertEquals("ben", user.getUsername());

	        ArrayList authorities = new ArrayList();
	        authorities.add(user.getAuthorities()[0].getAuthority());
	        authorities.add(user.getAuthorities()[1].getAuthority());
	        
	        System.out.println("user: "+user.getUsername());	        
	        
//	        assertTrue(authorities.contains("ROLE_FROM_ENTRY"));
//	        assertTrue(authorities.contains("ROLE_FROM_POPULATOR"));
 
 */
	    }
	
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext-resources.xml",
			"classpath*:testSecurityLDAP2.xml"};
	}	
			
	
/*	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-beans.xml",
			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml", 
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-resources.xml",
			"classpath*:applicationContext-aop.xml",
			"classpath*:testContext.xml", 
			"classpath*:testSecurityLDAP.xml", 
			"classpath*:testDataSource.xml"};
	}	*/
	
}