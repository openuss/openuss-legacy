package org.openuss.web.internalmessage;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;
import org.openuss.web.buddylist.BuddylistMainPage;

/**
 * 
 * @author Ralf Plattfaut
 *
 */
@Bean(name = "views$secured$internalMessages$messagecenter", scope = Scope.REQUEST)
@View
public class InternalMessageMainPage extends BasePage {
	private static final Logger logger = Logger
			.getLogger(BuddylistMainPage.class);

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("openuss4us_command_messagecenter"));
		crumb.setHint(i18n("openuss4us_command_messagecenter"));
		breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(crumb);
	}	
}
