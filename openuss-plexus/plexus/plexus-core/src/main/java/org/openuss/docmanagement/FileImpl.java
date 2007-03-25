package org.openuss.docmanagement;

import java.sql.Timestamp;



public class FileImpl extends ResourceImpl implements File{
	
	public long length;
	
	public Timestamp lastModification;
	
	public int version;
	
	public Timestamp distributionTime;
	
	public File predecessor;

	public String mimeType;
	
	public String owner;
	
	public String[] viewed;
	
	public String viewer;
	
	public FileImpl(){		
	}
	
	public FileImpl(Timestamp distributionTime, String id, Timestamp lastModification, long length, String message, String mimeType, String name, String path, File predecessor, int version, int visibility, String owner, String[] viewed, String viewer){
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
		this.owner = owner;
		this.viewed = viewed;
		this.viewer = viewer;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#getDistributionTime()
	 */
	public Timestamp getDistributionTime() {
		return distributionTime;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#setDistributionTime(java.sql.Timestamp)
	 */
	public void setDistributionTime(Timestamp distributionTime) {
		this.distributionTime = distributionTime;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#getLastModification()
	 */
	public Timestamp getLastModification() {
		return lastModification;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#setLastModification(java.sql.Timestamp)
	 */
	public void setLastModification(Timestamp lastModification) {
		this.lastModification = lastModification;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#getSize()
	 */
	public long getLength() {
		return length;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#setSize(long)
	 */
	public void setLength(long length) {
		this.length = length;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#getVersion()
	 */
	public int getVersion() {
		return version;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#setVersion(int)
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	public File getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(File predecessor) {
		this.predecessor = predecessor;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String[] getViewed() {
		return this.viewed;
	}

	public void setViewed(String[] viewed) {
		this.viewed = viewed;
	}

	public String getViewer() {
		return viewer;
	}

	public void setViewer(String viewer) {
		this.viewer = viewer;
	}
	
}