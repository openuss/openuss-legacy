package org.openuss.documents;

import java.io.Serializable;
import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe 
 */
public class FileInfo implements Serializable, DomainObject {

	private static final long serialVersionUID = -5716072311340972385L;

	public FileInfo() {
		this.id = null;
		this.name = null;
		this.fileName = null;
		this.absoluteName = null;
		this.description = null;
		this.path = null;
		this.fileSize = null;
		this.contentType = null;
		this.created = null;
		this.modified = null;
		this.inputStream = null;
		this.extension = null;
		this.released = false;
	}

	public FileInfo(Long id, String name, String fileName, String absoluteName, String description, String path,
			Integer fileSize, String contentType, Date created, Date modified, java.io.InputStream inputStream,
			String extension, boolean released) {
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.absoluteName = absoluteName;
		this.description = description;
		this.path = path;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.created = created;
		this.modified = modified;
		this.inputStream = inputStream;
		this.extension = extension;
		this.released = released;
	}

	/**
	 * Copies constructor from other FileInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public FileInfo(FileInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getFileName(), otherBean.getAbsoluteName(), otherBean
				.getDescription(), otherBean.getPath(), otherBean.getFileSize(), otherBean.getContentType(), otherBean
				.getCreated(), otherBean.getModified(), otherBean.getInputStream(), otherBean.getExtension(), otherBean
				.isReleased());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(FileInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setFileName(otherBean.getFileName());
		this.setAbsoluteName(otherBean.getAbsoluteName());
		this.setDescription(otherBean.getDescription());
		this.setPath(otherBean.getPath());
		this.setFileSize(otherBean.getFileSize());
		this.setContentType(otherBean.getContentType());
		this.setCreated(otherBean.getCreated());
		this.setModified(otherBean.getModified());
		this.setInputStream(otherBean.getInputStream());
		this.setExtension(otherBean.getExtension());
		this.setReleased(otherBean.isReleased());
	}

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String name;

	/**
	 * the display name of the file
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String fileName;

	/**
	 * the file name of the file.
	 */
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private String absoluteName;

	/**
	 * The filename with path information.
	 */
	public String getAbsoluteName() {
		return this.absoluteName;
	}

	public void setAbsoluteName(String absoluteName) {
		this.absoluteName = absoluteName;
	}

	private String description;

	/**
	 * description of the file
	 */
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String path;

	/**
	 * the path of the file
	 */
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private Integer fileSize;

	/**
	 * size of the file
	 */
	public Integer getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	private String contentType;

	/**
	 * content type of the file
	 */
	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	private Date created;

	/**
	 * time when the file was created, or the file will be released.
	 */
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	private Date modified;

	/**
	 * last modified date
	 */
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	private java.io.InputStream inputStream;

	/**
	 * InputStream to the file
	 */
	public java.io.InputStream getInputStream() {
		return this.inputStream;
	}

	public void setInputStream(java.io.InputStream inputStream) {
		this.inputStream = inputStream;
	}

	private String extension;

	/**
     * 
     */
	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	private boolean released;

	/**
     * 
     */
	public boolean isReleased() {
		return this.released;
	}

	public void setReleased(boolean released) {
		this.released = released;
	}

	/**
	 * Returns <code>true</code> if the argument is an FileInfo instance and all
	 * identifiers for this object equal the identifiers of the argument object.
	 * Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof FileInfo)) {
			return false;
		}
		final FileInfo that = (FileInfo) object;
		if (this.id == null || that.getId() == null || !this.id.equals(that.getId())) {
			return false;
		}

		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

		return hashCode;
	}

}