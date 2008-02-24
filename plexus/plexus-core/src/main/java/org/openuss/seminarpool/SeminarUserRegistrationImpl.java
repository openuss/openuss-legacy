// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;

import java.util.HashSet;
import java.util.Set;

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
    	if ( getSeminarPriority() != null ) {
    		getSeminarPriority().add(priority);
    	} else {
    		Set<org.openuss.seminarpool.SeminarPriority> set = new HashSet<org.openuss.seminarpool.SeminarPriority>();
    		set.add(priority);
    	}
    }

    /**
     * @see org.openuss.seminarpool.SeminarUserRegistration#removePriority(org.openuss.seminarpool.SeminarPriority)
     */
    public void removePriority(SeminarPriority priority)
    {
    	if ( getSeminarPriority() != null ) {
    		getSeminarPriority().remove(priority);
    	} 
    }

	@Override
	public void addUserCondition(
			SeminarUserConditionValue seminarUserConditionValue) {
		if ( getSeminarUserConditionValue() != null ) {
			getSeminarUserConditionValue().add(seminarUserConditionValue);
		} else {
			Set<SeminarUserConditionValue> set = new HashSet<SeminarUserConditionValue>();
			set.add(seminarUserConditionValue);
		}
	}

	@Override
	public void removeUserCondition(
			SeminarUserConditionValue seminarUserConditionValue) {
		if ( getSeminarUserConditionValue() != null ) {
			getSeminarUserConditionValue().remove(seminarUserConditionValue);
		}
	}

}