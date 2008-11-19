package org.openuss.statistics;

/**
 * @author Ingo Dueppe
 */
public abstract class SystemStatisticBase implements SystemStatistic, java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -1627171834634451701L;

	private java.lang.Long users;

	/**
	 * @see org.openuss.statistics.SystemStatistic#getUsers()
	 */
	public java.lang.Long getUsers() {
		return this.users;
	}

	/**
	 * @see org.openuss.statistics.SystemStatistic#setUsers(java.lang.Long
	 *      users)
	 */
	public void setUsers(java.lang.Long users) {
		this.users = users;
	}

	private java.lang.Long universities;

	/**
	 * @see org.openuss.statistics.SystemStatistic#getUniversities()
	 */
	public java.lang.Long getUniversities() {
		return this.universities;
	}

	/**
	 * @see org.openuss.statistics.SystemStatistic#setUniversities(java.lang.Long
	 *      universities)
	 */
	public void setUniversities(java.lang.Long universities) {
		this.universities = universities;
	}

	private java.lang.Long departments;

	/**
	 * @see org.openuss.statistics.SystemStatistic#getDepartments()
	 */
	public java.lang.Long getDepartments() {
		return this.departments;
	}

	/**
	 * @see org.openuss.statistics.SystemStatistic#setDepartments(java.lang.Long
	 *      departments)
	 */
	public void setDepartments(java.lang.Long departments) {
		this.departments = departments;
	}

	private java.lang.Long institutes;

	/**
	 * @see org.openuss.statistics.SystemStatistic#getInstitutes()
	 */
	public java.lang.Long getInstitutes() {
		return this.institutes;
	}

	/**
	 * @see org.openuss.statistics.SystemStatistic#setInstitutes(java.lang.Long
	 *      institutes)
	 */
	public void setInstitutes(java.lang.Long institutes) {
		this.institutes = institutes;
	}

	private java.lang.Long courses;

	/**
	 * @see org.openuss.statistics.SystemStatistic#getCourses()
	 */
	public java.lang.Long getCourses() {
		return this.courses;
	}

	/**
	 * @see org.openuss.statistics.SystemStatistic#setCourses(java.lang.Long
	 *      courses)
	 */
	public void setCourses(java.lang.Long courses) {
		this.courses = courses;
	}

	private java.lang.Long documents;

	/**
	 * @see org.openuss.statistics.SystemStatistic#getDocuments()
	 */
	public java.lang.Long getDocuments() {
		return this.documents;
	}

	/**
	 * @see org.openuss.statistics.SystemStatistic#setDocuments(java.lang.Long
	 *      documents)
	 */
	public void setDocuments(java.lang.Long documents) {
		this.documents = documents;
	}

	private java.lang.Long posts;

	/**
	 * @see org.openuss.statistics.SystemStatistic#getPosts()
	 */
	public java.lang.Long getPosts() {
		return this.posts;
	}

	/**
	 * @see org.openuss.statistics.SystemStatistic#setPosts(java.lang.Long
	 *      posts)
	 */
	public void setPosts(java.lang.Long posts) {
		this.posts = posts;
	}

	private java.util.Date createTime;

	/**
	 * @see org.openuss.statistics.SystemStatistic#getCreateTime()
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * @see org.openuss.statistics.SystemStatistic#setCreateTime(java.util.Date
	 *      createTime)
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	private java.lang.Long id;

	/**
	 * @see org.openuss.statistics.SystemStatistic#getId()
	 */
	public java.lang.Long getId() {
		return this.id;
	}

	/**
	 * @see org.openuss.statistics.SystemStatistic#setId(java.lang.Long id)
	 */
	public void setId(java.lang.Long id) {
		this.id = id;
	}

	/**
	 * Returns <code>true</code> if the argument is an SystemStatistic instance
	 * and all identifiers for this entity equal the identifiers of the argument
	 * entity. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof SystemStatistic)) {
			return false;
		}
		final SystemStatistic that = (SystemStatistic) object;
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