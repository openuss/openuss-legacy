package org.openuss.web.lecture;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.DepartmentInfo;

/** 
 * Backing Bean for the views departments.xhtml and departmentstable.xhtml.
 * 
 * @author Malte Stockmann
 *
 */
@Bean(name = "views$public$department$departments", scope = Scope.REQUEST)
@View

public class DepartmentsPage extends AbstractDepartmentsOverviewPage{
	
	public List<DepartmentInfo> fetchDepartmentList(int startRow, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("fetch institutes data page at " + startRow + ", "+ pageSize+" sorted by "+departments.getSortColumn());
		}
		List<DepartmentInfo> departmentList = departmentService.findDepartmentsByUniversity(universityInfo.getId());
		return departmentList;
	}
}