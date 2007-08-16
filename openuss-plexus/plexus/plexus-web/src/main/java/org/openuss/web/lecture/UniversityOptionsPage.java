package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.Period;
import org.openuss.news.NewsService;

/**
 *
 * @author Malte Stockmann
 * @author Kai Stettner
 */
@View
@Bean(name = "views$secured$lecture$universityoptions", scope = Scope.REQUEST)
public class UniversityOptionsPage extends AbstractUniversityPage {
	
	private static final long serialVersionUID = -1982354759705358593L;


	/**
	 * Refreshing university entity
	 * 
	 * @throws Exception
	 */
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
	}
	
	/**
	 * Save university options.
	 * @return outcome
	 * @throws LectureException
	 */
	public String saveUniversity() throws LectureException {
		universityService.update(universityInfo);
		addMessage(i18n("university_message_command_save_succeed"));
		return "success";
	}

}
