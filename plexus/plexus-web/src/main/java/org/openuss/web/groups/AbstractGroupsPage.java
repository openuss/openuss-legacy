package org.openuss.web.groups;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.groups.GroupService;
import org.openuss.groups.UserGroupInfo;
import org.openuss.web.BasePage;

public class AbstractGroupsPage extends BasePage {
	
	@Property(value = "#{groupService}")
	protected GroupService groupService;
	
	@Property(value = "#{groupInfo}")
	protected UserGroupInfo userGroupInfo;
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink(contextPath()+"/views/secured/groups/groups.faces");
		newCrumb.setName(i18n("openuss4us_command_groups"));
		newCrumb.setHint(i18n("openuss4us_command_groups"));	
		breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(newCrumb);
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
