package org.openuss.web.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.openuss.documents.FileInfo;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.web.Constants;

/**
 * ValueChangeListener for File Upload
 * 
 * @author Ingo Dueppe
 */
public class UploadListener extends BaseBean implements ValueChangeListener {
	private static final long serialVersionUID = 7200670022689284313L;

	private static final Logger logger = Logger.getLogger(UploadListener.class);

	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
		logger.debug("file uploaded");
		UploadedFile uploadedFile = (UploadedFile) event.getNewValue();
		if (uploadedFile != null) {
			logFileInfo(uploadedFile);
			logValueChangeEvent(event);
			try {
				saveUploadedFile(uploadedFile);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	private void logValueChangeEvent(ValueChangeEvent event) {
		logger.debug("source " + event.getSource());
		logger.debug("component " + event.getComponent());
	}

	private void logFileInfo(UploadedFile uploadedFile) {
		logger.debug("file name " + uploadedFile.getName());
		logger.debug("file type " + uploadedFile.getContentType());
		logger.debug("file size " + uploadedFile.getSize());
	}

	public void saveUploadedFile(UploadedFile uploadedFile) throws IOException {
		FileInfo fileInfo = new FileInfo();
		copyProperties(fileInfo, uploadedFile);
		
		File tempFile = storeTempFile(uploadedFile);
		fileInfo.setInputStream(new FileInputStream(tempFile));
		
		getUploadFileManager().registerFile(fileInfo, tempFile);
		setSessionBean(Constants.UPLOADED_FILE, fileInfo);
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

	private UploadFileManager getUploadFileManager() {
		return (UploadFileManager) getBean("uploadFileManager");
	}

	private void copyProperties(FileInfo toFile, UploadedFile fromFile) throws IOException {
		String fileName = extractFileName(fromFile.getName());
		
		toFile.setName(fileName);
		toFile.setFileName(fileName);
		toFile.setContentType(fromFile.getContentType());
		toFile.setFileSize((int)fromFile.getSize());
	}

	public String extractFileName(String fileName) {
		File file = new File(fileName);
		return file.getName();
	}	
	
	/**
	 * Creates and returns a {@link java.io.File File} representing a uniquely
	 * named temporary file in the configured repository path.
	 * 
	 * @return The {@link java.io.File File} to be used for temporary storage.
	 */
	protected File getTempFile() {
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
		String fileName = "upload_zip_" + getUniqueId() + ".tmp";
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
		synchronized (UploadListener.class) {
			current = counter++;
		}
		String id = Integer.toString(current);

		// If you manage to get more than 100 million of ids, you'll
		// start getting ids longer than 8 characters.
		if (current < 100000000) {
			id = ("00000000" + id).substring(id.length());
		}
		return id;
	}

}
