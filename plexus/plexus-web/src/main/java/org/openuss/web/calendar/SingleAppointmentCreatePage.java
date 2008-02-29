package org.openuss.web.calendar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.AppointmentInfo;
import org.openuss.calendar.AppointmentTypeInfo;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.calendar.CalendarInfo;
import org.openuss.calendar.CalendarService;
import org.openuss.calendar.CalendarType;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

@Bean(name = "views$secured$calendar$createsingleappointment", scope = Scope.REQUEST)
@View
public class SingleAppointmentCreatePage extends BasePage{
	
	@Property(value = "#{" + Constants.OPENUSS4US_CALENDAR + "}")
	private CalendarInfo calendarInfo;
	@Property(value = "#{" + Constants.APPOINTMENT_INFO + "}")
	private AppointmentInfo appointmentInfo;
	@Property(value = "#{calendarService}")
	private CalendarService calendarService;

	private Integer appointmentType;
	
	private List<AppointmentTypeInfo> appointmentTypes;
	
	/* ----- business logic ----- */

	@Override
	@Prerender
	public void prerender() throws Exception{
		super.prerender();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("calender_create_single_appointment_page"));
		newCrumb.setHint(i18n("calender_create_single_appointment_page"));
		breadcrumbs.addCrumb(newCrumb);
		
		AppointmentInfo appointmentInfo = new AppointmentInfo();
		setSessionBean(Constants.APPOINTMENT_INFO, appointmentInfo);
		
		CalendarInfo calendarInfo = new CalendarInfo();
		setSessionBean(Constants.OPENUSS4US_CALENDAR, calendarInfo);
		
		
		
	}
	
	public String save(){

			appointmentInfo.setAppointmentTypeInfo(appointmentTypes.get(appointmentType));
			appointmentInfo.setSerial(false);
			appointmentInfo.setCalendarType(calendarInfo.getCalendarType());
			calendarInfo.setCalendarType(CalendarType.user_calendar);
			
			try {
				calendarService.createAppointment(appointmentInfo, calendarInfo);
			} catch (CalendarApplicationException e) {
				// TODO Auto-generated catch block
				addError("Das Anlegen eines Appointments macht fehler");
			}

		return Constants.CALENDAR_HOME;
	}
	
	public List<SelectItem> getAppointTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(0, "Standard 1"));
		items.add(new SelectItem(1, "Standard 2"));
		items.add(new SelectItem(2, "Standard 3"));
		return items;
	}

	public void processAppointmentTypeChanged(ValueChangeEvent event) {
		Object accessTypeGroup = event.getNewValue();
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
}
