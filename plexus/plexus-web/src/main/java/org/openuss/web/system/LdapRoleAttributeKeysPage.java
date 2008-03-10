package org.openuss.web.system;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.web.PageLinks;
import org.openuss.web.lecture.AbstractLdapRoleAttributeKeysOverviewPage;

/** 
 * Backing Bean for the view secured/system/departments.xhtml
 * 
 * 
 *
 */
@Bean(name = "views$secured$system$ldap$ldap_roleattributekeys", scope = Scope.REQUEST)
@View

public class LdapRoleAttributeKeysPage extends AbstractLdapRoleAttributeKeysOverviewPage{
	
	@Prerender
	public void prerender() {
		try {
			super.prerender();
			addBreadCrumbs();
		} catch (Exception e) {
			
		}	
	}
	
	/**
	 * Adds an additional BreadCrumb.
	 */
	 private void addBreadCrumbs() {		 
		 breadcrumbs.init();	
		 
		 BreadCrumb myBreadCrumb = new BreadCrumb();		 
		 myBreadCrumb.setLink(PageLinks.ADMIN_LDAP_INDEX);
		 myBreadCrumb.setName(i18n("ldap_index"));
		 myBreadCrumb.setHint(i18n("ldap_index"));
		 breadcrumbs.addCrumb(myBreadCrumb);
		 
		 myBreadCrumb = new BreadCrumb();
		 myBreadCrumb.setLink(PageLinks.ADMIN_LDAP_ROLEATTRIBUTEKEY);
		 myBreadCrumb.setName(i18n("ldap_roleattributekey"));
		 myBreadCrumb.setHint(i18n("ldap_roleattributekey_hint"));
		 breadcrumbs.addCrumb(myBreadCrumb);
	 }
	
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
			
//			sort(roleAttributeKeyList);
			dataPage = new DataPage<RoleAttributeKeyInfo>(roleAttributeKeyList.size(),0,roleAttributeKeyList);
		}
		
		return dataPage;
	}
}