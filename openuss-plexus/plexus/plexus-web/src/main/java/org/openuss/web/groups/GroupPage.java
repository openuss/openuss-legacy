package org.openuss.web.groups;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.group.GroupAdminInformationInfo;
import org.openuss.group.GroupMemberInfo;
import org.openuss.group.GroupService;
import org.openuss.group.WorkingGroupInfo;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

@Bean(name = "views$secured$groups$group", scope = Scope.REQUEST)
@View
public class GroupPage extends AbstractCoursePage {
	
	@Property(value = "#{groupService}")
	protected GroupService groupService;

	@Property(value = "#{groupInfo}")
	protected WorkingGroupInfo groupInfo;
	
	protected GroupAdminInformationInfo adminInfo;
	
	private GroupMemberDataProvider groupMembers = new GroupMemberDataProvider();
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		adminInfo = groupService.getAdminInfo(courseInfo.getId());
		groupInfo = groupService.getGroup(groupInfo.getId());
		setSessionBean(Constants.GROUP_INFO, groupInfo);
	}
	
	
	public class GroupMemberDataProvider extends AbstractPagedTable<GroupMemberInfo> {

		private static final long serialVersionUID = 5974442506189912057L;
		
		private DataPage<GroupMemberInfo> page; 
		
		public void setDataPage(DataPage<GroupMemberInfo> page){
			this.page = page;
		}
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<GroupMemberInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<GroupMemberInfo> al = groupService.getMembers(groupInfo);
				page = new DataPage<GroupMemberInfo>(al.size(),0,al);
			}
			return page;
		}
		
		public DataPage<GroupMemberInfo> getDataPage(){
			return page;
		}
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public WorkingGroupInfo getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(WorkingGroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

	public GroupMemberDataProvider getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(GroupMemberDataProvider groupMembers) {
		this.groupMembers = groupMembers;
	}

	public GroupAdminInformationInfo getAdminInfo() {
		return adminInfo;
	}

	public void setAdminInfo(GroupAdminInformationInfo adminInfo) {
		this.adminInfo = adminInfo;
	}
}
