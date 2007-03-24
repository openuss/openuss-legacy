// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
	

	public void testFolderEntries() throws DocumentApplicationException {
		DomainObject domainObject = createDomainObject();
		List<FolderEntryInfo> entries = documentService.getFolderEntries(domainObject, null);
		assertNotNull(entries);
		assertEquals(0, entries.size());

		FolderInfo root = documentService.getFolder(domainObject);
		assertNotNull(root);
		assertEquals("ROOT",root.getName());
		assertTrue(root.isRoot());
		commit();
		
		FolderInfo folder = createSubFolder();

		assertNull(folder.getId());
		documentService.createFolder(folder, root);
		assertNotNull(folder.getId());
		assertEquals("/ROOT",folder.getPath());
		
		entries = documentService.getFolderEntries(null, root);
		assertNotNull(entries);
		assertEquals(1, entries.size());
		FolderEntryInfo info = entries.get(0);
		assertEquals(folder.getId(), info.getId());
		assertEquals(folder.getName(), info.getName());
		assertEquals(folder.getDescription(), info.getDescription());
		assertEquals(folder.getPath(), info.getPath());
		assertEquals(folder.getModified(), info.getModified());
		assertTrue( info.isFolder());
		assertEquals( folder.getCreated(), info.getReleaseDate());
		assertTrue( info.isReleased());
		
		entries = documentService.getFolderEntries(null, folder);
		assertNotNull(entries);
		assertEquals(0, entries.size());
	}

	public void testFolderPath() throws DocumentApplicationException {
		DomainObject domainObject = createDomainObject();
		FolderInfo root = documentService.getFolder(domainObject);
		
		FolderInfo subFolder1 = createSubFolder();
		FolderInfo subFolder2 = createSubFolder();
		FolderInfo subFolder3 = createSubFolder();
		
		documentService.createFolder(subFolder1, root);
		documentService.createFolder(subFolder2, subFolder1);
		documentService.createFolder(subFolder3, subFolder2);
		
		commit();
		
		List<Folder> folderPath = documentService.getFolderPath(subFolder3);
		
		assertNotNull(folderPath);
		assertEquals(4, folderPath.size());
		
		assertEquals(root, folderPath.get(0));
		assertEquals(subFolder1, folderPath.get(1));
		assertEquals(subFolder2, folderPath.get(2));
		assertEquals(subFolder3, folderPath.get(3));
	}
	
	public void testFolderAddRemoving() throws DocumentApplicationException {
		DomainObject domainObject = createDomainObject();
		FolderInfo root = documentService.getFolder(domainObject);
		
		FolderInfo subFolder1 = createSubFolder();
		FolderInfo subFolder2 = createSubFolder();
		FolderInfo subFolder3 = createSubFolder();
		FolderInfo subFolder4 = createSubFolder();
		
		documentService.createFolder(subFolder1, root);
		documentService.createFolder(subFolder2, subFolder1);
		documentService.createFolder(subFolder3, subFolder2);
		documentService.createFolder(subFolder4, subFolder2);
		
		subFolder1 = documentService.getFolder(subFolder1);
		subFolder2 = documentService.getFolder(subFolder2);
		subFolder3 = documentService.getFolder(subFolder3);
		subFolder4 = documentService.getFolder(subFolder4);
		
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
	
	public void testCreateFileByFolderEntryInfo() throws DocumentApplicationException {
		testUtility.createSecureContext();
		DomainObject domainObject = createDomainObject();
		FolderInfo root = documentService.getFolder(domainObject);
		
		RepositoryFile repositoryFile = createRepositoryFile();
		commit();
		
		FolderEntryInfo fileEntry = new FolderEntryInfo();
		fileEntry.setRepositoryFileId(repositoryFile.getId());
		
		assertNull(fileEntry.getId());
		documentService.createFileEntry(fileEntry, root);
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
		
		commit();
		
		documentService.removeFolderEntry(info);
		
		commit();
		
		FolderInfo folderInfo = documentService.getFolder(info);
		assertNull(folderInfo);
		
		RepositoryFile repoFile = repositoryService.getFile(repositoryFile);
		assertNull(repoFile);
		commit();
	}
	
	public void testCreateFileByFileInfo() throws DocumentApplicationException, IOException {
		testUtility.createSecureContext();
		DomainObject domainObject = createDomainObject();
		FolderInfo root = documentService.getFolder(domainObject);

		String name = "/dummy/dummy.txt";
		
		FileInfo info = createFileInfo(name);

		documentService.createFileEntry(info, root);
		
		info.getInputStream().close();
		
		commit();
		
		List<FolderEntryInfo> entries = documentService.getFolderEntries(domainObject, null);
		assertNotNull(entries);
		assertEquals(1, entries.size());
		
		FolderEntryInfo dummyFolder = entries.get(0);
		assertEquals("dummy", dummyFolder.getName());
		
		entries = documentService.getFolderEntries( null, documentService.getFolder(dummyFolder));
		assertNotNull(entries);
		assertEquals(1, entries.size());
		
		FolderEntryInfo entry = entries.get(0);
		
		assertEquals("dummy.txt", entry.getName());
		assertEquals("txt", entry.getExtension());
		assertEquals("/ROOT/dummy", entry.getPath());
		assertEquals("description", entry.getDescription());
		assertEquals(info.getSize(), entry.getSize());
		assertNotNull(entry.getCreated());
		assertNotNull(entry.getModified());
		assertTrue(entry.isReleased());
		assertFalse(entry.isFolder());
		assertEquals(entry.getCreated(), entry.getReleaseDate());
		
		RepositoryFile repoFile = RepositoryFile.Factory.newInstance();
		repoFile.setId(entry.getRepositoryFileId());

		repoFile = repositoryService.getFile(repoFile, false);
		assertNotNull(repoFile);
		
		documentService.removeFolderEntry(entry);
		commit();

		assertNull(repositoryService.getFile(repoFile, false));
		commit();
	}
	
	
	public void testCreateFileEntries() throws DocumentApplicationException {
		testUtility.createSecureContext();
		DomainObject domainObject = createDomainObject();
		FolderInfo root = documentService.getFolder(domainObject);
		
		FolderInfo subfolder = createSubFolder();
		subfolder.setName("subfolder");
		documentService.createFolder(subfolder, root);

		List<FileInfo> files = new ArrayList<FileInfo>();

		
		files.add(createFileInfo("readme.txt"));
		files.add(createFileInfo("/folien/kapitel 1/text.txt"));
		files.add(createFileInfo("/folien/kapitel 2/readme.txt"));
		files.add(createFileInfo("/übungen/aufgabe/aufgabentext1.txt"));
		
		documentService.createFolderEntries(files, subfolder);
		
		commit();
		
		List<FolderEntryInfo> entries = documentService.getFolderEntries(null, subfolder);
		assertNotNull(entries);
		assertEquals(3, entries.size());
		FolderEntryInfo entry = entries.get(0);
		assertTrue(entry.getPath().startsWith("/ROOT/subfolder"));

		List<FileInfo> infos = documentService.allFileEntries(entries);
		assertNotNull(infos);
		
		assertEquals(4, infos.size());
		
		FileInfo info = infos.get(0);
		validateFileInfo(info);
	}

	
	public void testGetFileEntry() throws DocumentApplicationException {
		testUtility.createSecureContext();
		DomainObject domainObject = createDomainObject();
		FolderInfo root = documentService.getFolder(domainObject);
		
		FileInfo info = createFileInfo("readme.txt");
		
		documentService.createFileEntry(info, root);
		commit();

		List<FolderEntryInfo> entries = documentService.getFolderEntries(domainObject, null);
		FolderEntryInfo entry = entries.get(0);
		
		FileInfo file = documentService.getFileEntry(entry, false);
		assertNull(file.getInputStream());
		
		file = documentService.getFileEntry(entry, true);
		validateFileInfo(info);
	}
	
	private void validateFileInfo(FileInfo info) {
		assertNotNull(info.getName());
		assertNotNull(info.getAbsoluteName());
		assertNotNull(info.getPath());
		assertNotNull(info.getFileName());
		assertEquals(info.getPath()+"/"+info.getFileName(), info.getAbsoluteName());
		assertNotNull(info.getCreated());
		assertNotNull(info.getModified());
		assertNotNull(info.getSize());
		assertNotNull(info.getInputStream());
		assertNotNull(info.getContentType());
	}


	private FileInfo createFileInfo(String name) {
		FileInfo info = new FileInfo();
		byte[] data = "this is the content of the file".getBytes();
		info.setName(name);
		info.setDescription("description");
		info.setContentType("plain/text");
		info.setSize(data.length);
		info.setInputStream(new ByteArrayInputStream(data));
		return info;
	}
	
	
	private RepositoryFile createRepositoryFile() {
		RepositoryFile repositoryFile = RepositoryFile.Factory.newInstance();
		byte[] data = "this is the content of the file".getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		repositoryFile.setFileName("dummy.pdf");
		repositoryFile.setFileSize(data.length);
		repositoryFile.setContentType("plain/text");
		repositoryFile.setName("name of the file");
		
		repositoryFile.setInputStream(bais);
		repositoryService.saveFile(repositoryFile);
		try {
			bais.close();
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return repositoryFile;
	}
	

	private FolderInfo createSubFolder() {
		FolderInfo folder = new FolderInfo();
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