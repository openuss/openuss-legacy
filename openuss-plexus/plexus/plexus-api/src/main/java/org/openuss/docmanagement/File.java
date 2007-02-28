package org.openuss.docmanagement;

import java.sql.Timestamp;

public interface File extends Node{

	public abstract Timestamp getDistributionTime();

	public abstract void setDistributionTime(Timestamp distributionTime);

	public abstract Timestamp getLastModification();

	public abstract void setLastModification(Timestamp lastModification);

	public abstract long getSize();

	public abstract void setSize(long size);

	public abstract int getVersion();

	public abstract void setVersion(int version);

	public abstract File getPredecessor();

	public abstract void setPredecessor(File predecessor);

	public abstract String getMimeType();

	public abstract void setMimeType(String mimeType);

}