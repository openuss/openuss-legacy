// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

/**
 * @author ingo dueppe
 * @see org.openuss.repository.RepositoryFile
 */
public class RepositoryFileImpl extends RepositoryFileBase implements RepositoryFile {

	private static final Logger logger = Logger.getLogger(RepositoryFileImpl.class);

	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -3666297282956508661L;

	private transient InputStream inputStream;

	/**
	 * @throws SQLException
	 * @see org.openuss.repository.File#getInputStream()
	 */
	public java.io.InputStream getInputStream() {
		if (inputStream == null) {
			try {
				inputStream = getContent().getBinaryStream();
			} catch (SQLException e) {
				logger.error(e);
				throw new RuntimeException(e);
			}
		}
		return inputStream;
	}

	/**
	 * @see org.openuss.repository.File#setInputStream(java.io.InputStream)
	 */
	public void setInputStream(java.io.InputStream inputStream) {
		this.inputStream = inputStream;
		try {
			setContent(Hibernate.createBlob(inputStream));
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void increaseDownloads() {
		setDownloads(getDownloads()+1L);
	}

}