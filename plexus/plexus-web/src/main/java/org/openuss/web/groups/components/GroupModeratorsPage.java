package org.openuss.web.groups.components;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
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
	List<UserGroupMemberInfo> moderators;

	/* ----- private classes ----- */

	private class GroupsDataProvider extends
			AbstractPagedTable<UserGroupMemberInfo> {

		private static final long serialVersionUID = -5342817757466323535L;

		@Override
		public DataPage<UserGroupMemberInfo> getDataPage(int startRow,
				int pageSize) {
			if (page == null) {
				logger.debug("fetching group list");
				moderators = groupService
						.getAllMembers(groupInfo);
				page = new DataPage<UserGroupMemberInfo>(moderators.size(), 0,
						moderators);
				sort(moderators);
			}
			return page;
		}
	}

	/* ----- business logic ----- */

	public String linkProfile() {
		User profile = User.Factory.newInstance();
		profile.setId(this.data.getRowData().getUserId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	public String save() {
		for(UserGroupMemberInfo moderator:moderators){
			
		}
		addMessage("Erfolg");
		return Constants.SUCCESS;
	}

	public String removeMember() {
		UserGroupMemberInfo member = data.getRowData();
		if (!groupService.isCreator(groupInfo, member.getUserId())) {
			groupService.removeMember(groupInfo, member.getUserId());
		} else {
			if (member.getUserId().compareTo(user.getId()) == 0) {
				groupService.removeMember(groupInfo, member.getUserId());
			} else {
				// TODO - Lutz: Error anpassen
				addError("group_delete_creator");
			}
		}
		return Constants.SUCCESS;
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
