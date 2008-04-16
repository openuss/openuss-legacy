// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.calendar;


/**
 * JUnit Test for Spring Hibernate AppointmentDao class.
 * @see org.openuss.calendar.AppointmentDao
 */
public class AppointmentDaoTest extends AppointmentDaoTestBase {
	
	public void testAppointmentDaoCreate() {
		Appointment appointment = Appointment.Factory.newInstance();
		appointment.setStarttime(null);
		appointment.setEndtime(null);
		appointment.setSubject(" ");
		appointment.setDescription(" ");
		appointment.setLocation(" ");
		appointment.setSerial(false);
		appointment.setTakingPlace(true);
		assertNull(appointment.getId());
		appointmentDao.create(appointment);
		assertNotNull(appointment.getId());
	}
}
