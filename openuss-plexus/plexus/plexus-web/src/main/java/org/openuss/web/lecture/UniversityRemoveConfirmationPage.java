package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** Backing bean for the universityremoveconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$universityremoveconfirmation", scope = Scope.REQUEST)
@View
public class UniversityRemoveConfirmationPage extends AbstractUniversityPage {

	private static final long serialVersionUID = -202776319652888870L;

	@Prerender
	public void prerender() throws LectureException {
			// prerender nothing
	}
	
	@Preprocess
	public void preprocess() throws Exception {
			// preprocess nothing
	}
	
	/**
	 * Delete complete university tree (including all belonging departments, institutes, course types and courses.
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeCompleteUniversityTree() throws LectureException {
		universityService.removeCompleteUniversityTree(universityInfo.getId());
		addMessage(i18n("message_university_removed"));
		return Constants.UNIVERSITIES_ADMIN_PAGE;
	}
	
}
