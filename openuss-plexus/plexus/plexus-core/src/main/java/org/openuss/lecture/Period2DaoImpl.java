// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;
/**
 * @see org.openuss.lecture.Period2
 */
public class Period2DaoImpl
    extends org.openuss.lecture.Period2DaoBase
{
    /**
     * @see org.openuss.lecture.Period2Dao#toPeriodInfo(org.openuss.lecture.Period2, org.openuss.lecture.PeriodInfo)
     */
    public void toPeriodInfo(
        org.openuss.lecture.Period2 sourceEntity,
        org.openuss.lecture.PeriodInfo targetVO)
    {
        // @todo verify behavior of toPeriodInfo
        super.toPeriodInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.lecture.Period2Dao#toPeriodInfo(org.openuss.lecture.Period2)
     */
    public org.openuss.lecture.PeriodInfo toPeriodInfo(final org.openuss.lecture.Period2 entity)
    {
        // @todo verify behavior of toPeriodInfo
        return super.toPeriodInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.lecture.Period2 loadPeriod2FromPeriodInfo(org.openuss.lecture.PeriodInfo periodInfo)
    {
        // @todo implement loadPeriod2FromPeriodInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.lecture.loadPeriod2FromPeriodInfo(org.openuss.lecture.PeriodInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.lecture.Period2 period2 = this.load(periodInfo.getId());
        if (period2 == null)
        {
            period2 = org.openuss.lecture.Period2.Factory.newInstance();
        }
        return period2;
        */
    }

    
    /**
     * @see org.openuss.lecture.Period2Dao#periodInfoToEntity(org.openuss.lecture.PeriodInfo)
     */
    public org.openuss.lecture.Period2 periodInfoToEntity(org.openuss.lecture.PeriodInfo periodInfo)
    {
        // @todo verify behavior of periodInfoToEntity
        org.openuss.lecture.Period2 entity = this.loadPeriod2FromPeriodInfo(periodInfo);
        this.periodInfoToEntity(periodInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.lecture.Period2Dao#periodInfoToEntity(org.openuss.lecture.PeriodInfo, org.openuss.lecture.Period2)
     */
    public void periodInfoToEntity(
        org.openuss.lecture.PeriodInfo sourceVO,
        org.openuss.lecture.Period2 targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of periodInfoToEntity
        super.periodInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}