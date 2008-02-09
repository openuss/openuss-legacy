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
import org.openuss.framework.jsfcontrols.components.flexlist.UIFlexList;
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
public class GroupMainPage extends AbstractGroupPage {
	
	private static final Logger logger = Logger.getLogger(GroupMainPage.class);

	private UIFlexList groupList;
	
	private String password;

	private List<CourseMemberInfo> moderators = new ArrayList<CourseMemberInfo>();
	

	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		//if (groupInfo != null) {
		//	moderators = groupService.getModerators(groupInfo);
		// }
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink("");
		newCrumb.setName(i18n("openuss4us_command_groups"));
		newCrumb.setHint(i18n("openuss4us_command_groups"));	
		breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
	
	// Navigation outcomes
	public String newGroup() {
		return Constants.OPENUSS4US_GROUPS_NEW;
	}
	public String joinGroup() {
		return Constants.OPENUSS4US_GROUPS_JOIN;
	}
	public String leaveGroup() {
		return Constants.OPENUSS4US_GROUPS_LEAVE;
	}
	
	// Flexlist of groups
	public UIFlexList getGroupList() {
		return groupList;
	}

	public void setGroupList(UIFlexList groupList) {
		logger.debug("Setting group flexlist component");
		this.groupList = groupList;
		groupList.getAttributes().put("title", i18n("flexlist_groups"));
		groupList.getAttributes().put("showButtonTitle", i18n("flexlist_more_groups"));
		groupList.getAttributes().put("hideButtonTitle", i18n("flexlist_less_groups"));
		// Needed ? Bookmarks
		// groupList.getAttributes().put("alternateRemoveBookmarkLinkTitle", i18n("flexlist_remove_bookmark"));

		// Load values into the component
		// loadValuesForGroupList(groupList);
	}
	
		// TODO Thomas: Convert from department/course to group -> found in MyUniPage.java
		/**	private void loadValuesForGroupList(UIFlexList groupList) {

	
		if (courseListDataLoaded == false && prerenderCalled == true && coursesList != null) {
			logger.debug("Loading data for courses flexlist");
			// Make sure myUni-Data is loaded
			prepareData();

			// Get the current university id
			Long universityId = chooseUniversity();

			// Put data in the component's attributes
			if (universityId != null && myUniData != null) {
				coursesList.getAttributes().put("visibleItems", getVisibleCourseListItems(universityId));
				coursesList.getAttributes().put("hiddenItems", getHiddenCourseListItems(universityId));

				// Make sure this isn't executed twice
				courseListDataLoaded = true;
			}
		}
	}
		
		// Returns a list of ListItemDAOs that contain the information to be shown
		// by the group flexlist
	
		private List<ListItemDAO> getGroupListItems(Long universityId) {
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();

		if (myUniData != null) {
			MyUniInfo myUniInfo = myUniData.get(universityId);
			if (myUniInfo != null) {
				ListItemDAO newItem;
				Collection<MyUniDepartmentInfo> departmentCollection = myUniInfo.getDepartments();

				for (MyUniDepartmentInfo departmentInfo : departmentCollection) {
					newItem = new ListItemDAO();
					newItem.setTitle(departmentInfo.getName());
					newItem.setUrl(contextPath()+departmentsBasePath + "?department=" + departmentInfo.getId());
					if (departmentInfo.isBookmarked())
						newItem.setRemoveBookmarkUrl(contextPath()+myUniBasePath + "?university=" + universityId
								+ "&remove_department=" + departmentInfo.getId());

					listItems.add(newItem);
				}
			}
		}

		return listItems;
	}
	
	*/	
	
	// Needed ??
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
