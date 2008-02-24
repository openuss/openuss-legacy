// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.seminarpool.Seminarpool
 */
public class SeminarpoolImpl
    extends org.openuss.seminarpool.SeminarpoolBase
	implements org.openuss.seminarpool.Seminarpool
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 8717885129558548597L;

    /**
     * @see org.openuss.seminarpool.Seminarpool#isPasswordCorrect(java.lang.String)
     */
    public java.lang.Boolean isPasswordCorrect(java.lang.String password)
    {
    	if (password == null){
    		return false;
    	}
        return password.equals(getPassword());
    }

    /**
     * @see org.openuss.seminarpool.Seminarpool#addCondition(org.openuss.seminarpool.SeminarCondition)
     */
    public void addCondition(org.openuss.seminarpool.SeminarCondition condition)
    {
    	Validate.notNull(condition, "SeminarCondition cannot be null");
    	if ( !getSeminarCondition().contains(condition) ) {
    		getSeminarCondition().add(condition);
    	}
    	condition.setSeminarpool(this);
    }

    /**
     * @see org.openuss.seminarpool.Seminarpool#removeCondition(org.openuss.seminarpool.SeminarCondition)
     */
    public void removeCondition(org.openuss.seminarpool.SeminarCondition condition)
    {
		Validate.notNull(condition, "SeminarCondition cannot be null");

		if (getSeminarCondition().remove(condition)) {
			if (condition.getSeminarpool().equals(this)) {
				condition.setSeminarpool(null);
			}
		}
	}

    /**
     * @see org.openuss.seminarpool.Seminarpool#addRegistration(org.openuss.seminarpool.SeminarUserRegistration)
     */
    public void addRegistration(org.openuss.seminarpool.SeminarUserRegistration registration)
    {
    	Validate.notNull(registration, "SeminarUserRegistration cannot be null");
    	if ( !getSeminarUserRegistration().contains(registration) ) {
    		getSeminarUserRegistration().add(registration);
    	}
    	registration.setSeminarpool(this);
    }

    /**
     * @see org.openuss.seminarpool.Seminarpool#removeRegistration(org.openuss.seminarpool.SeminarUserRegistration)
     */
    public void removeRegistration(org.openuss.seminarpool.SeminarUserRegistration registration)
    {
		Validate.notNull(registration, "SeminarUserRegistration cannot be null");
		if (getSeminarUserRegistration().remove(registration)) {
			if (registration.getSeminarpool().equals(this)) {
				registration.setSeminarpool(null);
			}
		}   
	}

    /**
     * @see org.openuss.seminarpool.Seminarpool#addCourseAllocation(org.openuss.seminarpool.CourseSeminarpoolAllocation)
     */
    public void addCourseAllocation(org.openuss.seminarpool.CourseSeminarpoolAllocation courseAllocation)
    {
    	Validate.notNull(courseAllocation, "CourseSeminarpoolAllocation cannot be null");
    	if ( !getCourseSeminarpoolAllocation().contains(courseAllocation) ) {
    		getCourseSeminarpoolAllocation().add(courseAllocation);
    	}
    	courseAllocation.setSeminarpool(this);    
    }

    /**
     * @see org.openuss.seminarpool.Seminarpool#removeCourseAllocation(org.openuss.seminarpool.CourseSeminarpoolAllocation)
     */
    public void removeCourseAllocation(org.openuss.seminarpool.CourseSeminarpoolAllocation courseAllocation)
    {
		Validate.notNull(courseAllocation, "CourseSeminarpoolAllocation cannot be null");
		if (getCourseSeminarpoolAllocation().remove(courseAllocation)) {
			if (courseAllocation.getSeminarpool().equals(this)) {
				courseAllocation.setSeminarpool(null);
			}
		}   
    }

}