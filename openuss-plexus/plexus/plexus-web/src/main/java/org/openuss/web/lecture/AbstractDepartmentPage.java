package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract Department Page
 * 
 * @author Kai Stettner
 * @author Tianyu Wang
 * @author Weijun Chen
 * @author Sebastian Roekens
 * 
 */
public abstract class AbstractDepartmentPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractDepartmentPage.class);

	@Property(value = "#{departmentInfo}")
	protected DepartmentInfo departmentInfo;

	@Property(value = "#{departmentService}")
	protected DepartmentService departmentService;

	@Property(value = "#{universityService}")
	protected UniversityService universityService;
	
	@Property (value="#{"+Constants.UNIVERSITY_INFO+"}")
	private UniversityInfo universityInfo;
	
	

	/**
	 * Refreshing department VO before apply values phase to be sure that request objects are restored.
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing department session object");
		refreshDepartment();
	}

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		logger.debug("prerender - refreshing department session object");
		refreshDepartment();
		if (departmentInfo == null || departmentInfo.getId() == null) {
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
			return;
		}
	}

	private void refreshDepartment() {
		if (departmentInfo != null && departmentInfo.getId() != null) {
			departmentInfo = departmentService.findDepartment(departmentInfo.getId());
			setBean(Constants.DEPARTMENT_INFO, departmentInfo);
		}
	}

	public Boolean getBookmarked() {
		if (desktopInfo == null || desktopInfo.getId() == null){
			refreshDesktop();
		}
		if (desktopInfo == null || desktopInfo.getId() == null){
			return false;
		}
		return desktopInfo.getDepartmentInfos().contains(departmentInfo);
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

	public UniversityInfo getUniversityInfo() {
		return universityInfo;
	}

	public void setUniversityInfo(UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}

}
