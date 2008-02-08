package org.openuss.web.groups;

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
import org.openuss.groups.GroupMemberInfo;
import org.openuss.groups.GroupMemberType;
import org.openuss.lecture.CourseApplicationException;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.web.Constants;
import org.openuss.web.BasePage;
/**
 * 
 * @author Lutz D. Kramer
 * @author Thomas Jansing
 *
 */
@Bean(name = "views$secured$groups$groups", scope = Scope.REQUEST)
@View
public class GroupMainPage extends BasePage {
	
	private static final Logger logger = Logger.getLogger(GroupMainPage.class);

	private String password;

	private List<CourseMemberInfo> moderators = new ArrayList<CourseMemberInfo>();
	

	@Override
	@Prerender
/**	public void prerender() throws Exception {
*		super.prerender();
*		//if (groupInfo != null) {
*		//	moderators = groupService.getModerators(groupInfo);
*		// }
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink("");
		newCrumb.setName(i18n("openuss4us_command_groups"));
		newCrumb.setHint(i18n("openuss4us_command_groups"));	
		breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
*/
	public String createGroup() {
		return Constants.OPENUSS4US_GROUPS_CREATE;
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
		groupService.addUserByPassword(password, groupInfo, user);
		addMessage(i18n("message_course_password_accepted"));
		return Constants.SUCCESS;
	}

	public String apply() throws GroupApplicationException {
		logger.debug("course entry applied");
		groupService.addMember(groupInfo, user);
		addMessage(i18n("message_course_send_application"));
		return Constants.SUCCESS;
	}

	public boolean isAspirant() throws GroupApplicationException {
		GroupMemberInfo info = groupService.getMemberInfo(groupInfo, user);
		return (info != null) && (info.getMemberType() == GroupMemberType.ASPIRANT);
	}
	
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
