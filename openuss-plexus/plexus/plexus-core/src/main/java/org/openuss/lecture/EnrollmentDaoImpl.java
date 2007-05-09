// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;
/**
 * @see org.openuss.lecture.Enrollment
 */
public class EnrollmentDaoImpl
    extends org.openuss.lecture.EnrollmentDaoBase
{
    /**
     * @see org.openuss.lecture.EnrollmentDao#toEnrollmentInfo(org.openuss.lecture.Enrollment, org.openuss.lecture.EnrollmentInfo)
     */
    public void toEnrollmentInfo(Enrollment sourceEntity,
        EnrollmentInfo targetVO)
    {
        super.toEnrollmentInfo(sourceEntity, targetVO);
        targetVO.setName(sourceEntity.getName());
    }


    /**
     * @see org.openuss.lecture.EnrollmentDao#toEnrollmentInfo(org.openuss.lecture.Enrollment)
     */
    public EnrollmentInfo toEnrollmentInfo(final Enrollment entity)
    {
    	EnrollmentInfo targetVO = new EnrollmentInfo();
        toEnrollmentInfo(entity, targetVO);
        return targetVO;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private Enrollment loadEnrollmentFromEnrollmentInfo(EnrollmentInfo enrollmentInfo)
    {
        Enrollment enrollment = this.load(enrollmentInfo.getId());
        if (enrollment == null)
        {
            enrollment = Enrollment.Factory.newInstance();
        }
        return enrollment;
    }

    
    /**
     * @see org.openuss.lecture.EnrollmentDao#enrollmentInfoToEntity(org.openuss.lecture.EnrollmentInfo)
     */
    public Enrollment enrollmentInfoToEntity(EnrollmentInfo enrollmentInfo)
    {
        Enrollment entity = this.loadEnrollmentFromEnrollmentInfo(enrollmentInfo);
        this.enrollmentInfoToEntity(enrollmentInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.lecture.EnrollmentDao#enrollmentInfoToEntity(org.openuss.lecture.EnrollmentInfo, org.openuss.lecture.Enrollment)
     */
    public void enrollmentInfoToEntity(
        org.openuss.lecture.EnrollmentInfo sourceVO,
        org.openuss.lecture.Enrollment targetEntity,
        boolean copyIfNull)
    {
        super.enrollmentInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}