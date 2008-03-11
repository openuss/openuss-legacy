package org.openuss.web.calendar;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.schedule.model.DefaultScheduleEntry;
import org.apache.myfaces.custom.schedule.model.ScheduleModel;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.AppointmentInfo;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.calendar.CalendarInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;

/**
 * Backing Bean for the user calendar-overview page (usercalendar.xhtml)
 * 
 * @author Thomas Jansing
 * 
 */

@Bean(name = "views$secured$calendar$usercalendar", scope = Scope.REQUEST)
@View
public class UserCalendarMainPage extends AbstractCalendarPage {

	private static final Logger logger = Logger
			.getLogger(UserCalendarMainPage.class);

	@Prerender
	public void prerender() throws Exception {
		
		super.prerender();
		logger.debug("Starting prerender of usercalendar");
		breadcrumbs.loadOpenuss4usCrumbs();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink(contextPath()
				+ "/views/secured/calendar/usercalendar.faces");
		newCrumb.setName(i18n("openuss4us_command_calendar"));
		newCrumb.setHint(i18n("openuss4us_command_calendar"));
		breadcrumbs.addCrumb(newCrumb);
		CalendarInfo calInfo = calendarService.getCalendar(user);
		setSessionBean(Constants.CALENDAR_INFO, calInfo);
		if (calInfo.getId() != null) {
			logger.debug("calendarInfo loaded with calendarInfo-ID:"
					+ calInfo.getId());
		}
		if (calInfo.getId() == null) {
			calendarService.createCalendar(user);
			logger
					.debug("new calendar & calendarInfo created for user with calendarInfo-ID:"
							+ calInfo.getId());
		}

		// Load entries for the user calendar schedule model
		loadEntries(calInfo);
		logger.debug("model entries loaded");
		// Refresh the model for correct view
		model.refresh();
	}

	public void loadEntries(CalendarInfo calInfo) {
		if (model == null)
			return;
		try {
			this.calendarInfo = calInfo;
			logger.debug("Loading entries for calendarInfo-ID:"
					+ calendarInfo.getId());
			List<AppointmentInfo> apps = null;
			apps = calendarService.getSingleAppointments(calendarInfo);
			logger.debug("Appointments to add: " + apps.size());
	
			for (AppointmentInfo app : apps) {
				// check if appointment takes place
				if (app.isTakingPlace()) {
					// adds the appointment entry to the user calendar schedule model
					DefaultScheduleEntry entry1 = new DefaultScheduleEntry();
					entry1.setId(app.getId().toString());
					entry1.setTitle(app.getSubject());
					entry1.setStartTime(app.getStarttime());
					entry1.setEndTime(app.getEndtime());
					entry1.setDescription(app.getDescription());
					entry1.setSubtitle(app.getDescription());
					model.addEntry(entry1);
					logger.debug("Appointment: - " + app.getSubject()
							+ " - added!");
				} else {
					logger.debug("Appointment: " + app.getSubject()
							+ " at Date " + app.getStarttime().toGMTString()
							+ " is not taking place -> ignored");
				}
			}
		} catch (CalendarApplicationException e) {
			addError("TODO: Error loading model entries");
			logger.debug("Error loading model entries");
		}
		model.refresh();
	}

	public String changeToDayMode() {
		if (model == null)
			addError(i18n("calendar_mode_change_error"));
		model.setMode(0);
		return Constants.OPENUSS4US_CALENDAR;
	}

	public String changeToWorkWeekMode() {
		if (model == null){
			addError(i18n("calendar_mode_change_error"));
		}
		model.setMode(1);
		return Constants.OPENUSS4US_CALENDAR;
	}

	public String changeToWeekMode() {
		if (model == null){
			addError(i18n("calendar_mode_change_error"));
		}
		model.setMode(2);
		return Constants.OPENUSS4US_CALENDAR;
	}

	public String changeToMonthMode() {
		if (model == null){
			addError(i18n("calendar_mode_change_error"));
		}
		model.setMode(3);
		return Constants.OPENUSS4US_CALENDAR;
	}

	public ScheduleModel getModel() {
		return model;
	}

	public void setModel(ScheduleModel model) {
		this.model = model;
	}

}