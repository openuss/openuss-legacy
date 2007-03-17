// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntity.vsl in andromda-hibernate-cartridge.
//
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;



/**
 * @see org.openuss.lecture.Faculty
 */
public class FacultyImpl extends org.openuss.lecture.FacultyBase implements org.openuss.lecture.Faculty {

	private static final long serialVersionUID = 2164596673303053977L;
	
	public FacultyImpl() {
	}

	@Override
	public void add(Enrollment enrollment) {
		getEnrollments().add(enrollment);
	}

	@Override
	public void add(Subject subject) {
		getSubjects().add(subject);
	}

	@Override
	public void add(Period period) {
		getPeriods().add(period);
	}

	@Override
	public void remove(Enrollment enrollment) {
		getEnrollments().remove(enrollment);
	}

	@Override
	public void remove(Subject subject) {
		getSubjects().remove(subject);
	}

	@Override
	public void remove(Period period) {
		getPeriods().remove(period);
	}

	@Override
	public List getActiveEnrollments() {
		if (getActivePeriod() == null) {
			return new ArrayList();
		} else {
			return getActivePeriod().getEnrollments();
		}
	}

}