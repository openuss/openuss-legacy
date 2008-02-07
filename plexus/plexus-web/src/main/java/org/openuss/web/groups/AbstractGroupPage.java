package org.openuss.web.groups;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.groups.GroupInfo;
import org.openuss.groups.GroupService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Lutz D. Kramer
 *
 */
public class AbstractGroupPage extends BasePage {
		
		private static final long serialVersionUID = 1394531398550932611L;
		
		@Property(value = "#{groupInfo}")
		protected GroupInfo groupInfo;
		
		@Property(value = "#{groupService}")
		protected GroupService groupService;

		@Prerender
		public void prerender() throws Exception {
			if (groupInfo != null && groupInfo.getId() != null) {
				groupInfo = groupService.getGroupInfo(groupInfo.getId());
			}
			if (groupInfo == null) {
				// TODO - Lutz: Properties anpassen
				addError(i18n("message_error_group_page"));
				redirect(Constants.OUTCOME_BACKWARD);
				return;
			} else {
				breadcrumbs.loadGroupCrumbs(groupInfo);
				setSessionBean(Constants.GROUP_INFO, groupInfo);
			}
		}

		public GroupService getGroupService() {
			return groupService;
		}

		public void setGroupService(GroupService groupService) {
			this.groupService = groupService;
		}

		public GroupInfo getGroupInfo() {
			return groupInfo;
		}

		public void setGroupInfo(GroupInfo GroupInfo) {
			this.groupInfo = GroupInfo;
		}
}
