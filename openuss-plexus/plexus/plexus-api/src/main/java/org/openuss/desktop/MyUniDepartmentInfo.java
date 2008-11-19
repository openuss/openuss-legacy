package org.openuss.desktop;

import java.io.Serializable;

/**
 * @author Ingo Dueppe
 */
public class MyUniDepartmentInfo implements Serializable {
	private static final long serialVersionUID = -4606253127852927634L;

	public MyUniDepartmentInfo() {
		this.id = null;
		this.name = null;
		this.bookmarked = null;
		this.myUniInfo = null;
	}

	public MyUniDepartmentInfo(Long id, String name, Boolean bookmarked, org.openuss.desktop.MyUniInfo myUniInfo) {
		this.id = id;
		this.name = name;
		this.bookmarked = bookmarked;
		this.myUniInfo = myUniInfo;
	}

	/**
	 * Copies constructor from other MyUniDepartmentInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public MyUniDepartmentInfo(MyUniDepartmentInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getBookmarked(), otherBean.getMyUniInfo());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(MyUniDepartmentInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setBookmarked(otherBean.getBookmarked());
		this.setMyUniInfo(otherBean.getMyUniInfo());
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

	private Boolean bookmarked;

	/**
     * 
     */
	public Boolean getBookmarked() {
		return this.bookmarked;
	}

	public void setBookmarked(Boolean bookmarked) {
		this.bookmarked = bookmarked;
	}

	private org.openuss.desktop.MyUniInfo myUniInfo;

	/**
	 * Get the myUniInfo
	 * 
	 */
	public org.openuss.desktop.MyUniInfo getMyUniInfo() {
		return this.myUniInfo;
	}

	/**
	 * Sets the myUniInfo
	 */
	public void setMyUniInfo(org.openuss.desktop.MyUniInfo myUniInfo) {
		this.myUniInfo = myUniInfo;
	}

	/**
	 * Returns <code>true</code> if the argument is an MyUniDepartmentInfo
	 * instance and all identifiers for this object equal the identifiers of the
	 * argument object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof MyUniDepartmentInfo)) {
			return false;
		}
		final MyUniDepartmentInfo that = (MyUniDepartmentInfo) object;
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