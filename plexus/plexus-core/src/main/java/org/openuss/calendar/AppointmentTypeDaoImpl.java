// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;
/**
 * @see org.openuss.calendar.AppointmentType
 */
public class AppointmentTypeDaoImpl
    extends org.openuss.calendar.AppointmentTypeDaoBase
{
    /**
     * @see org.openuss.calendar.AppointmentTypeDao#toAppointmentTypeInfo(org.openuss.calendar.AppointmentType, org.openuss.calendar.AppointmentTypeInfo)
     */
    public void toAppointmentTypeInfo(
        org.openuss.calendar.AppointmentType sourceEntity,
        org.openuss.calendar.AppointmentTypeInfo targetVO)
    {
        // @todo verify behavior of toAppointmentTypeInfo
        super.toAppointmentTypeInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.calendar.AppointmentTypeDao#toAppointmentTypeInfo(org.openuss.calendar.AppointmentType)
     */
    public org.openuss.calendar.AppointmentTypeInfo toAppointmentTypeInfo(final org.openuss.calendar.AppointmentType entity)
    {
        // @todo verify behavior of toAppointmentTypeInfo
        return super.toAppointmentTypeInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.calendar.AppointmentType loadAppointmentTypeFromAppointmentTypeInfo(org.openuss.calendar.AppointmentTypeInfo appointmentTypeInfo)
    {
    	//todo check if this works
        org.openuss.calendar.AppointmentType appointmentType = this.load(appointmentTypeInfo.getId());
        if (appointmentType == null)
        {
            appointmentType = org.openuss.calendar.AppointmentType.Factory.newInstance();
        }
        return appointmentType;
    }

    
    /**
     * @see org.openuss.calendar.AppointmentTypeDao#appointmentTypeInfoToEntity(org.openuss.calendar.AppointmentTypeInfo)
     */
    public org.openuss.calendar.AppointmentType appointmentTypeInfoToEntity(org.openuss.calendar.AppointmentTypeInfo appointmentTypeInfo)
    {
        // @todo verify behavior of appointmentTypeInfoToEntity
        org.openuss.calendar.AppointmentType entity = this.loadAppointmentTypeFromAppointmentTypeInfo(appointmentTypeInfo);
        this.appointmentTypeInfoToEntity(appointmentTypeInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.calendar.AppointmentTypeDao#appointmentTypeInfoToEntity(org.openuss.calendar.AppointmentTypeInfo, org.openuss.calendar.AppointmentType)
     */
    public void appointmentTypeInfoToEntity(
        org.openuss.calendar.AppointmentTypeInfo sourceVO,
        org.openuss.calendar.AppointmentType targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of appointmentTypeInfoToEntity
        super.appointmentTypeInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}