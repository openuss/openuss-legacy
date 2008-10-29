package org.openuss.lecture;

/**
 * 
 * @author Ingo Dueppe
 *
 */
public interface CourseMember {

	public org.openuss.lecture.CourseMemberPK getCourseMemberPk();

	public void setCourseMemberPk(org.openuss.lecture.CourseMemberPK courseMemberPk);

	public org.openuss.lecture.CourseMemberType getMemberType();

	public void setMemberType(org.openuss.lecture.CourseMemberType memberType);

}