package org.openuss.documents;

import java.util.Date;

/**
 * @author Ingo Dueppe 
 */
public class FolderInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {

	private static final long serialVersionUID = -2083548224584386725L;

	public FolderInfo() {
		this.id = null;
		this.name = null;
		this.description = null;
		this.created = null;
		this.modified = null;
		this.root = false;
		this.path = null;
	}

	public FolderInfo(Long id, String name, String description, Date created, Date modified, boolean root, String path) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.created = created;
		this.modified = modified;
		this.root = root;
		this.path = path;
	}

	/**
	 * Copies constructor from other FolderInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public FolderInfo(FolderInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getDescription(), otherBean.getCreated(), otherBean
				.getModified(), otherBean.isRoot(), otherBean.getPath());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(FolderInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setDescription(otherBean.getDescription());
		this.setCreated(otherBean.getCreated());
		this.setModified(otherBean.getModified());
		this.setRoot(otherBean.isRoot());
		this.setPath(otherBean.getPath());
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

	private boolean root;

	/**
     * 
     */
	public boolean isRoot() {
		return this.root;
	}

	public void setRoot(boolean root) {
		this.root = root;
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

	/**
	 * Returns <code>true</code> if the argument is an FolderInfo instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof FolderInfo)) {
			return false;
		}
		final FolderInfo that = (FolderInfo) object;
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