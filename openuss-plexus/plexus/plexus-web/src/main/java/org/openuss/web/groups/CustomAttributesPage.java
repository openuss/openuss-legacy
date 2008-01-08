package org.openuss.web.groups; 

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.group.CustomInfoInfo;
import org.openuss.group.GroupAdminInformationInfo;
import org.openuss.group.GroupService;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

@Bean(name = "views$secured$groups$admin_custom", scope = Scope.REQUEST)
@View
public class CustomAttributesPage extends AbstractCoursePage{
	
	private AttributeDataProvider attributes = new AttributeDataProvider();
	
	@Property(value = "#{groupService}")
	protected GroupService groupService;	
	
	@Property(value = "#{groupAdminInformationInfo}")
	protected GroupAdminInformationInfo groupAdminInformationInfo;		
	
	public String newValue;
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		groupAdminInformationInfo = groupService.getAdminInfo(courseInfo.getId());
		setSessionBean(Constants.GROUP_ADMIN_INFO, groupAdminInformationInfo);
	}	
	
	private class AttributeDataProvider extends AbstractPagedTable<CustomInfoInfo> {

		private static final long serialVersionUID = 597444250618991242L;
		
		private DataPage<CustomInfoInfo> page;
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<CustomInfoInfo> getDataPage(int startRow, int pageSize) {		
			List<CustomInfoInfo> al = groupService.getCustomParameters(groupAdminInformationInfo);
			page = new DataPage<CustomInfoInfo>(al.size(),0,al);
			return page;
		}

	}

	public String add(){
		groupService.addCustomInfo(newValue, courseInfo.getId());
		addMessage(i18n("group_custom_added"));
		return Constants.SUCCESS;
	}
	
	public String delete(){
		CustomInfoInfo ci = this.attributes.getRowData();
		groupService.deleteCustomInfo(ci);
		addMessage(i18n("group_custom_deleted"));
		return Constants.SUCCESS;
	}
	
	public String getNewValue() {
		return newValue;
	}
	
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public AttributeDataProvider getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributeDataProvider attributes) {
		this.attributes = attributes;
	}

	public GroupService getGroupService() {
		return groupService;
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



}