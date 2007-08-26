package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;

@Bean(name = "views$public$department$department", scope = Scope.REQUEST)
@View
public class DepartmentPage extends AbstractDepartmentPage {

	@Prerender
	public void prerender() throws LectureException 
	{
		super.prerender();
		addBreadCrumbs();
	}
	
	private void addBreadCrumbs()
	{
		breadcrumbs.loadDepartmentCrumbs(departmentInfo);	
	}
}
