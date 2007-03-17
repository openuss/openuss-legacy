// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.util.List;

import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate DocumentService class.
 * @see org.openuss.documents.DocumentService
 */
public class DocumentServiceIntegrationTest extends DocumentServiceIntegrationTestBase {
	
	private TestUtility testUtility;
	
	public void testFolderEntries() {
		DomainObject domainObject = new DomainObject(testUtility.unique());
		List<FolderEntryInfo> entries = documentService.getFolderEntries(domainObject, null);
		assertNotNull(entries);
		assertEquals(0, entries.size());

		Folder root = documentService.getFolder(domainObject, null);
		assertNotNull(root);
		assertEquals("ROOT_FOLDER",root.getName());
		assertEquals(domainObject.getId(), root.getDomainIdentifier());
		commit();
		
		Folder folder = Folder.Factory.newInstance();
		folder.setName("first folder");
		folder.setDescription("first folder description");
		assertNull(folder.getId());
		documentService.addFolderEntry(folder, root);
		assertNotNull(folder.getId());
		assertEquals(root, folder.getParent());
		
		entries = documentService.getFolderEntries(null, root);
		assertNotNull(entries);
		assertEquals(1, entries.size());
		FolderEntryInfo info = entries.get(0);
		assertEquals(folder.getId(), info.getId());
		assertEquals(folder.getName(), info.getName());
		assertEquals(folder.getDescription(), info.getDescription());
	}
	
	private void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}
	
	public static class DomainObject {
		private Long id;

		public DomainObject() {};
		
		public DomainObject(Long id) {
			this.id = id;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
}