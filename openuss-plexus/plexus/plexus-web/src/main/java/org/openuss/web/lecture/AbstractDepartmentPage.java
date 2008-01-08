package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract Department Page
 * 
 * @author Kai Stettner
 * @author Tianyu Wang
 * @author Weijun Chen
 */
public abstract class AbstractDepartmentPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractDepartmentPage.class);

	@Property(value = "#{departmentInfo}")
	protected DepartmentInfo departmentInfo;

	@Property(value = "#{departmentService}")
	protected DepartmentService departmentService;

	@Property(value = "#{universityService}")
	protected UniversityService universityService;

	/**
	 * Refreshing department VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing department session object");
		if (departmentInfo != null) {
			if (departmentInfo.getId() != null) {
				departmentInfo = departmentService.findDepartment(departmentInfo.getId());
			} else {
				departmentInfo = (DepartmentInfo) getSessionBean(Constants.DEPARTMENT_INFO);
			}
		}

		setSessionBean(Constants.DEPARTMENT_INFO, departmentInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing department session object");
		refreshDepartment();
		if (departmentInfo == null || departmentInfo.getId() == null) {
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshDepartment() {
		if (departmentInfo != null) {
			if (departmentInfo.getId() != null) {
				departmentInfo = departmentService.findDepartment(departmentInfo.getId());
				setSessionBean(Constants.DEPARTMENT_INFO, departmentInfo);
			}
		}
	}

	public Boolean getBookmarked() {
		try {
			return desktopService2.isDepartmentBookmarked(departmentInfo.getId(), user.getId());
		} catch (Exception e) {

		}

		return false;
	}

	public DepartmentInfo getDepartmentInfo() {
		return departmentInfo;
	}

	public void setDepartmentInfo(DepartmentInfo departmentInfo) {
		this.departmentInfo = departmentInfo;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}

}
