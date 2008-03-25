package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** Backing bean for the universitydisableconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$universitydisableconfirmation", scope = Scope.REQUEST)
@View
public class UniversityDisableConfirmationPage extends AbstractUniversityPage {

	private static final long serialVersionUID = -202776578652888870L;
	
	private static final Logger logger = Logger.getLogger(UniversityDisableConfirmationPage.class);

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		breadcrumbs.loadUniversityCrumbs(universityInfo);
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("university_command_disable"));
		newCrumb.setHint(i18n("university_command_disable"));
		breadcrumbs.addCrumb(newCrumb);
	}
	
	/**
	 * Disables the chosen university. This is just evident for the search indexing.
	 * @return Outcome
	 */
	public String disableUniversity() {
		logger.debug("Starting method disableUniversity");
		// setOrganisationStatus(true) = Enabled
		// setOrganisationStatus(false) = Disbled
		universityService.setUniversityStatus(universityInfo.getId(), false);
		addMessage(i18n("message_university_disabled"));
		return Constants.OUTCOME_BACKWARD;
	}
	

	
}
