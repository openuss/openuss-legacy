package org.openuss.lecture;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.lecture.Application
 * @author Ron Haus
 * @author Florian Dondorf
 */
public class ApplicationImpl extends org.openuss.lecture.ApplicationBase implements org.openuss.lecture.Application {

	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -3892436410768766673L;

	@Override
	public void add(Department department) {
		Validate.notNull(department, "Application.add(department) - department cannot be null.");

		if (!department.getApplications().contains(this)) {
			department.getApplications().add(this);
			this.setDepartment(department);
		} else {
			this.setDepartment(department);
			throw new IllegalArgumentException(
					"Application.add(Department) - the Department has already been in the List");
		}
	}

	@Override
	public void add(Institute institute) {
		Validate.notNull(institute, "Application.add(institute) - institute cannot be null.");

		this.setInstitute(institute);
		institute.getApplications().add(this);
	}

	@Override
	public void remove(Department department) {
		Validate.notNull(department, "Application.remove(department) - department cannot be null.");

		if (!department.getApplications().remove(this)) {
			throw new IllegalArgumentException(
					"Application.remove(Department) - the Department has not been in the List");
		}
	}

	@Override
	public void remove(Institute institute) {
		Validate.notNull(institute, "Application.remove(institute) - institute cannot be null.");
		if (this.getInstitute().equals(institute)) {
			this.setInstitute(null);
			institute.getApplications().remove(this);
		} else {
			throw new IllegalArgumentException(
					"Application.remove(Institute) - the Institute is not the same as the set institute.");
		}

	}

}