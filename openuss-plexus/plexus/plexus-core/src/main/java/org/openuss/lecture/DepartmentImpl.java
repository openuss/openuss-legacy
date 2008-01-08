// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.lecture.Department
 * @author Ron Haus
 * @author Florian Dondorf
 * @authro Ingo Düppe
 */
public class DepartmentImpl extends DepartmentBase implements Department {
	/** The serial version UID of this class. Needed for serialization. */
	private static final long serialVersionUID = 6918694707748248783L;

	/**
	 * @see org.openuss.lecture.Department#add(org.openuss.lecture.Institute)
	 */
	public void add(Institute institute) {
		Validate.notNull(institute, "Institute cannot be null");

		if (!getInstitutes().contains(institute)) {
			getInstitutes().add(institute);
		}
		institute.setDepartment(this);
	}

	/**
	 * @see org.openuss.lecture.Department#remove(org.openuss.lecture.Institute)
	 */
	public void remove(Institute institute) {
		Validate.notNull(institute, "Department.remove(Institute) - institute cannot be null");

		if (this.getInstitutes().remove(institute)) {
			if (institute.getDepartment().equals(this)) {
				institute.setDepartment(null);
			}
		}
	}

}