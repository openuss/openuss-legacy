package org.openuss.web.calendar;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.AppointmentInfo;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.calendar.CalendarType;
import org.openuss.calendar.SerialAppointmentInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;

/**
 * This class is the backing bean for calendarlist.xhtml. It provides one
 * dataproviders for all appointments of a calendar.
 * 
 * @author Thomas Jansing
 * @author Gerrit Busse
 */
@Bean(name = "views$secured$calendar$calendarlist", scope = Scope.REQUEST)
@View
public class CalendarListMainPage extends AbstractCalendarPage {

	private static final Logger logger = Logger	.getLogger(CalendarListMainPage.class);
	private static final String calendarListBasePath = "/views/secured/calendar/calendarlist.faces";


	// Data-Provider serial appointments
	private AppointmentDataProvider appointmentData = new AppointmentDataProvider();

	private class AppointmentDataProvider extends	AbstractPagedTable<AppointmentInfo> {

		private static final long serialVersionUID = -2279124328223684543L;

		private DataPage<AppointmentInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<AppointmentInfo> getDataPage(int startRow,
				int pageSize) {
			if (page == null) {
				List<AppointmentInfo> al = null;
				logger.debug("Loading appointments");
				try {
					al = calendarService.getUpcomingSingleAppointments(calendarInfo);
				} catch (CalendarApplicationException e) {
					this.addError(Constants.ERROR);
					e.printStackTrace();
				}
				sort(al);
				page = new DataPage<AppointmentInfo>(al.size(), 0, al);
			}
			return page;
		}
	}


	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		//TODO correct breadcrumbs
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink(contextPath() + calendarListBasePath);
		newCrumb.setName(i18n("openuss4us_command_groups"));
		newCrumb.setHint(i18n("openuss4us_command_groups"));
		breadcrumbs.addCrumb(newCrumb);
	}
	
	public String deleteSerialAppointment(){
		setSessionBean(Constants.APPOINTMENT_INFO, this.appointmentData.getRowData());
		return Constants.CALENDAR_DELETE_APPOINTMENT;
	}
	
	public String serialAppointmentDetails(){
		setSessionBean(Constants.APPOINTMENT_INFO, this.appointmentData.getRowData());
		return Constants.OPENUSS4US_APPOINTMENT_DETAILS;
	}

	public AppointmentDataProvider getAppointmentData() {
		return appointmentData;
	}

	public void setAppointmentData(AppointmentDataProvider appointmentData) {
		this.appointmentData = appointmentData;
	}
	
	public String appointmentDetails(){
		setSessionBean(Constants.APPOINTMENT_INFO, this.appointmentData.getRowData());
		return Constants.OPENUSS4US_APPOINTMENT_DETAILS;
	}
	
	public String deleteAppointment(){
		appointmentInfo = this.appointmentData.getRowData();
		setSessionBean(Constants.APPOINTMENT_INFO, appointmentInfo);
		return Constants.CALENDAR_DELETE_APPOINTMENT;
	}
}