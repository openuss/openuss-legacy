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
	
	public void testRepositoryService() throws IOException {
		testUtility.createSecureContext();
		final String str = "This is the content of the file";
		byte[] data = str.getBytes();
		
		String name = "dummy.dummy";
		
		RepositoryFile file = createRepositoryFile(data, name);
		
		repositoryService.saveFile(file);
		file.getInputStream().close();
		
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
		
		commit();
		
		repositoryService.removeFile(nfile);
		commit();
		
		assertNull(repositoryService.getFile(nfile));
	}

	private void commit() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}
	
	private RepositoryFile createRepositoryFile(byte[] data, String name) {
		RepositoryFile file = RepositoryFile.Factory.newInstance();
		InputStream stream = new ByteArrayInputStream(data);
		file.setFileName(name);
		file.setFileSize(data.length);
		file.setContentType("contentType");
		file.setName("dummy");
		file.setInputStream(stream);
		file.setCreated(new Timestamp(System.currentTimeMillis()));
		file.setModified(new Timestamp(System.currentTimeMillis()));
		return file;
	}

	private String getFileContentAsString(RepositoryFile nfile) {
		InputStream s = nfile.getInputStream();
		byte[] d2 = new byte[nfile.getFileSize().intValue()];
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