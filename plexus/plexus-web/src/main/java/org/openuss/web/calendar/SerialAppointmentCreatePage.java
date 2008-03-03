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
import org.openuss.calendar.AppointmentInfo;
import org.openuss.calendar.AppointmentTypeInfo;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.calendar.CalendarInfo;
import org.openuss.calendar.CalendarService;
import org.openuss.calendar.CalendarType;
import org.openuss.calendar.RecurrenceType;
import org.openuss.calendar.SerialAppointmentInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;

/**
 * Backing Bean for creating serial appointments (createserialappointment.xhtml)
 * 
 * @author Ralf Plattfaut
 * @author Robin Trenkner
 */

@Bean(name = "views$secured$calendar$createserialappointment", scope = Scope.REQUEST)
@View
public class SerialAppointmentCreatePage extends AbstractCalendarPage {

	private static final Logger logger = Logger
			.getLogger(SingleAppointmentCreatePage.class);

	private Integer appointmentType;

	private List<AppointmentTypeInfo> appointmentTypes;

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

		// Create new AppointmentInfo Object - Load CalendarInfo Object
		AppointmentInfo appointmentInfo = new SerialAppointmentInfo();
		((SerialAppointmentInfo)appointmentInfo).setRecurrencePeriod(1);
		setRecurrenceType(RecurrenceType.weekly.getValue());
		setSessionBean(Constants.APPOINTMENT_INFO, appointmentInfo);
	}

	public String save() {
		calendarInfo.setCalendarType(CalendarType.user_calendar);
		try {
			AppointmentTypeInfo appTI = new AppointmentTypeInfo();
			appTI.setId(appointmentType.longValue());
			appointmentInfo.setAppointmentTypeInfo(appTI);
			appointmentInfo.setSerial(true);
			appointmentInfo.setCalendarType(calendarInfo.getCalendarType());
			((SerialAppointmentInfo)(appointmentInfo)).setRecurrenceType(RecurrenceType.fromString(recurrenceType));
			calendarService.createSerialAppointment((SerialAppointmentInfo)appointmentInfo, calendarInfo);
		} catch (CalendarApplicationException e) {
			// TODO Auto-generated catch block
			addError("Das Anlegen eines Appointments schlug fehl");
		}
		//TODO correct navigation outcome
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
