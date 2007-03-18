// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

import org.openuss.TestUtility;

/**
 * JUnit Test for Spring Hibernate RepositoryService class.
 * @see org.openuss.repository.RepositoryService
 */
public class RepositoryServiceIntegrationTest extends RepositoryServiceIntegrationTestBase {
	
	private TestUtility testUtility;
	
	public void testRepositoryService() {
		testUtility.createSecureContext();
		final String str = "This is the content of the file";
		byte[] data = str.getBytes();
		
		InputStream stream = new ByteArrayInputStream(data);
		
		RepositoryFile file = RepositoryFile.Factory.newInstance();
		file.setFileName("dummy.dummy");
		file.setFileSize(data.length);
		file.setContentType("contentType");
		file.setName("dummy");
		file.setInputStream(stream);
		file.setCreated(new Timestamp(System.currentTimeMillis()));
		file.setModified(new Timestamp(System.currentTimeMillis()));
		
		repositoryService.saveFile(file);
		
		assertNotNull(file.getId());
		
		
		RepositoryFile nfile = RepositoryFile.Factory.newInstance();
		nfile.setId(file.getId());
		
		nfile = repositoryService.getFile(nfile);
		
		assertEquals("dummy.dummy", nfile.getFileName());
		assertTrue(data.length == nfile.getFileSize());
		assertEquals("contentType",nfile.getContentType());
		assertEquals("dummy", nfile.getName());
		
		assertNotNull(nfile.getInputStream());
		String str2 = getFileContentAsString(nfile);
		assertEquals(str, str2);
		
		repositoryService.removeFile(nfile);
		assertNull(repositoryService.getFile(nfile));
	}

	private String getFileContentAsString(RepositoryFile nfile) {
		InputStream s = nfile.getInputStream();
		byte[] d2 = new byte[nfile.getFileSize()];
		try {
			s.read(d2);
			s.close();
		} catch (IOException e) {
			fail();
		}
		String s2 = new String(d2);
		return s2;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	
}