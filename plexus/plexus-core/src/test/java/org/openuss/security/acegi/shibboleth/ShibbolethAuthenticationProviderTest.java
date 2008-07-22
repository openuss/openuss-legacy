package org.openuss.security.acegi.shibboleth;

import javax.naming.NamingException;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.adapters.PrincipalAcegiUserToken;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.memory.InMemoryDaoImpl;
import org.acegisecurity.userdetails.memory.UserMap;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetails;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetailsImpl;
import org.springframework.util.StringUtils;

import junit.framework.TestCase;

public class ShibbolethAuthenticationProviderTest extends TestCase {
    
	private final String SHIBBOLETHUSERNAMEHEADERKEY = "REMOTE_USER";
	private final String SHIBBOLETHFIRSTNAMEHEADERKEY = "SHIB_FIRSTNAME";
	private final String SHIBBOLETHLASTNAMEHEADERKEY = "SHIB_LASTNAME";
	private final String SHIBBOLETHEMAILHEADERKEY = "SHIB_MAIL";
	private final String KEY = "shib";
	private final String DEFAULTDOMAINNAME = "shibboleth";
	private final Long DEFAULTDOMAINID = 42L;
	private final String MIGRATIONTARGETURL = "/migration/migration.html";
	private final String DEFAULTROLE = "ROLE_SHIBBOLETH";
	private final String USERROLE = "ROLE_ACEGIUSER";
	private final String USERNAME = "test";
	private final String FIRSTNAME = "Joe";
	private final String LASTNAME = "Sixpack";
	private final String EMAIL = "j_sixpack42@acegisecurity.org";
	private final String DELIMITER = "\\";
	private Authentication authentication;
	private ShibbolethUserDetails shibbolethUserDetails;
	private InMemoryDaoImpl userDetailsService;
	private User unmigratedUser;
	private User migratedUser;
	private User disabledUser;
	private User lockedUser;
	private User credentialsExpiredUser;
	private User accountExpiredUser;
	
	private MockShibbolethAuthenticationProvider provider;
	// Tests from Siteminder

	public void setUp() {
		// Setup authentication request
		shibbolethUserDetails = new ShibbolethUserDetailsImpl();
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.USERNAME_KEY, USERNAME);
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.EMAIL_KEY, EMAIL);
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.FIRSTNAME_KEY, FIRSTNAME);
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.LASTNAME_KEY, LASTNAME);
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINNAME_KEY, DEFAULTDOMAINNAME);
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINID_KEY, DEFAULTDOMAINID);
		PrincipalAcegiUserToken auth = new PrincipalAcegiUserToken(KEY, USERNAME, "PW", new GrantedAuthority[]{new GrantedAuthorityImpl(DEFAULTROLE)}, USERNAME);
		auth.setDetails(shibbolethUserDetails);
		authentication = auth;
		
		// Setup users
		unmigratedUser = new User(USERNAME,"PW",true,true,true,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		migratedUser = new User(USERNAME,DELIMITER+"PW"+DELIMITER,true,true,true,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		disabledUser = new User(USERNAME,"PW",false,true,true,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		accountExpiredUser = new User(USERNAME,"PW",true,false,true,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		credentialsExpiredUser = new User(USERNAME,"PW",true,true,false,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		lockedUser = new User(USERNAME,"PW",true,true,true,false,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
				
		// Setup userDetailsService
		UserMap userMap = new UserMap();
		userDetailsService = new InMemoryDaoImpl();
		userDetailsService.setUserMap(userMap);
		
		// Setup class to test with defaults
		provider = new MockShibbolethAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setKey(KEY);
		provider.setMigrationEnabled(false);
		provider.setReconciliationEnabled(false);
		provider.setIgnoreDisabledException(false);
		provider.setForcePrincipalAsString(false);
		provider.setHideUserNotFoundExceptions(true);
		// provider needs a userCache mock. This will be configured within each test.

	}
	
	public void testReconciliation() {
		
	}
	
	public void testSuccessfulAuthenticationForMigratedUser() {
		
	}
	
	public void testReturnForManualMigrationOnMigrationPage() {
		
	}
	
	public void testAutomaticMigration() {
		
	}
	
	public void testLockedUser() {
		
	}
	
	public void testDisabledUser() {
		
	}
	
	public void testCredentialsExpired() {
		
	}
	
	public void testAccountExpired() {
		
	}
	
	public void testBackendFailure() {
		
	}
	
	public void testEmptyUsername() {
		
	}
	
	public void testAuthenticatesASecondTime() {
		
	}
	
	public void testAfterPropertiesSet() {
		
	}
	
	public void testForcePrincipalAsString() {
		
	}
	
	public void testHideUserNotFoundExceptions() {
		
	}
	
	public void testWrongKey() throws Exception {
		
		provider.afterPropertiesSet();
		
	}
	
	public void testSupports() {
		
		
	}
	
	public void testGettersSetters() {
		//forcePrincipalAsString
		//hideUserNotFoundExceptions
	}
	
	
	private class MockShibbolethAuthenticationProvider extends ShibbolethAuthenticationProvider {
		private int reconcileCount = 0;
		private int migrateCount = 0;
		
		@Override
		protected String generateUsernameFromAuthentication(Authentication authentication) {
			String username = "";
			try {
				username = (String)((ShibbolethUserDetails)authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.USERNAME_KEY).get();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return username;
		}

		@Override
		protected boolean isAlreadyMigrated(UserDetails user, Authentication authentication) {
			return user.getPassword().contains(DELIMITER);
		}

		@Override
		protected boolean reconcile(UserDetails user, Authentication authentication) {
			reconcileCount++;
			return true;
		}
		
		@Override
		protected void migrate(UserDetails user, Authentication authentication) {
			migrateCount++;
			UserMap userMap = new UserMap();
			userMap.addUser(new User(user.getPassword(),DELIMITER+user.getPassword()+DELIMITER,user.isEnabled(),user.isAccountNonExpired(),user.isCredentialsNonExpired(),user.isAccountNonLocked(),user.getAuthorities()));
			ShibbolethAuthenticationProviderTest.this.userDetailsService.setUserMap(userMap);
		}

		public int getReconcileCount() {
			return reconcileCount;
		}

		public void setReconcileCount(int reconcileCount) {
			this.reconcileCount = reconcileCount;
		}

		public int getMigrateCount() {
			return migrateCount;
		}

		public void setMigrateCount(int migrateCount) {
			this.migrateCount = migrateCount;
		}
		
	}
}
