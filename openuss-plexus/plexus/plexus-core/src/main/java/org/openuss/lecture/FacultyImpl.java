// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntity.vsl in andromda-hibernate-cartridge.
//
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;



/**
 * @see org.openuss.lecture.Faculty
 */
public class FacultyImpl extends org.openuss.lecture.FacultyBase implements org.openuss.lecture.Faculty {

	private static final long serialVersionUID = 2164596673303053977L;
	
	public FacultyImpl() {
	}

	@Override
	public void add(Course course) {
		getCourses().add(course);
	}

	@Override
	public String getWebsite() {
		String url = StringUtils.trimToNull(super.getWebsite());
		if (url != null) {
			if (!url.startsWith("https://") && !url.startsWith("http://")) {
				url = "http://"+url;
			}
		}
		return url;
	}

	@Override
	public void add(CourseType courseType) {
		getCourseTypes().add(courseType);
	}

	@Override
	public void add(Period period) {
		getPeriods().add(period);
	}

	@Override
	public void remove(Course course) {
		getCourses().remove(course);
	}

	@Override
	public void remove(CourseType courseType) {
		getCourseTypes().remove(courseType);
	}

	@Override
	public void remove(Period period) {
		getPeriods().remove(period);
	}

	@Override
	public List getActiveCourses() {
		if (getActivePeriod() == null) {
			return new ArrayList();
		} else {
			return getActivePeriod().getCourses();
		}
	}

}