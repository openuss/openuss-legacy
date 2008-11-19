package org.openuss.desktop;

import java.io.Serializable;

/**
 * @author Ingo Dueppe
 */
public class MyUniUniversityInfo implements Serializable {

	private static final long serialVersionUID = -7549477559666110716L;

	public MyUniUniversityInfo() {
		this.id = null;
		this.name = null;
	}

	public MyUniUniversityInfo(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Copies constructor from other MyUniUniversityInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public MyUniUniversityInfo(MyUniUniversityInfo otherBean) {
		this(otherBean.getId(), otherBean.getName());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(MyUniUniversityInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
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

	/**
	 * Returns <code>true</code> if the argument is an MyUniUniversityInfo
	 * instance and all identifiers for this object equal the identifiers of the
	 * argument object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof MyUniUniversityInfo)) {
			return false;
		}
		final MyUniUniversityInfo that = (MyUniUniversityInfo) object;
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