package org.openuss.lecture;

/**
 * 
 */
public class OrganisationHierarchy implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -694782982194760930L;

	public OrganisationHierarchy() {
		this.universityInfo = null;
	}

	public OrganisationHierarchy(org.openuss.lecture.UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}

	public OrganisationHierarchy(org.openuss.lecture.UniversityInfo universityInfo,
			org.openuss.lecture.DepartmentInfo departmentInfo, org.openuss.lecture.InstituteInfo instituteInfo) {
		this.universityInfo = universityInfo;
		this.departmentInfo = departmentInfo;
		this.instituteInfo = instituteInfo;
	}

	/**
	 * Copies constructor from other OrganisationHierarchy
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public OrganisationHierarchy(OrganisationHierarchy otherBean) {
		this(otherBean.getUniversityInfo(), otherBean.getDepartmentInfo(), otherBean.getInstituteInfo());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(OrganisationHierarchy otherBean) {
		this.setUniversityInfo(otherBean.getUniversityInfo());
		this.setDepartmentInfo(otherBean.getDepartmentInfo());
		this.setInstituteInfo(otherBean.getInstituteInfo());
	}

	private org.openuss.lecture.UniversityInfo universityInfo;

	/**
     * 
     */
	public org.openuss.lecture.UniversityInfo getUniversityInfo() {
		return this.universityInfo;
	}

	public void setUniversityInfo(org.openuss.lecture.UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}

	private org.openuss.lecture.DepartmentInfo departmentInfo;

	/**
     * 
     */
	public org.openuss.lecture.DepartmentInfo getDepartmentInfo() {
		return this.departmentInfo;
	}

	public void setDepartmentInfo(org.openuss.lecture.DepartmentInfo departmentInfo) {
		this.departmentInfo = departmentInfo;
	}

	private org.openuss.lecture.InstituteInfo instituteInfo;

	/**
     * 
     */
	public org.openuss.lecture.InstituteInfo getInstituteInfo() {
		return this.instituteInfo;
	}

	public void setInstituteInfo(org.openuss.lecture.InstituteInfo instituteInfo) {
		this.instituteInfo = instituteInfo;
	}

}