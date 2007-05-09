// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;



/**
 * JUnit Test for Spring Hibernate TemplateMessageDao class.
 * @see org.openuss.messaging.TemplateMessageDao
 */
public class TemplateMessageDaoTest extends TemplateMessageDaoTestBase {
	
	private static final String SUBJECT = "SUBJECT0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890132";
	private static final String SENDERNAME = "sendername";
	private static final String TEMPLATENAME = "templatename";

	public void testTemplateMessageDaoCreate() {
		TemplateMessage message = TemplateMessage.Factory.newInstance();
		message.setTemplate(TEMPLATENAME);
		message.setSenderName(SENDERNAME);
		message.setSubject(SUBJECT);
		assertNull(message.getId());
		templateMessageDao.create(message);
		assertNotNull(message.getId());
		commit();
		
		message.addParameter("name","name of the sender");
		message.addParameter("fromEmail","no-reply@openuss.org");
		message.addParameter("sender","mailing list");
		
		templateMessageDao.update(message);
		
		
		commit();
		
		TemplateMessage loadMessage = templateMessageDao.load(message.getId());
		
		commit();
		
		assertEquals(3, loadMessage.getParameters().size());
		assertEquals(TEMPLATENAME, loadMessage.getTemplate());
		assertEquals(SENDERNAME, loadMessage.getSenderName());
		assertEquals(SUBJECT, loadMessage.getSubject());
		
	}
}