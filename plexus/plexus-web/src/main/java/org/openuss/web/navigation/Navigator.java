package org.openuss.web.navigation;

import java.util.ArrayList;
import java.util.List;

import org.apache.myfaces.custom.navmenu.NavigationMenuItem;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.Desktop;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.Period;
import org.openuss.web.Constants;

/**
 * Navigator Bean managed the navigation through the application 
 * @author Ingo Dueppe
 */
@Bean(name="navigator", scope=Scope.SESSION)
@View
public class Navigator extends BaseBean {

	private String lastView;
	
	/**
	 * Back to last view will move the last view
	 * @return viewId or outcome of the last
	 */
	public String goLastView() {
		return lastView;
	}
	
	
	/**
	 * @param lastView
	 */
	public void setLastView(String lastView) {
		this.lastView = lastView;
	}
	
	
	public List<NavigationMenuItem> getBreadCrumbs() {
		List<NavigationMenuItem> crumbs = new ArrayList<NavigationMenuItem>();
		
		Desktop desktop = (Desktop) getSessionBean(Constants.DESKTOP);
		if (desktop != null) {
			NavigationMenuItem crumbDesktop = new NavigationMenuItem(i18n("desktop"), Constants.DESKTOP);
			crumbs.add(crumbDesktop);
		}
		
		Faculty faculty = (Faculty) getSessionBean(Constants.FACULTY);
		if (faculty != null) {
			NavigationMenuItem crumbFaculty = new NavigationMenuItem("#{sessionScope.faculty.shortcut}", Constants.FACULTY);
			crumbs.add(crumbFaculty);
		}

		Period period = (Period) getSessionBean(Constants.PERIOD);
		if (period != null) {
			crumbs.add(new NavigationMenuItem("#{msg.faculty_command_periods}", Constants.FACULTY_PERIODS_PAGE));
		}
	
		return crumbs;
	}
	

}
