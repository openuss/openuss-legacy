package org.openuss.desktop;

import java.io.Serializable;

/**
 * @author Ingo Dueppe
 */
public class MyUniInstituteInfo implements Serializable {

	private static final long serialVersionUID = 8620166789912075103L;

	public MyUniInstituteInfo() {
		this.id = null;
		this.name = null;
		this.bookmarked = null;
		this.numberOfCurrentCourses = null;
		this.pastUniInfo = null;
		this.currentUniInfo = null;
	}

	public MyUniInstituteInfo(Long id, String name, Boolean bookmarked, Integer numberOfCurrentCourses,
			MyUniInfo pastUniInfo, MyUniInfo currentUniInfo) {
		this.id = id;
		this.name = name;
		this.bookmarked = bookmarked;
		this.numberOfCurrentCourses = numberOfCurrentCourses;
		this.pastUniInfo = pastUniInfo;
		this.currentUniInfo = currentUniInfo;
	}

	/**
	 * Copies constructor from other MyUniInstituteInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public MyUniInstituteInfo(MyUniInstituteInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getBookmarked(), otherBean.getNumberOfCurrentCourses(),
				otherBean.getPastUniInfo(), otherBean.getCurrentUniInfo());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(MyUniInstituteInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setBookmarked(otherBean.getBookmarked());
		this.setNumberOfCurrentCourses(otherBean.getNumberOfCurrentCourses());
		this.setPastUniInfo(otherBean.getPastUniInfo());
		this.setCurrentUniInfo(otherBean.getCurrentUniInfo());
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

	private Integer numberOfCurrentCourses;

	/**
     * 
     */
	public Integer getNumberOfCurrentCourses() {
		return this.numberOfCurrentCourses;
	}

	public void setNumberOfCurrentCourses(Integer numberOfCurrentCourses) {
		this.numberOfCurrentCourses = numberOfCurrentCourses;
	}

	private MyUniInfo pastUniInfo;

	/**
	 * Get the pastUniInfo
	 * 
	 */
	public MyUniInfo getPastUniInfo() {
		return this.pastUniInfo;
	}

	/**
	 * Sets the pastUniInfo
	 */
	public void setPastUniInfo(MyUniInfo pastUniInfo) {
		this.pastUniInfo = pastUniInfo;
	}

	private MyUniInfo currentUniInfo;

	/**
	 * Get the currentUniInfo
	 * 
	 */
	public MyUniInfo getCurrentUniInfo() {
		return this.currentUniInfo;
	}

	/**
	 * Sets the currentUniInfo
	 */
	public void setCurrentUniInfo(MyUniInfo currentUniInfo) {
		this.currentUniInfo = currentUniInfo;
	}

	/**
	 * Returns <code>true</code> if the argument is an MyUniInstituteInfo
	 * instance and all identifiers for this object equal the identifiers of the
	 * argument object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof MyUniInstituteInfo)) {
			return false;
		}
		final MyUniInstituteInfo that = (MyUniInstituteInfo) object;
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