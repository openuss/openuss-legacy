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
import org.openuss.lecture.UniversityInfo;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;


/**
 * This page should be moved to the "Stammdaten view"
 * @author Tianyu Wang
 * @author Weijun Chen
 */
@Bean(name = "views$secured$lecture$instituteDepartments", scope = Scope.REQUEST)
@View
public class InstituteApplicationPage extends AbstractLecturePage{
	private static final long serialVersionUID = 20278675452385870L;
	

	
	@Property(value = "#{applicationInfo}")
	protected ApplicationInfo applicationInfo;
	
	private Long departmentId;
	
	
	
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
		
		 
		 
		 logger.debug("instituteId:"+instituteInfo.getId());
		 InstituteInfo institute = instituteService.findInstitute(instituteInfo.getId());
		 logger.debug("departmentId"+institute.getDepartmentId());
		 List<DepartmentInfo> departments = new ArrayList<DepartmentInfo>();
		 
		 logger.debug("Debug findDepartment");
		 DepartmentInfo d= departmentService.findDepartment(institute.getDepartmentId());
		 logger.debug("department name:"+d.getName());		 
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
	
	private Long getUniversityId(){
		Long departmentId = instituteService.findInstitute(instituteInfo.getId()).getDepartmentId();
		Long  universityId= departmentService.findDepartment(departmentId).getUniversityId();
		UniversityInfo universityInfo = universityService.findUniversity(universityId); 
		return universityInfo.getId();
	}
	public List<SelectItem> getAllDepartments(){
		departmentItems = new ArrayList<SelectItem>();
		
			
		logger.info("universityId:"+getUniversityId());
		allDepartments = departmentService.findDepartmentsByUniversity(getUniversityId());
		Iterator<DepartmentInfo> iter = allDepartments.iterator();
		DepartmentInfo department;
		
		while (iter.hasNext()){
			department = iter.next();
			SelectItem item = new SelectItem(department.getId(), department.getName());
			departmentItems.add(item);
		}
		
		logger.info("DepartmentId:" + allDepartments.get(0).getId());
		return departmentItems;
	
	}
	
	public String apply(){
		logger.debug("Debug apply");
	
		
		
		logger.debug("DepartmentId"+ departmentId);
		
		logger.debug("Descriptiony"+applicationInfo.getDescription());
	    
		
		logger.debug("InstituteI"+instituteInfo.getId());
		
		UserInfo userInfo = new UserInfo();
		userInfo.setId(user.getId());
		applicationInfo.setApplyingUserInfo(userInfo);
		
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setId(departmentId);
		applicationInfo.setDepartmentInfo(departmentInfo);
		
		applicationInfo.setInstituteInfo(instituteInfo);
		
		try{
		Long appId = instituteService.applyAtDepartment(applicationInfo);
			}
		catch(Exception e){;}
		
		
		return Constants.INSTITUTE_DEPARTMENTS_PAGE;
	}
	
  
    	
    public String signoffInstitute(){
    	
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

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}



}
