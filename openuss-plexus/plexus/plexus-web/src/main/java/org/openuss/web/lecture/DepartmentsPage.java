package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.lecture.DepartmentService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;




/**
 * 
 * @author Tianyu Wang
 * @author Weijun Chen
 *
 */
@Bean(name = "views$public$department$departments", scope = Scope.REQUEST)
@View
public class DepartmentsPage extends BasePage{

	private static final Logger logger = Logger.getLogger(InstitutesPage.class);

	private static final long serialVersionUID = 5069635767478432045L;
	
	private DepartmentTable departments = new DepartmentTable();
	
	@Property(value = "#{universityInfo}")
	protected UniversityInfo universityInfo;

	@Property(value = "#{departmentService}")
	private DepartmentService departmentService;
	
	@Prerender
	public void prerender() throws Exception {
		crumbs.clear();
	}	
	
	private DepartmentInfo currentDepartment() {
		
		DepartmentInfo department = departments.getRowData();
		
		return department;
	}
	
	private DataPage<DepartmentInfo> dataPage;
	
	public DataPage<DepartmentInfo> fetchDataPage(int startRow, int pageSize) {
		
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch institutes data page at " + startRow + ", "+ pageSize+" sorted by "+departments.getSortColumn());
			}
			List<DepartmentInfo> departmentList = new ArrayList<DepartmentInfo>();
		
			
			//sort(instituteList);
			dataPage = new DataPage<DepartmentInfo>(departmentList.size(),0,departmentList);
	}
		return dataPage;
	}

/*private void sort(List<InstituteDetails> instituteList) {
		if (StringUtils.equals("shortcut", departments.getSortColumn())) {
			Collections.sort(instituteList, new ShortcutComparator());
		} else if (StringUtils.equals("owner", departments.getSortColumn())){
			Collections.sort(instituteList, new OwnerComparator());
		} else {
			Collections.sort(instituteList, new NameComparator());
		}
	}*/

	public DepartmentTable getDepartments() {
		return departments;
	}

	private class DepartmentTable extends AbstractPagedTable<DepartmentInfo> {

		private static final long serialVersionUID = -6077435481342714879L;

		@Override
		public DataPage<DepartmentInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}
		
	}
	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public UniversityInfo getUniversityInfo() {
		return universityInfo;
	}

	public void setUniversityInfo(UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}
	
	/* ----------- institute sorting comparators -------------*/
	
	/*private class NameComparator implements Comparator<DepartmentInfo> {
		public int compare(InstituteDetails f1, InstituteDetails f2) {
			if (departments.isAscending()) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
	}

	private class OwnerComparator implements Comparator<InstituteDetails> {
		public int compare(InstituteDetails f1, InstituteDetails f2) {
			if (departments.isAscending()) {
				return f1.getOwnername().compareToIgnoreCase(f2.getOwnername());
			} else {
				return f2.getOwnername().compareToIgnoreCase(f1.getOwnername());
			}
		}
	}

	private class ShortcutComparator implements Comparator<InstituteDetails> {
		public int compare(InstituteDetails f1, InstituteDetails f2) {
			if (departments.isAscending()) {
				return f1.getShortcut().compareToIgnoreCase(f2.getShortcut());
			} else {
				return f2.getShortcut().compareToIgnoreCase(f1.getShortcut());
			}
		}
	}*/

	/*
	public String editDepartment(){
		DepartmentInfo Department = currentDepartment();
		setSessionBean(Constants.Department, Department);
		
		return Constants.Department_PAGE;
	}
	
	public String confirmRemoveDepartment(){
		DepartmentInfo Department = currentDepartment();
		setSessionBean(Constants.Department, Department);
		return "removed";
	}

	public String manageDepartment(){
		DepartmentInfo Department = currentDepartment();
		setSessionBean(Constants.Department, Department);
		
		return Constants.DEPARTMENT_MANAGEMENT;
	}
*/
}