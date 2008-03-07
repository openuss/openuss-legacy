package org.openuss.web.groups;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.groups.UserGroupInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the group joining on the groupJoinPage
 * 
 * @author Lutz D. Kramer
 * @author Thomas Jansing
 */
@Bean(name = "views$secured$groups$groupjoin", scope = Scope.REQUEST)
@View
public class GroupsJoinPage extends AbstractGroupsPage {

	private static final Logger logger = Logger.getLogger(GroupsMainPage.class);

	private GroupsDataProvider data = new GroupsDataProvider();
	private DataPage<UserGroupInfo> page;
	private List<UserGroupInfo> groups;

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

	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink(contextPath()
				+ "/views/secured/groups/groupjoin.faces");
		newCrumb.setName(i18n("openuss4us_command_groups_join"));
		newCrumb.setHint(i18n("openuss4us_command_groups_join"));
		breadcrumbs.addCrumb(newCrumb);
	}

	public String linkGroup() {
		UserGroupInfo group = this.data.getRowData();
		setSessionAttribute(Constants.GROUP_INFO, group);
		return Constants.GROUP_PAGE;
	}

	public String deleteGroup() {
		resetCachedData();
		return Constants.SUCCESS;
	}

	private void resetCachedData() {
		groups = null;
	}

	private List<UserGroupInfo> getGroups() {
		if (groups == null) {
			groups = groupService.getAllGroups();
			List<UserGroupInfo> userGroups = groupService.getGroupsByUser(user
					.getId());
			groups.removeAll(userGroups);
			for (UserGroupInfo group : groups) {
				logger.debug("Gruppe Creator: " + group.getCreator() + " - "
						+ group.getCreatorName());
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

}
