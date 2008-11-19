package org.openuss.web.course;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;

@Bean(name="views$secured$course$fslinstall", scope=Scope.REQUEST)
@View
public class FslInstallPage extends AbstractCoursePage{

	@Prerender
	public void prerender() throws Exception{
		super.prerender();
		if (isRedirected()){
			return;
		}		
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("freestyle_learning"));
		crumb.setHint(i18n("freestyle_learning"));
		breadcrumbs.addCrumb(crumb);
	}
}