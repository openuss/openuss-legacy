package org.openuss.web.groups;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.groups.GroupService;
import org.openuss.groups.UserGroupInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

public class AbstractGroupsPage extends BasePage {
	
	@Property(value = "#{groupService}")
	protected GroupService groupService;
	
	@Property(value = "#{groupInfo}")
	protected UserGroupInfo userGroupInfo;
	
	@Override
	@Prerender
	public void prerender() throws Exception { // NOPMD by devopenuss on 11.03.08 14:22
		super.prerender();
		breadcrumbs.loadBaseCrumbs();
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public UserGroupInfo getUserGroupInfo() {
		return userGroupInfo;
	}

	public void setUserGroupInfo(UserGroupInfo userGroupInfo) {
		this.userGroupInfo = userGroupInfo;
	}

}
