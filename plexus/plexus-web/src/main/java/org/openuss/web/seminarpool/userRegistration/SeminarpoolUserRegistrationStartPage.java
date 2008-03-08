package org.openuss.web.seminarpool.userRegistration;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;

@Bean(name = "views$secured$seminarpool$userRegistration$seminarpooluserregistration", scope = Scope.REQUEST)
@View
public class SeminarpoolUserRegistrationStartPage extends
		AbstractSeminarpoolPage {

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_user_registration_breadcrumb_start"));
		crumb.setHint(i18n("seminarpool_user_registration_breadcrumb_start"));
		breadcrumbs.addCrumb(crumb);
	}
}
