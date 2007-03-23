package org.openuss.web.documents;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.openuss.documents.FileInfo;
import org.openuss.web.documents.ZipFileUnpacker;

public class ZipFileImportTest extends TestCase {

	 public void testCreateFolderEntriesFromZip() throws IOException {
		 URL url = this.getClass().getClassLoader().getResource("example.zip");
		 File file = new File(url.getFile());
		 ZipFileUnpacker zip = new ZipFileUnpacker(file);
		 
		 List<FileInfo> info = zip.extractZipFile();
		 
		 zip.closeQuitly();
		 
		 assertNotNull(info);
		 assertEquals(3, info.size());
	}
}
