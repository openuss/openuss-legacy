package org.openuss.web.calendar;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.schedule.model.DefaultScheduleEntry;
import org.apache.myfaces.custom.schedule.model.ScheduleModel;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.calendar.AppointmentInfo;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.calendar.CalendarInfo;
import org.openuss.calendar.CalendarService;
import org.openuss.calendar.SerialAppointmentInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract Calendar Page
 * @author Thomas Jansing
 *
 */

public class AbstractCalendarPage extends BasePage {
	
	private static final Logger logger = Logger
	.getLogger(AbstractCalendarPage.class);
	
	@Property(value = "#{" + Constants.CALENDAR_INFO + "}")
	protected CalendarInfo calendarInfo;

	@Property(value = "#{" + Constants.APPOINTMENT_INFO + "}")
	protected AppointmentInfo appointmentInfo;
	
	@Property(value = "#{calendarService}")
	protected CalendarService calendarService;	
	
	@Property(value = "#{scheduleHandler.model}")
	protected ScheduleModel model;
	
	@Prerender
	public void prerender() throws Exception { // NOPMD by devopenuss on 11.03.08 16:13
		if (calendarInfo == null || calendarInfo.getId() == null) {
			calendarInfo = calendarService.getCalendar(user);
		}
		
	}
	
	public void removeSubscribedModelEntries(CalendarInfo calInfo) {
		try {
			List<AppointmentInfo> apps = calendarService.getSingleAppointments(calInfo);
			for (AppointmentInfo app : apps) {
				removeSingleModelEntry(app);
			}
		} catch (CalendarApplicationException e) {
			addError(i18n("TODO: Error removing model entries from subscribed calendar"));
		}
	}
	
	public void removeSerialModelEntries(AppointmentInfo appInfo) {
		try {
			List<AppointmentInfo> apps = calendarService.getCalculatedAppointments((SerialAppointmentInfo) appInfo);
			for (AppointmentInfo app : apps) {
				removeSingleModelEntry(app);
			}
		} catch (CalendarApplicationException e) {
			addError(i18n("TODO: Error removing serial model entries"));
		}
	}
	
	public void removeSingleModelEntry(AppointmentInfo appInfo) {
		DefaultScheduleEntry entry1 = new DefaultScheduleEntry();
		entry1.setId(appInfo.getId().toString());
		entry1.setTitle(appInfo.getSubject());
		entry1.setStartTime(appInfo.getStarttime());
		entry1.setEndTime(appInfo.getEndtime());
		entry1.setDescription(appInfo.getDescription());
		entry1.setSubtitle(appInfo.getDescription());
		logger.debug("Removing entry: " + entry1.getId() + " from the schedule model");
		model.removeEntry(entry1);
		model.refresh();
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
	
	public ScheduleModel getModel() {
		return model;
	}

	public void setModel(ScheduleModel model) {
		this.model = model;
	}

	
}
