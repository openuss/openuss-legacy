package org.openuss.documents;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public interface FileEntry extends FolderEntry, org.openuss.foundation.DomainObject {

	/**
	 * MimeType of Content.
	 */
	public String getContentType();

	public void setContentType(String contentType);

	public String getFileName();

	public void setFileName(String fileName);

	/**
	 * The size of the file in bytes.
	 */
	public Integer getFileSize();

	public void setFileSize(Integer fileSize);

	/**
	 * The point of time the file has been modified.
	 */  
	public Date getModified();

	public void setModified(Date modified);

}