package org.openuss.web.groups.components;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.groups.GroupApplicationException;
import org.openuss.groups.GroupService;
import org.openuss.lecture.CourseApplicationException;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.web.Constants;

/**
 *
 * @author Ingo Dueppe
 * 
 */
@Bean(name = "views$secured$groups$main", scope = Scope.REQUEST)
@View
public class GroupMainPage extends AbstractGroupPage {

	private static final Logger logger = Logger.getLogger(GroupMainPage.class);

	private String password;

	private List<CourseMemberInfo> moderators = new ArrayList<CourseMemberInfo>();
	

	@SuppressWarnings("unchecked")
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (groupInfo != null) {		
			// moderators = groupService.getModerators(groupInfo);
		}

		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("openuss4us_group_command_overview"));
		newCrumb.setHint(i18n("openuss4us_group_command_overview"));
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

	public String apply() throws GroupApplicationException {
		logger.debug("group entry applied");
		groupService.addMember(groupInfo, user.getId());
		addMessage(i18n("message_group_send_application"));
		return Constants.SUCCESS;
	}

	// public boolean isAspirant() {
		// GroupMemberInfo info = groupService.getAspirants(groupInfo);
		// CourseMemberInfo info = courseService.getMemberInfo(courseInfo, user);
		// return (info != null) && (info.getMemberType() == CourseMemberType.ASPIRANT);
	// }

	/** public Boolean getBookmarked() {
		try {
			return desktopService2.isCourseBookmarked(courseInfo.getId(), user.getId());
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}
	*/
	
	
	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * @return Outcome
	 
	public String shortcutCourse() {
		try {
			desktopService2.linkCourse(desktopInfo.getId(), courseInfo.getId());
			addMessage(i18n("desktop_command_add_course_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}

	public String removeCourseShortcut() {
		try {
			desktopService2.unlinkCourse(desktopInfo.getId(), courseInfo.getId());
			addMessage(i18n("desktop_command_add_course_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}
	*/

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<CourseMemberInfo> getModerators() {
		return moderators;
	}

}