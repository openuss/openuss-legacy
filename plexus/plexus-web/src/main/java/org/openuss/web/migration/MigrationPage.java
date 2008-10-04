package org.openuss.web.migration;

import javax.naming.NamingException;

import org.acegisecurity.Authentication;
import org.acegisecurity.adapters.PrincipalAcegiUserToken;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetails;
import org.openuss.framework.web.acegi.shibboleth.ShibbolethUserDetailsImpl;
import org.openuss.migration.CentralUserData;
import org.openuss.security.SecurityDomainUtility;
import org.openuss.web.BasePage;
import org.openuss.web.PageLinks;

/**
 * @author Peter Schuh
 *
 */
@Bean(name = "views$secured$migration$migration", scope = Scope.REQUEST)
@View
public class MigrationPage extends BasePage {
	
	@Property(value="#{centralUserData}")
	CentralUserData centralUserData;
 	
	@Prerender
 	public void prerender() throws Exception {
		super.prerender();
		addBreadCrumbs();
		mapShibbolethUserAttributesToCentralUserData();
 	}

	private void mapShibbolethUserAttributesToCentralUserData() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof PrincipalAcegiUserToken && authentication.getDetails() instanceof ShibbolethUserDetails) {
			try {
				centralUserData.setAuthenticationDomainName((String)((ShibbolethUserDetails)authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINNAME_KEY).get());
				centralUserData.setAuthenticationDomainId((Long)((ShibbolethUserDetails)authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.AUTHENTICATIONDOMAINID_KEY).get());
				centralUserData.setUsername(SecurityDomainUtility.toUsername(centralUserData.getAuthenticationDomainName(), (String)((ShibbolethUserDetails) authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.USERNAME_KEY).get()));
				centralUserData.setFirstName((String)((ShibbolethUserDetails)authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.FIRSTNAME_KEY).get());
				centralUserData.setLastName((String)((ShibbolethUserDetails)authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.LASTNAME_KEY).get());
				centralUserData.setEmail((String)((ShibbolethUserDetails)authentication.getDetails()).getAttributes().get(ShibbolethUserDetailsImpl.EMAIL_KEY).get());
			} catch (NamingException e) {
				// do nothing
			}
		}
	}
 
	 /**
	 * Adds an additional BreadCrumb to the course crumbs.
	 */
	 private void addBreadCrumbs() {
		 breadcrumbs.setCrumbs(null);
	
		 final BreadCrumb migrationBreadCrumb = new BreadCrumb();
		 migrationBreadCrumb.setLink(PageLinks.MIGRATION_PAGE);
		 migrationBreadCrumb.setName(i18n("migration_navigation_header"));
		 migrationBreadCrumb.setHint(i18n("migration_navigation_header"));
		 breadcrumbs.addCrumb(migrationBreadCrumb);
	 }

	public CentralUserData getCentralUserData() {
		return centralUserData;
	}

	public void setCentralUserData(CentralUserData centralUserData) {
		this.centralUserData = centralUserData;
	}

}
