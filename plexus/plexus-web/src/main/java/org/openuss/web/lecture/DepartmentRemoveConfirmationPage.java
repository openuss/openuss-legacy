package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.DepartmentServiceException;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** Backing bean for the departmentremoveconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$departmentremoveconfirmation", scope = Scope.REQUEST)
@View
public class DepartmentRemoveConfirmationPage extends AbstractDepartmentPage {

	private static final long serialVersionUID = -202671219652888870L;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		breadcrumbs.loadDepartmentCrumbs(departmentInfo);
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("department_command_remove"));
		newCrumb.setHint(i18n("department_command_remove"));
		breadcrumbs.addCrumb(newCrumb);
	}
	
	/**
	 * Deletes complete department tree (including all belonging institutes, course types and courses)
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeCompleteDepartmentTree() throws LectureException {
		try {
			departmentService.removeCompleteDepartmentTree(departmentInfo.getId());
			setBean("departmentInfo", null);
			setBean("instituteInfo", null);
			setBean("courseTypeInfo", null);
			setBean("courseInfo", null);
			addMessage(i18n("message_department_removed"));
			return Constants.DEPARTMENTS_ADMIN_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(i18n("message_department_cannot_be_removed"));
			return Constants.OUTCOME_BACKWARD;
		}
		
	}
	
	/**
	 * Deletes the selected department. Throws an exception if there are institutes, ecc. assigned to the department
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeDepartment() {
		try {
			departmentService.removeDepartment(departmentInfo.getId());
			setBean("departmentInfo", null);
			setBean("instituteInfo", null);
			setBean("courseTypeInfo", null);
			setBean("courseInfo", null);
			addMessage(i18n("message_department_removed"));
			return Constants.DEPARTMENTS_PAGE;
		} catch (DepartmentServiceException e) {
			addMessage(i18n("message_department_cannot_be_removed"));
			return Constants.OUTCOME_BACKWARD;
		}
		
	}
	
}
