// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.acegisecurity.acl.AclManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.openuss.TestUtility;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.repository.RepositoryService;
import org.openuss.repository.RepositoryServiceException;
import org.openuss.security.SecurityService;

/**
 * JUnit Test for Spring Hibernate DocumentService class.
 * 
 * @see org.openuss.documents.DocumentService
 */
public class DocumentServiceIntegrationTest extends
		DocumentServiceIntegrationTestBase {

	private AclManager aclManager;

	private RepositoryService repositoryService;

	private FolderEntryDao folderEntryDao;

	private FolderDao folderDao;

	private SecurityService securityService;

	private DefaultDomainObject defaultDomainObject;

	@Override
	protected void onSetUpInTransaction() throws Exception {
		AcegiUtils.setAclManager(aclManager);
		testUtility.createUserSecureContext();
		defaultDomainObject = createDomainObject();
		super.onSetUpInTransaction();
	}

	public void testNotReleasedFiles() throws DocumentApplicationException {
		FolderInfo root = documentService.getFolder(defaultDomainObject);

		FileInfo fileOne = createFileInfo("readme.txt");
		fileOne
				.setCreated(new Date(
						System.currentTimeMillis() + 1000 * 60 * 60));

		documentService.createFileEntry(fileOne, root);
		assertNotNull(fileOne.getId());
		assertFalse(fileOne.isReleased());

		FileInfo fileTwo = createFileInfo("released.txt");
		documentService.createFileEntry(fileTwo, root);
		assertNotNull(fileTwo.getId());
		assertTrue(fileTwo.isReleased());

		List<FolderEntryInfo> entries = documentService.getFolderEntries(
				defaultDomainObject, null);
		assertNotNull(entries);
		assertEquals(1, entries.size());

	}

	public void testFolderEntries() throws DocumentApplicationException {
		List<FolderEntryInfo> entries = documentService.getFolderEntries(
				defaultDomainObject, null);
		assertNotNull(entries);
		assertEquals(0, entries.size());

		FolderInfo root = documentService.getFolder(defaultDomainObject);
		assertNotNull(root);
		assertEquals("", root.getName());
		assertTrue(root.isRoot());
		flush();

		FolderInfo folder = createSubFolder();

		assertNull(folder.getId());
		documentService.createFolder(folder, root);
		assertNotNull(folder.getId());
		assertEquals("", folder.getPath());

		flush();
		entries = documentService.getFolderEntries(defaultDomainObject, root);
		assertNotNull(entries);
		// FIXME this asserts cause under some circustances trouble
		// I guess it has something todo with the security cache
		// neeed to be fixed soon -
		assertEquals(1, entries.size());
		FolderEntryInfo info = entries.get(0);
		assertEquals(folder.getId(), info.getId());
		assertEquals(folder.getName(), info.getName());
		assertEquals(folder.getDescription(), info.getDescription());
		assertEquals(folder.getPath(), info.getPath());
		assertEquals(folder.getModified(), info.getModified());
		assertTrue(info.isFolder());
		assertEquals(folder.getCreated(), info.getReleaseDate());
		assertTrue(info.isReleased());

		entries = documentService.getFolderEntries(defaultDomainObject, folder);
		assertNotNull(entries);
		assertEquals(0, entries.size());
	}

	public void testFolderPath() throws DocumentApplicationException {
		FolderInfo root = documentService.getFolder(defaultDomainObject);

		FolderInfo subFolder1 = createSubFolder();
		FolderInfo subFolder2 = createSubFolder();
		FolderInfo subFolder3 = createSubFolder();

		documentService.createFolder(subFolder1, root);
		documentService.createFolder(subFolder2, subFolder1);
		documentService.createFolder(subFolder3, subFolder2);

		flush();

		List<Folder> folderPath = documentService.getFolderPath(subFolder3);

		assertNotNull(folderPath);
		assertEquals(4, folderPath.size());

		assertEquals(root, folderPath.get(0));
		assertEquals(subFolder1, folderPath.get(1));
		assertEquals(subFolder2, folderPath.get(2));
		assertEquals(subFolder3, folderPath.get(3));
	}

	public void testFolderAddRemoving() throws DocumentApplicationException {
		FolderInfo root = documentService.getFolder(defaultDomainObject);

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

		List<FolderEntryInfo> entries = documentService.getFolderEntries(
				defaultDomainObject, subFolder2);
		assertNotNull(entries);
		assertEquals(2, entries.size());

		FolderEntryInfo infoA = entries.get(0);
		FolderEntryInfo infoB = entries.get(1);

		assertTrue(!infoA.getId().equals(infoB.getId()));
		assertTrue(infoA.getId().equals(subFolder3.getId())
				|| infoA.getId().equals(subFolder4.getId()));
		assertTrue(infoB.getId().equals(subFolder3.getId())
				|| infoB.getId().equals(subFolder4.getId()));

		flush();

		FolderEntryInfo info = new FolderEntryInfo();
		info.setId(subFolder2.getId());

		documentService.removeFolderEntry(info.getId());
		entries = documentService.getFolderEntries(defaultDomainObject,
				subFolder1);
		assertNotNull(entries);
		assertEquals(0, entries.size());

		// check that the subfolder is really deleted
		FolderEntry folderEntry = folderEntryDao.load(subFolder3.getId());
		assertNull(folderEntry);
	}

	public void testCreateFileInfo() throws DocumentApplicationException,
			IOException {
		FolderInfo root = documentService.getFolder(defaultDomainObject);

		String name = "/dummy/readme.txt";

		FileInfo info = createFileInfo(name);

		documentService.createFileEntry(info, root);

		info.getInputStream().close();

		flush();

		FileInfo loaded = documentService.getFileEntry(info.getId(), true);

		assertEquals("dummy", loaded.getPath());
		assertEquals("readme", loaded.getName());
		assertEquals("readme.txt", loaded.getFileName());
		assertEquals("description", loaded.getDescription());
		assertEquals(info.getFileSize(), loaded.getFileSize());
		assertNotNull(loaded.getCreated());
		assertNotNull(loaded.getModified());

	}

	public void testCreateFileByFileInfo() throws DocumentApplicationException,
			IOException {
		FolderInfo root = documentService.getFolder(defaultDomainObject);

		String name = "/dummy/readme.txt";

		FileInfo info = createFileInfo(name);

		documentService.createFileEntry(info, root);

		info.getInputStream().close();

		flush();

		List<FolderEntryInfo> entries = documentService.getFolderEntries(
				defaultDomainObject, null);
		assertNotNull(entries);
		assertEquals(1, entries.size());

		FolderEntryInfo dummyFolder = entries.get(0);
		assertEquals("dummy", dummyFolder.getName());

		entries = documentService.getFolderEntries(defaultDomainObject,
				documentService.getFolder(dummyFolder));
		assertNotNull(entries);
		assertEquals(1, entries.size());

		FolderEntryInfo entry = entries.get(0);

		assertEquals("txt", entry.getExtension());
		assertEquals("dummy", entry.getPath());
		assertEquals("readme", entry.getName());
		assertEquals("readme.txt", entry.getFileName());
		assertEquals("description", entry.getDescription());
		assertEquals(info.getFileSize(), entry.getFileSize());
		assertNotNull(entry.getCreated());
		assertNotNull(entry.getModified());
		assertTrue(entry.isReleased());
		assertFalse(entry.isFolder());
		assertEquals(entry.getCreated(), entry.getReleaseDate());

		InputStream is = repositoryService.loadContent(entry.getId());
		assertNotNull(is);
		is.close();

		documentService.removeFolderEntry(entry.getId());
		flush();

		try {
			repositoryService.loadContent(entry.getId());
			fail();
		} catch (RepositoryServiceException rse) {
			// expected
		}
	}

	public void testCreateFileEntries() throws DocumentApplicationException {
		FolderInfo root = documentService.getFolder(defaultDomainObject);

		FolderInfo subfolder = createSubFolder();
		subfolder.setName("subfolder");
		documentService.createFolder(subfolder, root);

		List<FileInfo> files = new ArrayList<FileInfo>();

		files.add(createFileInfo("readme.txt"));
		files.add(createFileInfo("/folien/kapitel 1/text.txt"));
		files.add(createFileInfo("/folien/kapitel 2/readme.txt"));
		files.add(createFileInfo("/übungen/aufgabe/aufgabentext1.txt"));

		documentService.createFileEntries(files, subfolder);

		flush();

		List<FolderEntryInfo> entries = documentService.getFolderEntries(
				defaultDomainObject, subfolder);
		assertNotNull(entries);
		assertEquals(3, entries.size());
		FolderEntryInfo entry = entries.get(0);
		assertTrue(entry.getPath().startsWith("subfolder"));

		List<FileInfo> infos = documentService.allFileEntries(entries);
		assertNotNull(infos);

		assertEquals(4, infos.size());

		FileInfo info = infos.get(0);
		validateFileInfo(info);
		assertNull(info.getInputStream());
	}

	public void testGetFileEntry() throws DocumentApplicationException {
		FolderInfo root = documentService.getFolder(defaultDomainObject);

		FileInfo info = createFileInfo("readme.txt");

		documentService.createFileEntry(info, root);
		flush();

		List<FolderEntryInfo> entries = documentService.getFolderEntries(
				defaultDomainObject, null);
		FolderEntryInfo entry = entries.get(0);

		FileInfo file = documentService.getFileEntry(entry.getId(), false);
		assertNull(file.getInputStream());

		documentService.getFileEntry(entry.getId(), true);
		validateFileInfo(info);
		assertNotNull(info.getInputStream());
	}
	
	public void testMoveFolderEntriesOne() throws Exception{
		// Create a file in the root directory
		FolderInfo root = documentService.getFolder(defaultDomainObject);
		FileInfo file1 = createFileInfo("file1.txt");
		documentService.createFileEntry(file1, root);

		// Create a sub folder
		FolderInfo subfolder = createSubFolder();
		subfolder.setName("subfolder");
		documentService.createFolder(subfolder, root);
		documentService.createFileEntry(createFileInfo("2.txt"),subfolder);
		
		// Test subfolder content
		List<FolderEntryInfo> test = documentService.getFolderEntries(defaultDomainObject, subfolder);
		assertEquals(1, test.size());
		
		// Test root folder content
		List<FolderEntryInfo> entries = documentService.getFolderEntries(defaultDomainObject, root);
		assertNotNull(entries);
		assertEquals(2, entries.size());
		
		// Move something
		List<FolderEntryInfo> toMove = new ArrayList<FolderEntryInfo>();
		toMove.add(entries.get(0));
		assertEquals("file1.txt", toMove.get(0).getFileName());
		documentService.moveFolderEntries(defaultDomainObject, subfolder, toMove);
		
		// Test subfolder content
		List<FolderEntryInfo> test2 = documentService.getFolderEntries(defaultDomainObject, subfolder);
		assertEquals(2,test2.size());
		assertEquals(test2.get(1).getPath(), "subfolder");
		assertEquals(1, documentService.getFolderEntries(defaultDomainObject,root).size());
	}
	
	public void testMoveFolderEntriesMany() throws Exception{
		// Create root
		FolderInfo rootFolderInfo = documentService.getFolder(defaultDomainObject);
		
		// Choose file names
		String testFn1 = "f1";
		String testFn2 = "f2";
		
		// Create 2 documents under root
		documentService.createFileEntry(createFileInfo(testFn1), rootFolderInfo);
		documentService.createFileEntry(createFileInfo(testFn2), rootFolderInfo);
		
		// Create subfolder
		FolderInfo subFolderInfo = createSubFolder();
		subFolderInfo.setName("subfolder");
		documentService.createFolder(subFolderInfo, rootFolderInfo);
		
		// Test content of root
		assertEquals(3, documentService.getFolderEntries(defaultDomainObject,rootFolderInfo).size());
		
		// Test content of subfolder
		assertEquals(0, documentService.getFolderEntries(defaultDomainObject,subFolderInfo).size());
		
		// Move both documents to subfolder
		List<FolderEntryInfo> subFolderEntries = new ArrayList<FolderEntryInfo>();
		Folder rootFolder = folderDao.folderInfoToEntity(rootFolderInfo);
		FolderEntryInfo fei1 = folderEntryDao.toFolderEntryInfo(rootFolder.getFolderEntryByName(testFn1));
		assertNotNull(fei1);
		subFolderEntries.add(fei1);
		FolderEntryInfo fei2 = folderEntryDao.toFolderEntryInfo(rootFolder.getFolderEntryByName(testFn2));
		assertNotNull(fei2);
		subFolderEntries.add(fei2);
		documentService.moveFolderEntries(defaultDomainObject, subFolderInfo, subFolderEntries);

		// Test content of root
		assertEquals(1,documentService.getFolderEntries(defaultDomainObject,rootFolderInfo).size());
		// Test content of subfolder
		assertEquals(subFolderEntries.size(), documentService.getFolderEntries(defaultDomainObject,subFolderInfo).size());
		
		// Move both documents back to root
		List<FolderEntryInfo> toMoveEntries = new ArrayList<FolderEntryInfo>();
		Folder subFolder = folderDao.folderInfoToEntity(subFolderInfo);
		fei1 = folderEntryDao.toFolderEntryInfo(subFolder.getFolderEntryByName(testFn1));
		assertNotNull(fei1);
		toMoveEntries.add(fei1);
		fei2 = folderEntryDao.toFolderEntryInfo(subFolder.getFolderEntryByName(testFn2));
		assertNotNull(fei2);
		toMoveEntries.add(fei2);
		documentService.moveFolderEntries(defaultDomainObject, rootFolderInfo, toMoveEntries);
		
		// Test content of root
		assertEquals(1 + toMoveEntries.size(),documentService.getFolderEntries(defaultDomainObject,rootFolderInfo).size());
		// Test content of subfolder
		assertEquals(0, documentService.getFolderEntries(defaultDomainObject,subFolderInfo).size());
	}

	public void testMoveFolderEntriesIllegalTarget() throws Exception {
		// create root
		FolderInfo folderInfoRoot = documentService
				.getFolder(defaultDomainObject);
		// create subfolder
		FolderInfo subfolder = createSubFolder();
		subfolder.setName("subfolder");
		documentService.createFolder(subfolder, folderInfoRoot);
		// test moving subfolder to subfolder
		List<FolderEntryInfo> chosen = new ArrayList<FolderEntryInfo>();
		chosen.add(getFolderDao().toFolderEntryInfo(
				getFolderDao().folderInfoToEntity(subfolder)));
		try {
			documentService.moveFolderEntries(defaultDomainObject, subfolder,
					chosen);
			assertTrue(false);
		} catch (DocumentServiceException e) {
			assertTrue(true);
		}
		// test moving root to subfolder
		chosen.clear();
		chosen.add(getFolderDao().toFolderEntryInfo(
				getFolderDao().folderInfoToEntity(folderInfoRoot)));
		try {
			documentService.moveFolderEntries(defaultDomainObject, subfolder,
					chosen);
			assertTrue(false);

		} catch (DocumentServiceException e) {
			assertTrue(true);
		}
		// test moving subfolder to subsubfolder
		chosen.clear();
		FolderInfo subsubfolder = createSubFolder();
		subsubfolder.setName("SubSubFolder");
		documentService.createFolder(subsubfolder, subfolder);
		chosen.add(getFolderDao().toFolderEntryInfo(
				getFolderDao().folderInfoToEntity(subfolder)));
		try {
			documentService.moveFolderEntries(defaultDomainObject,
					subsubfolder, chosen);
			assertTrue(false);

		} catch (DocumentServiceException e) {
			assertTrue(true);
		}
	}

	private void validateFileInfo(FileInfo info) {
		assertNotNull(info.getName());
		assertNotNull(info.getAbsoluteName());
		assertNotNull(info.getPath());
		assertNotNull(info.getFileName());
		assertEquals(StringUtils.isBlank(info.getPath()) ? info.getFileName()
				: info.getPath() + "/" + info.getFileName(), info
				.getAbsoluteName());
		assertNotNull(info.getCreated());
		assertNotNull(info.getModified());
		assertNotNull(info.getFileSize());
		assertNotNull(info.getContentType());
	}

	private FileInfo createFileInfo(String name) {
		FileInfo info = new FileInfo();
		byte[] data = "this is the content of the file".getBytes();
		info.setFileName(name);
		info.setDescription("description");
		info.setContentType("plain/text");
		info.setFileSize(data.length);
		info.setCreated(new Date());
		info.setModified(new Date());
		info.setInputStream(new ByteArrayInputStream(data));
		return info;
	}

	private FolderInfo createSubFolder() {
		FolderInfo folder = new FolderInfo();
		folder.setName(testUtility.unique("folder name"));
		folder.setDescription("first folder description");
		folder.setCreated(DateUtils.addDays(new Date(), -1));
		return folder;
	}

	private DefaultDomainObject createDomainObject() {
		DefaultDomainObject defaultDomainObject = new DefaultDomainObject(
				TestUtility.unique());
		securityService.createObjectIdentity(defaultDomainObject, null);
		return defaultDomainObject;
	}

	public void setFolderEntryDao(FolderEntryDao folderEntryDao) {
		this.folderEntryDao = folderEntryDao;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}

	public FolderDao getFolderDao() {
		return folderDao;
	}

	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
	}

}
