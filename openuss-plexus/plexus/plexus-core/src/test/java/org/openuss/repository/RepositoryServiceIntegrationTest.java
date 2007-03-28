// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate RepositoryService class.
 * 
 * @see org.openuss.repository.RepositoryService
 */
public class RepositoryServiceIntegrationTest extends RepositoryServiceIntegrationTestBase {

	private TestUtility testUtility;

	public void testRepositoryService() throws IOException {
		testUtility.createSecureContext();
		long fileId = testUtility.unique();
		URL url = this.getClass().getClassLoader().getResource("core-test.zip");
		File testFile = new File(url.getFile());
		long checksum = FileUtils.checksumCRC32(testFile);
		
		FileInputStream fis = new FileInputStream(testFile);
		
		repositoryService.saveContent(fileId, fis);
		
		commit();
		
		InputStream is = repositoryService.loadContent(fileId); 
		assertNotNull(is);
		is.close();
		String path = repositoryService.getRepositoryLocation();
		File cachedFile = new File(path+"/_filecontent_" + fileId + ".tmp");
		assertTrue(cachedFile.exists());
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

	private void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

}