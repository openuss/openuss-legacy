package org.openuss.web.seminarpool.add;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.el.ValueBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Init;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.seminarpool.CourseGroupInfo;
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.seminarpool.SeminarpoolStatus;
import org.openuss.seminarpool.util.SeminarpoolInfoNameComparator;
import org.openuss.seminarpool.util.SeminarpoolInfoShortcutComperator;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;




/**
 * Backing bean for the Course to Seminarpool registration. Is responsible starting the
 * wizard, binding the values and registrating the Course.
 * 
 * @author PS-Seminarplatzvergabe
 * 
 */

@Bean(name = Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP1, scope = Scope.REQUEST)
@View
public class SeminarRegistrationStep1Page extends BasePage{
	
	private static final long serialVersionUID = 5069930000478432045L;
	
// 	Spring Beans
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService; 
	
	@Property(value = "#{universityService}")
	protected UniversityService universityService; 
	
	@Property(value = "#{instituteService}")
	protected InstituteService instituteService; 

	@Property(value = "#{departmentService}")
	protected DepartmentService departmentService; 
	
//		
	protected SeminarpoolOverview seminarpoolOverview;
	
	private DataPage<SeminarpoolInfo> dataPage;

	private static final Logger logger = Logger.getLogger(SeminarRegistrationStep1Page.class);

	private CourseInfo courseInfo;
	
	@Init
	public void init() throws Exception {
		courseInfo = (CourseInfo)getSessionBean(Constants.COURSE_INFO);
		seminarpoolOverview = new SeminarpoolOverview();
		CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo = (CourseSeminarpoolAllocationInfo)getSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO);
		if (courseSeminarpoolAllocationInfo == null){
			courseSeminarpoolAllocationInfo = new CourseSeminarpoolAllocationInfo();
			setSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO, courseSeminarpoolAllocationInfo);
		}
		courseSeminarpoolAllocationInfo.setCourseId(courseInfo.getId());
	}
	
	

	// Start Seminarpool Overview 

	public DataPage<SeminarpoolInfo> fetchDataPage(int startRow, int pageSize) {

		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch seminarpools data page at " + startRow + ", " + pageSize + " sorted by "
						+ seminarpoolOverview.getSortColumn());
			}
			InstituteInfo instituteInfo = getInstituteService().findInstitute(courseInfo.getInstituteId());
			DepartmentInfo departmentInfo = getDepartmentService().findDepartment(instituteInfo.getDepartmentId());
			UniversityInfo universityInfo = getUniversityService().findUniversity(departmentInfo.getUniversityId());
			List<SeminarpoolInfo> seminarpoolList = new ArrayList<SeminarpoolInfo>(getSeminarpoolAdministrationService().findSeminarpoolsByUniversity(universityInfo.getId()));
			sort(seminarpoolList);
			dataPage = new DataPage<SeminarpoolInfo>(seminarpoolList.size(), 0, seminarpoolList);
		}
		return dataPage;
	}

	private void sort(List<SeminarpoolInfo> seminarpoolList) {
		if (StringUtils.equals("shortcut", seminarpoolOverview.getSortColumn())) {
			Collections.sort(seminarpoolList, new SeminarpoolInfoShortcutComperator(seminarpoolOverview.isAscending()));
		} 
		else {
			Collections.sort(seminarpoolList, new SeminarpoolInfoNameComparator(seminarpoolOverview.isAscending()));
		}
	}

	private class SeminarpoolOverview extends AbstractPagedTable<SeminarpoolInfo> {

		private static final long serialVersionUID = 5069930000478432045L;
		
		@Override
		public DataPage<SeminarpoolInfo> getDataPage(int startRow, int pageSize) {
			logger.debug("Starting method getDataPage");
			return fetchDataPage(startRow, pageSize);
		}
	}
	
	// End Seminarpool Overview

	public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
		return seminarpoolAdministrationService;
	}

	public void setSeminarpoolAdministrationService(
			SeminarpoolAdministrationService seminarpoolAdministrationService) {
		this.seminarpoolAdministrationService = seminarpoolAdministrationService;
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}

	public SeminarpoolOverview getSeminarpoolOverview() {
		return seminarpoolOverview;
	}

	public void setSeminarpoolOverview(SeminarpoolOverview seminarpoolsOverview) {
		this.seminarpoolOverview = seminarpoolsOverview;
	}


	public InstituteService getInstituteService() {
		return instituteService;
	}


	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}


	public DepartmentService getDepartmentService() {
		return departmentService;
	}


	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}	
}
