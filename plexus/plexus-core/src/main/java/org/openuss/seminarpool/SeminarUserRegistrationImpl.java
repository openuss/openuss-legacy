// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.seminarpool.SeminarUserRegistration
 */
public class SeminarUserRegistrationImpl
    extends org.openuss.seminarpool.SeminarUserRegistrationBase
	implements org.openuss.seminarpool.SeminarUserRegistration
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 4471176754230702098L;

    /**
     * @see org.openuss.seminarpool.SeminarUserRegistration#addPriority(org.openuss.seminarpool.SeminarPriority)
     */
    public void addPriority(SeminarPriority priority)
    {
    	Validate.notNull(priority, "SeminarPriority cannot be null");
    	if ( !getSeminarPriority().contains(priority) ) {
    		getSeminarPriority().add(priority);
    	}
    	priority.setSeminarUserRegistration(this);
    }

    /**
     * @see org.openuss.seminarpool.SeminarUserRegistration#removePriority(org.openuss.seminarpool.SeminarPriority)
     */
    public void removePriority(SeminarPriority priority)
    {
		Validate.notNull(priority, "SeminarPriority cannot be null");

		if (getSeminarPriority().remove(priority) && priority.getSeminarUserRegistration().equals(this)) {
			priority.setSeminarUserRegistration(null);
		}
		if (priority.getCourseSeminarPoolAllocation().getSeminarPriority().remove(priority) ){
			priority.setCourseSeminarPoolAllocation(null);
		}
    }

	@Override
	public void addUserCondition(
			SeminarUserConditionValue seminarUserConditionValue) {
    	Validate.notNull(seminarUserConditionValue, "SeminarUserConditionValue cannot be null");
    	if ( !getSeminarUserConditionValue().contains(seminarUserConditionValue) ) {
    		getSeminarUserConditionValue().add(seminarUserConditionValue);
    	}
    	seminarUserConditionValue.setSeminarUserRegistration(this);
	}

	@Override
	public void removeUserCondition(
			SeminarUserConditionValue seminarUserConditionValue) {
		Validate.notNull(seminarUserConditionValue, "SeminarPriority cannot be null");
		if (getSeminarUserConditionValue().remove(seminarUserConditionValue) && seminarUserConditionValue.getSeminarUserRegistration().equals(this)) {
				seminarUserConditionValue.setSeminarUserRegistration(null);
		}
	}

}