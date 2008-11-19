package org.openuss.migration;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.HashMap;

import junit.framework.TestCase;

import org.openuss.messaging.MessageService;
import org.openuss.security.SecurityDomainUtility;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;


public class UserMigrationUtilityTest extends TestCase {

	
	SecurityService securityService;
	MessageService messageService;
	UserMigrationUtility userMigrationUtility;
	CentralUserData centralUserData;
	UserInfo userInfo;
	
	public void setUp() {
		securityService = createNiceMock(SecurityService.class);
		messageService = createNiceMock(MessageService.class);

		userMigrationUtility = new UserMigrationUtilityImpl();
		userMigrationUtility.setMessageService(messageService);
		userMigrationUtility.setSecurityService(securityService);
		centralUserData = new CentralUserDataImpl();
		centralUserData.setFirstName("Hans");
		centralUserData.setLastName("Mustermann");
		centralUserData.setEmail("h_muster01@uni-muenster.de");
		centralUserData.setAuthenticationDomainName("wwu");
		centralUserData.setAuthenticationDomainId(42L);
		centralUserData.setUsername(SecurityDomainUtility.toUsername(centralUserData.getAuthenticationDomainName(), "h_muster01"));
		userInfo = new UserInfo();
	}

	public void testUserMigrationUtilityReconcile() {

		securityService.saveUser(userInfo);
		securityService.saveUser(userInfo);
		replay(securityService);
		
		// Nothing to do
		userInfo.setId(4711L);
		userInfo.setEmail(centralUserData.getEmail());
		userInfo.setFirstName(centralUserData.getFirstName());
		userInfo.setLastName(centralUserData.getLastName());
		
		assertFalse(userMigrationUtility.reconcile(userInfo, centralUserData, false));
		
		
		// Lastname changed, e. g. due to marriage.
		userInfo.setId(4711L);
		userInfo.setEmail(centralUserData.getEmail());
		userInfo.setFirstName(centralUserData.getFirstName());
		userInfo.setLastName("def");
		
		assertTrue(userMigrationUtility.reconcile(userInfo, centralUserData, false));
		
		// Force save user
		userInfo.setId(4711L);
		userInfo.setEmail(centralUserData.getEmail());
		userInfo.setFirstName(centralUserData.getFirstName());
		userInfo.setLastName(centralUserData.getLastName());		
		
		assertTrue(userMigrationUtility.reconcile(userInfo, centralUserData, true));
		
		verify(securityService);
	}
	
	/**
	 * Not working, due to password is a random value. So call to this method cannot be recorded in advance.
	 */
	public void testUserMigrationUtilityMigrate() {
		userInfo.setId(4711L);
		userInfo.setEnabled(false);
		userInfo.setUsername("username");
		userInfo.setEmail("test@test.org");
		userInfo.setFirstName("abc");
		userInfo.setLastName("def");
		expect(securityService.getGrantedAuthorities(userInfo)).andReturn(new String[]{"test"});
		securityService.changePassword("[some random password]");
		securityService.saveUser(userInfo);
		expect(messageService.sendMessage("user.migration.notification.sender", "user.migration.notification.subject", "migrationnotification",
				new HashMap<String, Object>(), userInfo)).andReturn(42L);
		replay(securityService, messageService);
		
		userMigrationUtility.migrate(userInfo, centralUserData);
		
		assertEquals(centralUserData.getUsername(), userInfo.getUsername());
		assertEquals(centralUserData.getEmail(), userInfo.getEmail());
		assertEquals(centralUserData.getFirstName(), userInfo.getFirstName());
		assertEquals(centralUserData.getLastName(), userInfo.getLastName());
		assertTrue(userInfo.isEnabled());
		// Verification fails, due to wrong parameters within method calls. 
		// For example the random password is not known in advance, 
		// i. e. within record phase of securityService mock, 
		// but generated at replay time. EasyMock developers are working on this.
		// verify(securityService, messageService);
	}	
	
	
	public void testUserMigrationUtilitySetterAndGetter() {
		UserMigrationUtilityImpl umu = new UserMigrationUtilityImpl();
		try {
			umu.afterPropertiesSet();
			fail("Exception expected. Necessary properties were not set.");
		} catch (Exception e) {
			// success
		}
	}
}
	
	

