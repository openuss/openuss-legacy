// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.Collection;

import org.openuss.lecture.Faculty;
import org.openuss.lecture.CourseType;

/**
 * @see org.openuss.desktop.Desktop
 * @author Ingo D�ppe
 */
public class DesktopDaoImpl extends org.openuss.desktop.DesktopDaoBase {

	@Override
	public java.util.Collection findByCourse(final int transform, final org.openuss.lecture.Course course)
    {
        return this.findByCourse(transform, "select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.Course e where e=:course and e in elements(d.courses)", course);
    }

	@Override
	public Collection findByFaculty(final int transform, final Faculty faculty) {
		return this.findByFaculty(transform, "select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.Faculty f where f=:faculty and f in elements(d.faculties)", faculty);
	}

	@Override
	public Collection findByCourseType(final int transform, final CourseType courseType) {
		return this.findByCourseType(transform, "select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.CourseType s where s=:courseType and s in elements(d.courseTypes)", courseType);
	}

	
	

}