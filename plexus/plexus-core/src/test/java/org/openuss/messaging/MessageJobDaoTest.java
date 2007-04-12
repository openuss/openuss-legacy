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
		messageJob.setSendAsSms(true);
		assertNull(messageJob.getId());
		messageJobDao.create(messageJob);
		assertNotNull(messageJob.getId());
	}
}