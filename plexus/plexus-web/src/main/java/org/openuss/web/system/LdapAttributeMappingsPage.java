package org.openuss.web.system;

import java.util.List;
import java.util.Vector;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.lecture.AbstractLdapAttributeMappingsOverviewPage;

/** 
 * Backing Bean for attributeMappings overview page.
 * 
 * @author Peter Schuh
 * @author Christian Grelle
 */
@Bean(name = "views$secured$system$ldap$ldap_attributemappings", scope = Scope.REQUEST)
@View

public class LdapAttributeMappingsPage extends AbstractLdapAttributeMappingsOverviewPage{
	
	private AttributeMappingTable attributeMappings = new AttributeMappingTable();

	
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
		 breadcrumbs.loadAdministrationCrumbs();	
		 
		 BreadCrumb myBreadCrumb = new BreadCrumb();		 
		 myBreadCrumb.setLink(PageLinks.ADMIN_LDAP_INDEX);
		 myBreadCrumb.setName(i18n("ldap_index"));
		 myBreadCrumb.setHint(i18n("ldap_index"));
		 breadcrumbs.addCrumb(myBreadCrumb);
		 
		 myBreadCrumb = new BreadCrumb();
		 myBreadCrumb.setLink(PageLinks.ADMIN_LDAP_ATTRIBUTEMAPPING);
		 myBreadCrumb.setName(i18n("ldap_attributemapping"));
		 myBreadCrumb.setHint(i18n("ldap_attributemapping_hint"));
		 breadcrumbs.addCrumb(myBreadCrumb);
	 }

	protected AttributeMappingInfo currentAttributeMapping() {
		AttributeMappingInfo attributeMapping = attributeMappings.getRowData();
		return attributeMapping;
	}

	/**
	 * Store the selected attributeMapping into session scope and go to attributeMapping
	 * main page.
	 * 
	 * @return Outcome
	 */
	public String selectAttributeMapping() {
		AttributeMappingInfo attributeMapping = currentAttributeMapping();
		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMapping);
		//TODO: CHRISTIAN: WRONG OUTCOME!!!!!
		return Constants.DEPARTMENT_PAGE;
	}
	
	
	/**
	 * Store the selected attributeMapping into session scope and go to attributeMapping
	 * main page.
	 * 
	 * @return Outcome
	 */
	public String selectAttributeMappingAndEdit() {
		AttributeMappingInfo attributeMapping = currentAttributeMapping();
		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMapping);
		
		// set selected items
		List<String> initiallySelectedRoleAttributeKeys = new Vector<String>();
		List<Long> selectedRoleAttributeKeyIds = attributeMapping.getRoleAttributeKeyIds();
		for (Long roleAttributeKeyId : selectedRoleAttributeKeyIds) {
			initiallySelectedRoleAttributeKeys.add(String.valueOf(roleAttributeKeyId));			
		}
		attributeMappingRegistrationController.setInitiallySelectedRoleAttributeKeyIds(initiallySelectedRoleAttributeKeys);

		return Constants.LDAP_ATTRIBUTEMAPPING_REGISTRATION_STEP1_PAGE;
	}

	/**
	 * Store the selected attributeMapping into session scope and go to attributeMapping
	 * remove confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectAttributeMappingAndConfirmRemove() {
		AttributeMappingInfo currentAttributeMapping = currentAttributeMapping();
		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, currentAttributeMapping);
		return Constants.ATTRIBUTEMAPPING_CONFIRM_REMOVE_PAGE;
	}
	
		
	public String removeAttributeMapping() throws Exception {
		
		AttributeMappingInfo currentAttributeMapping = currentAttributeMapping();
		ldapConfigurationService.deleteAttributeMapping(currentAttributeMapping);
		setSessionBean("attributeMappingInfo", null);
		addMessage(i18n("message_department_removed"));
		return Constants.LDAP_ATTRIBUTEMAPPING_PAGE;			
	}

	
	public AttributeMappingTable getAttributeMappings() {
			return attributeMappings;
	}

	
	protected class AttributeMappingTable extends AbstractPagedTable<AttributeMappingInfo> {
		
		private DataPage<AttributeMappingInfo> dataPage;
		
		@Override
		public DataPage<AttributeMappingInfo> getDataPage(int startRow, int pageSize) {
			if (dataPage == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetch attributemappings data page at " + startRow + ", "+ pageSize+" sorted by "+attributeMappings.getSortColumn());
				}
				List<AttributeMappingInfo> attributeMappingList = ldapConfigurationService.getAllAttributeMappings();
				

				logger.info("AttributeMappings:"+attributeMappingList);
				if (attributeMappingList != null) {
					logger.info("Size:"+attributeMappingList.size());
				}
				
				sort(attributeMappingList);
				dataPage = new DataPage<AttributeMappingInfo>(attributeMappingList.size(),0,attributeMappingList);
			}
			
			return dataPage;
		}

	}
	
}