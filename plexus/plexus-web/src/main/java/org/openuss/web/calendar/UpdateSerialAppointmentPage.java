package org.openuss.web.calendar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.AppointmentTypeInfo;
import org.openuss.calendar.CalculatedAppointmentException;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.calendar.CalendarType;
import org.openuss.calendar.RecurrenceType;
import org.openuss.calendar.SerialAppointmentInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;

/**
 * Backing Bean for updating serial appointments (updateserialappointment.xhtml)
 * 
 * @author Ralf Plattfaut
 */

@Bean(name = "views$secured$calendar$updateserialappointment", scope = Scope.REQUEST)
@View
public class UpdateSerialAppointmentPage extends AbstractCalendarPage {

	private static final Logger logger = Logger
			.getLogger(SingleAppointmentCreatePage.class);

	private Integer appointmentType;

	private String recurrenceType;

	/* ----- business logic ----- */

	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("calender_create_serial_appointment_page"));
		newCrumb.setHint(i18n("calender_create_serial_appointment_page"));
		breadcrumbs.addCrumb(newCrumb);
		
		// remove the old entries from the user calendar schedule model
		logger.debug("Removing old appointmentInfo entry:" + appointmentInfo.getId());
		removeSerialModelEntries(appointmentInfo);
		
		//initialize
		setAppointmentType(appointmentInfo.getAppointmentTypeInfo().getId().intValue());
	}

	public String save() {
		try {

			AppointmentTypeInfo appTI = new AppointmentTypeInfo();
			appTI.setId(appointmentType.longValue());
			appointmentInfo.setAppointmentTypeInfo(appTI);
			appointmentInfo.setSerial(true);
			((SerialAppointmentInfo)(appointmentInfo)).setRecurrenceType(RecurrenceType.fromString(recurrenceType));
			calendarService.updateSerialAppointment((SerialAppointmentInfo)appointmentInfo, calendarInfo);
		} catch (CalculatedAppointmentException e) {
			logger.error(e.getMessage());
			addError("Ein Serientermin darf nicht mehr als 500 Einzeltermine umfassen. Es wurden die ersten 500 Einzeltermine angelegt.");
		} catch (CalendarApplicationException e) {
			logger.error(e.getMessage());
			addError("Das Anlegen eines Appointments schlug fehl");
		}
		if (calendarInfo.getCalendarType().equals(CalendarType.course_calendar)) {
			// course calendar
			return Constants.COURSE_CALENDAR;
		}
		if (calendarInfo.getCalendarType().equals(CalendarType.group_calendar)) {
			// group calendar
			return Constants.GROUP_CALENDAR;
		}
		return Constants.CALENDAR_HOME;
	}

	public List<SelectItem> getAppointTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		
		try {
			for(AppointmentTypeInfo appType : (Collection<AppointmentTypeInfo>)calendarService.getAllAppointmentTypes()){
				items.add(new SelectItem(appType.getId().intValue(), appType.getName()));
			}
		} catch (CalendarApplicationException e) {
			this.addError("Error");
			return null;
		}
		return items;
	}
	
	public List<SelectItem> getRecTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(RecurrenceType.daily.getValue(),
				i18n("openuss4us_calendar_every_day")));
		items.add(new SelectItem(RecurrenceType.weekly.getValue(),
				i18n("openuss4us_calendar_every_week")));
		items.add(new SelectItem(RecurrenceType.monthly.getValue(),
				i18n("openuss4us_calendar_every_month")));
		items.add(new SelectItem(RecurrenceType.yearly.getValue(),
				i18n("openuss4us_calendar_every_year")));
		return items;
	}

	/* ----- getter and setter ----- */

	public Integer getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(Integer appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getRecurrenceType() {
		return recurrenceType;
	}

	public void setRecurrenceType(String recurrenceType) {
		this.recurrenceType = recurrenceType;
	}
}
