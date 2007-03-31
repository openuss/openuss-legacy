package org.openuss.documents;

import junit.framework.TestCase;

public class FolderTest extends TestCase {

	public void testCanAddFolderEntry() throws DocumentApplicationException {
		Folder folder = Folder.Factory.newInstance();
		
		FileEntry file1 = FileEntry.Factory.newInstance();
		file1.setName("ein erster test");
		file1.setFileName("readme.txt");

		FileEntry file2 = FileEntry.Factory.newInstance();
		file2.setName("ein anderer text");
		file2.setFileName("ReadMe.TXT");

		FileEntry file3 = FileEntry.Factory.newInstance();
		file3.setFileName("test.txt");
		
		folder.addFolderEntry(file1);
		
		assertFalse(folder.canAdd(file2));
		assertTrue(folder.canAdd(file1));
		assertTrue(folder.canAdd(file3));
		
		Folder subFolder = Folder.Factory.newInstance();
		subFolder.setName("readme.txt");
		
		assertFalse(folder.canAdd(subFolder));
	}
}
