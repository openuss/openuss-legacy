package org.openuss.web.groups.components;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.groups.UserGroupMemberInfo;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * 
 * @author Lutz D. Kramer
 * 
 */
@Bean(name = "views$secured$groups$components$groupmoderators", scope = Scope.REQUEST)
@View
public class GroupModeratorsPage extends AbstractGroupPage {

	private static final Logger logger = Logger
			.getLogger(GroupModeratorsPage.class);

	private GroupsDataProvider data = new GroupsDataProvider();
	private DataPage<UserGroupMemberInfo> page;
	private Set<UserGroupMemberInfo> changedUsers = new HashSet<UserGroupMemberInfo>();
	List<UserGroupMemberInfo> members;

	/* ----- private classes ----- */

	private class GroupsDataProvider extends
			AbstractPagedTable<UserGroupMemberInfo> {

		private static final long serialVersionUID = -5342817757466323535L;

		@Override
		public DataPage<UserGroupMemberInfo> getDataPage(int startRow,
				int pageSize) {
			if (page == null) {
				logger.debug("fetching group list");
				members = groupService.getAllMembers(groupInfo);
				for (UserGroupMemberInfo member : members) {
					if (member.getUserId().compareTo(user.getId()) == 0
							|| (groupService.isCreator(groupInfo, member
									.getUserId()) && !groupService.isModerator(
									groupInfo, member.getUserId()))) {
						member.setCreator(false);
					}
				}
				page = new DataPage<UserGroupMemberInfo>(members.size(), 0,
						members);
				sort(members);
			}
			return page;
		}
	}

	/* ----- business logic ----- */

	@Prerender
	@Override
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("group_command_moderator"));
		crumb.setHint(i18n("group_command_moderator"));
		breadcrumbs.addCrumb(crumb);
	}

	public String linkProfile() {
		User profile = User.Factory.newInstance();
		profile.setId(this.data.getRowData().getUserId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	public String save() {
		boolean newMod = false;
		for (UserGroupMemberInfo userInfo : changedUsers) {
			if (userInfo.isModerator()) {
				newMod = true;
			}
		}
		if (groupService.getModerators(groupInfo).size() == 1) {
			for (UserGroupMemberInfo userInfo : changedUsers) {
				if (newMod) {
					if (userInfo.isModerator()) {
						groupService.addModerator(groupInfo, userInfo
								.getUserId());
					} else {
						groupService.removeModerator(groupInfo, userInfo
								.getUserId());
					}
				} else {
					// Moderator can not be removed
					addError(i18n("group_remove_moderator_error"));
				}
			}
		} else {
			for (UserGroupMemberInfo userInfo : changedUsers) {
				if (userInfo.isModerator()) {
					groupService.addModerator(groupInfo, userInfo.getUserId());
				} else {
					groupService.removeModerator(groupInfo, userInfo
							.getUserId());
				}
				addMessage(i18n("system_message_changed_user_state", userInfo
						.getUsername()));
			}
		}
		resetCachedData();
		return Constants.GROUP_PAGE;
	}

	public String removeMember() {
		UserGroupMemberInfo member = data.getRowData();
		if (groupService.isModerator(groupInfo, member.getUserId())
				&& groupService.getModerators(groupInfo).size() == 1) {
			addError(i18n("group_delete_moderator_error"));
		} else {
			groupService.removeMember(groupInfo, member.getUserId());
			if (groupService.getAllMembers(groupInfo).size() == 0) {
				groupService.deleteUserGroup(groupInfo);
			}
		}
		return Constants.SUCCESS;
	}

	public void changedUserInfo(ValueChangeEvent event) {
		UserGroupMemberInfo userInfo = data.getRowData();
		logger.debug("changed state of " + userInfo.getUsername() + " from "
				+ event.getOldValue() + " to " + event.getNewValue());
		changedUsers.add(userInfo);
	}

	public boolean isAspirants() {
		return (groupService.getAspirants(groupInfo).size() == 0);
	}

	public void resetCachedData() {
		page = null;
	}

	/* ----- getter and setter ----- */

	public GroupsDataProvider getData() {
		return data;
	}

	public void setData(GroupsDataProvider data) {
		this.data = data;
	}

	public DataPage<UserGroupMemberInfo> getPage() {
		return page;
	}

	public void setPage(DataPage<UserGroupMemberInfo> page) {
		this.page = page;
	}

}
