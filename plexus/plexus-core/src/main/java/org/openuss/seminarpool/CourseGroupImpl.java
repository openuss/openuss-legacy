// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntityImpl.vsl in in openuss/tools/andromda/templates.
//
package org.openuss.seminarpool;

import org.openuss.security.User;

/**
 * @see org.openuss.seminarpool.CourseGroup
 */
public class CourseGroupImpl
    extends org.openuss.seminarpool.CourseGroupBase
	implements org.openuss.seminarpool.CourseGroup
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -5256797015565690286L;

	@Override
	public void addUser(User user) {
		if(user != null && !getUser().contains(user)){
			getUser().add(user);
		}
		if (! user.getCourseGroup().contains(user) ){
			user.getCourseGroup().add(this);
		}
	}

	@Override
	public void removeUser(User user) {
		if(user != null){
			getUser().remove(user);
		}
		if ( user.getCourseGroup().contains(user) ){
			user.getCourseGroup().remove(this);
		}
		
		
	}

	@Override
	public void addCourseSchedule(CourseSchedule courseSchedule) {
    	if ( !getCourseSchedule().contains(courseSchedule) ) {
    		getCourseSchedule().add(courseSchedule);
    	}
    	courseSchedule.setCourseGroup(this);	
    }

	@Override
	public void removeCourseGroup(CourseSchedule courseSchedule) {
		if (getCourseSchedule().remove(courseSchedule)) {
			if (courseSchedule.getCourseGroup().equals(this)) {
				courseSchedule.setCourseGroup(null);
			}
		}
	}

}