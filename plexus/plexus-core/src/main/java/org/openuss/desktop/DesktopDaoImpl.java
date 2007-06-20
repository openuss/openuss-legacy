// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.Collection;

import org.openuss.lecture.Institute;
import org.openuss.lecture.CourseType;

/**
 * @see org.openuss.desktop.Desktop
 * @author Ingo Düppe
 */
public class DesktopDaoImpl extends org.openuss.desktop.DesktopDaoBase {

	@Override
	public java.util.Collection findByCourse(final int transform, final org.openuss.lecture.Course course)
    {
        return this.findByCourse(transform, "select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.Course e where e=:course and e in elements(d.courses)", course);
    }

	@Override
	public Collection findByInstitute(final int transform, final Institute institute) {
		return this.findByInstitute(transform, "select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.Institute f where f=:institute and f in elements(d.institutes)", institute);
	}

	@Override
	public Collection findByCourseType(final int transform, final CourseType courseType) {
		return this.findByCourseType(transform, "select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.CourseType s where s=:courseType and s in elements(d.courseTypes)", courseType);
	}

	
	

}