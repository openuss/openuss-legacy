package org.openuss.web.calendar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import org.openuss.internationalisation.TranslationService;
import org.openuss.web.Constants;

@Bean(name = "views$secured$calendar$createsingleappointment", scope = Scope.REQUEST)
@View
public class SingleAppointmentCreatePage extends AbstractCalendarPage{
	
	private static final Logger logger = Logger.getLogger(SingleAppointmentCreatePage.class);
	
	private Integer appointmentType;
	
	@Property(value = "#{translationService}")
	private TranslationService translationService;
	
	private List<AppointmentTypeInfo> appointmentTypes;
	
	/* ----- business logic ----- */

	@Override
	@Prerender
	public void prerender() throws Exception{
		super.prerender();
		// Breadcrumbs
		breadcrumbs.loadAppointmentCrumbs();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("calender_create_single_appointment_page"));
		newCrumb.setHint(i18n("calender_create_single_appointment_page"));
		breadcrumbs.addCrumb(newCrumb);

		// Create new AppointmentInfo Object - Load CalendarInfo Object
		AppointmentInfo appointmentInfo = new AppointmentInfo();
		setSessionBean(Constants.APPOINTMENT_INFO, appointmentInfo);	
	}
	
	public String save(){
		try {	
		AppointmentTypeInfo appTI = new AppointmentTypeInfo();
		appTI.setId(appointmentType.longValue());
		appointmentInfo.setAppointmentTypeInfo(appTI);
		appointmentInfo.setSerial(false);
		appointmentInfo.setCalendarType(calendarInfo.getCalendarType());
		

			calendarService.createAppointment(appointmentInfo, calendarInfo);
		} catch (CalendarApplicationException e) {
			// TODO Auto-generated catch block
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
				String name = translationService.getTranslation(appType.getId(), appType.getName(), user.getLocale());
				items.add(new SelectItem(appType.getId().intValue(), name));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.addError("Error");
			return null;
		}
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

	public TranslationService getTranslationService() {
		return translationService;
	}

	public void setTranslationService(TranslationService translationService) {
		this.translationService = translationService;
	}

}