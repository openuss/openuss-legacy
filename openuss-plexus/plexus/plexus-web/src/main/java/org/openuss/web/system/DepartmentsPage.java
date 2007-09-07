package org.openuss.web.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;
import org.openuss.web.lecture.AbstractDepartmentsOverviewPage;

/** 
 * Backing Bean for the view secured/system/departments.xhtml
 * 
 * @author Malte Stockmann
 *
 */
@Bean(name = "views$secured$system$departments", scope = Scope.REQUEST)
@View

public class DepartmentsPage extends AbstractDepartmentsOverviewPage{
	
	public DataPage<DepartmentInfo> fetchDataPage(int startRow, int pageSize) {
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch institutes data page at " + startRow + ", "+ pageSize+" sorted by "+departments.getSortColumn());
			}
			List<DepartmentInfo> officialDepartmentList = new ArrayList<DepartmentInfo>(departmentService.findDepartmentsByType(DepartmentType.OFFICIAL));
			List<DepartmentInfo> nonOfficialDepartmentList = new ArrayList<DepartmentInfo>(departmentService.findDepartmentsByType(DepartmentType.NONOFFICIAL));
			
			List<DepartmentInfo> departmentList = new ArrayList<DepartmentInfo>();
			for(DepartmentInfo departmentInfo : officialDepartmentList){
				departmentList.add(departmentInfo);
			}
			for(DepartmentInfo departmentInfo : nonOfficialDepartmentList){
				departmentList.add(departmentInfo);
			}

			logger.info("Departments:"+departmentList);
			if (departmentList != null) {
				logger.info("Size:"+departmentList.size());
			}
			
			sort(departmentList);
			dataPage = new DataPage<DepartmentInfo>(departmentList.size(),0,departmentList);
		}
		return dataPage;
	}
}