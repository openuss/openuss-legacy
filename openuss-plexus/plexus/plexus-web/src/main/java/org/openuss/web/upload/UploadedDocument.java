package org.openuss.web.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;

/**
 * Value Object for uploaded files
 * 
 * @author Ingo Dueppe
 * 
 */
public class UploadedDocument implements Serializable {
	private static final long serialVersionUID = 5198805326329538450L;

	static final Logger logger = Logger.getLogger(UploadedDocument.class);

	private String source;

	private Integer fileSize;
	private String fileName;
	private String contentType;
	private File file;
	
	private transient InputStream inputStream;
	
	public UploadedDocument(UploadedFile uploadedFile, String source) throws IOException {
		Validate.notNull(uploadedFile, "Parameter uploadedFile must not be null!");
		Validate.notNull(source, "Paramter component must not be null");
		
		this.source = source;
		importUploadedFile(uploadedFile);
	}
	
	private void importUploadedFile(UploadedFile uploadedFile) throws IOException {
		logger.debug("file name " + uploadedFile.getName());
		logger.debug("file type " + uploadedFile.getContentType());
		logger.debug("file size " + uploadedFile.getSize());
		
		fileName = extractFileName(uploadedFile.getName());
		contentType = uploadedFile.getContentType();
		fileSize = (int) uploadedFile.getSize();
		file = storeTempFile(uploadedFile);
	}
	
	private File storeTempFile(UploadedFile uploadedFile) throws IOException {
		File file = TempFileHelper.createTempFile();
		FileOutputStream fos = null;
		InputStream is = null;
		try {
			fos = new FileOutputStream(file);
			is = uploadedFile.getInputStream();
			IOUtils.copyLarge(is, fos);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(fos);
		}
		return file;
	}
	
	public String getContentType() {
		return contentType;
	}

	public File getFile() {
		return file;
	}

	public String getFileName() {
		return fileName;
	}

	public InputStream getInputStream() throws IOException {
		if (inputStream == null) {
			inputStream = new ProxyFileInputStream(file);
		}
		return inputStream;
	}
	
	public String getSource() {
		return source;
	}
	
	private String extractFileName(String fileName) {
		File file = new File(fileName);
		return file.getName();
	}

	public Integer getFileSize() {
		return fileSize;
	}
	
	
	public void remove() {
		logger.debug("removing uploaded document "+fileName);
		if (file.exists()) {
			// TODO is this right?
			logger.debug("closing inputstream <-------------------------------------------------------------");
			//	IOUtils.closeQuietly(inputStream);
			if (!file.delete()) {
				logger.debug("Couldn't delete temp file "+file.getName()+", will be deleted on exit!");
				file.deleteOnExit();
			}
		}
	}
	

}
