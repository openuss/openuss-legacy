package org.openuss.web.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.GroupItem;
import org.openuss.security.SecurityService;
import org.openuss.web.BasePage;

/**
 * 
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$system$groups", scope=Scope.REQUEST)
@View
public class GroupsPage extends BasePage{

	private static final long serialVersionUID = -3339596434048309514L;
	
	@Property (value="#{securityService}")
	private SecurityService securityService;

	private GroupTable table = new GroupTable();

	private class GroupTable extends AbstractPagedTable<GroupItem> {
		
		private static final long serialVersionUID = 8632408249099301747L;
		
		private DataPage<GroupItem> page;
		
		@Override
		public DataPage<GroupItem> getDataPage(int startRow, int pageSize) {
			if (page == null){
				List<GroupItem> groups = new ArrayList(securityService.getAllGroups());
				sort(groups);
				page = new DataPage(groups.size(), 0, groups);
			}
			return page;
		}
	}
	
	/* ------------ properties ----------------- */

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public GroupTable getTable() {
		return table;
	}

	public void setTable(GroupTable data) {
		this.table = data;
	}

}
