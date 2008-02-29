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
 * This class is the backing bean for calendar.xhtml. It provides two
 * dataproviders for the serial and single appointments of a calendar.
 * 
 * @author Ralf Plattfaut
 */
@Bean(name = "views$secured$calendar$calendar", scope = Scope.REQUEST)
@View
public class CalendarMainPage extends BasePage {

	private static final Logger logger = Logger
			.getLogger(CalendarMainPage.class);
	private static final String calendarBasePath = "/views/secured/calendar/calendar.faces";

	@Property(value = "#{calendarService}")
	private CalendarService calendarService;

	@Property(value = "#{" + Constants.OPENUSS4US_CALENDAR + "}")
	private CalendarInfo calendarInfo;
	
	private boolean subscribed;

	// Data-Provider serial appointments
	private SerialAppointmentDataProvider serialAppointmentData = new SerialAppointmentDataProvider();

	private class SerialAppointmentDataProvider extends
			AbstractPagedTable<SerialAppointmentInfo> {

		private static final long serialVersionUID = -2279124328223684543L;

		private DataPage<SerialAppointmentInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<SerialAppointmentInfo> getDataPage(int startRow,
				int pageSize) {
			if (page == null) {
				List<SerialAppointmentInfo> al = null;
				logger.debug("Loading serial appointments");
				try {
					al = calendarService.getNaturalSerialAppointments(calendarInfo);
				} catch (CalendarApplicationException e) {
					this.addError(Constants.ERROR);
					e.printStackTrace();
				}
				sort(al);
				page = new DataPage<SerialAppointmentInfo>(al.size(), 0, al);
			}
			return page;
		}
	}

	// Data-Provider single appointments
	private SingleAppointmentDataProvider singleAppointmentData = new SingleAppointmentDataProvider();

	private class SingleAppointmentDataProvider extends
			AbstractPagedTable<AppointmentInfo> {

		private static final long serialVersionUID = -2279124328223684543L;

		private DataPage<AppointmentInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<AppointmentInfo> getDataPage(int startRow,
				int pageSize) {
			if (page == null) {
				List<AppointmentInfo> al = null;
				logger.debug("Loading single appointments");
				try {
					al = calendarService.getNaturalSingleAppointments(calendarInfo);
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
		if (calendarInfo == null) {
			addError(i18n("TODO: calendar not found!"));
			redirect(Constants.DESKTOP);
			return;
		}
		//TODO correct breadcrumbs
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink(contextPath() + calendarBasePath);
		newCrumb.setName(i18n("openuss4us_command_groups"));
		newCrumb.setHint(i18n("openuss4us_command_groups"));
		// breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
	
	public String changeSubscription(){
		try{
		if(getSubscribed()){
			this.addMessage("TODO: Unsubscribed");
			getCalendarService().endSubscription(calendarInfo);
		} else {
			this.addMessage("TODO: Subscribed");
			getCalendarService().addSubscription(calendarInfo);
		}
		} catch (CalendarApplicationException e) {
			this.addError("Error");
			return Constants.SUCCESS;
		}
		return "openuss4us_calendar_calendar";
	}

	public CalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	public CalendarInfo getCalendarInfo() {
		return calendarInfo;
	}

	public void setCalendarInfo(CalendarInfo calendarInfo) {
		this.calendarInfo = calendarInfo;
	}

	public SerialAppointmentDataProvider getSerialAppointmentData() {
		return serialAppointmentData;
	}

	public void setSerialAppointmentData(
			SerialAppointmentDataProvider serialAppointmentData) {
		this.serialAppointmentData = serialAppointmentData;
	}

	public SingleAppointmentDataProvider getSingleAppointmentData() {
		return singleAppointmentData;
	}

	public void setSingleAppointmentData(
			SingleAppointmentDataProvider singleAppointmentData) {
		this.singleAppointmentData = singleAppointmentData;
	}

	public boolean getSubscribed() {
		try {
			if(calendarService.getSubscriptions().contains(calendarInfo)){
				logger.debug("Already subscribed to this calendar");
				return true;
			}
		} catch (CalendarApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("Not yet subscribed to this calendar");
		return false;
	}

	public void setSubscribed(boolean isSubscribed) {
		this.subscribed = isSubscribed;
	}
}