package org.openuss.web.migration;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
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
 	}
 
	 /**
	 * Adds an additional BreadCrumb to the course crumbs.
	 */
	 private void addBreadCrumbs() {
		 breadcrumbs.setCrumbs(null);
		 List<BreadCrumb> crumbs = breadcrumbs.getCrumbs();
	
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
