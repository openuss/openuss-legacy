// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;

/**
 * @see org.openuss.calendar.Appointment
 */
public class AppointmentDaoImpl extends org.openuss.calendar.AppointmentDaoBase

{

	/**
	 * @see org.openuss.calendar.AppointmentDao#toAppointmentInfo(org.openuss.calendar.Appointment,
	 *      org.openuss.calendar.AppointmentInfo)
	 */
	public void toAppointmentInfo(
			org.openuss.calendar.Appointment sourceEntity,
			org.openuss.calendar.AppointmentInfo targetVO) {
	        super.toAppointmentInfo(sourceEntity, targetVO);
	        			
	        // create association between the value objects
	        
	        AppointmentTypeInfo appTypeInfo = new AppointmentTypeInfo();
	        appTypeInfo.setId(sourceEntity.getAppointmentType().getId());
	        appTypeInfo.setName(sourceEntity.getAppointmentType().getName());
	        targetVO.setAppointmentTypeInfo(appTypeInfo);
	        targetVO.setCalendarId(sourceEntity.getSourceCalendar().getId());
	        targetVO.setCalendarType(CalendarType.fromString(sourceEntity.getSourceCalendarType()));
	}

	/**
	 * @see org.openuss.calendar.AppointmentDao#toAppointmentInfo(org.openuss.calendar.Appointment)
	 */
	public org.openuss.calendar.AppointmentInfo toAppointmentInfo(
			final org.openuss.calendar.Appointment entity) {
		final AppointmentInfo target = new AppointmentInfo();
		this.toAppointmentInfo(entity, target);
		return target;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private org.openuss.calendar.Appointment loadAppointmentFromAppointmentInfo(
			org.openuss.calendar.AppointmentInfo appointmentInfo) {
		// @todo implement loadAppointmentFromAppointmentInfo

		org.openuss.calendar.Appointment appointment = this
				.load(appointmentInfo.getId());
		if (appointment == null) {
			appointment = org.openuss.calendar.Appointment.Factory
					.newInstance();
		}
		return appointment;

	}

	/**
	 * @see org.openuss.calendar.AppointmentDao#appointmentInfoToEntity(org.openuss.calendar.AppointmentInfo)
	 */
	public org.openuss.calendar.Appointment appointmentInfoToEntity(
			org.openuss.calendar.AppointmentInfo appointmentInfo) {
		// @todo verify behavior of appointmentInfoToEntity
		org.openuss.calendar.Appointment entity = this
				.loadAppointmentFromAppointmentInfo(appointmentInfo);
		this.appointmentInfoToEntity(appointmentInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.calendar.AppointmentDao#appointmentInfoToEntity(org.openuss.calendar.AppointmentInfo,
	 *      org.openuss.calendar.Appointment)
	 */
	public void appointmentInfoToEntity(
			org.openuss.calendar.AppointmentInfo sourceVO,
			org.openuss.calendar.Appointment targetEntity, boolean copyIfNull) {
		// @todo verify behavior of appointmentInfoToEntity
		super.appointmentInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}