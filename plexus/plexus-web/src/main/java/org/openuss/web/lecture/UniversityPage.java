package org.openuss.web.lecture;

import java.io.IOException;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;

/**
 * 
 * @author Malte Stockmann
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@View
@Bean(name = "views$public$university$university", scope = Scope.REQUEST)
public class UniversityPage extends AbstractUniversityPage {

	private static final long serialVersionUID = -1982354759705358593L;

	/**
	 * Refreshing university entity
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}

		if (universityInfo != null) {
			breadcrumbs.loadUniversityCrumbs(universityInfo.getId());
			addPageCrumb();
		}
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("university_command_university"));
		crumb.setHint(i18n("university_command_university"));

		breadcrumbs.loadUniversityCrumbs(universityInfo);
	}

}
