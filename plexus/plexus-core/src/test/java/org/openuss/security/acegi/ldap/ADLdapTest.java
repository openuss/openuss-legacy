// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.acegi.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

import org.acegisecurity.Authentication;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.ldap.LdapAuthenticationProvider;
import org.acegisecurity.providers.ldap.LdapAuthenticator;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsImpl;
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
	

	public void testLdapAuthenticationLive() {

//		 !!!!!!!!!!!!! ATTENTION: DON't commit your personal password !!!!!!!!!!!!!!!!!!!!
		 										UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("", "");
		 										
		 	Authentication authResult = authManager.authenticate(authRequest);		 	
		 	assertNotNull(authResult);
		 	
//		 	Authentifizierungs mittels LDAP Server
		 	if (authResult.getPrincipal() instanceof LdapUserDetails) {
		 		
		 		LdapUserDetails myLdapUserDetails = (LdapUserDetails) authResult.getPrincipal();
				logger.info("User is authenticated by means of LDAP!");
	 
			 	logger.info("getDetails: "+authResult.getDetails());
				
			 	NamingEnumeration<String> myNamingEnu = myLdapUserDetails.getAttributes().getIDs();
			 	Attributes myAttr = myLdapUserDetails.getAttributes();
			 	
			 	try {
			 		while (myNamingEnu.hasMore()) {
			 			String attrId = myNamingEnu.next().toString();
		                logger.info("Attribute: "+myAttr.get(attrId));
			 		}		 		
			 	} catch(NamingException ex){
			 		logger.info(ex);			 		
			 	}
			  	logger.info("myLdapUserDetails.getAttributes(): "+myLdapUserDetails.getAttributes());
			  	
			 	

			 	logger.info("user DN: "+myLdapUserDetails.getDn());
//			 	logger.info("getAuthorities(): "+authResult.getAuthorities());
			 	GrantedAuthority[] myGrantedAuthority = authResult.getAuthorities();

			 	
			 	for (GrantedAuthority grantedAuthority : myGrantedAuthority) {
			 		logger.info("!!!! Authority: "+grantedAuthority.getAuthority());
					
				}			 	 
		 	}
	 }
	 
	  
	
	public void testNormalUsaage() {
        LdapAuthenticationProvider ldapProvider = new LdapAuthenticationProvider(new MockAuthenticator());        
        UsernamePasswordAuthenticationToken myUsernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("h_muster01", "testpassword");
        LdapUserDetails myLdapUserDetails = (LdapUserDetails) ldapProvider.authenticate(myUsernamePasswordAuthenticationToken).getPrincipal();
    	assertNotNull(myLdapUserDetails);    	
        
        logger.info("Test User is authenticated by means of LDAP server!");
        
        
	 	NamingEnumeration<String> myNamingEnu = myLdapUserDetails.getAttributes().getIDs();
	 	Attributes myAttr = myLdapUserDetails.getAttributes();	 	
	 	try {
	 		while (myNamingEnu.hasMore()) {
	 			String attrId = myNamingEnu.next().toString();
                logger.info("Attribute: "+myAttr.get(attrId));
	 		}		 		
	 	} catch(NamingException ex){
	 		logger.info(ex);			 		
	 	}
	 	logger.info("user dn: "+myLdapUserDetails.getDn());
	 	assertEquals("h_muster01", myLdapUserDetails.getUsername());
        assertEquals("testpassword", myLdapUserDetails.getPassword());
        String mail="";
		try {
			mail = (String) myLdapUserDetails.getAttributes().get("mail").get();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        logger.info(mail);

	}

	public void testBadPasswordThrowsException() {
        LdapAuthenticationProvider ldapProvider = new LdapAuthenticationProvider(new MockAuthenticator());

        try {
        	ldapProvider.authenticate(new UsernamePasswordAuthenticationToken("h_muster01", "badpassword"));
            fail("Expected BadCredentialsException for bad password");
        } catch (BadCredentialsException expected) {}
        
    }

	
    public void testEmptyOrNullUserNameThrowsException() {
        LdapAuthenticationProvider ldapProvider = new LdapAuthenticationProvider(new MockAuthenticator());

        try {
        	ldapProvider.authenticate(new UsernamePasswordAuthenticationToken("", "testpassword"));
            fail("Expected BadCredentialsException for empty username");
        } catch (BadCredentialsException expected) {}

        try {
            ldapProvider.authenticate(new UsernamePasswordAuthenticationToken(null, "bobspassword"));
            fail("Expected BadCredentialsException for null username");
        } catch (BadCredentialsException expected) {}
    }
	
    
    
	
	 protected String[] getConfigLocations() {
		 return new String[] {
				 "classpath*:applicationContext-resources.xml",
				 "classpath*:testSecurityLDAP2.xml"};
	 }
	 
	 

	    //~ Inner Classes ==================================================================================================

	    class MockAuthenticator implements LdapAuthenticator {
	        
	    	Attributes userAttributes = new BasicAttributes();
	    	

	        public LdapUserDetails authenticate(String username, String password) {
	            LdapUserDetailsImpl.Essence userEssence = new LdapUserDetailsImpl.Essence();
	            userAttributes.put("cn", "h_muster01");
	            userAttributes.put("mail", "h_muster01@uni-muenster.de");
	            userAttributes.put("memberOf", "CN=u0dawin,OU=Projekt-Gruppen,DC=uni-muenster,DC=de");
	            userAttributes.put("sn", "Mustermann");
	            userAttributes.put("givenName", "Hans");
	            userAttributes.put("displayName", "Herr Hans Mustermann");
	            userAttributes.put("userPrincipalName", "h_muster01@uni-muenster.de");
	            userAttributes.put("distinguishedName", "CN=h_muster01,OU=Projekt-Benutzer,DC=uni-muenster,DC=de");
	            
	            
	            userEssence.setPassword("testpassword");
	            userEssence.setAttributes(userAttributes);
	            

	            if (username.equals("h_muster01") && password.equals("testpassword")) {
	                userEssence.setDn("CN=h_muster01,OU=Projekt-Benutzer,DC=uni-muenster,DC=de");

	                return userEssence.createUserDetails();
	                
	            }
	            // authentication fails if username or password are wrong
	            throw new BadCredentialsException("Authentication failed.");
	        }
	    }
	    
	    
	    
	
}