package org.openuss.web.seminarpool.courseAllocation;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;

@Bean(name = "views$secured$seminarpool$edit$courseAllocationCourseSchedules", scope = Scope.REQUEST)
@View
public class SeminarAllocationEditStep2Page extends AbstractSeminarpoolPage{
	private static final long serialVersionUID = 5069930000478432045L;
	
	private static final Logger logger = Logger.getLogger(SeminarAllocationEditStep1Page.class);

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("seminarpool_edit_course_allocation_breadcrumb_step2"));
		newCrumb.setHint(i18n("seminarpool_edit_course_allocation_breadcrumb_step2"));
		breadcrumbs.addCrumb(newCrumb);	
	}
}
