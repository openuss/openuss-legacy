package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** Backing bean for the departmentremoveconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$departmentremoveconfirmation", scope = Scope.REQUEST)
@View
public class DepartmentRemoveConfirmationPage extends AbstractDepartmentPage {

	private static final long serialVersionUID = -202671219652888870L;

	@Prerender
	public void prerender() throws LectureException {
			// prerender nothing
	}
	
	@Preprocess
	public void preprocess() throws Exception {
			// preprocess nothing
	}
	
	/**
	 * Delete complete department tree (including all belonging institutes, course types and courses.
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeCompleteDepartmentTree() throws LectureException {
		departmentService.removeDepartment(departmentInfo.getId());
		addMessage(i18n("message_department_removed"));
		return Constants.DEPARTMENTS_PAGE;
	}
	
}
