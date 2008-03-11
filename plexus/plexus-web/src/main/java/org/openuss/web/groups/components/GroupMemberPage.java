package org.openuss.web.groups.components;

import java.util.List;

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
 * @author Thomas Jansing
 */
@Bean(name = "views$secured$groups$components$groupmembers", scope = Scope.REQUEST)
@View
public class GroupMemberPage extends AbstractGroupPage {

	private static final Logger logger = Logger
			.getLogger(GroupMemberPage.class);

	private GroupsDataProvider data = new GroupsDataProvider();
	private DataPage<UserGroupMemberInfo> page;

	/* ----- private classes ----- */

	private class GroupsDataProvider extends
			AbstractPagedTable<UserGroupMemberInfo> {

		private static final long serialVersionUID = -5342817757466323535L;

		@Override
		public DataPage<UserGroupMemberInfo> getDataPage(int startRow,
				int pageSize) {
			if (page == null) {
				logger.debug("fetching group list");
				List<UserGroupMemberInfo> members = groupService
						.getAllMembers(groupInfo);
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
	public void prerender() throws Exception { // NOPMD by devopenuss on 11.03.08 14:24
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("group_command_member"));
		crumb.setHint(i18n("group_command_member"));
		breadcrumbs.addCrumb(crumb);
	}
	
	public String linkProfile() {
		User profile = User.Factory.newInstance();
		profile.setId(this.data.getRowData().getUserId());
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

	public DataPage<UserGroupMemberInfo> getPage() {
		return page;
	}

	public void setPage(DataPage<UserGroupMemberInfo> page) {
		this.page = page;
	}

}
