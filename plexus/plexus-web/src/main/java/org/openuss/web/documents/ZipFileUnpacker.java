package org.openuss.web.documents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.openuss.documents.FileInfo;
import org.openuss.web.upload.TempFileHelper;

/**
 * Class to unpack a zip file and collect all file entries in a list of FileInfo objects
 * @author Ingo Dueppe
 */
public class ZipFileUnpacker {

	private static final Logger logger = Logger.getLogger(ZipFileUnpacker.class);

	private File file;
	private ZipFile zipFile;
	
	private List<File> files ;
	
	public ZipFileUnpacker(File file) throws IOException {
		this.file = file;
		this.zipFile = new ZipFile(file,"Cp850");
		this.files = new ArrayList<File>();
	}
	
	public List<FileInfo> extractZipFile() throws IOException{
		logger.debug("start extracting zip file "+file.getAbsolutePath()+" size "+file.length());
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();

		Enumeration<ZipEntry> entries = zipFile.getEntries();
		while(entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			
			
			logger.debug(" - name: "+entry.getName());
			if (!entry.isDirectory()) {
				File file = TempFileHelper.createTempFile();
				OutputStream os = new FileOutputStream(file);
				InputStream is = zipFile.getInputStream(entry);
				IOUtils.copyLarge(is, os);
				IOUtils.closeQuietly(os);
				IOUtils.closeQuietly(is);
				FileInfo info = new FileInfo();
				info.setFileName(entry.getName());
				info.setDescription(entry.getComment());
				info.setCreated(new Date(entry.getTime()));
				info.setModified(info.getCreated());
				info.setContentType("application/octet-stream");
				info.setInputStream(new FileInputStream(file));
				info.setFileSize((int)entry.getSize());
				fileInfos.add(info);
				files.add(file);
			}
		}
		
		return fileInfos;
	}
	
	/**
	 * Closes the zip file, possible IOException will be caught. 
	 */
	public void closeQuitly() {
		try {
			for (File file : files) {
				if (!file.delete()) {
					logger.error("couldn't delete temp file!");
				}
			}
			zipFile.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
}
