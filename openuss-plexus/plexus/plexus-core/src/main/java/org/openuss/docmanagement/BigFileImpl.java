package org.openuss.docmanagement;

import java.io.InputStream;
import java.sql.Timestamp;

public class BigFileImpl extends FileImpl implements BigFile {
	
	public BigFileImpl(){
		super();
	}
	
	public BigFileImpl(Timestamp distributionTime, String id, Timestamp lastModification, long length, String message, String mimeType, String name, String path, File predecessor, int version, int visibility, InputStream file){
		this.setDistributionTime(distributionTime);
		this.setId(id);
		this.setLastModification(lastModification);
		this.setLength(length);
		this.setMessage(message);
		this.setMimeType(mimeType);
		this.setName(name);
		this.setPath(path);
		this.setPredecessor(predecessor);
		this.setVersion(version);
		this.setVisibility(visibility);
		this.setFile(file);
	}

	public BigFileImpl(Timestamp distributionTime, Timestamp lastModification, long length, String message, String mimeType, String name, String path, File predecessor, int version, int visibility, InputStream file){
		this.setDistributionTime(distributionTime);
		this.setLastModification(lastModification);
		this.setLength(length);
		this.setMessage(message);
		this.setMimeType(mimeType);
		this.setName(name);
		this.setPath(path);
		this.setPredecessor(predecessor);
		this.setVersion(version);
		this.setVisibility(visibility);
		this.setFile(file);
	}
	
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