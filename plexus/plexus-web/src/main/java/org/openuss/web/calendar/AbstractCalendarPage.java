package org.openuss.web.calendar;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.calendar.AppointmentInfo;
import org.openuss.calendar.CalendarInfo;
import org.openuss.calendar.CalendarService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * AbstractCalendarPage
 * @author Thomas Jansing
 *
 */

public class AbstractCalendarPage extends BasePage {
	
	@Property(value = "#{" + Constants.CALENDAR_INFO + "}")
	protected CalendarInfo calendarInfo;

	@Property(value = "#{" + Constants.APPOINTMENT_INFO + "}")
	protected AppointmentInfo appointmentInfo;
	
	@Property(value = "#{calendarService}")
	protected CalendarService calendarService;	
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		// TODO Thomas: check calendarInfo
//		if (calendarInfo == null || calendarInfo.getId() == null) {
//			addError(i18n("TODO: Calendar not found!"));
//			redirect(Constants.OUTCOME_BACKWARD);
//			return; }
		
	}

	public CalendarInfo getCalendarInfo() {
		return calendarInfo;
	}

	public void setCalendarInfo(CalendarInfo calendarInfo) {
		this.calendarInfo = calendarInfo;
	}

	public AppointmentInfo getAppointmentInfo() {
		return appointmentInfo;
	}

	public void setAppointmentInfo(AppointmentInfo appointmentInfo) {
		this.appointmentInfo = appointmentInfo;
	}

	public CalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}
	
	
	
}
