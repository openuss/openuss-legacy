// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.Collection;

import org.openuss.lecture.Faculty;
import org.openuss.lecture.Subject;

/**
 * @see org.openuss.desktop.Desktop
 * @author Ingo Düppe
 */
public class DesktopDaoImpl extends org.openuss.desktop.DesktopDaoBase {

	@Override
	public java.util.Collection findByEnrollment(final int transform, final org.openuss.lecture.Enrollment enrollment)
    {
        return this.findByEnrollment(transform, "select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.Enrollment e where e=:enrollment and e in elements(d.enrollments)", enrollment);
    }

	@Override
	public Collection findByFaculty(final int transform, final Faculty faculty) {
		return this.findByFaculty(transform, "select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.Faculty f where f=:faculty and f in elements(d.faculties)", faculty);
	}

	@Override
	public Collection findBySubject(final int transform, final Subject subject) {
		return this.findBySubject(transform, "select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.Subject s where s=:subject and s in elements(d.subjects)", subject);
	}

	
	

}