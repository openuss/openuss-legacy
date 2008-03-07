package org.openuss.web.course;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;

@Bean(name = "views$secured$course$calendar", scope = Scope.REQUEST)
@View
public class CourseCalendarPage extends AbstractCoursePage {

	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("breadcrumb_command_calendar"));
		crumb.setHint(i18n("breadcrumb_command_calendar"));
		breadcrumbs.addCrumb(crumb);
	}

}
