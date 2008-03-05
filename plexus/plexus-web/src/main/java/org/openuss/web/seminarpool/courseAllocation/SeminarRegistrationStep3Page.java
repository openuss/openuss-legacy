package org.openuss.web.seminarpool.courseAllocation;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;

@Bean(name = "views$secured$seminarpool$add$courseAllocationStep3", scope = Scope.REQUEST)
@View
public class SeminarRegistrationStep3Page extends BasePage {
		
	private static final Logger logger = Logger.getLogger(SeminarRegistrationStep3Page.class);
	
	
	@Prerender
	public void prerender() throws Exception  {
		breadcrumbs.init();	
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("seminarpool_add_course_allocation_breadcrumb_step3"));
		newCrumb.setHint(i18n("seminarpool_add_course_allocation_breadcrumb_step3"));
		breadcrumbs.addCrumb(newCrumb);	
	}
}
