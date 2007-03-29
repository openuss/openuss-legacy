package org.openuss.web.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
public class UploadedDocument {
	private static final Logger logger = Logger.getLogger(UploadedDocument.class);

	private Object source;

	private Integer fileSize;
	private String fileName;
	private String contentType;
	private InputStream inputStream;
	private File file;
	
	public UploadedDocument(UploadedFile uploadedFile, Object source) {
		Validate.notNull(uploadedFile, "Parameter uploadedFile must not be null!");
		Validate.notNull(source, "Paramter component must not be null");
		
		this.source = source;
		importUploadedFile(uploadedFile);
	}
	
	private void importUploadedFile(UploadedFile uploadedFile) {
		logger.debug("file name " + uploadedFile.getName());
		logger.debug("file type " + uploadedFile.getContentType());
		logger.debug("file size " + uploadedFile.getSize());
		
		fileName = extractFileName(uploadedFile.getName());
		contentType = uploadedFile.getContentType();
		fileSize = (int) uploadedFile.getSize();
		file = storeTempFile(uploadedFile);
	}
	
	private File storeTempFile(UploadedFile uploadedFile) {
		File file = getTempFile();
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
			inputStream = new FileInputStream(file);
		}
		return inputStream;
	}

	public Object getSource() {
		return source;
	}
	
	private String extractFileName(String fileName) {
		File file = new File(fileName);
		return file.getName();
	}

	public Integer getFileSize() {
		return fileSize;
	}
	
	
	/**
	 * Creates and returns a {@link java.io.File File} representing a uniquely
	 * named temporary file in the configured repository path.
	 * 
	 * @return The {@link java.io.File File} to be used for temporary storage.
	 */
	private File getTempFile() {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));

		File file = new File(tempDir, uniqueFileName());
		file = verifyTempFile(tempDir, file);
		file.deleteOnExit();
		
		logger.debug("created temp file "+file.getAbsolutePath());
		return file;
	}

	private File verifyTempFile(File tempDir, File file) {
		if (file.exists()) {
			int tries = 0;
			while (!file.exists()) {
				logger.error("generated duplicated temp file, trying again.");
				file = new File(tempDir, uniqueFileName());
				tries ++;
				if (tries > 1000 ) {
					throw new RuntimeException("Cannot generate new temp file");
				}
			}
		}
		return file;
	}

	private String uniqueFileName() {
		String fileName = "upload_" + getUniqueId() + ".tmp";
		return fileName;
	}

    /**
     * Counter used in unique identifier generation.
     */
    private static int counter = 0;
	
	/**
	 * Returns an identifier that is unique within the class loader used to load
	 * this class, but does not have random-like apearance.
	 * 
	 * @return A String with the non-random looking instance identifier.
	 */
	private static String getUniqueId() {
		int current;
		synchronized (UploadedDocument.class) {
			current = counter++;
		}
		String id = Integer.toString(current);

		if (current < 100000000) {
			id = ("00000000" + id).substring(id.length());
		}
		return id;
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
