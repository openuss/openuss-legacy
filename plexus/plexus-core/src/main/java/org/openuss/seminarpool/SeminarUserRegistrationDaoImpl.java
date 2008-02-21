// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.seminarpool;
/**
 * @see org.openuss.seminarpool.SeminarUserRegistration
 */
public class SeminarUserRegistrationDaoImpl
    extends org.openuss.seminarpool.SeminarUserRegistrationDaoBase
{
    /**
     * @see org.openuss.seminarpool.SeminarUserRegistrationDao#toSeminarUserRegistrationInfo(org.openuss.seminarpool.SeminarUserRegistration, org.openuss.seminarpool.SeminarUserRegistrationInfo)
     */
    public void toSeminarUserRegistrationInfo(
        org.openuss.seminarpool.SeminarUserRegistration sourceEntity,
        org.openuss.seminarpool.SeminarUserRegistrationInfo targetVO)
    {
        // @todo verify behavior of toSeminarUserRegistrationInfo
        super.toSeminarUserRegistrationInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.seminarpool.SeminarUserRegistrationDao#toSeminarUserRegistrationInfo(org.openuss.seminarpool.SeminarUserRegistration)
     */
    public org.openuss.seminarpool.SeminarUserRegistrationInfo toSeminarUserRegistrationInfo(final org.openuss.seminarpool.SeminarUserRegistration entity)
    {
        // @todo verify behavior of toSeminarUserRegistrationInfo
        return super.toSeminarUserRegistrationInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.seminarpool.SeminarUserRegistration loadSeminarUserRegistrationFromSeminarUserRegistrationInfo(org.openuss.seminarpool.SeminarUserRegistrationInfo seminarUserRegistrationInfo)
    {
        // @todo implement loadSeminarUserRegistrationFromSeminarUserRegistrationInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.seminarpool.loadSeminarUserRegistrationFromSeminarUserRegistrationInfo(org.openuss.seminarpool.SeminarUserRegistrationInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.seminarpool.SeminarUserRegistration seminarUserRegistration = this.load(seminarUserRegistrationInfo.getId());
        if (seminarUserRegistration == null)
        {
            seminarUserRegistration = org.openuss.seminarpool.SeminarUserRegistration.Factory.newInstance();
        }
        return seminarUserRegistration;
        */
    }

    
    /**
     * @see org.openuss.seminarpool.SeminarUserRegistrationDao#seminarUserRegistrationInfoToEntity(org.openuss.seminarpool.SeminarUserRegistrationInfo)
     */
    public org.openuss.seminarpool.SeminarUserRegistration seminarUserRegistrationInfoToEntity(org.openuss.seminarpool.SeminarUserRegistrationInfo seminarUserRegistrationInfo)
    {
        // @todo verify behavior of seminarUserRegistrationInfoToEntity
        org.openuss.seminarpool.SeminarUserRegistration entity = this.loadSeminarUserRegistrationFromSeminarUserRegistrationInfo(seminarUserRegistrationInfo);
        this.seminarUserRegistrationInfoToEntity(seminarUserRegistrationInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.seminarpool.SeminarUserRegistrationDao#seminarUserRegistrationInfoToEntity(org.openuss.seminarpool.SeminarUserRegistrationInfo, org.openuss.seminarpool.SeminarUserRegistration)
     */
    public void seminarUserRegistrationInfoToEntity(
        org.openuss.seminarpool.SeminarUserRegistrationInfo sourceVO,
        org.openuss.seminarpool.SeminarUserRegistration targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of seminarUserRegistrationInfoToEntity
        super.seminarUserRegistrationInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}