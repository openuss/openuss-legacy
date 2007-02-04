// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.repository;

import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

/**
 * @see org.openuss.repository.RepositoryFile
 */
public class RepositoryFileImpl extends org.openuss.repository.RepositoryFileBase implements
		org.openuss.repository.RepositoryFile {
	
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -3666297282956508661L;

	private InputStream inputStream;

	/**
	 * @see org.openuss.repository.File#getInputStream()
	 */
	public java.io.InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @see org.openuss.repository.File#setInputStream(java.io.InputStream)
	 */
	public void setInputStream(java.io.InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public String getExtension() {
		String fileName = getFileName();
		String extension = "";
		if (StringUtils.isNotBlank(fileName)) {
			int index = getFileName().lastIndexOf(".");
			if (index > -1)
				extension = fileName.substring(index+1);
		}
		return extension;
	}
}