// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.util.Date;


/**
 * JUnit Test for Spring Hibernate FolderEntryDao class.
 * @see org.openuss.documents.FolderEntryDao
 */
public class FolderEntryDaoTest extends FolderEntryDaoTestBase {
	
	public void testFolderEntryDaoCreate() {
		FolderEntry folderEntry = new FolderEntryImpl();
		folderEntry.setName("name of the folder");
		folderEntry.setCreated(new Date());
		assertNull(folderEntry.getId());
		folderEntryDao.create(folderEntry);
		assertNotNull(folderEntry.getId());
	}
}