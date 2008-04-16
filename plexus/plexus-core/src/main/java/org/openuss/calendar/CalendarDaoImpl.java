// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;
/**
 * @see org.openuss.calendar.Calendar
 */
public class CalendarDaoImpl
    extends org.openuss.calendar.CalendarDaoBase
{
    /**
     * @see org.openuss.calendar.CalendarDao#toCalendarInfo(org.openuss.calendar.Calendar, org.openuss.calendar.CalendarInfo)
     */
    public void toCalendarInfo(
        org.openuss.calendar.Calendar sourceEntity,
        org.openuss.calendar.CalendarInfo targetVO)
    {
        super.toCalendarInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.calendar.CalendarDao#toCalendarInfo(org.openuss.calendar.Calendar)
     */
    public org.openuss.calendar.CalendarInfo toCalendarInfo(final org.openuss.calendar.Calendar entity)
    {
        // @todo verify behavior of toCalendarInfo
        return super.toCalendarInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.calendar.Calendar loadCalendarFromCalendarInfo(org.openuss.calendar.CalendarInfo calendarInfo)
    {
        // @todo implement loadCalendarFromCalendarInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.loadCalendarFromCalendarInfo(org.openuss.calendar.CalendarInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.calendar.Calendar calendar = this.load(calendarInfo.getId());
        if (calendar == null)
        {
            calendar = org.openuss.calendar.Calendar.Factory.newInstance();
        }
        return calendar;
        */
    }

    
    /**
     * @see org.openuss.calendar.CalendarDao#calendarInfoToEntity(org.openuss.calendar.CalendarInfo)
     */
    public org.openuss.calendar.Calendar calendarInfoToEntity(org.openuss.calendar.CalendarInfo calendarInfo)
    {
        // @todo verify behavior of calendarInfoToEntity
        org.openuss.calendar.Calendar entity = this.loadCalendarFromCalendarInfo(calendarInfo);
        this.calendarInfoToEntity(calendarInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.calendar.CalendarDao#calendarInfoToEntity(org.openuss.calendar.CalendarInfo, org.openuss.calendar.Calendar)
     */
    public void calendarInfoToEntity(
        org.openuss.calendar.CalendarInfo sourceVO,
        org.openuss.calendar.Calendar targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of calendarInfoToEntity
        super.calendarInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}