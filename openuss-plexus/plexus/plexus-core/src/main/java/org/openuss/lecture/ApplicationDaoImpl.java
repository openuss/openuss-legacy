// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;
/**
 * @see org.openuss.lecture.Application
 */
public class ApplicationDaoImpl
    extends org.openuss.lecture.ApplicationDaoBase
{
    /**
     * @see org.openuss.lecture.ApplicationDao#toApplicationInfo(org.openuss.lecture.Application, org.openuss.lecture.ApplicationInfo)
     */
    public void toApplicationInfo(
        org.openuss.lecture.Application sourceEntity,
        org.openuss.lecture.ApplicationInfo targetVO)
    {
        // @todo verify behavior of toApplicationInfo
        super.toApplicationInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.lecture.ApplicationDao#toApplicationInfo(org.openuss.lecture.Application)
     */
    public org.openuss.lecture.ApplicationInfo toApplicationInfo(final org.openuss.lecture.Application entity)
    {
        // @todo verify behavior of toApplicationInfo
        return super.toApplicationInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.lecture.Application loadApplicationFromApplicationInfo(org.openuss.lecture.ApplicationInfo applicationInfo)
    {
        // @todo implement loadApplicationFromApplicationInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.lecture.loadApplicationFromApplicationInfo(org.openuss.lecture.ApplicationInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.lecture.Application application = this.load(applicationInfo.getId());
        if (application == null)
        {
            application = org.openuss.lecture.Application.Factory.newInstance();
        }
        return application;
        */
    }

    
    /**
     * @see org.openuss.lecture.ApplicationDao#applicationInfoToEntity(org.openuss.lecture.ApplicationInfo)
     */
    public org.openuss.lecture.Application applicationInfoToEntity(org.openuss.lecture.ApplicationInfo applicationInfo)
    {
        // @todo verify behavior of applicationInfoToEntity
        org.openuss.lecture.Application entity = this.loadApplicationFromApplicationInfo(applicationInfo);
        this.applicationInfoToEntity(applicationInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.lecture.ApplicationDao#applicationInfoToEntity(org.openuss.lecture.ApplicationInfo, org.openuss.lecture.Application)
     */
    public void applicationInfoToEntity(
        org.openuss.lecture.ApplicationInfo sourceVO,
        org.openuss.lecture.Application targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of applicationInfoToEntity
        super.applicationInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}