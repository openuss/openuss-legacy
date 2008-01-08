package org.openuss.web.course;

import java.util.ArrayList;
import java.util.Date;
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
import org.openuss.desktop.DesktopException;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.group.GroupAdminInformationInfo;
import org.openuss.group.GroupService;
import org.openuss.group.WorkingGroupInfo;
import org.openuss.lecture.CourseApplicationException;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.lecture.CourseService;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
@Bean(name = "views$secured$course$main", scope = Scope.REQUEST)
@View
public class CourseMainPage extends AbstractCoursePage {

	private static final Logger logger = Logger.getLogger(CourseMainPage.class);

	private String password;

	private List<CourseMemberInfo> assistants = new ArrayList<CourseMemberInfo>();

	private GroupDataProvider groupData = new GroupDataProvider();
	
	protected GroupAdminInformationInfo adminInfo;

	@Property(value = "#{groupService}")
	protected GroupService groupService;
	
	@SuppressWarnings("unchecked")
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (courseInfo != null) {
			assistants = courseService.getAssistants(courseInfo);
		}

		adminInfo = groupService.getAdminInfo(courseInfo.getId());
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("course_command_overview"));
		newCrumb.setHint(i18n("course_command_overview"));
		breadcrumbs.addCrumb(newCrumb);
	}
	
	
	private class GroupDataProvider extends AbstractPagedTable<WorkingGroupInfo> {

		private static final long serialVersionUID = 5974442506189912055L;
		
		private DataPage<WorkingGroupInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<WorkingGroupInfo> getDataPage(int startRow, int pageSize) {		
			List<WorkingGroupInfo> al = groupService.getGroups(courseInfo.getId());
			page = new DataPage<WorkingGroupInfo>(al.size(),0,al);
			return page;
		}
	}

	public GroupDataProvider getGroupData() {
		return groupData;
	}

	public void setGroupData(GroupDataProvider groupData) {
		this.groupData = groupData;
	}
	

	public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
		String password = (String) value;
		if (!StringUtils.equalsIgnoreCase(password, courseInfo.getPassword())) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("message_error_password_is_not_correct"), null);
		}
	}

	public String applyWithPassword() throws CourseApplicationException {
		logger.debug("course entry with password applied");
		courseService.applyUserByPassword(password, courseInfo, user);
		addMessage(i18n("message_course_password_accepted"));
		return Constants.SUCCESS;
	}

	public String apply() throws CourseApplicationException {
		logger.debug("course entry applied");
		courseService.applyUser(courseInfo, user);
		addMessage(i18n("message_course_send_application"));
		return Constants.SUCCESS;
	}

	public boolean isAspirant() {
		CourseMemberInfo info = courseService.getMemberInfo(courseInfo, user);
		return (info != null) && (info.getMemberType() == CourseMemberType.ASPIRANT);
	}

	public Boolean getBookmarked() {
		try {
			return desktopService2.isCourseBookmarked(courseInfo.getId(), user.getId());
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}
	
	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * @return Outcome
	 */
	public String shortcutCourse() {
		//courseInfo = courseData.getRowData();
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
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}

	public String addGroup(){
		WorkingGroupInfo workingGroup = new WorkingGroupInfo();
		setSessionBean(Constants.GROUP_WORKING_GROUP, workingGroup);		
		return Constants.GROUP_NEWGROUP;
	}

	public String changeGroup(){
		WorkingGroupInfo workingGroup = this.groupData.getRowData();
		setSessionBean(Constants.GROUP_WORKING_GROUP, workingGroup);
		return Constants.GROUP_NEWGROUP;
	}
	
	public String deleteGroup(){
		WorkingGroupInfo workingGroup = this.groupData.getRowData();
		getGroupService().deleteGroup(workingGroup);
		addMessage(i18n("group_deleted"));
		return Constants.SUCCESS;		
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<CourseMemberInfo> getAssistants() {
		return assistants;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public GroupAdminInformationInfo getAdminInfo() {
		return adminInfo;
	}

	public void setAdminInfo(GroupAdminInformationInfo adminInfo) {
		this.adminInfo = adminInfo;
	}

}