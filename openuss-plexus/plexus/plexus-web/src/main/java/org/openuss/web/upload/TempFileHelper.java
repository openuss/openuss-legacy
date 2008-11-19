package org.openuss.web.upload;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Wrapper Class.
 * 
 * @author Ingo Dueppe
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public class TempFileHelper {

	private static final Logger logger = Logger.getLogger(TempFileHelper.class);
	
	/**
	 * Creates and returns a {@link java.io.File File} representing a uniquely
	 * named temporary file in the configured repository path.
	 * 
	 * @return The {@link java.io.File File} to be used for temporary storage.
	 * @throws IOException 
	 */
	public static File createTempFile() throws IOException {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
	
		// NOTE: Removed the uniqueFilename stuff because there is a very good java method for that! 
		File file = File.createTempFile("upload_", ".tmp", tempDir); 
		file.deleteOnExit();
		
		logger.debug("Created temp file "+file.getAbsolutePath());
		return file;
	}
}
