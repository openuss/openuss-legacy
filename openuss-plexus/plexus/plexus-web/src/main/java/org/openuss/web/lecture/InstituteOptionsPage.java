package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$instituteoptions", scope = Scope.REQUEST)
@View
public class InstituteOptionsPage extends AbstractLecturePage {

	private static final long serialVersionUID = -202776319652385870L;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("institute_command_settings"));
		crumb.setHint(i18n("institute_command_settings"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}	
	
	/**
	 * Save institute options.
	 * @return outcome
	 * @throws LectureException
	 */
	public String saveInstitute() throws LectureException {
		instituteService.update(instituteInfo);
		addMessage(i18n("institute_message_command_save_succeed"));
		return Constants.SUCCESS;
	}

}
