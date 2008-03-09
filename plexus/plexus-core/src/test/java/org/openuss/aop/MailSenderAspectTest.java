package org.openuss.aop;

import org.hibernate.SessionFactory;
import org.openuss.TestUtility;
import org.openuss.lecture.OrganisationService;
import org.openuss.lecture.University;
import org.openuss.messaging.MessageJobDaoMock;
import org.openuss.messaging.MessageSendingCommand;
import org.openuss.security.User;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * 
 * @author Ingo Dueppe
 *
 */
public class MailSenderAspectTest extends AbstractTransactionalDataSourceSpringContextTests {

	protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		jobDao = new MessageJobDaoMock();
		messageSendingCommand.setMessageJobDao(jobDao);
	}

	public void testSendAddMemberMail() {
		University university = testUtility.createUniqueUniversityInDB();
		User user = testUtility.createUniqueUserInDB();
		user.setEmail("plexus@openuss-plexus.org");

		OrganisationService organisationService = (OrganisationService) this.applicationContext
				.getBean("organisationService");
		organisationService.addMember(university.getId(), user.getId());
	}

	protected String[] getConfigLocations() {
		return new String[] { 
				"classpath*:applicationContext.xml", 
				"classpath*:applicationContext-beans.xml",
				"classpath*:applicationContext-lucene.xml", 
				"classpath*:applicationContext-cache.xml",
				"classpath*:applicationContext-messaging.xml", 
				"classpath*:applicationContext-resources.xml",
				"classpath*:applicationContext-aop.xml", 
				"classpath*:applicationContext-events.xml",
				"classpath*:testContext.xml", 
				"classpath*:testSecurity.xml",
				"classpath*:testDataSource.xml" };
	}

	private TestUtility testUtility;

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	private MessageSendingCommand messageSendingCommand;

	public MessageSendingCommand getMessageSendingCommand() {
		return messageSendingCommand;
	}

	public void setMessageSendingCommand(MessageSendingCommand messageSendingCommand) {
		this.messageSendingCommand = messageSendingCommand;
	}
	private MessageJobDaoMock jobDao;

}
