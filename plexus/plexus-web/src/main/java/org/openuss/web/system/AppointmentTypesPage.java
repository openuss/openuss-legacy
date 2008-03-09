package org.openuss.web.system;

import java.util.List;
import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
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
import org.openuss.internationalisation.TranslationService;
import org.openuss.internationalisation.TranslationTextInfo;
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
@Bean(name = "views$secured$system$appointmenttypes", scope = Scope.REQUEST)
@View

public class AppointmentTypesPage extends BasePage{
		
	@Property(value = "#{appointmentTypeInfo}")
	AppointmentTypeInfo appointmentTypeInfo;
	
	@Property(value = "#{calendarService}")
	CalendarService calendarService;
	
	private AppointmenttypesTable data = new AppointmenttypesTable();
	
	public AppointmentTypeInfo getAppointmentTypeInfo() {
		return appointmentTypeInfo;
	}

	public void setAppointmentTypeInfo(AppointmentTypeInfo appointmentTypeInfo) {
		this.appointmentTypeInfo = appointmentTypeInfo;
	}

	public String addAppointmentType(){
		try {
			calendarService.createAppointmentType(appointmentTypeInfo);
		} catch (CalendarApplicationException e) {
			this.addError("Error");
			return Constants.ERROR;
		}
		this.addMessage(i18n("openuss4us_calendar_appointmenttype_added"));
		return Constants.SUCCESS;
	}
	
	public String delete(){
		try {
			calendarService.deleteAppointmentType(this.data.getRowData());
		} catch (CalendarApplicationException e) {
			this.addError(Constants.ERROR);
			return Constants.ERROR;
		}
		this.addMessage(i18n("openuss4us_calendar_appointmenttype_deleted"));
		return "admin_calendar";
	}
	
	public String translate(){
		setSessionAttribute(Constants.APPOINTMENTTYPE_INFO, data.getRowData());
		return Constants.OPENUSS4US_CALENDAR_TRANSLATE;
	}
	
	@Prerender
	public void prerender() {
		try {
			super.prerender();
		} catch (Exception e) {
			
		}
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("departmentList_header"));
		newCrumb.setHint(i18n("departmentList_header"));
		newCrumb.setLink(PageLinks.ADMIN_DEPARTMENTS);
		
		breadcrumbs.loadAdministrationCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
	
	private class AppointmenttypesTable extends AbstractPagedTable<AppointmentTypeInfo> {

		private static final long serialVersionUID = -2279124328223684525L;
		
		private DataPage<AppointmentTypeInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<AppointmentTypeInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<AppointmentTypeInfo> al;
				try {
					al = calendarService.getAllAppointmentTypes();
					sort(al);
					page = new DataPage<AppointmentTypeInfo>(al.size(),0,al);
				} catch (CalendarApplicationException e) {
					addError("Error");
				}
			}
			return page;
		}
	}


	public CalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	public AppointmenttypesTable getData() {
		return data;
	}

	public void setData(AppointmenttypesTable data) {
		this.data = data;
	}
}