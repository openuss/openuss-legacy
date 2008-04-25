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
import org.openuss.calendar.AppointmentTypeInfo;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.calendar.CalendarType;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.internationalisation.TranslationService;
import org.openuss.web.Constants;

/**
 * Backing Bean for updating single appointments (updatesingleappointment.xhtml)
 * 
 * @author Ralf Plattfaut
 */

@Bean(name = "views$secured$calendar$updatesingleappointment", scope = Scope.REQUEST)
@View
public class UpdateSingleAppointmentPage extends AbstractCalendarPage{
	
	private static final Logger logger = Logger.getLogger(UpdateSingleAppointmentPage.class);
	
	private Integer appointmentType;
	
	@Property(value = "#{translationService}")
	private TranslationService translationService;
		
	/* ----- business logic ----- */


	@Override
	@Prerender
	public void prerender() throws Exception{
		super.prerender();
		// Breadcrumbs
		breadcrumbs.loadAppointmentCrumbs();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("calender_update_single_appointment_page"));
		newCrumb.setHint(i18n("calender_update_single_appointment_page"));
		breadcrumbs.addCrumb(newCrumb);	
		
		// remove the old entry from the user calendar schedule model
		logger.debug("Removing old appointmentInfo entry:" + appointmentInfo.getId());
		removeSingleModelEntry(appointmentInfo);
		
		//initialize
		setAppointmentType(appointmentInfo.getAppointmentTypeInfo().getId().intValue());
	}
	
	public String save(){
		try {
		AppointmentTypeInfo appTI = new AppointmentTypeInfo();
		appTI.setId(appointmentType.longValue());
		appointmentInfo.setAppointmentTypeInfo(appTI);
		appointmentInfo.setSerial(false);
		calendarService.updateAppointment(appointmentInfo, calendarInfo);
		logger.debug("Updating appointmentInfo:" + appointmentInfo.getId());		
		} catch (CalendarApplicationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			addError("Das Updaten eines Appointments schlug fehl");
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
			this.addError("Error");
			return null;
		}
		return items;
	}

	/* ----- getter and setter ----- */
	
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
