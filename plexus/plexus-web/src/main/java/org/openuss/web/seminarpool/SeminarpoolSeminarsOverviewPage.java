package org.openuss.web.seminarpool;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;


@Bean(name = "views$secured$seminarpool$seminarpoolseminars", scope = Scope.REQUEST)
@View
public class SeminarpoolSeminarsOverviewPage extends AbstractSeminarpoolPage {
	private static final long serialVersionUID = 5069930000478432045L;
	
	private static final Logger logger = Logger.getLogger(SeminarpoolSeminarsOverviewPage.class);

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("seminarpool_seminars_overview_breadcrumb"));
		newCrumb.setHint(i18n("seminarpool_seminars_overview_breadcrumb"));
		breadcrumbs.addCrumb(newCrumb);	
	}
}
