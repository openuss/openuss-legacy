package org.openuss.web.calendar;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.schedule.model.DefaultScheduleEntry;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
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
import org.openuss.groups.GroupService;
import org.openuss.lecture.CourseService;
import org.openuss.security.SecurityService;
import org.openuss.web.Constants;

/**
 * 
 * @author Ralf Plattfaut
 */
@Bean(name = "views$secured$calendar$viewappointmentdetails", scope = Scope.REQUEST)
@View
public class AppointmentDetailPage extends AbstractCalendarPage {

	private static final Logger logger = Logger
			.getLogger(AppointmentDetailPage.class);
	private static final String calendarBasePath = "/views/secured/calendar/calendar.faces";

	@Property(value = "#{securityService}")
	protected SecurityService securityService;

	@Property(value = "#{courseService}")
	protected CourseService courseService;

	@Property(value = "#{groupService}")
	protected GroupService groupService;
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (appointmentInfo == null) {
			addError(i18n("TODO: appointment not found!"));
			redirect(Constants.CALENDAR_HOME);
			return;
		}
		// TODO correct breadcrumbs
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink(contextPath() + calendarBasePath);
		newCrumb.setName(i18n("openuss4us_command_groups"));
		newCrumb.setHint(i18n("openuss4us_command_groups"));
		breadcrumbs.addCrumb(newCrumb);
	}

	// Data-Provider single appointments
	private SingleAppointmentDataProvider data = new SingleAppointmentDataProvider();

	private class SingleAppointmentDataProvider extends
			AbstractPagedTable<AppointmentInfo> {

		private static final long serialVersionUID = -2279124328223684543L;

		private DataPage<AppointmentInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<AppointmentInfo> getDataPage(int startRow, int pageSize) {
			if (!isSerial()) {
				return null;
			}
			if (page == null) {
				List<AppointmentInfo> al = null;
				logger.debug("Loading single appointments");
				try {
					al = calendarService
							.getCalculatedAppointments((SerialAppointmentInfo) appointmentInfo);
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

	public String deactivate() {
		try {
			AppointmentInfo app = data.getRowData();
			if (app.isTakingPlace()) {
				calendarService.addException(
						(SerialAppointmentInfo) appointmentInfo, app);
				addMessage(i18n("openuss4us_calendar_message_exception_added"));
			} else {
				calendarService.deleteException(
						(SerialAppointmentInfo) appointmentInfo, app);
				addMessage(i18n("openuss4us_calendar_message_exception_deleted"));
			}
		} catch (CalendarApplicationException e) {
			addError(i18n("openuss4us_calendar_message_exception_error"));
		}
		return Constants.OPENUSS4US_APPOINTMENT_DETAILS;
	}

	/**
	 * Checks whether the appointment viewed is serial.
	 */
	public boolean isSerial() {
		return (appointmentInfo instanceof SerialAppointmentInfo);
	}

	public boolean isSameDay() {
		return (appointmentInfo.getStarttime().getYear() == appointmentInfo
				.getEndtime().getYear())
				&& (appointmentInfo.getStarttime().getMonth() == appointmentInfo
						.getEndtime().getMonth())
				&& (appointmentInfo.getStarttime().getDate() == appointmentInfo
						.getEndtime().getDate());
	}

	public String delete() {
		logger.debug("Deleting appointment " + appointmentInfo.getId());
		try {
			if (isSerial()) {
				List<AppointmentInfo> apps = calendarService.getCalculatedAppointments((SerialAppointmentInfo) appointmentInfo);
				for (AppointmentInfo app : apps) {
					removeModelEntry(app);
				}
				calendarService.deleteSerialAppointment(
						(SerialAppointmentInfo) appointmentInfo, calendarInfo);			
			} else {
				removeModelEntry(appointmentInfo);
				calendarService
						.deleteAppointment(appointmentInfo, calendarInfo);
			}
			addMessage(i18n("openuss4us_calendar_message_appointment_deleted"));
		} catch (Exception e) {
			e.printStackTrace();
			addError(i18n("openuss4us_calendar_message_appointment_deleted_error"));
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
	
	public String update(){
		if(isSerial())
			return Constants.CALENDAR_UPDATE_SERIAL;
		else
			return Constants.CALENDAR_UPDATE_SINGLE;
	}

	public void removeModelEntry(AppointmentInfo appInfo) {
		DefaultScheduleEntry entry1 = new DefaultScheduleEntry();
		entry1.setId(appInfo.getId().toString());
		entry1.setTitle(appInfo.getSubject());
		entry1.setStartTime(appInfo.getStarttime());
		entry1.setEndTime(appInfo.getEndtime());
		entry1.setDescription(appInfo.getDescription());
		entry1.setSubtitle(appInfo.getDescription());
		logger.debug("Removing entry: " + entry1.getId() + " from the schedule model");
		model.removeEntry(entry1);
	}
	
	public SingleAppointmentDataProvider getData() {
		return data;
	}

	public void setData(SingleAppointmentDataProvider data) {
		this.data = data;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

}