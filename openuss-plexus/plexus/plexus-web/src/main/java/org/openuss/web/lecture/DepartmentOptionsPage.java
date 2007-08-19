package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * 
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$departmentoptions", scope = Scope.REQUEST)
@View
public class DepartmentOptionsPage extends AbstractDepartmentPage {

	private static final long serialVersionUID = -202799999652385870L;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("department_command_settings"));
		crumb.setHint(i18n("department_command_settings"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}	
	
	/**
	 * Save department options.
	 * @return outcome
	 * @throws LectureException
	 */
	public String saveDepartment() throws LectureException {
		departmentService.update(departmentInfo);
		addMessage(i18n("department_message_command_save_succeed"));
		return Constants.SUCCESS;
	}

}