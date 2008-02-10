package org.openuss.web.groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.jsfcontrols.components.flexlist.ListItemDAO;
import org.openuss.framework.jsfcontrols.components.flexlist.UIFlexList;
import org.openuss.groups.GroupApplicationException;
import org.openuss.groups.GroupMemberType;
import org.openuss.groups.UserGroupInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;
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

	private User user;
	
	private UIFlexList groupList;
	private boolean groupListDataLoaded = false;
	private boolean prerenderCalled = false;
	private Map<Long, GroupInfo> groupData;
	private static final String groupBasePath = "/views/secured/groups/group.faces";
	
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
	// TODO Thomas: Implement Security - Max Groups allowed ?
	public String createGroup() {
		return Constants.OPENUSS4US_GROUPS_CREATE;
	}
	public String joinGroup() {
		return Constants.OPENUSS4US_GROUPS_JOIN;
	}
	public String leaveGroup() {
		return Constants.OPENUSS4US_GROUPS_LEAVE;
	}
	
	public List getGroups() {
		List<UserGroupInfo> groups = groupService.getGroups();
		// sort(groups);
		return groups;
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
		// TODO Thomas: Needed ? Bookmarks for groups ?
		// groupList.getAttributes().put("alternateRemoveBookmarkLinkTitle", i18n("flexlist_remove_bookmark"));

		// Load values into the component
		loadValuesForGroupList(groupList);
	}
	
		// TODO Thomas: Convert from department/course to group -> found in MyUniPage.java
		private void loadValuesForGroupList(UIFlexList groupList) {
	
		if (groupListDataLoaded == false && groupList != null) {
			logger.debug("Loading data for group flexlist");
			// Make sure myUni-Data is loaded
			// prepareData();

			// Get the current user id
			
			Long userId = 1111L;

			// Put data in the component's attributes
			if (userId != null) {
				groupList.getAttributes().put("visibleItems", getGroupListItems(userId));

				// Make sure this isn't executed twice
				groupListDataLoaded = true;
			}
		}
	}
		
		// Returns a list of ListItemDAOs that contain the information to be shown
		// by the group flexlist
	
	private List<ListItemDAO> getGroupListItems(Long userId) {
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();

		// GroupInfo groupInfo = groupData.get(userId);

		ListItemDAO newItem;
		newItem = new ListItemDAO();
		
		// TODO Thomas: delete dummy links
		newItem.setTitle("Socialising Muenster");
		newItem.setUrl(contextPath()+groupBasePath);
		listItems.add(newItem);
		
		newItem = new ListItemDAO();
		newItem.setTitle("OpenArena Test Group");
		newItem.setUrl(contextPath()+groupBasePath);
		listItems.add(newItem);		
		return listItems;
	}
	

	
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
		groupService.addUserByPassword(groupInfo, password);
		addMessage(i18n("message_course_password_accepted"));
		return Constants.SUCCESS;
	}

	public String apply() throws GroupApplicationException {
		logger.debug("course entry applied");
		groupService.addMember(groupInfo, user.getId());
		addMessage(i18n("message_course_send_application"));
		return Constants.SUCCESS;
	}

	public boolean isAspirant() throws GroupApplicationException {
		List<UserInfo> aspirants = groupService.getAspirants(groupInfo);
		for(UserInfo aspirant:aspirants){
			if (aspirant.getId() == user.getId()){
				return true;
			}
		}
		return false;
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