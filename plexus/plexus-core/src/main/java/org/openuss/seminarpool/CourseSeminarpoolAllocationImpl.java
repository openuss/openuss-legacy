// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

/**
 * @see org.openuss.seminarpool.CourseSeminarpoolAllocation
 */
public class CourseSeminarpoolAllocationImpl
    extends org.openuss.seminarpool.CourseSeminarpoolAllocationBase
	implements org.openuss.seminarpool.CourseSeminarpoolAllocation
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 7459013160324036581L;

    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocation#addUser(java.lang.Long)
     */
    public void addUser(java.lang.Long userId)
    {
        // @todo implement public void addUser(java.lang.Long userId)
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.CourseSeminarpoolAllocation.addUser(java.lang.Long userId) Not implemented!");
    }

    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocation#removeUser(java.lang.Long)
     */
    public void removeUser(java.lang.Long userId)
    {
        // @todo implement public void removeUser(java.lang.Long userId)
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.CourseSeminarpoolAllocation.removeUser(java.lang.Long userId) Not implemented!");
    }

    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocation#addCourse(java.lang.Long)
     */
    public void addCourse(java.lang.Long courseId)
    {
        // @todo implement public void addCourse(java.lang.Long courseId)
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.CourseSeminarpoolAllocation.addCourse(java.lang.Long courseId) Not implemented!");
    }

    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocation#removeCourse(java.lang.Long)
     */
    public void removeCourse(java.lang.Long courseId)
    {
        // @todo implement public void removeCourse(java.lang.Long courseId)
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.CourseSeminarpoolAllocation.removeCourse(java.lang.Long courseId) Not implemented!");
    }

    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocation#addCourseGroup(org.openuss.seminarpool.CourseGroup)
     */
    public void addCourseGroup(org.openuss.seminarpool.CourseGroup courseGroup)
    {
    	if ( !getCourseGroup().contains(courseGroup) ) {
    		getCourseGroup().add(courseGroup);
    	}
    	courseGroup.setCourseSeminarpoolAllocation(this);
	}

    /**
     * @see org.openuss.seminarpool.CourseSeminarpoolAllocation#removeCourseGroup(org.openuss.seminarpool.CourseGroup)
     */
    public void removeCourseGroup(org.openuss.seminarpool.CourseGroup courseGroup)
    {
		if (getCourseGroup().remove(courseGroup)) {
			if (courseGroup.getCourseSeminarpoolAllocation().equals(this)) {
				courseGroup.setCourseSeminarpoolAllocation(null);
			}
		}
    }

}