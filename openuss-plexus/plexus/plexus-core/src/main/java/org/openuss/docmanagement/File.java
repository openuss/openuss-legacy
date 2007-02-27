package org.openuss.docmanagement;

import java.sql.Timestamp;



public class FileImpl extends Node{
	
	public long size;
	
	public Timestamp lastModification;
	
	public int version;
	
	public String message;
	
	public Timestamp distributionTime;
	
	public File predecessor;

	public Timestamp getDistributionTime() {
		return distributionTime;
	}

	public void setDistributionTime(Timestamp distributionTime) {
		this.distributionTime = distributionTime;
	}

	public Timestamp getLastModification() {
		return lastModification;
	}

	public void setLastModification(Timestamp lastModification) {
		this.lastModification = lastModification;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getVersion() {
		return version;
	}

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