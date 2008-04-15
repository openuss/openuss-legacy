package org.openuss.documents;

import java.util.Date;
import java.util.List;

import org.acegisecurity.acl.AclManager;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.security.SecurityService;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * This test case test the DocumentService without using transactions.
 * 
 * @author Ingo Dueppe
 *
 */
public class DocumentServiceIntegrationNonTransactionalTest extends AbstractDependencyInjectionSpringContextTests {
	
	private DocumentService documentService;
	
	private SecurityService securityService;
	
	private TestUtility testUtility;
	
	private AclManager aclManager;
	
	private SessionFactory sessionFactory;;
	
	@Override
	protected void onSetUp() throws Exception {
		AcegiUtils.setAclManager(aclManager);
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		testUtility.createUserSecureContext();
		tx.commit();
		session.close();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		super.onSetUp();
	}

	private DefaultDomainObject createDomainObject() {
		DefaultDomainObject defaultDomainObject = new DefaultDomainObject(TestUtility.unique());
		securityService.createObjectIdentity(defaultDomainObject, null);
		return defaultDomainObject;
	}

	public void testFolderRemoving() throws DocumentApplicationException, InterruptedException {
		final DefaultDomainObject domainObject = createDomainObject();
		
		// create folder structure
		FolderInfo root = documentService.getFolder(domainObject);
				
		final FolderInfo subFolder1 = createSubFolder("one");
		final FolderInfo subFolder2 = createSubFolder("two");
		final FolderInfo subFolder3 = createSubFolder("three");
		final FolderInfo subFolder4 = createSubFolder("four");
		
		documentService.createFolder(subFolder1, root);
		documentService.createFolder(subFolder2, subFolder1);
		documentService.createFolder(subFolder3, subFolder2);
		documentService.createFolder(subFolder4, subFolder2);
		
		Runnable loadRootFolder = new Runnable() {
			public void run() {
				FolderInfo root = documentService.getFolder(domainObject);
				assertNotNull(root);
				logger.debug(root.getName());
			}
		};
		Thread load = new Thread(loadRootFolder);
		load.start();
		load.join();
	
		Runnable removeFolders = new Runnable() {
			public void run() {
				try {
					documentService.removeFolderEntry(subFolder1.getId());
				} catch (DocumentApplicationException e) {
					fail(e.getMessage());
				}
			}
		};
		Thread remove = new Thread(removeFolders);
		remove.start();
		remove.join();
		
		FolderInfo folder = documentService.getFolder(domainObject, root);
		assertNotNull(folder);
		List<FolderEntryInfo> entries = documentService.getFolderEntries(domainObject, root);
		assertNotNull(entries);
		assertTrue(entries.isEmpty());
	}
	
	private FolderInfo createSubFolder(String name) {
		FolderInfo folder = new FolderInfo();
		folder.setName(testUtility.unique(name));
		folder.setDescription(name);
		folder.setCreated(DateUtils.addDays(new Date(), -1));
		return folder;
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
				"classpath*:testDisableSecurity.xml", 
				"classpath*:testDataSource.xml"};
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	public AclManager getAclManager() {
		return aclManager;
	}

	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
