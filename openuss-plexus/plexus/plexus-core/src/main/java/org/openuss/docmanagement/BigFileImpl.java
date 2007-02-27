package org.openuss.docmanagement;

import java.io.InputStream;

public class BigFileImpl extends FileImpl implements BigFile{
	
	public InputStream file;

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.BigFile#getFile()
	 */
	public InputStream getFile() {
		return file;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.BigFile#setFile(java.io.InputStream)
	 */
	public void setFile(InputStream file) {
		this.file = file;
	}
}