package org.openuss.web.chat;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$course$chatmain", scope = Scope.REQUEST)
@View
public class ChatMainPage extends AbstractCoursePage {

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("course_command_chat"));
		crumb.setHint(i18n("course_command_chat"));
		crumbs.add(crumb);
		setRequestBean(Constants.BREADCRUMBS, crumbs);
	}

}