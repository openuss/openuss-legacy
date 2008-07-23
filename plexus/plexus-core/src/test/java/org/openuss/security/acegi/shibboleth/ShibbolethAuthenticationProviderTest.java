package org.openuss.security.acegi.shibboleth;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.Arrays;

import javax.naming.NamingException;

import junit.framework.TestCase;

import org.acegisecurity.AccountExpiredException;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationServiceException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.CredentialsExpiredException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.LockedException;
import org.acegisecurity.adapters.PrincipalAcegiUserToken;
import org.acegisecurity.intercept.InterceptorStatusToken;
import org.acegisecurity.providers.TestingAuthenticationToken;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.anonymous.AnonymousAuthenticationToken;
import org.acegisecurity.providers.cas.CasAuthenticationToken;
import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.providers.jaas.JaasAuthenticationToken;
import org.acegisecurity.providers.rememberme.RememberMeAuthenticationToken;
import org.acegisecurity.providers.x509.X509AuthenticationToken;
import org.acegisecurity.runas.RunAsUserToken;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.acegisecurity.userdetails.memory.InMemoryDaoImpl;
import org.acegisecurity.userdetails.memory.UserMap;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetails;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetailsImpl;
import org.springframework.dao.DataAccessResourceFailureException;

public class ShibbolethAuthenticationProviderTest extends TestCase {
    
	private final String KEY = "shib";
	private final String DEFAULTDOMAINNAME = "shibboleth";
	private final Long DEFAULTDOMAINID = 42L;
	private final String DEFAULTROLE = "ROLE_SHIBBOLETH";
	private final String USERROLE = "ROLE_ACEGIUSER";
	private final String USERNAME = "test";
	private final String FIRSTNAME = "Joe";
	private final String LASTNAME = "Sixpack";
	private final String EMAIL = "j_sixpack42@acegisecurity.org";
	private final String DELIMITER = "\\";
	private final String PW = "PW";
	private Authentication authentication;
	private ShibbolethUserDetails shibbolethUserDetails;
	private InMemoryDaoImpl userDetailsService;
	private User unmigratedUser;
	private User migratedUser;
	private User reconciledUser;
	private User disabledUser;
	private User lockedUser;
	private User credentialsExpiredUser;
	private User accountExpiredUser;
	
	private MockShibbolethAuthenticationProvider provider;

