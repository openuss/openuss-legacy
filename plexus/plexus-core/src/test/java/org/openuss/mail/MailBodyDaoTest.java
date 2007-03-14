// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mail;


/**
 * JUnit Test for Spring Hibernate MailBodyDao class.
 * @see org.openuss.mail.MailBodyDao
 */
public class MailBodyDaoTest extends MailBodyDaoTestBase {
	
	public void testMailBodyDaoCreate() {
		MailBody mailBody = new MailBodyImpl();
		assertNull(mailBody.getId());
		mailBodyDao.create(mailBody);
		assertNotNull(mailBody.getId());
	}
}