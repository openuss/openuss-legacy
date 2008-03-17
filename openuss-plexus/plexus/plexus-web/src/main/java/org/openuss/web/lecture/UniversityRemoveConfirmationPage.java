package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** Backing bean for the universityremoveconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$universityremoveconfirmation", scope = Scope.REQUEST)
@View
public class UniversityRemoveConfirmationPage extends AbstractUniversityPage {

	private static final long serialVersionUID = -202776319652888870L;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		breadcrumbs.loadUniversityCrumbs(universityInfo);
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("university_command_remove"));
		newCrumb.setHint(i18n("university_command_remove"));
		breadcrumbs.addCrumb(newCrumb);
	}
	
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
	}
	
	/**
	 * Delete complete university tree (including all belonging departments, institutes, course types and courses)
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeCompleteUniversityTree() throws LectureException {
		try {
			universityService.removeCompleteUniversityTree(universityInfo.getId());
			setBean("universityInfo", null);
			setBean("departmentInfo", null);
			setBean("instituteInfo", null);
			setBean("courseTypeInfo", null);
			setBean("courseInfo", null);
			addMessage(i18n("message_university_removed"));
			return Constants.UNIVERSITIES_ADMIN_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(i18n("message_university_cannot_be_removed"));
			return Constants.UNIVERSITIES_ADMIN_PAGE;
		}
		
	}
	
}
