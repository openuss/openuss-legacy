package org.openuss.web.seminarpool.courseAllocation;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;


@Bean(name = "views$secured$seminarpool$add$courseAllocationFinish", scope = Scope.REQUEST)
@View
public class SeminarRegistrationFinishPage extends BasePage {
	@Prerender
	public void prerender() throws Exception {
		breadcrumbs.init();	
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("seminarpool_add_course_allocation_breadcrumb_finish"));
		newCrumb.setHint(i18n("seminarpool_add_course_allocation_breadcrumb_finish"));
		breadcrumbs.addCrumb(newCrumb);
	}
}
