// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.lecture.Department
 * @author Ron Haus, Florian Dondorf
 */
public class DepartmentImpl
    extends org.openuss.lecture.DepartmentBase
	implements org.openuss.lecture.Department
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 6918694707748248783L;

    /**
     * @see org.openuss.lecture.Department#add(org.openuss.lecture.Institute)
     */
    public void add(org.openuss.lecture.Institute institute)
    {
        if (institute != null)
        	this.getInstitutes().add(institute);
		Validate.notNull(institute, "Department.add(Institute) - institute cannot be null");

		if (!this.getInstitutes().contains(institute)) {
			this.getInstitutes().add(institute);
			institute.setDepartment(this);
		} else {
			institute.setDepartment(this);
			throw new IllegalArgumentException(
					"Department.add(Institute) - the Institute has already been in the List");
		}
    }

    /**
     * @see org.openuss.lecture.Department#remove(org.openuss.lecture.Institute)
     */
    public void remove(org.openuss.lecture.Institute institute)
    {
    	Validate.notNull(institute, "Department.remove(Institute) - institute cannot be null");

		if (!this.getInstitutes().remove(institute)) {
			if (institute.getDepartment().equals(this)) {
				institute.setDepartment(null);
			}
			throw new IllegalArgumentException(
					"Department.remove(Institute) - the Institute has not been in the List");
		}
		institute.setDepartment(null);
    }

}