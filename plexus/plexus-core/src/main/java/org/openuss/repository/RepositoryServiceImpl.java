// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author Ingo Dueppe
 * @see org.openuss.repository.RepositoryService
 */
public class RepositoryServiceImpl extends org.openuss.repository.RepositoryServiceBase {

	private static final int DRAIN_BUFFER_SIZE = 1024;

	private static final Logger logger = Logger.getLogger(RepositoryServiceImpl.class);

	private String path;

	public RepositoryServiceImpl() {
		String tmpPath = System.getProperty("java.io.tmpdir", "./");
		setRepositoryLocation(tmpPath + "/plexus/");
	}

	/**
	 * @see org.openuss.repository.RepositoryService#removeFile(org.openuss.repository.RepositoryFile)
	 */
	protected void handleRemoveFile(RepositoryFile file) throws java.lang.Exception {
		Validate.notEmpty(path, "RepositoryLocation is not configured!");
		String fileName = toFileName(file);
		if (logger.isDebugEnabled()) {
			logger.debug("removing file " + fileName + " from repository.");
		}

		getRepositoryFileDao().remove(file);

		File f = new File(fileName);
		if (f.exists()) {
			boolean success = f.delete();
			if (!success) {
				logger.error("couldn't delete");
				f.deleteOnExit();
			}
		}
	}



	/**
	 * @see org.openuss.repository.RepositoryService#getSaveFile(org.openuss.repository.RepositoryFile)
	 */
	public void handleSaveFile(RepositoryFile file) throws java.lang.Exception {
		Validate.notEmpty(path, "RepositoryLocation is not configured!");

		final RepositoryFileDao fileDao = getRepositoryFileDao();

		file.setModified(new Date());
		if (file.getId() == null) { 
			Date now = new Date();
			if (file.getCreated() == null) {
				file.setCreated(now);
			}
			if (file.getModified() == null) {
				file.setModified(now);
			}
			fileDao.create(file);
		} else {
			file.setModified(new Date());
			fileDao.update(file);
		}

		String fileName = toFileName(file);

		if (logger.isDebugEnabled()) {
			logger.debug("create file " + file.getFileName() + "(" + fileName + ") in repository");
		}
		try {
			OutputStream output = new FileOutputStream(fileName);
			InputStream input = file.getInputStream();
			drain(input, output);
			// do not close input stream - it could be an used futher on - it belongs to the caller
			output.close();
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw e;
		}
	}

	private String toFileName(RepositoryFile file) {
		return path + "/" + file.getId() + ".data";
	}

	/**
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

	@Override
	protected void handleSetRepositoryLocation(String path) throws Exception {
		Validate.notEmpty(path, "RepositoryLocation is not configured!");

		logger.info("set repository path to " + path);

		this.path = path;

		// ensure the server path exists
		File dirPath = new File(path);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	@Override
	protected String handleGetRepositoryLocation() throws Exception {
		return path;
	}

	@Override
	protected InputStream handleGetInputStreamOfFile(RepositoryFile repositoryFile) throws Exception {
		Validate.notNull(repositoryFile, "Parameter repositoryFile must not be null!");
		
		if (logger.isDebugEnabled()) {
			logger.debug("loading file " + repositoryFile.getFileName());
		}
		String fileName = toFileName(repositoryFile);
		InputStream is = new FileInputStream(fileName);
		repositoryFile.setInputStream(is);
		return is;
	}

	@Override
	protected RepositoryFile handleGetFile(RepositoryFile file, boolean openStream) throws Exception {
		Validate.notEmpty(path, "RepositoryLocation is not configured!");
		Validate.notNull(file, "Parameter file is mandatory!");
		Validate.notNull(file.getId(), "Parameter file.getId() must not be null.");
		
		RepositoryFile repositoryFile = getRepositoryFileDao().load(file.getId());
		if (repositoryFile == null) {
			if (logger.isDebugEnabled()) {
				logger.error("file with id " + file.getId() + " not found!");
			}
		} else if (openStream) {
			getInputStreamOfFile(repositoryFile);
		}
		return repositoryFile;
	}
	
	/**
	 * @see org.openuss.repository.RepositoryService#getFile(org.openuss.repository.RepositoryFile)
	 */
	protected RepositoryFile handleGetFile(RepositoryFile file) throws java.lang.Exception {
		return getFile(file, true);
	}
}