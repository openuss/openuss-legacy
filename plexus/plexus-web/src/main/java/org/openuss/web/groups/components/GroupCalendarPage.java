package org.openuss.web.groups.components;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;

@Bean(name = "views$secured$groups$components$calendar", scope = Scope.REQUEST)
@View
public class GroupCalendarPage extends AbstractGroupPage{
	
	@Override
	@Prerender
	public void prerender() throws Exception{ // NOPMD by devopenuss on 11.03.08 14:24
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("breadcrumb_command_calendar"));
		crumb.setHint(i18n("breadcrumb_command_calendar"));
		breadcrumbs.addCrumb(crumb);
	}

	
}
