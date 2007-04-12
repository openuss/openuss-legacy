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
	
	public void testTemplateMessageDaoCreate() {
		TemplateMessage templateMessage = new TemplateMessageImpl();
		templateMessage.setTemplate(" ");
		assertNull(templateMessage.getId());
		templateMessageDao.create(templateMessage);
		assertNotNull(templateMessage.getId());
	}
}