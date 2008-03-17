package org.openuss.web.braincontest; 

import org.acegisecurity.Authentication;
import org.acegisecurity.acl.AclEntry;
import org.acegisecurity.acl.AclManager;
import org.acegisecurity.acl.basic.BasicAclEntry;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContestInfo;
import org.openuss.braincontest.BrainContestService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.course.AbstractCoursePage;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$braincontest$braincontestmain", scope = Scope.REQUEST)
@View
public class AbstractBrainContestPage extends AbstractCoursePage{
	
	@Property(value = "#{braincontest_contest}")
	protected BrainContestInfo brainContest;
	
	@Property(value = "#{brainContestService}")
	private BrainContestService brainContestService;
	
	@Property(value = "#{aclManager}")
	protected AclManager aclManager;
	
	
	@Prerender
	public void prerender() throws Exception{ // NOPMD idueppe
		super.prerender();
		if (isRedirected()){
			return;
		}
		if (brainContest != null && brainContest.getId() != null){
			brainContest = brainContestService.getContest(brainContest);
			setBean(Constants.BRAINCONTENT_CONTEST, brainContest);
			if (brainContest != null && !brainContest.getDomainIdentifier().equals(courseInfo.getId())) {
				addError(i18n("braincontest_message_contest_not_found"));
				redirect(Constants.BRAINCONTEST_MAIN);
				return;
			}
		}
		if (brainContest != null) {
			generateBreadCrumbs();
		}
	}

	public void generateBreadCrumbs(){
		breadcrumbs.loadCourseCrumbs(courseInfo);
		
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("braincontest_main_header"));
		crumb.setHint(i18n("braincontest_main_header"));
		crumb.setLink(PageLinks.BRAINCONTEST_MAIN);
		crumb.addParameter("course", courseInfo.getId());
		crumb.addParameter("braincontest",brainContest.getId());
		breadcrumbs.addCrumb(crumb);
	}
	
	protected boolean isAssistant(){
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AclEntry[] acls = aclManager.getAcls(courseInfo, auth);
		if ((acls != null) && acls.length > 0) {
			for (AclEntry aclEntry : acls) {
				if (aclEntry instanceof BasicAclEntry) {
					BasicAclEntry processableAcl = (BasicAclEntry) aclEntry;
					if (processableAcl.isPermitted(LectureAclEntry.ASSIST)) { //NOPMD idueppe
						return true;
					}
				}
			}
		}
		return false;

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

	public AclManager getAclManager() {
		return aclManager;
	}

	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}	
	

	
}