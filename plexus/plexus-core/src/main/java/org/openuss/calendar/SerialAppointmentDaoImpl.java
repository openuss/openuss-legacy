// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;
/**
 * @see org.openuss.calendar.SerialAppointment
 */
public class SerialAppointmentDaoImpl
    extends org.openuss.calendar.SerialAppointmentDaoBase
{
    /**
     * @see org.openuss.calendar.SerialAppointmentDao#toSerialAppointmentInfo(org.openuss.calendar.SerialAppointment, org.openuss.calendar.SerialAppointmentInfo)
     */
    public void toSerialAppointmentInfo(
        org.openuss.calendar.SerialAppointment sourceEntity,
        org.openuss.calendar.SerialAppointmentInfo targetVO)
    {
        // @todo verify behavior of toSerialAppointmentInfo
        super.toSerialAppointmentInfo(sourceEntity, targetVO);
        // WARNING! No conversion for targetVO.creator (can't convert sourceEntity.getCreator():org.openuss.security.User to org.openuss.security.UserInfo
        // WARNING! No conversion for targetVO.scheduleExceptions (can't convert sourceEntity.getScheduleExceptions():org.openuss.calendar.ScheduleException to java.util.List
    }


    /**
     * @see org.openuss.calendar.SerialAppointmentDao#toSerialAppointmentInfo(org.openuss.calendar.SerialAppointment)
     */
    public org.openuss.calendar.SerialAppointmentInfo toSerialAppointmentInfo(final org.openuss.calendar.SerialAppointment entity)
    {
        // @todo verify behavior of toSerialAppointmentInfo
        return super.toSerialAppointmentInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.calendar.SerialAppointment loadSerialAppointmentFromSerialAppointmentInfo(org.openuss.calendar.SerialAppointmentInfo serialAppointmentInfo)
    {
        // @todo implement loadSerialAppointmentFromSerialAppointmentInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.loadSerialAppointmentFromSerialAppointmentInfo(org.openuss.calendar.SerialAppointmentInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.calendar.SerialAppointment serialAppointment = this.load(serialAppointmentInfo.getId());
        if (serialAppointment == null)
        {
            serialAppointment = org.openuss.calendar.SerialAppointment.Factory.newInstance();
        }
        return serialAppointment;
        */
    }

    
    /**
     * @see org.openuss.calendar.SerialAppointmentDao#serialAppointmentInfoToEntity(org.openuss.calendar.SerialAppointmentInfo)
     */
    public org.openuss.calendar.SerialAppointment serialAppointmentInfoToEntity(org.openuss.calendar.SerialAppointmentInfo serialAppointmentInfo)
    {
        // @todo verify behavior of serialAppointmentInfoToEntity
        org.openuss.calendar.SerialAppointment entity = this.loadSerialAppointmentFromSerialAppointmentInfo(serialAppointmentInfo);
        this.serialAppointmentInfoToEntity(serialAppointmentInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.calendar.SerialAppointmentDao#serialAppointmentInfoToEntity(org.openuss.calendar.SerialAppointmentInfo, org.openuss.calendar.SerialAppointment)
     */
    public void serialAppointmentInfoToEntity(
        org.openuss.calendar.SerialAppointmentInfo sourceVO,
        org.openuss.calendar.SerialAppointment targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of serialAppointmentInfoToEntity
        super.serialAppointmentInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

    /**
     * @see org.openuss.calendar.SerialAppointmentDao#toAppointmentInfo(org.openuss.calendar.SerialAppointment, org.openuss.calendar.AppointmentInfo)
     */
    public void toAppointmentInfo(
        org.openuss.calendar.SerialAppointment sourceEntity,
        org.openuss.calendar.AppointmentInfo targetVO)
    {
        // @todo verify behavior of toAppointmentInfo
        super.toAppointmentInfo(sourceEntity, targetVO);
        // WARNING! No conversion for targetVO.creator (can't convert sourceEntity.getCreator():org.openuss.security.User to org.openuss.security.UserInfo
        // WARNING! No conversion for targetVO.appointmentType (can't convert sourceEntity.getAppointmentType():org.openuss.calendar.AppointmentType to org.openuss.calendar.AppointmentTypeInfo
    }


    /**
     * @see org.openuss.calendar.SerialAppointmentDao#toAppointmentInfo(org.openuss.calendar.SerialAppointment)
     */
    public org.openuss.calendar.AppointmentInfo toAppointmentInfo(final org.openuss.calendar.SerialAppointment entity)
    {
        // @todo verify behavior of toAppointmentInfo
        return super.toAppointmentInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.calendar.SerialAppointment loadSerialAppointmentFromAppointmentInfo(org.openuss.calendar.AppointmentInfo appointmentInfo)
    {
        // @todo implement loadSerialAppointmentFromAppointmentInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.calendar.loadSerialAppointmentFromAppointmentInfo(org.openuss.calendar.AppointmentInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.calendar.SerialAppointment serialAppointment = this.load(appointmentInfo.getId());
        if (serialAppointment == null)
        {
            serialAppointment = org.openuss.calendar.SerialAppointment.Factory.newInstance();
        }
        return serialAppointment;
        */
    }

    
    /**
     * @see org.openuss.calendar.SerialAppointmentDao#appointmentInfoToEntity(org.openuss.calendar.AppointmentInfo)
     */
    public org.openuss.calendar.SerialAppointment appointmentInfoToEntity(org.openuss.calendar.AppointmentInfo appointmentInfo)
    {
        // @todo verify behavior of appointmentInfoToEntity
        org.openuss.calendar.SerialAppointment entity = this.loadSerialAppointmentFromAppointmentInfo(appointmentInfo);
        this.appointmentInfoToEntity(appointmentInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.calendar.SerialAppointmentDao#appointmentInfoToEntity(org.openuss.calendar.AppointmentInfo, org.openuss.calendar.SerialAppointment)
     */
    public void appointmentInfoToEntity(
        org.openuss.calendar.AppointmentInfo sourceVO,
        org.openuss.calendar.SerialAppointment targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of appointmentInfoToEntity
        super.appointmentInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}