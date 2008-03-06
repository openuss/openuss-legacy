package org.openuss.web.calendar;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.calendar.CalendarInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;

/**
 * This class is the backing bean for subscribedcalendars.xhtml.
 * 
 * @author Ralf Plattfaut
 */
@Bean(name = "views$secured$calendar$subscribedcalendars", scope = Scope.REQUEST)
@View
public class SubscribedCalendarPage extends AbstractCalendarPage {

	private static final Logger logger = Logger
			.getLogger(SubscribedCalendarPage.class);
	private static final String calendarBasePath = "/views/secured/calendar/calendar.faces";

	// Data-Provider serial appointments
	private SubscribedCalendarsDataProvider data = new SubscribedCalendarsDataProvider();

	private class SubscribedCalendarsDataProvider extends
			AbstractPagedTable<CalendarInfo> {

		private static final long serialVersionUID = -2279124328223684543L;

		private DataPage<CalendarInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<CalendarInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CalendarInfo> al = null;
				logger.debug("Loading all subscribed calendars");
				try {
					al = calendarService.getSubscriptions();
				} catch (CalendarApplicationException e) {
					this.addError(Constants.ERROR);
					e.printStackTrace();
				}
				sort(al);
				page = new DataPage<CalendarInfo>(al.size(), 0, al);
			}
			return page;
		}
	}

	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		// TODO correct breadcrumbs
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink(contextPath() + calendarBasePath);
		newCrumb.setName(i18n("openuss4us_command_groups"));
		newCrumb.setHint(i18n("openuss4us_command_groups"));
		breadcrumbs.addCrumb(newCrumb);
	}

	public String removeSubscription() {
		try {
			calendarService.endSubscription(this.data.getRowData());
			this.addMessage(i18n("openuss4us_calendar_message_unsubscribe"));
		} catch (CalendarApplicationException e) {
			this.addError(i18n("openuss4us_calendar_message_subscribe_error"));
			e.printStackTrace();
		}
		return Constants.CALENDAR_EDIT_SUBSCRIPTIONS;
	}

	public SubscribedCalendarsDataProvider getData() {
		return data;
	}

	public void setData(SubscribedCalendarsDataProvider data) {
		this.data = data;
	}
}