package org.openuss.web.groups.components;

import java.util.ArrayList;
import java.util.List;

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
 * @author Thomas Jansing
 */
@Bean(name = "views$secured$groups$components$groupmembers", scope = Scope.REQUEST)
@View
public class GroupMemberPage extends AbstractGroupPage {

	private static final Logger logger = Logger
			.getLogger(GroupMemberPage.class);

	private GroupsDataProvider data = new GroupsDataProvider();
	private DataPage<MemberInfo> page;

	/* ----- private classes ----- */

	private class MemberInfo {

		private Long id;
		private String username;
		private String firstName;
		private String lastName;
		private boolean moderator;

		/* ----- getter and setter ----- */

		public MemberInfo(Long id, String username, String firstName, String lastName,
				boolean moderator) {
			this.id = id;
			this.username = username;
			this.firstName = firstName;
			this.lastName = lastName;
			this.moderator = moderator;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public boolean isModerator() {
			return moderator;
		}

		public void setModerator(boolean moderator) {
			this.moderator = moderator;
		}

		
		public Long getId() {
			return id;
		}

		
		public void setId(Long id) {
			this.id = id;
		}

	}

	private class GroupsDataProvider extends AbstractPagedTable<MemberInfo> {

		private static final long serialVersionUID = -5342817757466323535L;

		@Override
		public DataPage<MemberInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				logger.debug("fetching group list");
				List<UserGroupMemberInfo> membersInfo = groupService
						.getAllMembers(groupInfo);
				List<MemberInfo> members = new ArrayList<MemberInfo>();
				for (UserGroupMemberInfo member : membersInfo) {
					members.add(new MemberInfo(member.getUserId(), member.getUsername(), member
							.getFirstName(), member.getLastName(), groupService
							.isModerator(groupInfo, member.getUserId())));
				}
				page = new DataPage<MemberInfo>(members.size(), 0, members);
				sort(members);
			}
			return page;
		}
	}

	/* ----- business logic ----- */
	
	public String linkProfile(){
		User profile = User.Factory.newInstance();
		profile.setId(this.data.getRowData().getId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	/* ----- getter and setter ----- */

	public GroupsDataProvider getData() {
		return data;
	}

	public void setData(GroupsDataProvider data) {
		this.data = data;
	}

	public DataPage<MemberInfo> getPage() {
		return page;
	}

	public void setPage(DataPage<MemberInfo> page) {
		this.page = page;
	}
	
}
