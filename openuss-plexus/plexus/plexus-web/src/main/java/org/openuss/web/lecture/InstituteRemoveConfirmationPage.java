package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** Backing bean for the instituteremoveconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$instituteremoveconfirmation", scope = Scope.REQUEST)
@View
public class InstituteRemoveConfirmationPage extends AbstractLecturePage {

	private static final long serialVersionUID = -202671219652000070L;

	@Prerender
	public void prerender() throws LectureException {
			// prerender nothing
	}
	
	@Preprocess
	public void preprocess() throws Exception {
			// preprocess nothing
	}
	
	/**
	 * Delete complete institute tree (including all belonging course types and courses)
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeCompleteInstituteTree() throws LectureException {
		instituteService.removeCompleteInstituteTree(instituteInfo.getId());
		setSessionBean("instituteInfo", null);
		setSessionBean("courseTypeInfo", null);
		setSessionBean("courseInfo", null);
		addMessage(i18n("message_institute_removed"));
		return Constants.INSTITUTES_PAGE;
	}
	
}
