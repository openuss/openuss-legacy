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
	
	public boolean isBookmarked()
	{
		return true;
		// TODO Ask service layer if the department is bookmarked
	}
	
	public void shortcutDepartment()
	{
		try {
			desktopService2.linkDepartment(desktopInfo.getId(), departmentInfo.getId());
		} catch (Exception e) {
			addError(i18n("DEPARTMENT_ERROR_SHORTCUT"), e.getMessage());
		}
	}
	
	public void removeShortcurDepartment()
	{
		try {
			desktopService2.unlinkDepartment(desktopInfo.getId(), departmentInfo.getId());
		} catch (Exception e) {
			addError(i18n("department_error_remove_shortcut"), e.getMessage());
		}
	}
}
