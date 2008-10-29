package org.openuss.documents;

import junit.framework.TestCase;

public class FolderTest extends TestCase {

	public void testCanAddFolderEntry() throws DocumentApplicationException {
		Folder folder =new FolderImpl();
		
		FileEntry file1 = new FileEntryImpl();
		file1.setName("ein erster test");
		file1.setFileName("readme.txt");

		FileEntry file2 = new FileEntryImpl();
		file2.setName("ein anderer text");
		file2.setFileName("ReadMe.TXT");

		FileEntry file3 = new FileEntryImpl();
		file3.setFileName("test.txt");
		
		folder.addFolderEntry(file1);
		
		assertFalse(folder.canAdd(file2));
		assertTrue(folder.canAdd(file1));
		assertTrue(folder.canAdd(file3));
		
		Folder subFolder =new FolderImpl();
		subFolder.setName("readme.txt");
		
		assertFalse(folder.canAdd(subFolder));
	}
}
