package org.openuss.lecture;

/**
 * 
 */
public abstract class CourseMemberBase implements CourseMember, java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -6447217518398907808L;

	private org.openuss.lecture.CourseMemberPK courseMemberPk;

	public org.openuss.lecture.CourseMemberPK getCourseMemberPk() {
		return this.courseMemberPk;
	}

	public void setCourseMemberPk(org.openuss.lecture.CourseMemberPK courseMemberPk) {
		this.courseMemberPk = courseMemberPk;
	}

	private org.openuss.lecture.CourseMemberType memberType = org.openuss.lecture.CourseMemberType.ASPIRANT;

	/**
	 * @see org.openuss.lecture.CourseMember#getMemberType()
	 */
	public org.openuss.lecture.CourseMemberType getMemberType() {
		return this.memberType;
	}

	/**
	 * @see org.openuss.lecture.CourseMember#setMemberType(org.openuss.lecture.CourseMemberType
	 *      memberType)
	 */
	public void setMemberType(org.openuss.lecture.CourseMemberType memberType) {
		this.memberType = memberType;
	}

	/**
	 * Returns <code>true</code> if the argument is an CourseMember instance and
	 * all identifiers for this entity equal the identifiers of the argument
	 * entity. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof CourseMember)) {
			return false;
		}
		final CourseMember that = (CourseMember) object;
		if (this.courseMemberPk == null || that.getCourseMemberPk() == null
				|| !this.courseMemberPk.equals(that.getCourseMemberPk())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (courseMemberPk == null ? 0 : courseMemberPk.hashCode());

		return hashCode;
	}

}