package org.openuss.web.system;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.UserDnPatternInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.lecture.AbstractLdapUserDnPatternsOverviewPage;

/** 
 * Backing Bean for the view secured/system/departments.xhtml
 * 
 * @author Peter Schuh
 * @author Christian Grelle 
 *
 */
@Bean(name = "views$secured$system$ldap$ldap_userdnpatterns", scope = Scope.REQUEST)
@View

public class LdapUserDnPatternsPage extends AbstractLdapUserDnPatternsOverviewPage{
	
	protected UserDnPatternTable userDnPatterns = new UserDnPatternTable();

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
		 myBreadCrumb.setLink(PageLinks.ADMIN_LDAP_USERDNPATTERN);
		 myBreadCrumb.setName(i18n("ldap_userdnpattern"));
		 myBreadCrumb.setHint(i18n("ldap_userdnpattern_hint"));
		 breadcrumbs.addCrumb(myBreadCrumb);
	 }
	

	protected UserDnPatternInfo currentUserDnPattern() {
		UserDnPatternInfo userDnPattern = userDnPatterns.getRowData();
		return userDnPattern;
	}

	/**
	 * Store the selected userDnPattern into session scope and go to userDnPattern
	 * remove confirmation page.
	 * 
	 * @return Outcome
	 */

	public String selectUserDnPatternAndConfirmRemove() throws Exception {
		logger.debug("Starting method selectUserDnPatternAndConfirmRemove");		
		setSessionBean(Constants.USERDNPATTERN_INFO, currentUserDnPattern());
		
		return Constants.USERDNPATTERN_CONFIRM_REMOVE_PAGE;
	}	
		


	public String removeUserDnPattern() throws Exception {
		try {
			logger.debug("Starting method selectUserDnPatternAndRemove");
			UserDnPatternInfo currentUserDnPattern = (UserDnPatternInfo) getSessionBean(Constants.USERDNPATTERN_INFO);
			if (currentUserDnPattern.getLdapServerIds() == null || currentUserDnPattern.getLdapServerIds().size()==0) {
				ldapConfigurationService.deleteUserDnPattern(currentUserDnPattern);
				setSessionBean(Constants.USERDNPATTERN_INFO, null);
				return Constants.LDAP_USERDNPATTERN_PAGE;
			} else {
				addMessage(i18n("message_ldap_userdnpattern_still_in_use_cannot_be_removed"));
				return Constants.LDAP_USERDNPATTERN_PAGE;
			  }
			}
			catch (Exception e) {
				addMessage(i18n("message_ldap_userdnpattern_cannot_be_removed"));
				return Constants.LDAP_USERDNPATTERN_PAGE;
			}
	}

		
	/**
	 * Store the selected UserDnPattern into session scope and go to userDnPattern
	 * edit page.
	 * 
	 * @return Outcome
	 */
	public String selectUserDnPatternAndEdit() {
		UserDnPatternInfo userDnPattern = currentUserDnPattern();
		setSessionBean(Constants.USERDNPATTERN_INFO, userDnPattern);		
		return Constants.LDAP_USERDNPATTERN_REGISTRATION_STEP1_PAGE;
	}
	 
	public String confirmRemoveUserDnPattern() {
		UserDnPatternInfo userDnPatternInfo = currentUserDnPattern();
		setSessionBean(Constants.USERDNPATTERN, userDnPatternInfo);
		return "removed";
	}

	
	public UserDnPatternTable getUserDnPatterns() {
		return userDnPatterns;
	}
	
	
	protected class UserDnPatternTable extends AbstractPagedTable<UserDnPatternInfo> {

		private DataPage<UserDnPatternInfo> dataPage;

		@Override
		public DataPage<UserDnPatternInfo> getDataPage(int startRow, int pageSize) {
			if (dataPage == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetch userdnpatterns data page at " + startRow + ", "+ pageSize+" sorted by "+userDnPatterns.getSortColumn());
				}
				List<UserDnPatternInfo> userDnPatternList = ldapConfigurationService.getAllUserDnPatterns();
				

				if (userDnPatternList != null) {
					logger.info("Size:"+userDnPatternList.size());
				}
				
				sort(userDnPatternList);
				dataPage = new DataPage<UserDnPatternInfo>(userDnPatternList.size(),0,userDnPatternList);
			}
			
				return dataPage;
		}

	}		
}