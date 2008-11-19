// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

import java.util.Date;



/**
 * JUnit Test for Spring Hibernate MessageJobDao class.
 * @see org.openuss.messaging.MessageJobDao
 */
public class MessageJobDaoTest extends MessageJobDaoTestBase {
	
	public void testMessageJobDaoCreate() {
		MessageJob messageJob = new MessageJobImpl();
		messageJob.setCreated(new Date());
		messageJob.setState(JobState.INQUEUE);
		TextMessage message = new TextMessageImpl();
		message.setSenderName("sender name");
		message.setSubject("subject");
		message.setText("Text");
		messageJob.setMessage(message);
		assertNull(messageJob.getId());
		messageJobDao.create(messageJob);
		assertNotNull(messageJob.getId());
		messageJob.setState(JobState.DONE);
		messageJobDao.update(messageJob);
	}
}