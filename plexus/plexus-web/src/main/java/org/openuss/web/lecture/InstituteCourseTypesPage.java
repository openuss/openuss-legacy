package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * View for the course types of an institution
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$coursetypes", scope = Scope.REQUEST)
@View
public class InstituteCourseTypesPage extends AbstractLecturePage {
	
	private static final long serialVersionUID = -202789013476385870L;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("coursetype_coursetypestable_header"));
		crumb.setHint(i18n("coursetype_coursetypestable_header"));
		
		breadcrumbs.loadInstituteCrumbs(instituteInfo);
		breadcrumbs.addCrumb(crumb);
	}	

}
