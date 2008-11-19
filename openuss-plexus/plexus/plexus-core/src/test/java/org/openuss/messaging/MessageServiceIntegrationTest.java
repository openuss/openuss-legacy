// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openuss.security.User;

/**
 * JUnit Test for Spring Hibernate MessageService class.
 * @see org.openuss.messaging.MessageService
 */
public class MessageServiceIntegrationTest extends MessageServiceIntegrationTestBase {
	
	public void testSendTextMessage() {
		List<User> recipients = createUsers(50);
		Long messageId = messageService.sendMessage("sender", "courseType", "text", true, recipients);
		assertNotNull(messageId);
		flush();
		JobInfo state = messageService.getJobState(messageId);
		assertEquals(recipients.size(), state.getTosend());
		assertEquals(0, state.getError());
		assertEquals(0, state.getSend());
	}

	public void testSendTemplateMessage() {
		List<User> recipients = createUsers(50);
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("param1", "value");
		parameters.put("param2", "value");
		
		Long messageId = messageService.sendMessage("sender", "courseType", "templatename", parameters , recipients);
		
		assertNotNull(messageId);
		flush();
		JobInfo state = messageService.getJobState(messageId);
		assertEquals(recipients.size(), state.getTosend());
		assertEquals(0, state.getError());
		assertEquals(0, state.getSend());
	}
	
	private List<User> createUsers(int number) {
		List<User> users = new ArrayList<User>();
		for(int i = 0; i < number; i++) {
			users.add(testUtility.createUniqueUserInDB());
		}
		return users;
	}
	
}