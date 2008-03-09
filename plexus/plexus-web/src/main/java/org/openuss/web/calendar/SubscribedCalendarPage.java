package org.openuss.web.calendar;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.calendar.CalendarInfo;
import org.openuss.calendar.CalendarType;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.groups.UserGroupInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * This class is the backing bean for subscribedcalendars.xhtml.
 * 
 * @author Ralf Plattfaut
 */
@Bean(name = "views$secured$calendar$subscribedcalendars", scope = Scope.REQUEST)
@View
public class SubscribedCalendarPage extends AbstractCalendarPage {

	@Property(value="#{"+Constants.SHOW_USER_PROFILE+"}")
	private User profile;
	
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
			// remove the subscribed entries from the user calendar schedule model
			removeSubscribedModelEntries(this.data.getRowData());
			calendarService.endSubscription(this.data.getRowData());
			this.addMessage(i18n("openuss4us_calendar_message_unsubscribe"));
		} catch (CalendarApplicationException e) {
			this.addError(i18n("openuss4us_calendar_message_subscribe_error"));
			e.printStackTrace();
		}
		return Constants.CALENDAR_EDIT_SUBSCRIPTIONS;
	}
	
	public String linkToOrigin() {
		CalendarInfo calendar = this.data.getRowData();
		if(calendar.getCalendarType().equals(CalendarType.course_calendar)){
			CourseInfo course = new CourseInfo();
			course.setId(calendar.getDomainIdentifier());
			setSessionBean(Constants.COURSE_INFO, course);
			return Constants.COURSE_PAGE;
		}
		if(calendar.getCalendarType().equals(CalendarType.group_calendar)){
			UserGroupInfo groupInfo = new UserGroupInfo();
			groupInfo.setId(calendar.getDomainIdentifier());
			setSessionBean(Constants.GROUP_INFO, groupInfo);
			return Constants.GROUP_PAGE;
		}
		if(calendar.getCalendarType().equals(CalendarType.user_calendar)){
			//Should never happen
			profile.setId(calendar.getDomainIdentifier());
			setSessionBean(Constants.SHOW_USER_PROFILE, profile);
			return Constants.USER_PROFILE_VIEW_PAGE;
		}
		addError(i18n("openuss4us_calendar_not_found"));
		return Constants.HOME;
	}

	public SubscribedCalendarsDataProvider getData() {
		return data;
	}

	public void setData(SubscribedCalendarsDataProvider data) {
		this.data = data;
	}

	public User getProfile() {
		return profile;
	}

	public void setProfile(User profile) {
		this.profile = profile;
	}
}