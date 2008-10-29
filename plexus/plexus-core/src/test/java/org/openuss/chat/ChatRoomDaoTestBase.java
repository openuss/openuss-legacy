// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.chat;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * JUnit Test for Spring Hibernate ChatRoomDao class.
 * @see org.openuss.chat.ChatRoomDao
 */
public abstract class ChatRoomDaoTestBase extends AbstractTransactionalDataSourceSpringContextTests {
	
	protected static final Logger logger = Logger.getLogger(ChatRoomDaoTestBase.class);

	protected ChatRoomDao chatRoomDao;
	
	public ChatRoomDao getChatRoomDao() {
		return chatRoomDao;
	}

	public void setChatRoomDao(ChatRoomDao chatRoomDao) {
		this.chatRoomDao = chatRoomDao;
	}

	public void testChatRoomDaoInjection() {
		assertNotNull(chatRoomDao);
	}
	
	protected void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}
	
	protected void flush() {
	    sessionFactory.getCurrentSession().flush();
	}
	
	protected SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
	    return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) { 
	     this.sessionFactory = sessionFactory;
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
			"classpath*:testContext.xml", 
			"classpath*:testDisableSecurity.xml", 
			"classpath*:testDataSource.xml"};
	}
	
	
}