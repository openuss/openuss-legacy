package org.openuss.web.system.ldap;

import java.util.ArrayList;
import java.util.List;

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

/** 
 * Backing Bean for attributeMappings overview page.
 * 
 * @author Peter Schuh
 * @author Christian Grelle
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$system$ldap$ldap_attributemappings", scope = Scope.REQUEST)
@View
public class LdapAttributeMappingsPage extends AbstractLdapPage{
	
	private AttributeMappingTable attributeMappings = new AttributeMappingTable();
	
	@Prerender
	public void prerender() {
		try {
			super.prerender();
			addBreadCrumbs();
		} catch (Exception e) {
			logger.error(e);
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
	public String selectAttributeMappingAndEdit() {
		AttributeMappingInfo attributeMapping = currentAttributeMapping();
		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMapping);
		
		return Constants.LDAP_ATTRIBUTEMAPPING_EDIT_PAGE;
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
		try {
			logger.debug("Starting method removeAttributeMapping");
			AttributeMappingInfo currentAttributeMapping = (AttributeMappingInfo) getSessionBean(Constants.ATTRIBUTEMAPPING_INFO);
			if (currentAttributeMapping.getAuthenticationDomainIds() == null || currentAttributeMapping.getAuthenticationDomainIds().size()==0) {
				ldapConfigurationService.deleteAttributeMapping(currentAttributeMapping);
				setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, null);
				addMessage(i18n("message_ldap_attributemapping_removed"));
				return Constants.LDAP_ATTRIBUTEMAPPING_PAGE;
			} else {
				addError(i18n("message_ldap_attributemapping_still_in_use_cannot_be_removed"));
				return Constants.LDAP_ATTRIBUTEMAPPING_PAGE;
			  }
			}
			catch (Exception e) {
				addError(e.getMessage());
				addError(i18n("message_ldap_attributemapping_cannot_be_removed"));
				return Constants.LDAP_ATTRIBUTEMAPPING_PAGE;
			}		
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
				} else attributeMappingList = new ArrayList<AttributeMappingInfo>();
				
				sort(attributeMappingList);
				dataPage = new DataPage<AttributeMappingInfo>(attributeMappingList.size(),0,attributeMappingList);
			}
			
			return dataPage;
		}

	}
	
}