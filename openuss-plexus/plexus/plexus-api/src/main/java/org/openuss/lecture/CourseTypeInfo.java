package org.openuss.lecture;

/**
 * 
 */
public class CourseTypeInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -925064398225840237L;

	public CourseTypeInfo() {
		this.id = null;
		this.name = null;
		this.shortcut = null;
		this.instituteId = null;
		this.courseCount = null;
	}

	public CourseTypeInfo(Long id, String name, String shortcut, Long instituteId, Long courseCount) {
		this.id = id;
		this.name = name;
		this.shortcut = shortcut;
		this.instituteId = instituteId;
		this.courseCount = courseCount;
	}

	public CourseTypeInfo(Long id, String name, String shortcut, String description, Long instituteId, Long courseCount) {
		this.id = id;
		this.name = name;
		this.shortcut = shortcut;
		this.description = description;
		this.instituteId = instituteId;
		this.courseCount = courseCount;
	}

	/**
	 * Copies constructor from other CourseTypeInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public CourseTypeInfo(CourseTypeInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getShortcut(), otherBean.getDescription(), otherBean
				.getInstituteId(), otherBean.getCourseCount());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(CourseTypeInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setShortcut(otherBean.getShortcut());
		this.setDescription(otherBean.getDescription());
		this.setInstituteId(otherBean.getInstituteId());
		this.setCourseCount(otherBean.getCourseCount());
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

	private String shortcut;

	/**
     * 
     */
	public String getShortcut() {
		return this.shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
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

	private Long instituteId;

	/**
     * 
     */
	public Long getInstituteId() {
		return this.instituteId;
	}

	public void setInstituteId(Long instituteId) {
		this.instituteId = instituteId;
	}

	private Long courseCount;

	/**
     * 
     */
	public Long getCourseCount() {
		return this.courseCount;
	}

	public void setCourseCount(Long courseCount) {
		this.courseCount = courseCount;
	}

	/**
	 * Returns <code>true</code> if the argument is an CourseTypeInfo instance
	 * and all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof CourseTypeInfo)) {
			return false;
		}
		final CourseTypeInfo that = (CourseTypeInfo) object;
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