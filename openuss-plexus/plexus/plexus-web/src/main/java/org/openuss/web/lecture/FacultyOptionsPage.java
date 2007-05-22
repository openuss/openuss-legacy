package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$facultyoptions", scope = Scope.REQUEST)
@View
public class FacultyOptionsPage extends AbstractLecturePage {

	private static final long serialVersionUID = -202776319652385870L;
	
	/**
	 * Save faculty options.
	 * @return outcome
	 * @throws LectureException
	 */
	public String saveFaculty() throws LectureException {
		lectureService.persist(faculty);
		addMessage(i18n("faculty_message_command_save_succeed"));
		return Constants.SUCCESS;
	}

}
