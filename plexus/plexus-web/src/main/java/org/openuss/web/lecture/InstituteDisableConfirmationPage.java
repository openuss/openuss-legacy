package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** Backing bean for the institutedisableconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$institutedisableconfirmation", scope = Scope.REQUEST)
@View
public class InstituteDisableConfirmationPage extends AbstractLecturePage {

	private static final long serialVersionUID = -202671219652111170L;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		breadcrumbs.loadInstituteCrumbs(instituteInfo);
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("institute_disable_header"));
		newCrumb.setHint(i18n("institute_disable_header"));
		
		breadcrumbs.addCrumb(newCrumb);
	}
	
	/**
	 * Disables the chosen institute and all belonging course types and courses. This is just evident for the search indexing.
	 * @return Outcome
	 */
	public String disableInstitute() {
		logger.debug("Starting method disableInstitute");
		// setOrganisationStatus(true) = Enabled
		// setOrganisationStatus(false) = Disabled
		instituteService.setInstituteStatus(instituteInfo.getId(), false);
		
		addMessage(i18n("message_institute_disabled"));
		return Constants.OUTCOME_BACKWARD;
	}
	
}
