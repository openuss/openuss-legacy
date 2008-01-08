package org.openuss.web.groups;

import org.openuss.group.WorkingGroupInfo;

public class GroupWrapper {
	GroupDataProvider groupAspirants;
	
	GroupDataProvider groupMembers;
	
	WorkingGroupInfo groupInfo;
	
	boolean existing;

	public WorkingGroupInfo getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(WorkingGroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

	public boolean isExisting() {
		return existing;
	}

	public void setExisting(boolean existing) {
		this.existing = existing;
	}

	public GroupDataProvider getGroupAspirants() {
		return groupAspirants;
	}

	public void setGroupAspirants(GroupDataProvider groupAspirants) {
		this.groupAspirants = groupAspirants;
	}

	public GroupDataProvider getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(GroupDataProvider groupMembers) {
		this.groupMembers = groupMembers;
	}
}
