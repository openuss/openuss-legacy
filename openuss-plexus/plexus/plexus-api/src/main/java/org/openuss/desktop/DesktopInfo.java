package org.openuss.desktop;

import java.io.Serializable;
import java.util.List;

import org.openuss.foundation.DomainObject;
import org.openuss.security.UserInfo;

/**
 * @author Ingo Dueppe
 */
public class DesktopInfo implements Serializable, DomainObject {

	private static final long serialVersionUID = -964024977412283957L;

	public DesktopInfo() {
		this.id = null;
		this.userInfo = null;
		this.universityInfos = null;
		this.departmentInfos = null;
		this.instituteInfos = null;
		this.courseTypeInfos = null;
		this.courseInfos = null;
	}

	public DesktopInfo(Long id, UserInfo userInfo, List universityInfos, List departmentInfos, List instituteInfos,
			List courseTypeInfos, List courseInfos) {
		this.id = id;
		this.userInfo = userInfo;
		this.universityInfos = universityInfos;
		this.departmentInfos = departmentInfos;
		this.instituteInfos = instituteInfos;
		this.courseTypeInfos = courseTypeInfos;
		this.courseInfos = courseInfos;
	}

	/**
	 * Copies constructor from other DesktopInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public DesktopInfo(DesktopInfo otherBean) {
		this(otherBean.getId(), otherBean.getUserInfo(), otherBean.getUniversityInfos(),
				otherBean.getDepartmentInfos(), otherBean.getInstituteInfos(), otherBean.getCourseTypeInfos(),
				otherBean.getCourseInfos());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(DesktopInfo otherBean) {
		this.setId(otherBean.getId());
		this.setUserInfo(otherBean.getUserInfo());
		this.setUniversityInfos(otherBean.getUniversityInfos());
		this.setDepartmentInfos(otherBean.getDepartmentInfos());
		this.setInstituteInfos(otherBean.getInstituteInfos());
		this.setCourseTypeInfos(otherBean.getCourseTypeInfos());
		this.setCourseInfos(otherBean.getCourseInfos());
	}

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private UserInfo userInfo;

	/**
     * 
     */
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	private List universityInfos;

	public List getUniversityInfos() {
		return this.universityInfos;
	}

	public void setUniversityInfos(List universityInfos) {
		this.universityInfos = universityInfos;
	}

	private List departmentInfos;

	public List getDepartmentInfos() {
		return this.departmentInfos;
	}

	public void setDepartmentInfos(List departmentInfos) {
		this.departmentInfos = departmentInfos;
	}

	private List instituteInfos;

	public List getInstituteInfos() {
		return this.instituteInfos;
	}

	public void setInstituteInfos(List instituteInfos) {
		this.instituteInfos = instituteInfos;
	}

	private List courseTypeInfos;

	public List getCourseTypeInfos() {
		return this.courseTypeInfos;
	}

	public void setCourseTypeInfos(List courseTypeInfos) {
		this.courseTypeInfos = courseTypeInfos;
	}

	private List courseInfos;

	public List getCourseInfos() {
		return this.courseInfos;
	}

	public void setCourseInfos(List courseInfos) {
		this.courseInfos = courseInfos;
	}

	/**
	 * Returns <code>true</code> if the argument is an DesktopInfo instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof DesktopInfo)) {
			return false;
		}
		final DesktopInfo that = (DesktopInfo) object;
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