// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntity.vsl in andromda-hibernate-cartridge.
//
package org.openuss.lecture;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.DateUtils;

/**
 * @see org.openuss.lecture.Period
 */
public class PeriodImpl extends org.openuss.lecture.PeriodBase implements org.openuss.lecture.Period {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 9086031628022698697L;

	@Override
	public void add(Course course) {
		Validate.notNull(course, "Period.add(Course) - course cannot be null");

		if (!this.getCourses().contains(course)) {
			this.getCourses().add(course);
			course.setPeriod(this);
		} else {
			course.setPeriod(this);
			throw new IllegalArgumentException(
			"University.add(Course) - the Course has already been in the List");
		}	
	}

	@Override
	public void remove(Course course) {
		getCourses().remove(course);
	}

	@Override 
	public boolean isActive() {
		Date now = new Date();
		if (this.getStartdate().before((now)) && this.getEnddate().after(now))
			return true;
		else
			return false;
	}

	@Override
	public boolean isRemovable() {
		if (this.getCourses().size() < 1)
			return true;
		else
			return false;
	}

	@Override
	public void setEnddate(Date enddate) {
		super.setEnddate(DateUtils.truncate(enddate, Calendar.DATE));
	}

	@Override
	public void setStartdate(Date startdate) {
		super.setStartdate(DateUtils.truncate(startdate, Calendar.DATE));
	}
	
	
}