package org.openuss.web.groups;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.jsfcontrols.components.flexlist.GroupUIFlexList;
import org.openuss.framework.jsfcontrols.components.flexlist.ListItemDAO;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.groups.UserGroupInfo;
import org.openuss.web.Constants;

/**
 * @author Lutz D. Kramer
 * @author Thomas Jansing
 */
@Bean(name = "views$secured$groups$groups", scope = Scope.REQUEST)
@View
public class GroupsMainPage extends AbstractGroupsPage {
	
	private static final Logger logger = Logger.getLogger(GroupsMainPage.class);

	private GroupUIFlexList groupsList;
		
	private static final String groupBasePath = "/views/secured/groups/components/main.faces?group=";
	
	private GroupsDataProvider data = new GroupsDataProvider();
	private DataPage<UserGroupInfo> page;
	private List<UserGroupInfo> groups;
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("openuss4us_command_groups"));
		crumb.setHint(i18n("openuss4us_command_groups"));
		breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(crumb);
	}
	
	/* ----- private classes ----- */
	
	private class GroupsDataProvider extends AbstractPagedTable<UserGroupInfo> {
		
		private static final long serialVersionUID = -5342817757466323535L;

		@Override
		public DataPage<UserGroupInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				logger.debug("fetching group list");
				List<UserGroupInfo> groups = getGroups();
				page = new DataPage<UserGroupInfo>(groups.size(), 0, groups);
				sort(groups);
			}
			return page;
		}
	}
	
	/* ----- business logic ----- */
	
	public String createGroup() {
		return Constants.OPENUSS4US_GROUPS_CREATE;
	}
	
	public String joinGroup() {
		return Constants.OPENUSS4US_GROUPS_JOIN;
	}
	
	public String linkGroup(){
		UserGroupInfo group = this.data.getRowData();
		setSessionAttribute(Constants.GROUP_INFO, group);
		return Constants.GROUP_PAGE;
	}
	
	public void outerLeaveGroup(Long id){
		
	}
	
	public String leaveGroup() {
		logger.debug("member leave group");
		UserGroupInfo group = data.getRowData();
		if((groupService.getModerators(group).size() == 1) && groupService.isModerator(group, user.getId())){
			if (groupService.getAllMembers(group).size() == 1){
				groupService.deleteUserGroup(group);
				addMessage(i18n("message_group_deleted", group.getName()));	
			} else {
				addError(i18n("group_last_moderator_error"));
			}		
		} else {
			groupService.removeMember(group, user.getId());
			addMessage(i18n("message_group_left", group.getName()));
		}	
		resetCachedData();
		return Constants.SUCCESS;
	}
	
	private void resetCachedData() {
		groups = null;
	}
	
	private List<UserGroupInfo> getGroups() {
		if (groups == null) {
			groups = groupService.getGroupsByUser(user.getId());
			logger.debug("User GROUPS: " + groups.size() + ", " + groups);
			for(UserGroupInfo group:groups){
				logger.debug("Gruppe Creator:" +group.getCreator() + " - " + group.getCreatorName());
			}
		}
		return groups;
	}
	
	/* ----- getter and setter ----- */
	
	public GroupsDataProvider getData() {
		return data;
	}

	public void setData(GroupsDataProvider data) {
		this.data = data;
	}

	/* ----- groups flexlist ----- */
	
	public GroupUIFlexList getGroupsList() {
		return groupsList;
	}
	
	public void setGroupsList(GroupUIFlexList groupsList) {
		logger.debug("Setting groups flexlist component");
		this.groupsList = groupsList;
		groupsList.getAttributes().put("title", i18n("flexlist_groups"));
		groupsList.getAttributes().put("showButtonTitle", i18n("flexlist_more_groups"));
		groupsList.getAttributes().put("hideButtonTitle", i18n("flexlist_less_groups"));
//		groupsList.getAttributes().put("alternateRemoveBookmarkLinkTitle", i18n("flexlist_remove_bookmark"));
		// Load values into the component
		loadValuesForGroupsList(groupsList);
	}
	
	private void loadValuesForGroupsList(GroupUIFlexList groupsList) {
		logger.debug("Loading data for groups flexlist");
		groupsList.getAttributes().put("visibleItems", getGroupsListItems());
	}

	/*
	 * Returns a list of ListItemDAOs that contain the information to be shown
	 * by the groups flexlist
	 */
	private List<ListItemDAO> getGroupsListItems() {
		List<ListItemDAO> listItems = new ArrayList<ListItemDAO>();
			ListItemDAO newItem;
			List<UserGroupInfo> groupInfos = getGroups();
			for (UserGroupInfo groupInfo : groupInfos) {
				newItem = new ListItemDAO();
				newItem.setTitle(groupInfo.getName());
				newItem.setUrl(contextPath()+ groupBasePath + groupInfo.getId());	
				listItems.add(newItem);
			}
		return listItems;
		}
	
	
	
	}
