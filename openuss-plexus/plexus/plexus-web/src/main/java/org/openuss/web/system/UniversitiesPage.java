package org.openuss.web.system;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;
import org.openuss.web.PageLinks;

/**
 * Universities for System-Admin
 * 
 * @author Julian Reimann
 */
@Bean(name="views$secured$system$universities", scope=Scope.REQUEST)
@View
public class UniversitiesPage extends BasePage{

	@Prerender
	public void prerender() {
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("admin_command_universities"));
		newCrumb.setLink(PageLinks.ADMIN_UNIVERSITIES);
		
		breadcrumbs.loadAdministrationCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}

}
