// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.openuss.TestUtility;
import org.openuss.repository.RepositoryFile;
import org.openuss.repository.RepositoryService;

/**
 * JUnit Test for Spring Hibernate DocumentService class.
 * @see org.openuss.documents.DocumentService
 */
public class DocumentServiceIntegrationTest extends DocumentServiceIntegrationTestBase {
	
	private TestUtility testUtility;
	
	private RepositoryService repositoryService;
	
	private FolderEntryDao folderEntryDao;
	

	public void testFolderEntries() throws DocumentExcepion {
		DomainObject domainObject = createDomainObject();
		List<FolderEntryInfo> entries = documentService.getFolderEntries(domainObject, null);
		assertNotNull(entries);
		assertEquals(0, entries.size());

		Folder root = documentService.getFolder(domainObject, null);
		assertNotNull(root);
		assertEquals("ROOT_FOLDER",root.getName());
		assertEquals(domainObject.getId(), root.getDomainIdentifier());
		commit();
		
		Folder folder = createSubFolder();

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
		assertEquals(folder.getExtension(), info.getExtension());
		
		entries = documentService.getFolderEntries(null, folder);
		assertNotNull(entries);
		assertEquals(0, entries.size());
	}

	public void testFolderPath() throws DocumentExcepion {
		DomainObject domainObject = createDomainObject();
		Folder root = documentService.getFolder(domainObject, null);
		
		Folder subFolder1 = createSubFolder();
		Folder subFolder2 = createSubFolder();
		Folder subFolder3 = createSubFolder();
		
		documentService.addFolderEntry(subFolder1, root);
		documentService.addFolderEntry(subFolder2, subFolder1);
		documentService.addFolderEntry(subFolder3, subFolder2);
		
		commit();
		
		List<Folder> folderPath = documentService.getFolderPath(subFolder3);
		
		assertNotNull(folderPath);
		assertEquals(4, folderPath.size());
		
		assertEquals(root, folderPath.get(0));
		assertEquals(subFolder1, folderPath.get(1));
		assertEquals(subFolder2, folderPath.get(2));
		assertEquals(subFolder3, folderPath.get(3));
	}
	
	public void testFolderAddRemoving() throws DocumentExcepion {
		DomainObject domainObject = createDomainObject();
		Folder root = documentService.getFolder(domainObject, null);
		
		Folder subFolder1 = createSubFolder();
		Folder subFolder2 = createSubFolder();
		Folder subFolder3 = createSubFolder();
		Folder subFolder4 = createSubFolder();
		
		documentService.addFolderEntry(subFolder1, root);
		documentService.addFolderEntry(subFolder2, subFolder1);
		documentService.addFolderEntry(subFolder3, subFolder2);
		documentService.addFolderEntry(subFolder4, subFolder2);
		
		assertNotNull(subFolder1.getId());
		assertNotNull(subFolder2.getId());
		assertNotNull(subFolder3.getId());
		assertNotNull(subFolder4.getId());
		
		List<FolderEntryInfo> entries = documentService.getFolderEntries(domainObject, subFolder2);
		assertNotNull(entries);
		assertEquals(2, entries.size());

		FolderEntryInfo infoA = entries.get(0);
		FolderEntryInfo infoB = entries.get(1);
		
		assertTrue(!infoA.getId().equals(infoB.getId()));
		assertTrue(infoA.getId().equals(subFolder3.getId()) || infoA.getId().equals(subFolder4.getId()));
		assertTrue(infoB.getId().equals(subFolder3.getId()) || infoB.getId().equals(subFolder4.getId()));
		
		commit();
		
		FolderEntryInfo info = new FolderEntryInfo();
		info.setId(subFolder2.getId());
		
		documentService.removeFolderEntry(info);
		entries = documentService.getFolderEntries(null, subFolder1);
		assertNotNull(entries);
		assertEquals(0, entries.size());
		
		// check that the subfolder is really deleted
		FolderEntry folderEntry = folderEntryDao.load(subFolder3.getId());
		assertNull(folderEntry);
	}
	
	public void testFileEntry() throws DocumentExcepion {
		testUtility.createSecureContext();
		DomainObject domainObject = createDomainObject();
		Folder root = documentService.getFolder(domainObject, null);
		
		RepositoryFile repositoryFile = createRepositoryFile();
		FileEntry fileEntry = FileEntry.Factory.newInstance();
		fileEntry.setRepositoryFile(repositoryFile);
		
		
		assertNull(fileEntry.getId());
		documentService.addFileEntry(fileEntry, root);
		assertNotNull(fileEntry.getId());
		assertNotNull(repositoryFile.getId());
		
		List<FolderEntryInfo> entries = documentService.getFolderEntries(domainObject, null);
		assertNotNull(entries);
		assertEquals(1, entries.size());
		FolderEntryInfo info = entries.get(0);
		
		assertEquals(repositoryFile.getName(), info.getName());
		assertEquals(repositoryFile.getDescription(), info.getDescription());
		assertEquals(repositoryFile.getFileSize(), info.getSize());
		assertEquals(repositoryFile.getCreated(), info.getCreated());
		assertEquals(repositoryFile.getModified(), info.getModified());
		
		documentService.removeFolderEntry(info);
		
		RepositoryFile repoFile = repositoryService.getFile(repositoryFile);
		assertNull(repoFile);
	}

	private RepositoryFile createRepositoryFile() {
		RepositoryFile repositoryFile = RepositoryFile.Factory.newInstance();
		byte[] data = "this is the content of the file".getBytes();
		repositoryFile.setFileName("dummy.pdf");
		repositoryFile.setFileSize(data.length);
		repositoryFile.setContentType("plain/text");
		repositoryFile.setName("name of the file");
		repositoryFile.setInputStream(new ByteArrayInputStream(data));
		return repositoryFile;
	}
	

	private Folder createSubFolder() {
		Folder folder = Folder.Factory.newInstance();
		folder.setName(testUtility.unique("folder name"));
		folder.setDescription("first folder description");
		return folder;
	}

	
	private DomainObject createDomainObject() {
		DomainObject domainObject = new DomainObject(testUtility.unique());
		return domainObject;
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

	public FolderEntryDao getFolderEntryDao() {
		return folderEntryDao;
	}

	public void setFolderEntryDao(FolderEntryDao folderEntryDao) {
		this.folderEntryDao = folderEntryDao;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	
}