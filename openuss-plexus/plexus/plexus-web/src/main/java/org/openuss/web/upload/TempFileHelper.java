package org.openuss.web.upload;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * Wrapper Class 
 * @author Ingo Dueppe
 */
public class TempFileHelper {

	private static final Logger logger = Logger.getLogger(TempFileHelper.class);
	
	/**
	 * Creates and returns a {@link java.io.File File} representing a uniquely
	 * named temporary file in the configured repository path.
	 * 
	 * @return The {@link java.io.File File} to be used for temporary storage.
	 */
	public static File createTempFile() {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
	
		File file = new File(tempDir, uniqueFileName());
		file = verifyTempFile(tempDir, file);
		file.deleteOnExit();
		
		logger.debug("Created temp file "+file.getAbsolutePath());
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
		synchronized (UploadedDocument.class) {
			current = counter++;
		}
		String id = Integer.toString(current);
	
		if (current < 100000000) {
			id = ("00000000" + id).substring(id.length());
		}
		return id;
	}

	private static String uniqueFileName() {
		String fileName = "upload_" + getUniqueId() + ".tmp";
		return fileName;
	}

	private static File verifyTempFile(File tempDir, File file) {
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


}
