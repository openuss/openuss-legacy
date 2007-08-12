package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.DepartmentInfo;

import org.openuss.lecture.LectureException;
import org.openuss.lecture.DepartmentService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * Abstract Department Page
 * 
 * @author Tianyu Wang
 * @author Weijun Chen
 */
public abstract class AbstractDepartmentPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractDepartmentPage.class);

	@Property(value = "#{department}")
	protected DepartmentInfo department;

	@Property(value = "#{departmentService}")
	protected DepartmentService departmentService;
	
	

		/**
	 * Refreshing institute entity
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing institute session object");
		if (department != null) {
			department = departmentService.findDepartment(department.getId());
		} else {
			department = (DepartmentInfo) getSessionBean(Constants.DEPARTMENT);
		}
		setSessionBean(Constants.DEPARTMENT, department);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing department session object");
		refreshDepartment();
		if (department == null) {
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
		} 
			generateCrumbs();
		
	}

	private void refreshDepartment() {
		if (department != null) {
			department = departmentService.findDepartment(department.getId());
			setSessionBean(Constants.DEPARTMENT, department);
		}
	}

	private void generateCrumbs() {
		crumbs.clear();
		BreadCrumb departmentCrumb = new BreadCrumb();
		departmentCrumb.setName(department.getShortcut());
		departmentCrumb.setLink(PageLinks.DEPARTMENT_PAGE);
		departmentCrumb.addParameter("department", department.getId());
		departmentCrumb.setHint(department.getName());
		crumbs.add(departmentCrumb);
	}

	public DepartmentInfo getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentInfo department) {
		this. department = department;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
}
