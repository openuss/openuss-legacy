package org.openuss.web.groups.components;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.AppointmentInfo;
import org.openuss.calendar.CalendarService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.groups.GroupAccessType;
import org.openuss.groups.GroupApplicationException;
import org.openuss.groups.UserGroupMemberInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.web.Constants;

/**
 *
 * @author Lutz D. Kramer
 * @author Thomas Jansing
 * 
 */
@Bean(name = "views$secured$groups$components$main", scope = Scope.REQUEST)
@View
public class GroupMainPage extends AbstractGroupPage {

	private static final Logger logger = Logger.getLogger(GroupMainPage.class);

	private String password;

	private List<UserGroupMemberInfo> moderators = new ArrayList<UserGroupMemberInfo>();

	private List<AppointmentInfo> appointments = new ArrayList<AppointmentInfo>();

	/* ----- business logic ----- */
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (groupInfo != null) {
			moderators = groupService.getModerators(groupInfo);
			appointments = getCalendarService().getNaturalSerialAppointments(getCalendarInfo());
		}
		BreadCrumb newCrumb = new BreadCrumb();
		// TODO - Breadcrumbs
		newCrumb.setName(i18n("group_command_overview"));
		newCrumb.setHint(i18n("group_command_overview"));
		breadcrumbs.addCrumb(newCrumb);
	}
	
	public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
		String password = (String) value;
		if (!StringUtils.equalsIgnoreCase(password, groupInfo.getPassword())) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("message_error_password_is_not_correct"), null);
		}
	}

	public String applyWithPassword() throws GroupApplicationException {
		logger.debug("group entry with password applied");
		groupService.addUserByPassword(groupInfo, password, user.getId());
		addMessage(i18n("message_group_password_accepted"));
		return Constants.SUCCESS;
	}

	public String applyAsAspirant() throws GroupApplicationException{
		logger.debug("group apply as aspirant");
		groupService.addAspirant(groupInfo, user.getId());
		addMessage(i18n("message_group_applied_as_aspirant"));
		return Constants.SUCCESS;
	}
	
	public String apply() throws GroupApplicationException {
		logger.debug("group entry applied");
		groupService.addMember(groupInfo, user.getId());
		addMessage(i18n("message_group_applied_as_member"));
		return Constants.SUCCESS;
	}

	public boolean isAspirant() {
		return groupService.isAspirant(groupInfo, user.getId());
	}
	
	/* ----- getter and setter ----- */
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserGroupMemberInfo> getModerators() {
		return moderators;
	}

	public List<AppointmentInfo> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<AppointmentInfo> appointments) {
		this.appointments = appointments;
	}

}