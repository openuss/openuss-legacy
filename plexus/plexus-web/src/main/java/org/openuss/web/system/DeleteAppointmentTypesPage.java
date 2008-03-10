package org.openuss.web.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.AppointmentTypeInfo;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.calendar.CalendarService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.internationalisation.TranslationApplicationException;
import org.openuss.internationalisation.TranslationService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/** 
 * Backing Bean for the view secured/system/appointmenttypes.xhtml
 * 
 * @author Ralf Plattfaut
 * @author Gerrit Busse
 *
 */
@Bean(name = "views$secured$system$deleteappointmenttypes", scope = Scope.REQUEST)
@View

public class DeleteAppointmentTypesPage extends BasePage{
		
	@Property(value = "#{appointmentTypeInfo}")
	AppointmentTypeInfo appointmentTypeInfo;
	
	@Property(value = "#{calendarService}")
	CalendarService calendarService;
	
	@Property(value = "#{translationService}")
	TranslationService translationService;
		
	public TranslationService getTranslationService() {
		return translationService;
	}

	public void setTranslationService(TranslationService translationService) {
		this.translationService = translationService;
	}

	public AppointmentTypeInfo getAppointmentTypeInfo() {
		return appointmentTypeInfo;
	}

	public void setAppointmentTypeInfo(AppointmentTypeInfo appointmentTypeInfo) {
		this.appointmentTypeInfo = appointmentTypeInfo;
	}
	
	public Integer substitute;
	
	public String deleteFinal(){
		try {
			AppointmentTypeInfo substituteAppointmentType = new AppointmentTypeInfo();
			substituteAppointmentType.setId(getSubstitute().longValue());

			// TODO correct here
			// User should choose a new appointmenttype.
			calendarService.deleteAppointmentType(appointmentTypeInfo, substituteAppointmentType);
		} catch (CalendarApplicationException e) {
			this.addError(Constants.ERROR);
			return Constants.ERROR;
		}
		this.addMessage(i18n("openuss4us_calendar_appointmenttype_deleted"));
		return "admin_calendar";
	}
		
	@Prerender
	public void prerender() throws CalendarApplicationException {
		try {
			super.prerender();
		} catch (Exception e) {
			
		}
		BreadCrumb newCrumb = new BreadCrumb();
		if(calendarService.getAllAppointmentTypes().size()<=1){
			addError(i18n("openuss4us_calendar_appointmenttype_delete_last"));
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		}
		breadcrumbs.loadAdministrationCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
	
	public List<SelectItem> getAppointTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		
		try {
			for(AppointmentTypeInfo appType : (Collection<AppointmentTypeInfo>)calendarService.getAllAppointmentTypes()){
				if(!appType.getId().equals(appointmentTypeInfo.getId())){
					String name = appType.getName() + " (" + translationService.getTranslation(appType.getId(), appType.getName(), user.getLocale())+")";
					items.add(new SelectItem(appType.getId().intValue(), name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.addError("Error");
			return null;
		}
		return items;
	}	
	
	public String getTranslation() throws TranslationApplicationException {
		return translationService.getTranslation(appointmentTypeInfo.getId(), appointmentTypeInfo.getName(), user.getLocale());
	}
	
	public CalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	public Integer getSubstitute() {
		return substitute;
	}

	public void setSubstitute(Integer substitute) {
		this.substitute = substitute;
	}
}
