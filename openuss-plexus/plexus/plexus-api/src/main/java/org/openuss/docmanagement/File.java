package org.openuss.docmanagement;

import java.sql.Timestamp;

public interface File extends Resource {

	public Timestamp getDistributionTime();

	public void setDistributionTime(Timestamp distributionTime);

	public Timestamp getLastModification();

	public void setLastModification(Timestamp lastModification);

	public long getLength();

	public void setLength(long length);

	public int getVersion();

	public void setVersion(int version);

	public File getPredecessor();

	public void setPredecessor(File predecessor);

	public String getMimeType();

	public void setMimeType(String mimeType);

}