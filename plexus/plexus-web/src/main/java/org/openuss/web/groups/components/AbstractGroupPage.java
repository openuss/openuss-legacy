package org.openuss.web.groups.components;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.groups.GroupService;
import org.openuss.groups.UserGroupInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.security.SecurityService;

/**
 * 
 * @author Lutz D. Kramer
 * @author Thomas Jansing
 *
 */
public class AbstractGroupPage extends BasePage {
		
		private static final long serialVersionUID = 2394531398550932611L;
		
		@Property(value = "#{groupInfo}")
		protected UserGroupInfo groupInfo;
		
		@Property(value = "#{groupService}")
		protected GroupService groupService;

		@Prerender
		public void prerender() throws Exception {
			if (groupInfo != null && groupInfo.getId() != null) {
				groupInfo = groupService.getGroupInfo(groupInfo.getId());
			}
			if (groupInfo == null) {
				addError(i18n("group_page not found!"));
				redirect(Constants.OUTCOME_BACKWARD);
				return;
			} else {
				addGroupCrumb();
				addError("No ERROR");
				setSessionBean(Constants.GROUP_INFO, groupInfo);
			}
		}

		private void addGroupCrumb() {
			BreadCrumb groupMain = new BreadCrumb();
			groupMain.setName(i18n("openuss4us_command_groups"));
			groupMain.setHint(i18n("openuss4us_command_groups"));
			groupMain.setLink(PageLinks.GROUPS_MAIN);
			// groupMain.addParameter("group",groupInfo.getId());
			// TODO: Thomas: change from openuss4us to group
			// -> breadcrumbs.loadGroupCrumbs(groupInfo);
			breadcrumbs.loadOpenuss4usCrumbs();
			breadcrumbs.addCrumb(groupMain);
		}
		
		public GroupService getGroupService() {
			return groupService;
		}

		public void setGroupService(GroupService groupService) {
			this.groupService = groupService;
		}

		public UserGroupInfo getGroupInfo() {
			return groupInfo;
		}

		public void setGroupInfo(UserGroupInfo GroupInfo) {
			this.groupInfo = GroupInfo;
		}
}
