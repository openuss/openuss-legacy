package org.openuss.web.braincontest; 

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContestInfo;
import org.openuss.braincontest.BrainContestService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
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
	
	@Property(value = "#{systemService}")
	private SystemService systemService;

	@Prerender
	public void prerender() throws Exception{
		super.prerender();
		generateBreadCrumbs();
	}

	public void generateBreadCrumbs(){
		super.generateBreadCrumbs();
		BreadCrumb brainConstestCrumb = new BreadCrumb();
		brainConstestCrumb.setName(i18n("braincontest_main_header"));
		brainConstestCrumb.setHint(i18n("braincontest_main_header"));
		String brainContestArgument = "";
		if (brainContest.getId()!=null&&brainContest.getId()!=0) brainContestArgument = "&braincontest="+brainContest.getId();
		String link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+PageLinks.BRAINCONTEST_MAIN+"?course="+course.getId()+brainContestArgument;
		brainConstestCrumb.setLink(link);
		crumbs.add(brainConstestCrumb);
	}
	
	public BrainContestService getBrainContestService() {
		return brainContestService;
	}

	public void setBrainContestService(BrainContestService brainContestService) {
		this.brainContestService = brainContestService;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public BrainContestInfo getBrainContest() {
		return brainContest;
	}

	public void setBrainContest(BrainContestInfo brainContest) {
		this.brainContest = brainContest;
	}	
	

	
}