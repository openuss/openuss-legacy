// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate RepositoryService class.
 * 
 * @see org.openuss.repository.RepositoryService
 */
public class RepositoryServiceIntegrationTest extends RepositoryServiceIntegrationTestBase {

	public void testRepositoryService() throws IOException {
		String fileName = this.getClass().getClassLoader().getResource("core-test.zip").getFile();
		checkRepositoryRoundtrip(fileName);
	}


	private void checkRepositoryRoundtrip(String fileName) throws IOException, FileNotFoundException {
		testUtility.createAdminSecureContext();
		long fileId = TestUtility.unique();
		File testFile = new File(fileName);
		long checksum = FileUtils.checksumCRC32(testFile);
		
		FileInputStream fis = new FileInputStream(testFile);
		
		repositoryService.saveContent(fileId, fis);
		
		commit();
		
		InputStream is = repositoryService.loadContent(fileId);
		
		assertNotNull(is);
		is.close();
		String path = repositoryService.getRepositoryLocation();
		File cachedFile = new File(path+"/_filecontent_" + fileId + ".tmp");
		logger.debug("Cached File ==========> "+cachedFile.getAbsolutePath());
		assertTrue(cachedFile.exists());
		assertTrue(cachedFile.isFile());
		assertTrue(testFile.length() == cachedFile.length());
		assertEquals(checksum, FileUtils.checksumCRC32(cachedFile));
		
		repositoryService.removeContent(fileId);
		assertFalse(cachedFile.exists());
		
		try {
			repositoryService.loadContent(fileId);
			fail("RepositoryServiceException expected that fileId doesn't exists");
		} catch (RepositoryServiceException rse) {
			// expected
		}
	}
}