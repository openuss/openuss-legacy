package org.openuss.web.seminarpool.courseAllocation;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.LectureException;
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

@Bean(name = "views$secured$seminarpool$add$start", scope = Scope.REQUEST)
@View
public class SeminarRegistrationStartPage extends BasePage {

	
	@Prerender
	public void prerender() throws Exception {
		breadcrumbs.init();	
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("seminarpool_add_course_allocation_breadcrumb_start"));
		newCrumb.setHint(i18n("seminarpool_add_course_allocation_breadcrumb_start"));
		breadcrumbs.addCrumb(newCrumb);
	}
	

}
