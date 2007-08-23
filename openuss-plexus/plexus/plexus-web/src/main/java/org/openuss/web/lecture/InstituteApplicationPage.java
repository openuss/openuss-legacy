package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.ApplicationInfo;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * 
 * @author Tianyu Wang
 * @author Weijun Chen
 */
@Bean(name = "views$secured$lecture$instituteApplicationPage", scope = Scope.REQUEST)
@View
public class InstituteApplicationPage extends AbstractLecturePage {
	private static final long serialVersionUID = 20278675452385870L;

	@Property(value = "#{applicationInfo}")
	protected ApplicationInfo applicationInfo;

	private DepartmentsTable departments = new DepartmentsTable();

	private List<SelectItem> departmentItems;

	private List<DepartmentInfo> allDepartments;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("department_command_institutes"));
		crumb.setHint(i18n("department_command_institutes"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}

	/**
	 * @param startRow
	 *            row to start from
	 * @param pageSize
	 *            number of rows on each page
	 * @return
	 */
	private DataPage<DepartmentInfo> fetchDataPage(int startRow, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDataPage(" + startRow + "," + pageSize + ")");
		}

		logger.debug("instituteId:" + instituteInfo.getId());
		InstituteInfo institute = instituteService.findInstitute(instituteInfo.getId());
		logger.debug("departmentId" + institute.getDepartmentId());
		List<DepartmentInfo> departments = new ArrayList<DepartmentInfo>();

		logger.debug("Debug findDepartment");
		DepartmentInfo d = departmentService.findDepartment(institute.getDepartmentId());
		logger.debug("department name:" + d.getName());
		departments.add(d);

		return new DataPage<DepartmentInfo>(departments.size(), 0, departments);
	}

	/**
	 * LocalDataModel of Universitry Members
	 */
	private class DepartmentsTable extends AbstractPagedTable<DepartmentInfo> {

		private static final long serialVersionUID = 449438778521068451L;

		@Override
		public DataPage<DepartmentInfo> getDataPage(int startRow, int pageSize) {

			return fetchDataPage(startRow, pageSize);
		}
	}

	private Long getUniversityId() {
		Long departmentId = instituteService.findInstitute(instituteInfo.getId()).getDepartmentId();
		return departmentService.findDepartment(departmentId).getUniversityId();
	}

	public List<SelectItem> getAllDepartments() {
		departmentItems = new ArrayList<SelectItem>();

		logger.info("universityId:" + getUniversityId());
		allDepartments = departmentService.findDepartmentsByUniversity(getUniversityId());
		Iterator<DepartmentInfo> iter = allDepartments.iterator();
		DepartmentInfo department;

		while (iter.hasNext()) {
			department = iter.next();
			SelectItem item = new SelectItem(department.getId(), department.getName());
			departmentItems.add(item);
		}

		logger.info("DepartmentId:" + allDepartments.get(0).getId());
		return departmentItems;

	}

	public String apply() {
		logger.debug("Debug apply");
		/*
		 * 
		 * 
		 * logger.debug("DepartmentId"+ applicationInfo.getDepartmentId());
		 * 
		 * logger.debug("Descriptiony"+applicationInfo.getDescription());
		 */

		logger.debug("InstituteI" + instituteInfo.getId());
		applicationInfo.setApplyingUserId(user.getId());
		applicationInfo.setInstituteId(instituteInfo.getId());
		applicationInfo.setDepartmentId(7L);
		Long appId = instituteService.applyAtDepartment(applicationInfo);
		return Constants.SUCCESS;
	}

	public String signoffInstitute() {

		departmentService.signoffInstitute(instituteInfo.getId());
		return Constants.SUCCESS;
	}

	public DepartmentsTable getDepartments() {
		return departments;
	}

	public ApplicationInfo getApplicationInfo() {
		return applicationInfo;
	}

	public void setApplicationInfo(ApplicationInfo applicationInfo) {
		this.applicationInfo = applicationInfo;
	}

}