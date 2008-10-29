package org.openuss.web.lecture;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
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

	@SuppressWarnings("unchecked")
	public List<DepartmentInfo> fetchDepartmentList(int startRow, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("fetch institutes data page at " + startRow + ", "+ pageSize+" sorted by "+departments.getSortColumn());
		}
		List<DepartmentInfo> departmentList = departmentService.findDepartmentsByUniversityAndTypeAndEnabled(universityInfo.getId(), DepartmentType.NONOFFICIAL, true);
		
		return departmentList;
	}

}