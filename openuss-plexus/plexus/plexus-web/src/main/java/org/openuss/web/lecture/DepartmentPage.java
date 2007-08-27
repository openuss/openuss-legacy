package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

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
	
	/**
	 * Adds a shortcut to the department
	 * @return
	 */
	public String addShortcut()
	{
		try {
			desktopService2.linkDepartment(desktopInfo.getId(), departmentInfo.getId());
		} catch (Exception e) {
			addError(i18n("department_error_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		this.addMessage(i18n("department_success_shortcut"));
		return Constants.SUCCESS;
	}
	
	/**
	 * Removes the shortcut to the department
	 * @return
	 */
	public String removeShortcut()
	{
		try {
			desktopService2.unlinkDepartment(desktopInfo.getId(), departmentInfo.getId());
		} catch (Exception e) {
			addError(i18n("department_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		addMessage(i18n("department_success_remove_shortcut"));
		return Constants.SUCCESS;
	}
}
