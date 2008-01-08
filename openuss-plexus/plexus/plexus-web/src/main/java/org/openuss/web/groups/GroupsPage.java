package org.openuss.web.groups;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.group.GroupAdminInformationInfo;
import org.openuss.group.GroupService;
import org.openuss.group.WorkingGroupInfo;
import org.openuss.web.Constants;

@Bean(name = "views$secured$groups$groups", scope = Scope.REQUEST)
@View
public class GroupsPage extends AbstractGroupPage{
	
	@Property(value = "#{groupService}")
	protected GroupService groupService;
	
//	protected List<GroupWrapper> groupListMembers;
//
//	protected List<GroupWrapper> groupListAspirants;
	
	protected GroupDataProvider groupAspirants1;
	protected GroupDataProvider groupAspirants2;
	protected GroupDataProvider groupAspirants3;
	protected GroupDataProvider groupAspirants4;
	protected GroupDataProvider groupAspirants5;
	protected WorkingGroupInfo group1;
	protected WorkingGroupInfo group2;
	protected WorkingGroupInfo group3;
	protected WorkingGroupInfo group4;
	protected WorkingGroupInfo group5;
	protected GroupDataProvider groupMembers1;
	protected GroupDataProvider groupMembers2;
	protected GroupDataProvider groupMembers3;
	protected GroupDataProvider groupMembers4;
	protected GroupDataProvider groupMembers5;
	protected boolean group1Existing;
	protected boolean group2Existing;
	protected boolean group3Existing;
	protected boolean group4Existing;
	protected boolean group5Existing;
	
	
	protected GroupAdminInformationInfo adminInfo;
	
