// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.documents;

import java.util.Date;


/**
 * JUnit Test for Spring Hibernate FolderDao class.
 * @see org.openuss.documents.FolderDao
 */
public class FolderDaoTest extends FolderDaoTestBase {
	
	public void testFolderDaoCreate() {
		Folder folder = new FolderImpl();
		folder.setName("foldername");
		folder.setCreated(new Date());
		assertNull(folder.getId());
		folderDao.create(folder);
		assertNotNull(folder.getId());
	}
}