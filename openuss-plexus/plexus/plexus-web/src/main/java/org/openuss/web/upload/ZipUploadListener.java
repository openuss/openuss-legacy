package org.openuss.web.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.web.Constants;

/**
 * ValueChangeListener for File Upload
 * 
 * @author Ingo Dueppe
 */
public class ZipUploadListener extends BaseBean implements ValueChangeListener {
	private static final long serialVersionUID = 7200670022689284313L;

	private static final Logger logger = Logger.getLogger(UploadListener.class);

	private static final int DRAIN_BUFFER_SIZE = 8192;

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
		File file = getTempFile();
		
		FileOutputStream fos = null;
		InputStream is = null;
		try {
			fos = new FileOutputStream(file);
			is = uploadedFile.getInputStream();
			drain(is, fos);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		// store last uploaded file reference in session
		setSessionBean(Constants.UPLOADED_ZIP_FILE, file);
	}

	/**
     * copy bytes from input to output stream
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	private void drain(InputStream input, OutputStream output) throws IOException {
		int bytesRead = 0;
		byte[] buffer = new byte[DRAIN_BUFFER_SIZE];

		while ((bytesRead = input.read(buffer, 0, DRAIN_BUFFER_SIZE)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	/**
	 * Creates and returns a {@link java.io.File File} representing a uniquely
	 * named temporary file in the configured repository path.
	 * 
	 * @return The {@link java.io.File File} to be used for temporary storage.
	 */
	protected File getTempFile() {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));

		String fileName = "upload_zip_" + getUniqueId() + ".tmp";

		File file = new File(tempDir, fileName);
		file.deleteOnExit();
		return file;
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
		synchronized (ZipUploadListener.class) {
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
