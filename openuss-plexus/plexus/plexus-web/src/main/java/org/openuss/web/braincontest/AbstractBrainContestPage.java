package org.openuss.web.braincontest; 

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContestInfo;
import org.openuss.braincontest.BrainContestService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.PageLinks;
import org.openuss.web.course.AbstractCoursePage;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$braincontest$braincontestmain", scope = Scope.REQUEST)
@View
public class AbstractBrainContestPage extends AbstractCoursePage{
	
	@Property(value = "#{braincontest_contest}")
	private BrainContestInfo brainContest;
	
	@Property(value = "#{brainContestService}")
	private BrainContestService brainContestService;
	
	@Prerender
	public void prerender() throws Exception{
		super.prerender();
		generateBreadCrumbs();
	}

	public void generateBreadCrumbs(){
		super.generateBreadCrumbs();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("braincontest_main_header"));
		crumb.setHint(i18n("braincontest_main_header"));
		crumb.setLink(PageLinks.BRAINCONTEST_MAIN);
		crumb.addParameter("course", courseInfo.getId());
		if (brainContest.getId() != null && brainContest.getId() != 0) {
			crumb.addParameter("braincontest",brainContest.getId());
		}

		crumbs.add(crumb);
	}
	
	public BrainContestService getBrainContestService() {
		return brainContestService;
	}

	public void setBrainContestService(BrainContestService brainContestService) {
		this.brainContestService = brainContestService;
	}

	public BrainContestInfo getBrainContest() {
		return brainContest;
	}

	public void setBrainContest(BrainContestInfo brainContest) {
		this.brainContest = brainContest;
	}	
	

	
}