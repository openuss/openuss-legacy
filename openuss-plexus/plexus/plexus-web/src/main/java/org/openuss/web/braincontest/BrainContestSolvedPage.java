package org.openuss.web.braincontest;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$braincontest$braincontestsolved", scope = Scope.REQUEST)
@View
public class BrainContestSolvedPage extends AbstractBrainContestPage {

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("braincontest_result_header"));
		crumb.setHint(i18n("braincontest_result_header"));
		crumbs.add(crumb);
		setRequestBean(Constants.BREADCRUMBS, crumbs);
	}
}