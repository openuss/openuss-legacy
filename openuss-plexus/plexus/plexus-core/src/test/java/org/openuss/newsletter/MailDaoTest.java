// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.newsletter;

import java.util.Date;

import org.openuss.TestUtility;



/**
 * JUnit Test for Spring Hibernate MailDao class.
 * @see org.openuss.newsletter.MailDao
 */
public class MailDaoTest extends MailDaoTestBase {
	
	private TestUtility testUtility;
	
	private NewsletterDao newsletterDao;
	
	public void testMailDaoCreate() {
		Newsletter newsletter = Newsletter.Factory.newInstance();
		newsletter.setDomainIdentifier(TestUtility.unique());
		newsletter.setName("Name");
		newsletterDao.create(newsletter);
		
		Mail mail = new MailImpl();
		mail.setNewsletter(newsletter);
		mail.setStatus(MailingStatus.INQUEUE);
		mail.setSubject("SUBJECT");
		mail.setText("TEXT");
		mail.setSms(true);
		mail.setSendDate(new Date());
		mail.setMessageId(TestUtility.unique());
		assertNull(mail.getId());
		mailDao.create(mail);
		assertNotNull(mail.getId());
		assertNotNull(mailDao.findMailByMessageId(mail.getMessageId()));
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public NewsletterDao getNewsletterDao() {
		return newsletterDao;
	}

	public void setNewsletterDao(NewsletterDao newsletterDao) {
		this.newsletterDao = newsletterDao;
	}
}