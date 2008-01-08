package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** Backing bean for the instituteremoveconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$instituteremoveconfirmation", scope = Scope.REQUEST)
@View
public class InstituteRemoveConfirmationPage extends AbstractLecturePage {

	private static final long serialVersionUID = -202671219652000070L;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		breadcrumbs.loadInstituteCrumbs(instituteInfo);	
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("institute_remove_header"));
		newCrumb.setHint(i18n("institute_remove_header"));
		breadcrumbs.addCrumb(newCrumb);
	}
	
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
	}
	
	/**
	 * Delete complete institute tree (including all belonging course types and courses)
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeCompleteInstituteTree() throws LectureException {
		try {
			instituteService.removeCompleteInstituteTree(instituteInfo.getId());
			setSessionBean("instituteInfo", null);
			setSessionBean("courseTypeInfo", null);
			setSessionBean("courseInfo", null);
			addMessage(i18n("message_institute_removed"));
			return Constants.OUTCOME_BACKWARD;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(i18n("message_institute_cannot_be_removed"));
			return Constants.OUTCOME_BACKWARD;
		}
	}	
}
