package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.RoleAttributeKeyInfo;

/** 
 * Backing Bean for the views departments.xhtml and departmentstable.xhtml.
 * 
 * 
 *
 */
@Bean(name = "views$public$department$departments", scope = Scope.REQUEST)
@View

public class LdapRoleAttributeKeysPage extends AbstractLdapRoleAttributeKeysOverviewPage{
	
	public DataPage<RoleAttributeKeyInfo> fetchDataPage(int startRow, int pageSize) {
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch roleattributekeys data page at " + startRow + ", "+ pageSize+" sorted by "+roleAttributeKeys.getSortColumn());
			}
			List<RoleAttributeKeyInfo> roleAttributeKeyList = ldapConfigurationService.getAllRoleAttributeKeys();

			logger.info("RoleAttributeKeys:"+roleAttributeKeyList);
			if (roleAttributeKeyList != null) {
				logger.info("Size:"+roleAttributeKeyList.size());
			}
			
			/*sort(roleAttributeKeyList);*/
			dataPage = new DataPage<RoleAttributeKeyInfo>(roleAttributeKeyList.size(),0,roleAttributeKeyList);
		}
		
		/*
		 
		 public DataPage<RoleAttributeKeyInfo> fetchDataPage(int startRow, int pageSize) {
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch roleattributekeys data page at " + startRow + ", "+ pageSize+" sorted by "+roleAttributeKeys.getSortColumn());
			}
			List<RoleAttributeKeyInfo> roleAttributeKeyList = new ArrayList<RoleAttributeKeyInfo>(ldapConfigurationService.findDepartmentsByUniversity(universityInfo.getId()));

			logger.info("Departments:"+departmentList);
			if (departmentList != null) {
				logger.info("Size:"+departmentList.size());
			}
			
			sort(departmentList);
			dataPage = new DataPage<DepartmentInfo>(departmentList.size(),0,departmentList);
		}
		 
		 */
		
		
		return dataPage;
	}
}