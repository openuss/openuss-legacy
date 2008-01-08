package org.openuss.web.groups;

import java.util.ArrayList;
import java.util.List;

import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.group.GroupMemberInfo;

public class GroupDataProvider extends AbstractPagedTable<GroupMemberInfo> {

	private static final long serialVersionUID = 5974442506189912057L;
	
	private DataPage<GroupMemberInfo> page;
	
	private String groupName;
	
	
	public GroupDataProvider(List<GroupMemberInfo> al, String groupName){
		if (al==null){
			al = new ArrayList<GroupMemberInfo>();
		}
		sort(al);
		page = new DataPage<GroupMemberInfo>(al.size(),0,al);
		this.groupName = groupName;
	}
	
	public void setDataPage(DataPage<GroupMemberInfo> page){
		this.page = page;
	}
	
	@Override 
	public DataPage<GroupMemberInfo> getDataPage(int startRow, int pageSize) {
		return page;
	}
	
	public DataPage<GroupMemberInfo> getDataPage(){
		return page;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}