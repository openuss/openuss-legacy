// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;



/**
 * JUnit Test for Spring Hibernate TemplateMessageDao class.
 * @see org.openuss.messaging.TemplateMessageDao
 */
public class TemplateMessageDaoTest extends TemplateMessageDaoTestBase {
	
	private static final String SUBJECT = "SUBJECT0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890132";
	private static final String SENDERNAME = "sendername";
	private static final String TEMPLATENAME = "templatename";

	public void testTemplateMessageDaoCreate() {
		TemplateMessage message = new TemplateMessageImpl();
		message.setTemplate(TEMPLATENAME);
		message.setSenderName(SENDERNAME);
		message.setSubject(SUBJECT);
		assertNull(message.getId());
		templateMessageDao.create(message);
		assertNotNull(message.getId());
		flush();
		
		message.addParameter("name","name of the sender");
		message.addParameter("fromEmail","no-reply@openuss.org");
		message.addParameter("sender","mailing list");
		message.addParameter(RandomStringUtils.random(64), RandomStringUtils.randomAlphanumeric(255));
		
		templateMessageDao.update(message);
		flush();
		
		TemplateMessage loadMessage = templateMessageDao.load(message.getId());
		
		flush();
		
		assertEquals(4, loadMessage.getParameters().size());
		assertEquals(TEMPLATENAME, loadMessage.getTemplate());
		assertEquals(SENDERNAME, loadMessage.getSenderName());
		assertEquals(SUBJECT, loadMessage.getSubject());
	}
	
	public void testTemplateMessageParameters() {
		TemplateMessage message = new TemplateMessageImpl();
		message.addParameter("paramter_1", RandomStringUtils.random(255) );
		message.addParameter("paramter_2", RandomStringUtils.random(255) );
		message.addParameter("paramter_3", RandomStringUtils.random(255) );
		message.addParameter("paramter_4", RandomStringUtils.random(255) );
		message.addParameter("paramter_4", RandomStringUtils.random(255) );
		
		Set<TemplateParameter> params = message.getParameters();
		assertEquals(4, params.size());
		
		for (TemplateParameter param : new ArrayList<TemplateParameter>(params)) {
			message.removeParameter(param);
		}
		
		assertEquals(0, message.getParameters().size());
	}
}