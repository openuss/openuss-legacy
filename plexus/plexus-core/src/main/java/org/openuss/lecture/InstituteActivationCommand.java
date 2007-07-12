package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.commands.AbstractDomainCommand;
import org.openuss.commands.DomainCommand;

public class InstituteActivationCommand extends AbstractDomainCommand implements DomainCommand{

	private static Logger logger = Logger.getLogger(InstituteActivationCommand.class);
	
	private LectureService lectureService;

	public void execute() throws Exception {
		Institute institute = lectureService.getInstitute(getDomainObject().getId());
		institute.setEnabled(true);
		lectureService.persist(institute);
		logger.debug("Institute activated");
	}

	public LectureService getLectureService() {
		return lectureService;
	}
	
	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}
}