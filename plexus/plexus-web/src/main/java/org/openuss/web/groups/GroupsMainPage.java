package org.openuss.web.groups;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.groups.GroupService;
import org.openuss.groups.UserGroupInfo;
import org.openuss.security.SecurityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * @author Lutz D. Kramer
 * @author Thomas Jansing
 */
@Bean(name = "views$secured$groups$groups", scope = Scope.REQUEST)
@View
public class GroupsMainPage extends BasePage {
	
	private static final Logger logger = Logger.getLogger(GroupsMainPage.class);
	private static final String userGroupBasePath = "/views/secured/groups/components/main.faces";

	@Property(value = "#{groupService}")
	protected GroupService groupService;
	
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
		newCrumb.setLink(contextPath()+userGroupBasePath);
		newCrumb.setName(i18n("openuss4us_command_groups"));
		newCrumb.setHint(i18n("openuss4us_command_groups"));	
		breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
	
	public String createGroup() {
		return Constants.OPENUSS4US_GROUPS_CREATE;
	}
	
	public String joinGroup() {
		return Constants.OPENUSS4US_GROUPS_JOIN;
	}
	
	public String leaveGroup() {
		logger.debug("course member deleted");
		UserGroupInfo groups = data.getRowData();
		logger.debug("GRUPPE " + +groups.getId() + ", " + groups.getName());
		List<UserGroupInfo> mods = groupService.getModerators(groups);
		logger.debug("LEERE GRUPPE? " + mods.size());
		List<UserGroupInfo> memb = groupService.getMembers(groups);
		logger.debug("LEERE GRUPPE? " + memb.size());
		List<UserGroupInfo> asp = groupService.getAspirants(groups);
		logger.debug("LEERE GRUPPE? " + asp.size());
		groupService.removeMember(groups, user.getId());
		// TODO - Lutz: Properties anpassen
		addMessage(i18n("message_group_left", groups.getName()));
		resetCachedData();
		return Constants.SUCCESS;
	}
	
	private void resetCachedData() {
		page = null;
	}
	
	private List<UserGroupInfo> getGroups() {
		if (groups == null) {
			groups = groupService.getGroupsByUser(user.getId());
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

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

}
