package org.openuss.web.calendar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
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
import org.openuss.security.UserInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.groups.GroupsMainPage;

@Bean(name = "views$secured$calendar$createsingleappointment", scope = Scope.REQUEST)
@View
public class SingleAppointmentCreatePage extends AbstractCalendarPage{
	
	private static final Logger logger = Logger.getLogger(SingleAppointmentCreatePage.class);
	
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

		// Create new AppointmentInfo Object - Load CalendarInfo Object
		AppointmentInfo appointmentInfo = new AppointmentInfo();
		CalendarInfo calendarInfo = calendarService.getCalendar(user);
		
		setSessionBean(Constants.CALENDAR_INFO, calendarInfo);	
		setSessionBean(Constants.APPOINTMENT_INFO, appointmentInfo);	
	}
	
	public String save(){
		
		calendarInfo.setCalendarType(CalendarType.user_calendar);
		
		// appointmentInfo.setAppointmentTypeInfo(appointmentTypes.get(appointmentType));
		appointmentInfo.setSerial(false);
		AppointmentTypeInfo appTInfo = new AppointmentTypeInfo();
		appTInfo.setId(1l);
		appointmentInfo.setAppointmentTypeInfo(appTInfo);
		appointmentInfo.setCalendarType(calendarInfo.getCalendarType());
		
		try {
			calendarService.createAppointment(appointmentInfo, calendarInfo);
		} catch (CalendarApplicationException e) {
			// TODO Auto-generated catch block
			addError("Das Anlegen eines Appointments schlug fehl");
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
