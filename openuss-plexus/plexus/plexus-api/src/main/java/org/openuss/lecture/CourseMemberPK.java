package org.openuss.lecture;

/**
 * Primary key class for CourseMember
 */
public class CourseMemberPK implements java.io.Serializable {

	private static final long serialVersionUID = -6447217518398907808L;

	private org.openuss.lecture.Course course;

	public org.openuss.lecture.Course getCourse() {
		return this.course;
	}

	public void setCourse(org.openuss.lecture.Course course) {
		this.course = course;
	}

	private org.openuss.security.User user;

	public org.openuss.security.User getUser() {
		return this.user;
	}

	public void setUser(org.openuss.security.User user) {
		this.user = user;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof CourseMemberPK)) {
			return false;
		}
		final CourseMemberPK that = (CourseMemberPK) object;
		return new org.apache.commons.lang.builder.EqualsBuilder().append(this.getCourse(), that.getCourse()).append(
				this.getUser(), that.getUser()).isEquals();
	}

	public int hashCode() {
		return new org.apache.commons.lang.builder.HashCodeBuilder().append(getCourse()).append(getUser()).toHashCode();
	}
}