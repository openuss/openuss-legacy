// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate RepositoryFileDao class.
 * @see org.openuss.repository.RepositoryFileDao
 */
public class RepositoryFileDaoTest extends RepositoryFileDaoTestBase {
	
	private TestUtility testUtility;
	

	public void testRepositoryFileDaoCreate() {
		final String str = "This is the content of the file";
		byte[] data = str.getBytes();
		
		RepositoryFile file = new RepositoryFileImpl();
		file.setId(TestUtility.unique());
		file.setModified(new Date());
		file.setInputStream(new ByteArrayInputStream(data));
		
		repositoryFileDao.create(file);
		
		commit();
		
		RepositoryFile loaded = repositoryFileDao.load(file.getId());
		assertEquals(file, loaded);
		assertEquals(str, getFileContentAsString(loaded, data.length));
	}
	
	private String getFileContentAsString(RepositoryFile nfile, int size) {
		InputStream s = nfile.getInputStream();
		byte[] d2 = new byte[size];
		try {
			assertTrue("size of data ",s.read(d2) == size);
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