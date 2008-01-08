package org.openuss.web.groups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.group.GroupAdminInformationInfo;
import org.openuss.group.GroupMemberInfo;
import org.openuss.group.GroupService;
import org.openuss.group.WorkingGroupInfo;
import org.openuss.lecture.AccessType;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

@Bean(name = "views$secured$groups$assignment_custom", scope = Scope.REQUEST)
@View
public class CustomAssignmentPage extends AbstractGroupPage{
	
	@Property(value = "#{groupService}")
	protected GroupService groupService;
	
	@Property(value = "#{groupAdminInformationInfo}")
	protected GroupAdminInformationInfo groupAdminInformationInfo;	
		
	@Property(value = "#{sourceGroups}")
	protected Collection<SelectItem> sourceGroups;

	@Property(value = "#{targetGroups}")
	protected Collection<SelectItem> targetGroups;
	
	@Property(value = "#{sourceGroup}")
	protected String sourceGroup;
	
	@Property(value = "#{targetGroup}")
	protected String targetGroup;
	
	
	@Property(value = "#{source}")
	protected GroupDataProvider source;
	
	@Property(value = "#{target}")
	protected GroupDataProvider target;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		groupAdminInformationInfo = groupService.getAdminInfo(courseInfo.getId());
		setSessionBean(Constants.GROUP_ADMIN_INFO, groupAdminInformationInfo);
		initializeRequestInformation(false);
		
	}

	private void initializeRequestInformation(boolean overrideCurrent) {
		List<WorkingGroupInfo> groups = groupService.getGroups(courseInfo.getId());
		this.sourceGroups = new ArrayList<SelectItem>();
		this.targetGroups = new ArrayList<SelectItem>();
		for (WorkingGroupInfo group:groups){
			this.sourceGroups.add(new SelectItem(group.getId().toString(), getNameWithSizeInfo(group)));
			this.targetGroups.add(new SelectItem(group.getId().toString(), getNameWithSizeInfo(group)));
		}
		setSessionBean("sourceGroups", sourceGroups);
		setSessionBean("targetGroups", targetGroups);
		WorkingGroupInfo sourceWgi = groupService.getGroup(new Long((String)this.sourceGroups.iterator().next().getValue()));
		WorkingGroupInfo targetWgi = groupService.getGroup(new Long((String)this.targetGroups.iterator().next().getValue()));
		if (source==null||overrideCurrent){
			source = new GroupDataProvider(groupService.getMembers(sourceWgi), getNameWithSizeInfo(sourceWgi));
			setSessionBean("source", source);
		}
		if (target==null||overrideCurrent){
			target = new GroupDataProvider(groupService.getMembers(targetWgi), getNameWithSizeInfo(targetWgi));
			setSessionBean("target", target);
		}
		if (sourceGroup==null||overrideCurrent){
			sourceGroup = sourceWgi.getId().toString();
			setSessionBean("sourceGroup", sourceGroup);
		}
		if (targetGroup == null||overrideCurrent) {
			targetGroup = targetWgi.getId().toString();
			setSessionBean("targetGroup", targetGroup);
		}
	}
	
	public String move(){
		List<GroupMemberInfo> members = this.source.getDataPage().getData();
		for (GroupMemberInfo member:members){
			if (member.isSelected()) {
				groupService.moveMember(member, findGroup(sourceGroups,
						sourceGroup), findGroup(targetGroups, targetGroup));
			}
		}
		initializeRequestInformation(true);		
		return Constants.GROUPS_ASSIGN_CUSTOM;
	}

	public void changeTableEntrySelection(ValueChangeEvent event){
		Long id = source.getRowData().getId();
		for (GroupMemberInfo member: source.getDataPage().getData()){
			if (member.getId().longValue()==id.longValue()){
				member.setSelected(!member.isSelected());
			}
		}		
		setSessionBean("source", source);
		
	}
	
	public GroupService getGroupService() {
		return groupService;
	}
	
	public void newSourceGroup(ValueChangeEvent event){
		Long sourceGroup = new Long((String) event.getNewValue());
		WorkingGroupInfo sourceWorkingGroupInfo = groupService.getGroup(sourceGroup);
		setSource(new GroupDataProvider(groupService.getMembers(sourceWorkingGroupInfo), getNameWithSizeInfo(sourceWorkingGroupInfo)));
		setSessionBean("source", this.source);
	}
	
	public void newTargetGroup(ValueChangeEvent event){
		Long targetGroup = new Long((String) event.getNewValue());
		WorkingGroupInfo targetWorkingGroupInfo = groupService.getGroup(targetGroup);
		setTarget(new GroupDataProvider(groupService.getMembers(targetWorkingGroupInfo), getNameWithSizeInfo(targetWorkingGroupInfo)));		
		setSessionBean("target", this.target);
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public GroupAdminInformationInfo getGroupAdminInformationInfo() {
		return groupAdminInformationInfo;
	}

	public void setGroupAdminInformationInfo(
			GroupAdminInformationInfo groupAdminInformationInfo) {
		this.groupAdminInformationInfo = groupAdminInformationInfo;
	}

	public Collection<SelectItem> getGroups() {
		return sourceGroups;
	}

	public void setGroups(Collection<SelectItem> groups) {
		this.sourceGroups = groups;
	}

	public Collection<SelectItem> getTargetGroups() {
		return targetGroups;
	}

	public void setTargetGroups(Collection<SelectItem> targetGroups) {
		this.targetGroups = targetGroups;
	}

	public Collection<SelectItem> getSourceGroups() {
		return sourceGroups;
	}

	public void setSourceGroups(Collection<SelectItem> sourceGroups) {
		this.sourceGroups = sourceGroups;
	}

	public String getSourceGroup() {
		if (sourceGroup==null){
			sourceGroup = sourceGroups.iterator().next().getValue().toString();
		}
		return sourceGroup;
	}

	public void setSourceGroup(String sourceGroup) {
		this.sourceGroup = sourceGroup;
	}

	public String getTargetGroup() {
		if (targetGroup==null){
			targetGroup = targetGroups.iterator().next().getValue().toString();
		}
		return targetGroup;
	}

	public void setTargetGroup(String targetGroup) {
		this.targetGroup = targetGroup;
	}

	private WorkingGroupInfo findGroup(Collection<SelectItem> items, String groupId){
		for (SelectItem item:items){
			if (((String)item.getValue()).equals(groupId)){
				return groupService.getGroup(new Long((String)item.getValue()));
			}				
		}
		return null;
	}
	
	public GroupDataProvider getSource() {
		if (source==null){
			WorkingGroupInfo sourceWorkingGroupInfo = findGroup(sourceGroups, sourceGroup);
			source = new GroupDataProvider(groupService.getMembers(sourceWorkingGroupInfo), getNameWithSizeInfo(sourceWorkingGroupInfo));
		}
		return source;
	}

	public void setSource(GroupDataProvider source) {
		this.source = source;
		setSessionBean("source", this.source);
	}

	public GroupDataProvider getTarget() {
		if (target==null){
			WorkingGroupInfo targetWorkingGroupInfo = findGroup(targetGroups, targetGroup);
			target = new GroupDataProvider(groupService.getMembers(targetWorkingGroupInfo), getNameWithSizeInfo(targetWorkingGroupInfo));
		}
		return target;
	}

	public void setTarget(GroupDataProvider target) {
		this.target = target;
		setSessionBean("target", this.target);
	}

}