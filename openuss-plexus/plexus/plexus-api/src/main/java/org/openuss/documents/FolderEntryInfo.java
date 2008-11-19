package org.openuss.documents;

import java.util.Date;

/**
 * @author Ingo Dueppe 
 */
public class FolderEntryInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {

	private static final long serialVersionUID = -4784578107022583691L;

	public FolderEntryInfo() {
		this.id = null;
		this.name = null;
		this.fileName = null;
		this.description = null;
		this.path = null;
		this.created = null;
		this.modified = null;
		this.releaseDate = null;
		this.released = false;
		this.extension = null;
		this.folder = false;
		this.fileSize = null;
		this.sizeAsString = null;
		this.viewState = null;
	}

	public FolderEntryInfo(Long id, String name, String fileName, String description, String path, Date created,
			Date modified, Date releaseDate, boolean released, String extension, boolean folder, Integer fileSize,
			String sizeAsString, org.openuss.viewtracking.ViewState viewState) {
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.description = description;
		this.path = path;
		this.created = created;
		this.modified = modified;
		this.releaseDate = releaseDate;
		this.released = released;
		this.extension = extension;
		this.folder = folder;
		this.fileSize = fileSize;
		this.sizeAsString = sizeAsString;
		this.viewState = viewState;
	}

	/**
	 * Copies constructor from other FolderEntryInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public FolderEntryInfo(FolderEntryInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getFileName(), otherBean.getDescription(), otherBean
				.getPath(), otherBean.getCreated(), otherBean.getModified(), otherBean.getReleaseDate(), otherBean
				.isReleased(), otherBean.getExtension(), otherBean.isFolder(), otherBean.getFileSize(), otherBean
				.getSizeAsString(), otherBean.getViewState());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(FolderEntryInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setFileName(otherBean.getFileName());
		this.setDescription(otherBean.getDescription());
		this.setPath(otherBean.getPath());
		this.setCreated(otherBean.getCreated());
		this.setModified(otherBean.getModified());
		this.setReleaseDate(otherBean.getReleaseDate());
		this.setReleased(otherBean.isReleased());
		this.setExtension(otherBean.getExtension());
		this.setFolder(otherBean.isFolder());
		this.setFileSize(otherBean.getFileSize());
		this.setSizeAsString(otherBean.getSizeAsString());
		this.setViewState(otherBean.getViewState());
	}

	private Long id;

	/**
     * 
     */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String name;

	/**
     * 
     */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String fileName;

	/**
     * 
     */
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private String description;

	/**
     * 
     */
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String path;

	/**
     * 
     */
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private Date created;

	/**
     * 
     */
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	private Date modified;

	/**
     * 
     */
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	private Date releaseDate;

	/**
     * 
     */
	public Date getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
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

	private boolean folder;

	/**
     * 
     */
	public boolean isFolder() {
		return this.folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	private Integer fileSize;

	/**
     * 
     */
	public Integer getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	private String sizeAsString;

	/**
     * 
     */
	public String getSizeAsString() {
		return this.sizeAsString;
	}

	public void setSizeAsString(String sizeAsString) {
		this.sizeAsString = sizeAsString;
	}

	private org.openuss.viewtracking.ViewState viewState;

	/**
     * 
     */
	public org.openuss.viewtracking.ViewState getViewState() {
		return this.viewState;
	}

	public void setViewState(org.openuss.viewtracking.ViewState viewState) {
		this.viewState = viewState;
	}

	/**
	 * Returns <code>true</code> if the argument is an FolderEntryInfo instance
	 * and all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof FolderEntryInfo)) {
			return false;
		}
		final FolderEntryInfo that = (FolderEntryInfo) object;
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