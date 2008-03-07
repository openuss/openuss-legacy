package org.openuss.web.seminarpool.allocation;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;


@Bean(name = "views$secured$seminarpool$allocation$userAllocationByUser", scope = Scope.REQUEST)
@View
public class UserAllocationByUserPage extends AbstractSeminarpoolPage {

	public static final Logger logger = Logger.getLogger(UserAllocationByUserPage.class);
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_allocation_breadcrumb_user_step1"));
		crumb.setHint(i18n("seminarpool_allocation_breadcrumb_user_step1"));
		breadcrumbs.addCrumb(crumb);
	}
}