	private void initRequestValues(){
		List<WorkingGroupInfo> groups = groupService.getGroups(courseInfo.getId());
		group1Existing = false;
		group2Existing = false;
		group3Existing = false;
		group4Existing = false;
		group5Existing = false;
		for (int i = 0; i < groups.size(); i++){
			if (i==0){
				group1 = groups.get(i);
				group1Existing = true;
				
			}
			if (i==1){
				group2 = groups.get(i);
				group2Existing = true;
			}
			if (i==2){
				group3 = groups.get(i);
				group3Existing = true;
			}
			if (i==3){
				group4 = groups.get(i);
				group4Existing = true;
			}
			if (i==4){
				group5 = groups.get(i);
				group5Existing = true;
			}
		}
		adminInfo = groupService.getAdminInfo(courseInfo.getId());
		if (adminInfo.isDeadlineReached()){
			for (int i = 0; i < groups.size(); i++){
				if (i == 0){
					groupMembers1 = new GroupDataProvider(groupService.getMembers(groups.get(i)), getNameWithSizeInfo(groups.get(i)));
				}
				if (i == 1){
					groupMembers2 = new GroupDataProvider(groupService.getMembers(groups.get(i)), getNameWithSizeInfo(groups.get(i)));
				}
				if (i == 2){
					groupMembers3 = new GroupDataProvider(groupService.getMembers(groups.get(i)), getNameWithSizeInfo(groups.get(i)));
				}
				if (i == 3){
					groupMembers4 = new GroupDataProvider(groupService.getMembers(groups.get(i)), getNameWithSizeInfo(groups.get(i)));
				}
				if (i == 4){
					groupMembers5 = new GroupDataProvider(groupService.getMembers(groups.get(i)), getNameWithSizeInfo(groups.get(i)));
				}
			}
		}
		if (!adminInfo.isDeadlineReached()){
			for (int i = 0; i < groups.size(); i++){
				if (i == 0){
					groupAspirants1 = new GroupDataProvider(groupService.getFilteredAspirants(groups.get(i)), getNameWithSizeInfo(groups.get(i)));
				}
				if (i == 1){
					groupAspirants2 = new GroupDataProvider(groupService.getFilteredAspirants(groups.get(i)), getNameWithSizeInfo(groups.get(i)));
				}
				if (i == 2){
					groupAspirants3 = new GroupDataProvider(groupService.getFilteredAspirants(groups.get(i)), getNameWithSizeInfo(groups.get(i)));
				}
				if (i == 3){
					groupAspirants4 = new GroupDataProvider(groupService.getFilteredAspirants(groups.get(i)), getNameWithSizeInfo(groups.get(i)));
				}
				if (i == 4){
					groupAspirants5 = new GroupDataProvider(groupService.getFilteredAspirants(groups.get(i)), getNameWithSizeInfo(groups.get(i)));
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();

//		groupListMembers = new ArrayList<GroupWrapper>();
//		adminInfo = groupService.getAdminInfo(courseInfo.getId());
//		if (adminInfo.isDeadlineReached()){
//			for (WorkingGroupInfo group:groups){
//				GroupWrapper gw = new GroupWrapper();
//				gw.setGroupInfo(group);
//				gw.setGroupData(new GroupDataProvider(groupService.getMembers(group)));
//				groupListMembers.add(gw);
//			}
//		} else if (!adminInfo.isDeadlineReached()){
//			groupListAspirants = new ArrayList<GroupWrapper>();
//			for (WorkingGroupInfo group:groups){
//				GroupWrapper gw = new GroupWrapper();
//				gw.setGroupInfo(group);
//				gw.setGroupData(new GroupDataProvider(groupService.getAspirants(group)));
//				groupListAspirants.add(gw);
//			}
//		}
	}
	
	public String assign(){
		groupService.assignAspirants(courseInfo.getId());
		initRequestValues();
		return Constants.GROUPS_GROUPS;
	}
	
	public String clear(){
		groupService.clearGroups(courseInfo.getId());
		initRequestValues();
		return Constants.GROUPS_GROUPS;
	}
	
	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}


//	public List<GroupWrapper> getGroupListMembers() {
//		return groupListMembers;
//	}
//
//
//	public void setGroupListMembers(List<GroupWrapper> groupListMembers) {
//		this.groupListMembers = groupListMembers;
//	}
//
//
//	public List<GroupWrapper> getGroupListAspirants() {
//		return groupListAspirants;
//	}
//
//
//	public void setGroupListAspirants(List<GroupWrapper> groupListAspirants) {
//		this.groupListAspirants = groupListAspirants;
//	}


	public GroupAdminInformationInfo getAdminInfo() {
		if (adminInfo==null){
			initRequestValues();
		}
		return adminInfo;
	}


	public void setAdminInfo(GroupAdminInformationInfo adminInfo) {
		this.adminInfo = adminInfo;
	}


	public GroupDataProvider getGroupAspirants1() {
		if (groupAspirants1==null){
			initRequestValues();
		}
		return groupAspirants1;
	}


	public void setGroupAspirants1(GroupDataProvider groupAspirants1) {
		this.groupAspirants1 = groupAspirants1;
	}


	public GroupDataProvider getGroupAspirants2() {
		if (groupAspirants2==null){
			initRequestValues();
		}
		return groupAspirants2;
	}


	public void setGroupAspirants2(GroupDataProvider groupAspirants2) {
		this.groupAspirants2 = groupAspirants2;
	}


	public GroupDataProvider getGroupAspirants3() {
		if (groupAspirants3==null){
			initRequestValues();
		}
		return groupAspirants3;
	}


	public void setGroupAspirants3(GroupDataProvider groupAspirants3) {
		this.groupAspirants3 = groupAspirants3;
	}


	public GroupDataProvider getGroupAspirants4() {
		if (groupAspirants4==null){
			initRequestValues();
		}
		return groupAspirants4;
	}


	public void setGroupAspirants4(GroupDataProvider groupAspirants4) {
		this.groupAspirants4 = groupAspirants4;
	}


	public GroupDataProvider getGroupAspirants5() {
		if (groupAspirants5==null){
			initRequestValues();
		}
		return groupAspirants5;
	}


	public void setGroupAspirants5(GroupDataProvider groupAspirants5) {
		this.groupAspirants5 = groupAspirants5;
	}


	public WorkingGroupInfo getGroup1() {
		if (group1==null){
			initRequestValues();
		}
		return group1;
	}


	public void setGroup1(WorkingGroupInfo group1) {
		this.group1 = group1;
	}


	public WorkingGroupInfo getGroup2() {
		if (group2==null){
			initRequestValues();
		}
		return group2;
	}


	public void setGroup2(WorkingGroupInfo group2) {
		this.group2 = group2;
	}


	public WorkingGroupInfo getGroup3() {
		if (group3==null){
			initRequestValues();
		}
		return group3;
	}


	public void setGroup3(WorkingGroupInfo group3) {
		this.group3 = group3;
	}


	public WorkingGroupInfo getGroup4() {
		if (group4==null){
			initRequestValues();
		}
		return group4;
	}


	public void setGroup4(WorkingGroupInfo group4) {
		this.group4 = group4;
	}


	public WorkingGroupInfo getGroup5() {
		if (group5==null){
			initRequestValues();
		}
		return group5;
	}


	public void setGroup5(WorkingGroupInfo group5) {
		this.group5 = group5;
	}


	public GroupDataProvider getGroupMembers1() {
		if (groupMembers1==null){
			initRequestValues();
		}
		return groupMembers1;
	}


	public void setGroupMembers1(GroupDataProvider groupMembers1) {
		this.groupMembers1 = groupMembers1;
	}


	public GroupDataProvider getGroupMembers2() {
		if (groupMembers2==null){
			initRequestValues();
		}
		return groupMembers2;
	}


	public void setGroupMembers2(GroupDataProvider groupMembers2) {
		this.groupMembers2 = groupMembers2;
	}


	public GroupDataProvider getGroupMembers3() {
		if (groupMembers3==null){
			initRequestValues();
		}
		return groupMembers3;
	}


	public void setGroupMembers3(GroupDataProvider groupMembers3) {
		this.groupMembers3 = groupMembers3;
	}


	public GroupDataProvider getGroupMembers4() {
		if (groupMembers4==null){
			initRequestValues();
		}
		return groupMembers4;
	}


	public void setGroupMembers4(GroupDataProvider groupMembers4) {
		this.groupMembers4 = groupMembers4;
	}


	public GroupDataProvider getGroupMembers5() {
		if (groupMembers5==null){
			initRequestValues();
		}
		return groupMembers5;
	}


	public void setGroupMembers5(GroupDataProvider groupMembers5) {
		this.groupMembers5 = groupMembers5;
	}


	public boolean isGroup1Existing() {
		return group1Existing;
	}


	public void setGroup1Existing(boolean group1Existing) {
		this.group1Existing = group1Existing;
	}


	public boolean isGroup2Existing() {
		return group2Existing;
	}


	public void setGroup2Existing(boolean group2Existing) {
		this.group2Existing = group2Existing;
	}


	public boolean isGroup3Existing() {
		return group3Existing;
	}


	public void setGroup3Existing(boolean group3Existing) {
		this.group3Existing = group3Existing;
	}


	public boolean isGroup4Existing() {
		return group4Existing;
	}


	public void setGroup4Existing(boolean group4Existing) {
		this.group4Existing = group4Existing;
	}


	public boolean isGroup5Existing() {
		return group5Existing;
	}


	public void setGroup5Existing(boolean group5Existing) {
		this.group5Existing = group5Existing;
	}

}
