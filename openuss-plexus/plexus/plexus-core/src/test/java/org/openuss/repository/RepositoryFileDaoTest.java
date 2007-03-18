// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.repository;

import java.sql.Timestamp;


/**
 * JUnit Test for Spring Hibernate RepositoryFileDao class.
 * @see org.openuss.repository.RepositoryFileDao
 */
public class RepositoryFileDaoTest extends RepositoryFileDaoTestBase {
	
	public void testRepositoryFileDaoCreate() {
		RepositoryFile repositoryFile = new RepositoryFileImpl();
		repositoryFile.setName(" ");
		repositoryFile.setFileName(" ");
		repositoryFile.setFileSize(100);
		repositoryFile.setContentType(" ");
//		repositoryFile.setReleaseDate(new Timestamp(System.currentTimeMillis()));
		repositoryFile.setCreated(new Timestamp(System.currentTimeMillis()));
		repositoryFile.setModified(new Timestamp(System.currentTimeMillis()));
		assertNull(repositoryFile.getId());
		repositoryFileDao.create(repositoryFile);
		assertNotNull(repositoryFile.getId());
	}
}