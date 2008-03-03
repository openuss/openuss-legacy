package org.openuss.web.calendar;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.AppointmentInfo;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.calendar.CalendarInfo;
import org.openuss.calendar.CalendarService;
import org.openuss.calendar.SerialAppointmentInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Ralf Plattfaut
 */
@Bean(name = "views$secured$calendar$viewappointmentdetails", scope = Scope.REQUEST)
@View
public class AppointmentDetailPage extends AbstractCalendarPage {

	private static final Logger logger = Logger	.getLogger(AppointmentDetailPage.class);
	private static final String calendarBasePath = "/views/secured/calendar/calendar.faces";

	//checks weather the user watches a serial or a normal appointment
	private boolean serial;

	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (appointmentInfo == null) {
			addError(i18n("TODO: calendar not found!"));
			redirect(Constants.CALENDAR_HOME);
			return;
		}
		//TODO correct breadcrumbs
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink(contextPath() + calendarBasePath);
		newCrumb.setName(i18n("openuss4us_command_groups"));
		newCrumb.setHint(i18n("openuss4us_command_groups"));
		breadcrumbs.addCrumb(newCrumb);
		//Load appointmentinfo
		//TODO check wether appointmentInfo.getId() == null
		appointmentInfo = calendarService.getAppointment(appointmentInfo.getId());
		if(appointmentInfo instanceof SerialAppointmentInfo)
			this.serial = true;
		setSessionBean(Constants.APPOINTMENT_INFO, appointmentInfo);
	}

	public boolean getSerial() {
		return serial;
	}

	public void setSerial(boolean serial) {
		this.serial = serial;
	}
}