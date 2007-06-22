package org.openuss.web.braincontest;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$braincontest$braincontestsolved", scope = Scope.REQUEST)
@View
public class BrainContestSolvedPage extends AbstractBrainContestPage {

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
	}

}