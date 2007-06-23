// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;

import java.util.Date;

import org.openuss.TestUtility;



/**
 * JUnit Test for Spring Hibernate MailDao class.
 * @see org.openuss.mailinglist.MailDao
 */
public class MailDaoTest extends MailDaoTestBase {
	
	private TestUtility testUtility;
	
	private MailingListDao mailingListDao;
	
	public void testMailDaoCreate() {
		MailingList mailingList = MailingList.Factory.newInstance();
		mailingList.setDomainIdentifier(testUtility.unique());
		mailingList.setName("Name");
		mailingListDao.create(mailingList);
		
		Mail mail = Mail.Factory.newInstance();
		mail.setMailingList(mailingList);
		mail.setStatus(MailingStatus.INQUEUE);
		mail.setSubject("SUBJECT");
		mail.setText("TEXT");
		mail.setSms(true);
		mail.setSendDate(new Date());
		mail.setMessageId(testUtility.unique());
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

	public MailingListDao getMailingListDao() {
		return mailingListDao;
	}

	public void setMailingListDao(MailingListDao mailingListDao) {
		this.mailingListDao = mailingListDao;
	}
}