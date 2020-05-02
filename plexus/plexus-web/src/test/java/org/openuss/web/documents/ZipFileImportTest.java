package org.openuss.web.documents;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.openuss.documents.FileInfo;

public class ZipFileImportTest extends TestCase {

	 private String filename = "example/�bungen/Dies ist eine b�ser langer Dateiname für den Test von Umlauten, und was sonst noch so an merkw�rdigen Zeichen existieren, wie ������ und �.txt";
	
	 public void testCreateFolderEntriesFromZip() throws IOException {
		 URL url = this.getClass().getClassLoader().getResource("example.zip");
		 File file = new File(url.getFile());
		 ZipFileUnpacker zip = new ZipFileUnpacker(file);
		 
		 List<FileInfo> infos = zip.extractZipFile();
		 
		 zip.closeQuitly();
		 
		 assertNotNull(infos);
		 assertEquals(3, infos.size());
		 assertEquals(filename, infos.get(0).getFileName());
	}
}
