package org.openuss.desktop;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Ingo Dueppe
 */
public class MyUniInfo implements Serializable {

	private static final long serialVersionUID = -4160369315495536056L;

	public MyUniInfo() {
		this.myUniUniversityInfo = null;
	}

	public MyUniInfo(org.openuss.desktop.MyUniUniversityInfo myUniUniversityInfo) {
		this.myUniUniversityInfo = myUniUniversityInfo;
	}

	public MyUniInfo(org.openuss.desktop.MyUniUniversityInfo myUniUniversityInfo,
			Collection<MyUniInstituteInfo> pastInstitutes, Collection<MyUniCourseInfo> pastCourses,
			Collection<MyUniDepartmentInfo> departments, Collection<MyUniCourseInfo> currentCourses,
			Collection<MyUniInstituteInfo> currentInstitutes) {
		this.myUniUniversityInfo = myUniUniversityInfo;
		this.pastInstitutes = pastInstitutes;
		this.pastCourses = pastCourses;
		this.departments = departments;
		this.currentCourses = currentCourses;
		this.currentInstitutes = currentInstitutes;
	}

	/**
	 * Copies constructor from other MyUniInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public MyUniInfo(MyUniInfo otherBean) {
		this(otherBean.getMyUniUniversityInfo(), otherBean.getPastInstitutes(), otherBean.getPastCourses(), otherBean
				.getDepartments(), otherBean.getCurrentCourses(), otherBean.getCurrentInstitutes());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(MyUniInfo otherBean) {
		this.setMyUniUniversityInfo(otherBean.getMyUniUniversityInfo());
		this.setPastInstitutes(otherBean.getPastInstitutes());
		this.setPastCourses(otherBean.getPastCourses());
		this.setDepartments(otherBean.getDepartments());
		this.setCurrentCourses(otherBean.getCurrentCourses());
		this.setCurrentInstitutes(otherBean.getCurrentInstitutes());
	}

	private MyUniUniversityInfo myUniUniversityInfo;

	/**
     * 
     */
	public MyUniUniversityInfo getMyUniUniversityInfo() {
		return this.myUniUniversityInfo;
	}

	public void setMyUniUniversityInfo(MyUniUniversityInfo myUniUniversityInfo) {
		this.myUniUniversityInfo = myUniUniversityInfo;
	}

	private Collection<MyUniInstituteInfo> pastInstitutes;

	/**
	 * Get the pastInstitutes
	 * 
	 */
	public Collection<MyUniInstituteInfo> getPastInstitutes() {
		return this.pastInstitutes;
	}

	/**
	 * Sets the pastInstitutes
	 */
	public void setPastInstitutes(Collection<MyUniInstituteInfo> pastInstitutes) {
		this.pastInstitutes = pastInstitutes;
	}

	private Collection<MyUniCourseInfo> pastCourses;

	/**
	 * Get the pastCourses
	 * 
	 */
	public Collection<MyUniCourseInfo> getPastCourses() {
		return this.pastCourses;
	}

	/**
	 * Sets the pastCourses
	 */
	public void setPastCourses(Collection<MyUniCourseInfo> pastCourses) {
		this.pastCourses = pastCourses;
	}

	private Collection<MyUniDepartmentInfo> departments;

	/**
	 * Get the departments
	 * 
	 */
	public Collection<MyUniDepartmentInfo> getDepartments() {
		return this.departments;
	}

	/**
	 * Sets the departments
	 */
	public void setDepartments(Collection<MyUniDepartmentInfo> departments) {
		this.departments = departments;
	}

	private Collection<MyUniCourseInfo> currentCourses;

	/**
	 * Get the currentCourses
	 * 
	 */
	public Collection<MyUniCourseInfo> getCurrentCourses() {
		return this.currentCourses;
	}

	/**
	 * Sets the currentCourses
	 */
	public void setCurrentCourses(Collection<MyUniCourseInfo> currentCourses) {
		this.currentCourses = currentCourses;
	}

	private Collection<MyUniInstituteInfo> currentInstitutes;

	/**
	 * Get the currentInstitutes
	 * 
	 */
	public Collection<MyUniInstituteInfo> getCurrentInstitutes() {
		return this.currentInstitutes;
	}

	/**
	 * Sets the currentInstitutes
	 */
	public void setCurrentInstitutes(Collection<MyUniInstituteInfo> currentInstitutes) {
		this.currentInstitutes = currentInstitutes;
	}

}