package org.openuss.docmanagement;

import java.sql.Timestamp;



public class FileImpl extends NodeImpl implements File{
	
	public long size;
	
	public Timestamp lastModification;
	
	public int version;
	
	public String message;
	
	public Timestamp distributionTime;
	
	public File predecessor;

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
	 * @see org.openuss.docmanagement.File#getMessage()
	 */
	public String getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#setMessage(java.lang.String)
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#getSize()
	 */
	public long getSize() {
		return size;
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.File#setSize(long)
	 */
	public void setSize(long size) {
		this.size = size;
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
	
	
}