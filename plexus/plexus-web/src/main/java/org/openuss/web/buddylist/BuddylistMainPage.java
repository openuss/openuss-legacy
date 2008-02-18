package org.openuss.web.buddylist;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.buddylist.*;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.groups.GroupApplicationException;
import org.openuss.lecture.CourseApplicationException;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;
import org.openuss.web.BreadCrumbs;
import org.openuss.web.BasePage;

/**
 * 
 * @author Thomas Jansing
 * @author Ralf Plattfaut
 *
 */
@Bean(name = "views$secured$buddylist$buddylist", scope = Scope.REQUEST)
@View
public class BuddylistMainPage extends BasePage {
	
	private static final Logger logger = Logger.getLogger(BuddylistMainPage.class);
	
	@Property(value = "#{buddyService}")
	private BuddyService buddyService;
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("openuss4us_command_buddylist"));
		crumb.setHint(i18n("openuss4us_command_buddylist"));
		breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(crumb);
	}	
	
	public List<BuddyInfo> getBuddys(){
		logger.debug("read buddys");
		return buddyService.getBuddyList();
	}

	public void setBuddyService(BuddyService buddyService) {
		this.buddyService = buddyService;
	}
	
}
