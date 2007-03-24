package org.openuss.web.documents;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.openuss.documents.FileInfo;

/**
 * Class to unpack a zip file and collect all file entries in a list of FileInfo objects
 * @author Ingo Dueppe
 */
public class ZipFileUnpacker {

	private static final Logger logger = Logger.getLogger(ZipFileUnpacker.class);

	private ZipFile zipFile;
	
	public ZipFileUnpacker(File file) throws IOException {
		zipFile = new ZipFile(file);
	}
	
	public List<FileInfo> extractZipFile() {
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();

		try {
			Enumeration entries = zipFile.getEntries();
			while(entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				logger.debug(" - name: "+entry.getName());
				if (!entry.isDirectory()) {
					FileInfo info = new FileInfo();
					info.setName(entry.getName());
					info.setFileName(entry.getName());
					info.setDescription(entry.getComment());
					info.setCreated(new Date(entry.getTime()));
					info.setModified(info.getCreated());
					info.setContentType("application/octet-stream");
					info.setInputStream(zipFile.getInputStream(entry));
					info.setSize((int)entry.getSize());
					fileInfos.add(info);
				}
			}
		} catch (IOException e) {
			logger.error(e);
		}
		
		return fileInfos;
	}
	
	/**
	 * Closes the zip file, possible IOException will be caught. 
	 */
	public void closeQuitly() {
		try {
			zipFile.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
}
