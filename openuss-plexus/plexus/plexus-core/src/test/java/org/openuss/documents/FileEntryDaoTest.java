// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;



/**
 * JUnit Test for Spring Hibernate FileEntryDao class.
 * @see org.openuss.documents.FileEntryDao
 */
public class FileEntryDaoTest extends FileEntryDaoTestBase {
	
	public void testFileEntryDaoCreate() {
		FileEntry fileEntry = new FileEntryImpl();
		assertNull(fileEntry.getId());
		// TODO add real test here
//		fileEntryDao.create(fileEntry);
//		assertNotNull(fileEntry.getId());
	}
	
}