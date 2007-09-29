package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;

/** 
 * Backing Bean for the views nonofficialdepartmentstableoverview.xhtml
 * 
 * @author Malte Stockmann
 *
 */
@Bean(name = "views$public$department$NonOfficialDepartments", scope = Scope.REQUEST)
@View
public class NonOfficialDepartmentsPage extends AbstractDepartmentsOverviewPage{

	public DataPage<DepartmentInfo> fetchDataPage(int startRow, int pageSize) {
		
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch institutes data page at " + startRow + ", "+ pageSize+" sorted by "+departments.getSortColumn());
			}
			List<DepartmentInfo> departmentList = new ArrayList<DepartmentInfo>(departmentService.findDepartmentsByUniversityAndTypeAndEnabled(universityInfo.getId(), DepartmentType.NONOFFICIAL, true));

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