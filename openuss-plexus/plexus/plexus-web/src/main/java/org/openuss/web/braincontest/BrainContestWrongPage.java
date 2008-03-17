package org.openuss.web.braincontest;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$braincontest$braincontestwrong", scope = Scope.REQUEST)
@View
public class BrainContestWrongPage extends AbstractBrainContestPage {
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}		
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("braincontest_result_header"));
		crumb.setHint(i18n("braincontest_result_header"));
		breadcrumbs.addCrumb(crumb);
	}
}