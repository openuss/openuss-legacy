package org.openuss.statistics;

import java.util.Date;

/**
 * 
 */
public class SystemStatisticInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 7588490034762447686L;

	public SystemStatisticInfo() {
		this.id = null;
		this.posts = null;
		this.universities = null;
		this.departments = null;
		this.documents = null;
		this.courses = null;
		this.institutes = null;
		this.users = null;
		this.createTime = null;
	}

	public SystemStatisticInfo(Long id, Long posts, Long universities, Long departments, Long documents, Long courses,
			Long institutes, Long users, Date createTime) {
		this.id = id;
		this.posts = posts;
		this.universities = universities;
		this.departments = departments;
		this.documents = documents;
		this.courses = courses;
		this.institutes = institutes;
		this.users = users;
		this.createTime = createTime;
	}

	/**
	 * Copies constructor from other SystemStatisticInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public SystemStatisticInfo(SystemStatisticInfo otherBean) {
		this(otherBean.getId(), otherBean.getPosts(), otherBean.getUniversities(), otherBean.getDepartments(),
				otherBean.getDocuments(), otherBean.getCourses(), otherBean.getInstitutes(), otherBean.getUsers(),
				otherBean.getCreateTime());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(SystemStatisticInfo otherBean) {
		this.setId(otherBean.getId());
		this.setPosts(otherBean.getPosts());
		this.setUniversities(otherBean.getUniversities());
		this.setDepartments(otherBean.getDepartments());
		this.setDocuments(otherBean.getDocuments());
		this.setCourses(otherBean.getCourses());
		this.setInstitutes(otherBean.getInstitutes());
		this.setUsers(otherBean.getUsers());
		this.setCreateTime(otherBean.getCreateTime());
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

	private Long posts;

	/**
     * 
     */
	public Long getPosts() {
		return this.posts;
	}

	public void setPosts(Long posts) {
		this.posts = posts;
	}

	private Long universities;

	/**
     * 
     */
	public Long getUniversities() {
		return this.universities;
	}

	public void setUniversities(Long universities) {
		this.universities = universities;
	}

	private Long departments;

	/**
     * 
     */
	public Long getDepartments() {
		return this.departments;
	}

	public void setDepartments(Long departments) {
		this.departments = departments;
	}

	private Long documents;

	/**
     * 
     */
	public Long getDocuments() {
		return this.documents;
	}

	public void setDocuments(Long documents) {
		this.documents = documents;
	}

	private Long courses;

	/**
     * 
     */
	public Long getCourses() {
		return this.courses;
	}

	public void setCourses(Long courses) {
		this.courses = courses;
	}

	private Long institutes;

	/**
     * 
     */
	public Long getInstitutes() {
		return this.institutes;
	}

	public void setInstitutes(Long institutes) {
		this.institutes = institutes;
	}

	private Long users;

	/**
     * 
     */
	public Long getUsers() {
		return this.users;
	}

	public void setUsers(Long users) {
		this.users = users;
	}

	private Date createTime;

	/**
     * 
     */
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Returns <code>true</code> if the argument is an SystemStatisticInfo
	 * instance and all identifiers for this object equal the identifiers of the
	 * argument object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof SystemStatisticInfo)) {
			return false;
		}
		final SystemStatisticInfo that = (SystemStatisticInfo) object;
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