// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import org.acegisecurity.Authentication;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.apache.log4j.Logger;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


/**
 * JUnit Test for Acegi LDAP and Active Directory.
 * @see org.openuss.security.UserDao
 */
public class ADLdapTest extends AbstractDependencyInjectionSpringContextTests {
	
	protected static final Logger logger = Logger.getLogger(ADLdapTest.class);

	protected ProviderManager authManager;	
	
	protected UsernamePasswordAuthenticationToken ldapPswdAuthToken;
	
	public ADLdapTest() {
		super();
	}
	
	public ProviderManager getAuthManager() {
		return authManager;
	}

	public void setAuthManager(ProviderManager authManager) {
		this.authManager = authManager;
	}	
	
	
	public void testAuthManagerInjection() {
		assertNotNull(authManager);
	}
	
	 public void testNormalUsage() {

	        //UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(ldapUser, ldapPswd);
		 	//UsernamePasswordAuthenticationToken authRequest = ldapPswdAuthToken;
//		 !!!!!!!!!!!!! ATTENTION: DON't commit your personal password !!!!!!!!!!!!!!!!!!!!
		 							UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("", "...");
		 	
		 	Authentication authResult = authManager.authenticate(authRequest);
		 	
		 	assertNotNull(authResult);
		 	
		 	logger.info(authResult);
		 	/*
		 	 
		 	assertEquals("", authResult.getCredentials());	        
	        
	        
	        
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