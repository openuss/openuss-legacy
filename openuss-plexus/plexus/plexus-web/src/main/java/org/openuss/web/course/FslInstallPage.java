package org.openuss.web.course;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;

@Bean(name="views$secured$course$fslinstall", scope=Scope.REQUEST)
@View
public class FslInstallPage extends AbstractCoursePage{

	@Prerender
	public void prerender() throws Exception{
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("freestyle_learning"));
		crumb.setHint(i18n("freestyle_learning"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}
}