package org.openuss.web.groups; 

import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.group.GroupService;
import org.openuss.group.WorkingGroupInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.security.SecurityService;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

@Bean(name = "views$secured$groups$newgroup", scope = Scope.REQUEST)
@View
public class NewGroupPage extends AbstractCoursePage{
	
	@Property(value = "#{securityService}")
	protected SecurityService securityService;

	@Property(value = "#{groupService}")
	protected GroupService groupService;
	
	@Property(value = "#{workingGroup}")
	protected WorkingGroupInfo workingGroup;
	
	List<SelectItem> instituteMembers;
	
	Set<Long> assistantsUserIds;
	
	List<CourseMemberInfo> assistants;
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
	}
	
	public String saveGroup(){
		if (workingGroup.getId()!=null){
			getGroupService().changeGroup(workingGroup);			
		}else if (workingGroup.getId()==null){
			getGroupService().addGroup(workingGroup, courseInfo.getId());
		}
		addMessage(i18n("group_saved"));
		return Constants.COURSE_PAGE;
	}
	
	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public WorkingGroupInfo getWorkingGroup() {
		return workingGroup;
	}

	public void setWorkingGroup(WorkingGroupInfo workingGroup) {
		this.workingGroup = workingGroup;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public List<SelectItem> getInstituteMembers() {
		return instituteMembers;
	}

	public void setInstituteMembers(List<SelectItem> instituteMembers) {
		this.instituteMembers = instituteMembers;
	}
}