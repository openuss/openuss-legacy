package org.openuss.web.system;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.web.PageLinks;
import org.openuss.web.lecture.AbstractLdapAttributeMappingsOverviewPage;

/** 
 * Backing Bean for the view secured/system/departments.xhtml
 * 
 * 
 *
 */
@Bean(name = "views$secured$system$ldap$ldap_attributemapping", scope = Scope.REQUEST)
@View

public class LdapAttributeMappingsPage extends AbstractLdapAttributeMappingsOverviewPage{
	
	@Prerender
	public void prerender() {
		try {
			super.prerender();
		} catch (Exception e) {
			
		}
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("departmentList_header"));
		newCrumb.setHint(i18n("departmentList_header"));
		newCrumb.setLink(PageLinks.ADMIN_DEPARTMENTS);
		
		breadcrumbs.loadAdministrationCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
	
	public DataPage<AttributeMappingInfo> fetchDataPage(int startRow, int pageSize) {
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch attributemappings data page at " + startRow + ", "+ pageSize+" sorted by "+attributeMappings.getSortColumn());
			}
			List<AttributeMappingInfo> attributeMappingList = ldapConfigurationService.getAllAttributeMappings();
			

			logger.info("AttributeMappings:"+attributeMappingList);
			if (attributeMappingList != null) {
				logger.info("Size:"+attributeMappingList.size());
			}
			
			/*sort(roleAttributeKeyList);*/
			dataPage = new DataPage<AttributeMappingInfo>(attributeMappingList.size(),0,attributeMappingList);
		}
		
		/*
		 public DataPage<RoleAttributeKeyInfo> fetchDataPage(int startRow, int pageSize) {
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch roleattributekeys data page at " + startRow + ", "+ pageSize+" sorted by "+roleAttributeKeys.getSortColumn());
			}
			List<DepartmentInfo> officialDepartmentList = new ArrayList<DepartmentInfo>(departmentService.findDepartmentsByType(DepartmentType.OFFICIAL));
			List<DepartmentInfo> nonOfficialDepartmentList = new ArrayList<DepartmentInfo>(departmentService.findDepartmentsByType(DepartmentType.NONOFFICIAL));
			
			List<DepartmentInfo> departmentList = new ArrayList<DepartmentInfo>();
			for(DepartmentInfo departmentInfo : officialDepartmentList){
				departmentList.add(departmentInfo);
			}
			for(DepartmentInfo departmentInfo : nonOfficialDepartmentList){
				departmentList.add(departmentInfo);
			}

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