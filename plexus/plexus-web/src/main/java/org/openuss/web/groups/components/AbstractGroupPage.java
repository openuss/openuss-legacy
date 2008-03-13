package org.openuss.web.groups.components;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.calendar.CalendarInfo;
import org.openuss.calendar.CalendarService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.groups.GroupService;
import org.openuss.groups.UserGroupInfo;
import org.openuss.lecture.LectureService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * 
 * @author Lutz D. Kramer
 * @author Thomas Jansing
 * @author Ralf Plattfaut
 */
public class AbstractGroupPage extends BasePage {

	private static final long serialVersionUID = 2394531398550932611L;

	@Property(value = "#{groupInfo}")
	protected UserGroupInfo groupInfo;

	@Property(value = "#{groupService}")
	protected GroupService groupService;

	@Property(value = "#{lectureService}")
	protected LectureService lectureService;
	
	//added for course calendar support
	@Property(value = "#{calendarService}")
	protected CalendarService calendarService;
	
	@Property(value = "#{" + Constants.CALENDAR_INFO + "}")
	protected CalendarInfo calendarInfo;

	@Prerender
	public void prerender() throws Exception { // NOPMD by devopenuss on 11.03.08 14:23
		if (groupInfo != null && groupInfo.getId() != null) {
			groupInfo = groupService.getGroupInfo(groupInfo.getId());
			calendarInfo = calendarService.getCalendar(groupInfo);
			setSessionAttribute(Constants.CALENDAR_INFO, calendarInfo);
		}
		if (groupInfo == null) {
			addError(i18n("group_page_not_found"));
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		} else {
			calendarInfo = calendarService.getCalendar(groupInfo);
			setSessionBean(Constants.CALENDAR_INFO, calendarInfo);
			setSessionBean(Constants.GROUP_INFO, groupInfo);
		}
		breadcrumbs.loadGroupCrumbs(groupInfo);
		
	}

	public boolean isMember() {
		return groupService.isMember(groupInfo, user.getId());
	}

	public boolean isModerator() {
		return groupService.isModerator(groupInfo, user.getId());
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public UserGroupInfo getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(UserGroupInfo GroupInfo) {
		this.groupInfo = GroupInfo;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
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

}
