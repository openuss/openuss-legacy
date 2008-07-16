package org.openuss.migration;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.openuss.messaging.JobInfo;
import org.openuss.messaging.MessageService;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;


public class UserMigrationUtilityTest extends TestCase {

	
	SecurityService securityService;
	MessageService messageService = new NullMessageService();
	UserMigrationUtility userMigrationUtility;
	CentralUserData centralUserData;
	UserInfo userInfo;
	
	public void setUp() {
		securityService = createMock(SecurityService.class);
		userMigrationUtility = new UserMigrationUtilityImpl();
		userMigrationUtility.setMessageService(messageService);
		userMigrationUtility.setSecurityService(securityService);
		centralUserData = new CentralUserDataImpl();
		centralUserData.setUsername("h_muster01");
		centralUserData.setFirstName("Hans");
		centralUserData.setLastName("Mustermann");
		centralUserData.setEmail("h_muster01@uni-muenster.de");
		centralUserData.setAuthenticationDomainName("wwu");
		centralUserData.setAuthenticationDomainId(42L);
		userInfo = new UserInfo();
	}

	public void testUserMigrationUtilityReconcile() {
		securityService.saveUser(userInfo);
		securityService.saveUser(userInfo);
		replay();
		
		// Nothing to do
		userInfo.setEmail(centralUserData.getEmail());
		userInfo.setFirstName(centralUserData.getFirstName());
		userInfo.setLastName(centralUserData.getLastName());
		
		assertFalse(userMigrationUtility.reconcile(userInfo, centralUserData, false));
		
		
		// Lastname changed, e. g. due to marriage.
		userInfo.setEmail(centralUserData.getEmail());
		userInfo.setFirstName(centralUserData.getFirstName());
		userInfo.setLastName("def");
		
		assertTrue(userMigrationUtility.reconcile(userInfo, centralUserData, false));
		
		// Force save user
		userInfo.setEmail(centralUserData.getEmail());
		userInfo.setFirstName(centralUserData.getFirstName());
		userInfo.setLastName(centralUserData.getLastName());		
		
		assertTrue(userMigrationUtility.reconcile(userInfo, centralUserData, true));
		
		verify();
	}
	
	/**
	 * Not working, due to password is a random value. So call to this method cannot be recorded in advance.
	 */
//	public void testUserMigrationUtilityMigrate() {
//		String password = "";
//		expect(securityService.getGrantedAuthorities(userInfo)).andReturn(null);
//		securityService.changePassword(password);
//		securityService.saveUser(userInfo);
//		replay();
//		userInfo.setEnabled(false);
//		userInfo.setUsername("username");
//		userInfo.setEmail("test@test.org");
//		userInfo.setFirstName("abc");
//		userInfo.setLastName("def");
//		
//		userMigrationUtility.migrate(userInfo, centralUserData);
//		
//		assertEquals(centralUserData.getUsername(), userInfo.getUsername());
//		assertEquals(centralUserData.getEmail(), userInfo.getEmail());
//		assertEquals(centralUserData.getFirstName(), userInfo.getFirstName());
//		assertEquals(centralUserData.getLastName(), userInfo.getLastName());
//		assertTrue(userInfo.isEnabled());		
//	}	
	
	
	public void testUserMigrationUtilitySetterAndGetter() {
		UserMigrationUtilityImpl umu = new UserMigrationUtilityImpl();
		try {
			umu.afterPropertiesSet();
			fail("Exception expected. Necessary properties were not set.");
		} catch (Exception e) {
			// success
		}
	}
	
	
	private class NullMessageService implements MessageService {

		@Override
		public JobInfo getJobState(Long messageId) {
			return null;
		}

		@Override
		public Long sendMessage(String sender, String subject, String text,
				boolean sms, List recipients) {
			return null;
		}

		@Override
		public Long sendMessage(String sender, String subject, String text,
				boolean sms, UserInfo recipient) {
			return null;
		}

		@Override
		public Long sendMessage(String sender, String subject,
				String templateName, Map parameters, List recipients) {
			return null;
		}

		@Override
		public Long sendMessage(String sender, String subject,
				String templateName, Map paramters, UserInfo recipient) {
			return null;
		}

		@Override
		public Long sendMessage(String sender, String subject,
				String templateName, Map parameters, String email, String locale) {
			return null;
		}
	
	}
}
