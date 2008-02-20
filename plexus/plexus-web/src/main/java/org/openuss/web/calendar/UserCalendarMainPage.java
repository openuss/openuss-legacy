package org.openuss.web.calendar;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.groups.UserGroupInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.groups.GroupsMainPage;

@Bean(name = "views$secured$calendar$calendar", scope = Scope.REQUEST)
@View
public class UserCalendarMainPage extends BasePage {

	private static final Logger logger = Logger.getLogger(UserCalendarMainPage.class);
	private static final String userCalendarBasePath = "/views/secured/calendar/usercalendar.faces";
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();

		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink(contextPath()+userCalendarBasePath);
		newCrumb.setName(i18n("openuss4us_command_groups"));
		newCrumb.setHint(i18n("openuss4us_command_groups"));	
		breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
	
	// createAppointment 
	// TODO Thomas: Implement
	public String createAppointment() {
		
		// TODO Thomas: Implement
		return Constants.OPENUSS4US_CALENDAR;
	}
	
}