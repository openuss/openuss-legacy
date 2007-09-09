package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** 
 * Backing bean for the departmentdisableconfirmation.xhtml view.
 * 
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$departmentdisableconfirmation", scope = Scope.REQUEST)
@View
public class DepartmentDisableConfirmationPage extends AbstractDepartmentPage {

	private static final long serialVersionUID = -202699999652888870L;
	
	private static final Logger logger = Logger.getLogger(DepartmentDisableConfirmationPage.class);

	@Prerender
	public void prerender() throws LectureException {
		// prerender nothing
	}
	
	@Preprocess
	public void preprocess() throws Exception {
		// preprocess nothing
	}
	
	/**
	 * Disables the chosen department and belonging institutes, course types 
	 * and course. This is just evident for the search indexing.
	 * @return Outcome
	 */
	public String disableDepartment() {
		logger.debug("Starting method disableDepartment");
		try{
			departmentService.setDepartmentStatus(departmentInfo.getId(), false);
			addMessage(i18n("message_department_disabled"));
		} catch(Exception ex){
			addMessage(i18n("message_department_disabled_failed"));
		}
		return Constants.OUTCOME_BACKWARD;
	}
	
}