	public void setUp() {
		// Setup authentication request
		shibbolethUserDetails = new ShibbolethUserDetailsImpl();
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.USERNAME_KEY, USERNAME);
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.EMAIL_KEY, EMAIL);
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.FIRSTNAME_KEY, FIRSTNAME);
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.LASTNAME_KEY, LASTNAME);
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINNAME_KEY, DEFAULTDOMAINNAME);
		shibbolethUserDetails.getAttributes().put(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINID_KEY, DEFAULTDOMAINID);
		PrincipalAcegiUserToken auth = new PrincipalAcegiUserToken(KEY, USERNAME, PW, new GrantedAuthority[]{new GrantedAuthorityImpl(DEFAULTROLE)}, USERNAME);
		auth.setDetails(shibbolethUserDetails);
		authentication = auth;
		
		// Setup users
		unmigratedUser = new User(USERNAME,PW,true,true,true,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		migratedUser = new User(USERNAME,DELIMITER+PW+DELIMITER,true,true,true,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		reconciledUser = new User(USERNAME,DELIMITER+"TOBEDIFFERENT"+DELIMITER,true,true,true,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		disabledUser = new User(USERNAME,PW,false,true,true,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		accountExpiredUser = new User(USERNAME,PW,true,false,true,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		credentialsExpiredUser = new User(USERNAME,PW,true,true,false,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		lockedUser = new User(USERNAME,PW,true,true,true,false,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		
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
	
	public void tearDown() {
		provider = null;
	}
	
	public void testCacheOutdatedUserLockedMeanwhile() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(unmigratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setIgnoreDisabledException(false);
		provider.setMigrationEnabled(true);
		provider.setReconciliationEnabled(true);
		userDetailsService.getUserMap().addUser(lockedUser);
		// Test migrate user being unmigrated in cache, but having locked status within userDetailsService.
		Authentication authResult = null;
		try {
			authResult = provider.authenticate(authentication);
			fail("LockedException expected.");
		} catch (LockedException e) {
			// success
			assertNull(authResult);
			assertEquals(0, provider.migrateCount);
		}
		verify(userCache);
		
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(unmigratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setMigrationEnabled(false);
		userDetailsService.getUserMap().addUser(lockedUser);
		// Test take user from cache. Do not query userDetailsService. Do not try to migrate user.
		authResult = provider.authenticate(authentication);
		assertEquals(unmigratedUser, (User)authResult.getPrincipal());
		verify(userCache);
	}	
	
	public void testCacheOutdatedUserEnabledMeanwhile() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(disabledUser);
		userCache.putUserInCache(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setIgnoreDisabledException(false);
		provider.setMigrationEnabled(true);
		provider.setReconciliationEnabled(true);
		userDetailsService.getUserMap().addUser(unmigratedUser);
		// Test
		Authentication authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		assertEquals(1, provider.migrateCount);
		verify(userCache);	
	}
	
	public void testReconciliationWithCacheMiss() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		userCache.putUserInCache(reconciledUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setMigrationEnabled(true);
		provider.setReconciliationEnabled(true);
		userDetailsService.getUserMap().addUser(migratedUser);
		// Test
		Authentication authResult = provider.authenticate(authentication);
		assertEquals(reconciledUser, (User)authResult.getPrincipal());
		assertEquals(1, provider.getReconcileCount());
		verify(userCache);			
	}
	
	public void testReconciliationWithCacheHit() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setMigrationEnabled(true);
		provider.setReconciliationEnabled(true);
		userDetailsService.getUserMap().addUser(migratedUser);
		// Test with user from cache. Not reconciled to prevent lost updates, if cache is out-dated.
		Authentication authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		assertEquals(0, provider.reconcileCount);
		verify(userCache);		
	}
	
	public void testSuccessfulAuthenticationForMigratedUserWithCacheMiss() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		userCache.putUserInCache(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setMigrationEnabled(true);
		userDetailsService.getUserMap().addUser(migratedUser);
		// Test
		Authentication authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		verify(userCache);		
	}
	
	public void testSuccessfulAuthenticationForMigratedUserWithCacheHit() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setMigrationEnabled(true);
		// Test
		Authentication authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		verify(userCache);	
	}
	
	public void testAutomaticMigrationWithCacheMiss() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		userCache.putUserInCache(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setMigrationEnabled(true);
		userDetailsService.getUserMap().addUser(unmigratedUser);
		// Test enabled, unmigrated user
		Authentication authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		assertEquals(1, provider.getMigrateCount());
		verify(userCache);
		
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		userCache.putUserInCache(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setMigrationEnabled(true);
		provider.setIgnoreDisabledException(true);
		UserMap userMap = new UserMap();
		userMap.addUser(disabledUser);
		userDetailsService.setUserMap(userMap);
		// Test disabled, unmigrated user
		authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		assertEquals(2, provider.getMigrateCount());
		verify(userCache);
		
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		userCache.putUserInCache(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setMigrationEnabled(true);
		provider.setIgnoreDisabledException(true);
		userMap = new UserMap();
		userMap.addUser(migratedUser);
		userDetailsService.setUserMap(userMap);
		// Test with migrated user
		authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		assertEquals(2, provider.getMigrateCount());
		verify(userCache);
	}

	public void testAutomaticMigrationWithCacheHit() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(unmigratedUser);
		userCache.putUserInCache(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setMigrationEnabled(true);
		provider.resetCounters();
		userDetailsService.getUserMap().addUser(unmigratedUser);
		// Test with enabled, unmigrated user within cache and userDetailsService.
		Authentication authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		assertEquals(1, provider.getMigrateCount());
		verify(userCache);
		
		authResult = null;
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(disabledUser);
		userCache.putUserInCache(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setIgnoreDisabledException(true);
		provider.resetCounters();
		UserMap userMap = new UserMap();
		userMap.addUser(disabledUser);
		userDetailsService.setUserMap(userMap);
		// Test with disabled, unmigrated user
		authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		assertEquals(1, provider.getMigrateCount());
		verify(userCache);
		
		authResult = null;
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(lockedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setIgnoreDisabledException(true);
		provider.resetCounters();
		userMap = new UserMap();
		userMap.addUser(lockedUser);
		userDetailsService.setUserMap(userMap);
		// Test with locked, unmigrated user
		try {
			authResult = provider.authenticate(authentication);
			fail("LockedException expected.");
		} catch (LockedException e) {
			assertNull(authResult);
			assertEquals(0, provider.getMigrateCount());
		}
		verify(userCache);

		authResult = null;
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.resetCounters();
		userMap = new UserMap();
		userMap.addUser(unmigratedUser);
		userDetailsService.setUserMap(userMap);
		// Test with migrated user in cache, but unmigrated within userDetailsService. Impossible case, due to migration cannot be reverted. Do not migrate again.
		authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		assertEquals(0, provider.getMigrateCount());
		verify(userCache);
		
		authResult = null;
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(unmigratedUser);
		userCache.putUserInCache(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.resetCounters();
		userMap = new UserMap();
		userMap.addUser(migratedUser);
		userDetailsService.setUserMap(userMap);
		// Test with unmigrated user in cache, but migrated within userDetailsService. Do not migrate again.
		authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		assertEquals(0, provider.getMigrateCount());
		verify(userCache);
		
		authResult = null;
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.resetCounters();
		userMap = new UserMap();
		userMap.addUser(migratedUser);
		userDetailsService.setUserMap(userMap);
		// Test with migrated user in cache and userDetailsService.
		authResult = provider.authenticate(authentication);
		assertEquals(migratedUser, (User)authResult.getPrincipal());
		assertEquals(0, provider.getMigrateCount());
		verify(userCache);
	}
	
	public void testReturnForManualMigrationOnMigrationPage() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setMigrationEnabled(true);
		// Test
		Authentication authResult = provider.authenticate(authentication);
		assertTrue(authentication.equals(authResult));
		verify(userCache);
	}

	public void testAuthenticatesASecondTime() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		userCache.putUserInCache(unmigratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		userDetailsService.getUserMap().addUser(unmigratedUser);
		// Test 1st time
		Authentication authResult = provider.authenticate(authentication);
		// Test 2nd time
		try {
			provider.authenticate(authResult);			
		} catch (IllegalArgumentException e) {
			assertEquals("Only PrincipalAcegiUserToken is supported", e.getMessage());
		}
		verify(userCache);
	}

	public void testCreateSuccessAuthentication() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		userCache.putUserInCache(unmigratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		userDetailsService.getUserMap().addUser(unmigratedUser);		
		// Test 
		Authentication authResult = provider.authenticate(authentication);
		assertEquals(unmigratedUser, (User)authResult.getPrincipal());
		assertEquals(authentication.getCredentials(), authResult.getCredentials());
		assertTrue(Arrays.equals(unmigratedUser.getAuthorities(), authResult.getAuthorities()));
		assertEquals(shibbolethUserDetails, (ShibbolethUserDetails)authResult.getDetails());
		verify(userCache);
	}
	
	public void testForcePrincipalAsString() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		userCache.putUserInCache(unmigratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		userDetailsService.getUserMap().addUser(unmigratedUser);		
		provider.setForcePrincipalAsString(false);
		// Test principal as object
		Authentication authResult = provider.authenticate(authentication);
		assertEquals(unmigratedUser, (User)authResult.getPrincipal());
		verify(userCache);
		
		authResult = null;
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		userCache.putUserInCache(unmigratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setForcePrincipalAsString(true);
		// Test principal as string
		authResult = provider.authenticate(authentication);
		assertEquals(USERNAME, (String)authResult.getPrincipal());
		verify(userCache);		
	}

	
	public void testDisabledUser() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(disabledUser);
		replay(userCache);
		provider.setUserCache(userCache);
		userDetailsService.getUserMap().addUser(disabledUser);
		provider.setIgnoreDisabledException(false);
		// Test not ignoring disabled user status for not yet migrated users.
		try {
			provider.authenticate(authentication);
			fail("DisabledException expected.");
		} catch (DisabledException e) {
			// success
		}
		verify(userCache);		
	
		provider.setIgnoreDisabledException(true);
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		userCache.putUserInCache(disabledUser);
		replay(userCache);
		provider.setUserCache(userCache);
		Authentication authResult = null;
		// Test ignoring disabled status for not yet migrated users.
		try {
			authResult = provider.authenticate(authentication);
			assertTrue((authResult instanceof UsernamePasswordAuthenticationToken) && (((User)authResult.getPrincipal()).equals(disabledUser)));
		} catch (DisabledException e) {
			fail("Unexpected DisabledException.");
		}
		verify(userCache);
	
		authResult = null;
		provider.setIgnoreDisabledException(true);
		UserMap userMap = new UserMap();
		userMap.addUser(migratedUser);
		userDetailsService.setUserMap(userMap);
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		userCache.putUserInCache(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		// Test not ignoring disabled status for migrated users. Test with enabled, migrated user.
		try {
			authResult = provider.authenticate(authentication);
			assertTrue((authResult instanceof UsernamePasswordAuthenticationToken) && (((User)authResult.getPrincipal()).equals(migratedUser)));
		} catch (DisabledException e) {
			fail("Unexpected DisabledException.");
		}
		verify(userCache);
	
		authResult = null;
		migratedUser = new User(USERNAME,DELIMITER+PW+DELIMITER,false,true,true,true,new GrantedAuthority[]{new GrantedAuthorityImpl(USERROLE)});
		userMap = new UserMap();
		userMap.addUser(migratedUser);
		userDetailsService.setUserMap(userMap);
		provider.setIgnoreDisabledException(true);
		userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(migratedUser);
		replay(userCache);
		provider.setUserCache(userCache);
		// Test not ignoring disabled status for migrated users. Test with disabled, migrated user.
		try {
			provider.authenticate(authentication);
			fail("DisabledException expected.");
		} catch (DisabledException e) {
			// success
		}
		verify(userCache);
	}


	public void testLockedUser() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		replay(userCache);
		provider.setUserCache(userCache);
		userDetailsService.getUserMap().addUser(lockedUser);
		// Test
		try {
			provider.authenticate(authentication);
			fail("LockedException expected.");
		} catch (LockedException e) {
			// success
		}
		verify(userCache);		
	}
	
	
	public void testAccountExpired() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		replay(userCache);
		provider.setUserCache(userCache);
		userDetailsService.getUserMap().addUser(accountExpiredUser);
		// Test
		try {
			provider.authenticate(authentication);
			fail("AccountExpiredException expected.");
		} catch (AccountExpiredException e) {
			// success
		}
		verify(userCache);
	}

	
	public void testCredentialsExpired() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		replay(userCache);
		provider.setUserCache(userCache);
		userDetailsService.getUserMap().addUser(credentialsExpiredUser);
		// Test
		try {
			provider.authenticate(authentication);
			fail("CredentialsExpiredException expected.");
		} catch (CredentialsExpiredException e) {
			// success
		}
		verify(userCache);		
	}
	
	
	public void testHideUserNotFoundExceptions() {
		UserCache userCache = createMock(UserCache.class);
		expect(userCache.getUserFromCache(USERNAME)).andReturn(null);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setHideUserNotFoundExceptions(true);
		// Test
		try {
			provider.authenticate(authentication);
			fail("BadCredentialsException expected.");
		} catch (BadCredentialsException e) {
			// success
		}
		verify(userCache);		
	}

	
	public void testWrongKey(){
		UserCache userCache = createMock(UserCache.class);
		replay(userCache);
		provider.setUserCache(userCache);
		provider.setKey("WrongKey");
		try {
			provider.authenticate(authentication);
			fail("AuthenticationException expected, due to wrong key.");
		} catch (AuthenticationException e) {
			assertEquals("An authentication was presented, that was not generated by the corresponding shibboleth filter and is thus not supported.", e.getMessage());
		}
		verify(userCache);
	}

	
	public void testEmptyUsername() {
		UserDetails user = null;
		try {
			user = provider.retrieveUser("", (PrincipalAcegiUserToken)authentication);
			fail("UserNotFoundException expected. Instead "+user.getUsername()+" was loaded.");
		} catch (UsernameNotFoundException e) {
			// success
		}
		
		user = null;
		try {
			user = provider.retrieveUser(null, (PrincipalAcegiUserToken)authentication);
			fail("NullPointerException expected.");
		} catch (NullPointerException e) {
			// success
		}		
	}

	
	public void testBackendFailure() {
		// Setup
		String message = "Something went wrong.";
		UserDetailsService userDetailsService = createMock(UserDetailsService.class);
		expect(userDetailsService.loadUserByUsername(USERNAME)).andThrow(new DataAccessResourceFailureException(message));
		replay(userDetailsService);
		provider.setUserDetailsService(userDetailsService);
		
		// Test
		try {
			UserDetails user = provider.retrieveUser(USERNAME, (PrincipalAcegiUserToken)authentication);
			fail("User "+user.getUsername()+" was not expected to be loaded.");			
		} catch (AuthenticationServiceException ase) {
			assertTrue(ase.getMessage().contains(message));
		}
		
		// Setup
		userDetailsService = createMock(UserDetailsService.class);
		expect(userDetailsService.loadUserByUsername(USERNAME)).andReturn(null);
		replay(userDetailsService);
		provider.setUserDetailsService(userDetailsService);

		// Test
		try {
			UserDetails user = provider.retrieveUser(USERNAME, (PrincipalAcegiUserToken)authentication);
			fail("User "+user.getUsername()+" was not expected to be loaded.");			
		} catch (AuthenticationServiceException ase) {
			assertEquals("UserDetailsService returned null, which is an interface contract violation", ase.getMessage());
		}
	}
	
	
	public void testAfterPropertiesSet() {
		MockShibbolethAuthenticationProvider provider = new MockShibbolethAuthenticationProvider();
		UserCache userCache = createMock(UserCache.class);
		replay(userCache);
		
		// Setup working provider.
		provider.setUserDetailsService(userDetailsService);
		provider.setKey(KEY);
		provider.setUserCache(userCache);
		// Test
		try {
			provider.afterPropertiesSet();
		} catch (IllegalArgumentException e) {
			fail("Unexpected IllegalArgumentException");
		} catch (Exception e) {
			fail("Unexpected Exception");
		}
		
		// Setup provider without UserDetailsService.
		provider.setUserDetailsService(null);
		provider.setKey(KEY);
		provider.setUserCache(userCache);
		// Test
		try {
			provider.afterPropertiesSet();
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("A UserDetailsService must be set"));
		} catch (Exception e) {
			fail ("IllegalArgumentException expected.");
		}

		// Setup provider without Key.		
		provider.setUserDetailsService(userDetailsService);
		provider.setKey(null);
		provider.setUserCache(userCache);
		// Test
		try {
			provider.afterPropertiesSet();
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("A key must be set"));
		} catch (Exception e) {
			fail ("IllegalArgumentException expected.");
		}

		// Setup provider without UserCache.
		provider.setUserDetailsService(userDetailsService);
		provider.setKey(KEY);
		provider.setUserCache(null);
		// Test
		try {
			provider.afterPropertiesSet();
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("A user cache must be set"));
		} catch (Exception e) {
			fail ("IllegalArgumentException expected.");
		}
		verify(userCache);
	}
	
	
	public void testSupports() {
		assertTrue(provider.supports(PrincipalAcegiUserToken.class));
		assertFalse(provider.supports(UsernamePasswordAuthenticationToken.class));
		assertFalse(provider.supports(AnonymousAuthenticationToken.class));
		assertFalse(provider.supports(RememberMeAuthenticationToken.class));
		assertFalse(provider.supports(TestingAuthenticationToken.class));
		assertFalse(provider.supports(RunAsUserToken.class));
		assertFalse(provider.supports(JaasAuthenticationToken.class));
		assertFalse(provider.supports(X509AuthenticationToken.class));
		assertFalse(provider.supports(CasAuthenticationToken.class));
		assertFalse(provider.supports(InterceptorStatusToken.class));
	}
	
	
	public void testGettersSetters() {
		UserCache userCache = createMock(UserCache.class);
		replay(userCache);
		provider.setUserCache(userCache);
		assertEquals(userCache, provider.getUserCache());
				
		provider.setUserDetailsService(userDetailsService);
		assertEquals(userDetailsService, provider.getUserDetailsService());
		
		provider.setKey(KEY);
		assertEquals(KEY, provider.getKey());
		
		boolean migrationEnabled = true;
		provider.setMigrationEnabled(migrationEnabled);
		assertTrue(provider.isMigrationEnabled());

		boolean reconciliationEnabled = true;
		provider.setReconciliationEnabled(reconciliationEnabled);
		assertTrue(provider.isReconciliationEnabled());
		
		boolean ignoreDisabledException = true;
		provider.setIgnoreDisabledException(ignoreDisabledException);
		assertTrue(provider.isIgnoreDisabledException());
		
		boolean forcePrincipalAsString = true;
		provider.setForcePrincipalAsString(forcePrincipalAsString);
		assertTrue(provider.isForcePrincipalAsString());
		
		boolean hideUserNotFoundExceptions = false;
		provider.setHideUserNotFoundExceptions(hideUserNotFoundExceptions);
		assertFalse(provider.isHideUserNotFoundExceptions());
		
		verify(userCache);
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
			UserMap userMap = new UserMap();
			userMap.addUser(reconciledUser);
			ShibbolethAuthenticationProviderTest.this.userDetailsService.setUserMap(userMap);
			return true;
		}
		
		@Override
		protected void migrate(UserDetails user, Authentication authentication) {
			migrateCount++;
			UserMap userMap = new UserMap();
			userMap.addUser(migratedUser);
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
		
		public void resetCounters() {
			setMigrateCount(0);
			setReconcileCount(0);
		}
		
	}
}
