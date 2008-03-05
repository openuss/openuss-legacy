package org.openuss.web.seminarpool.courseAllocation;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;

@Bean(name = "views$secured$seminarpool$edit$courseAllocationCourseGroups", scope = Scope.REQUEST)
@View
public class SeminarAllocationEditStep1Page extends BasePage {

	
	private static final long serialVersionUID = 5069930000478432045L;
	
	private static final Logger logger = Logger.getLogger(SeminarAllocationEditStep1Page.class);

	@Prerender
	public void prerender() throws Exception {
		breadcrumbs.init();	
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("seminarpool_edit_course_allocation_breadcrumb_step1"));
		newCrumb.setHint(i18n("seminarpool_edit_course_allocation_breadcrumb_step1"));
		breadcrumbs.addCrumb(newCrumb);	
	}
}
