package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.commands.AbstractDomainCommand;
import org.openuss.commands.DomainCommand;

public class FacultyActivationCommand extends AbstractDomainCommand implements DomainCommand{

	private static Logger logger = Logger.getLogger(FacultyActivationCommand.class);
	
	private FacultyDao facultyDao;
	
	public void execute() throws Exception {
		Faculty faculty = facultyDao.load(getDomainObject().getId());
		faculty.setEnabled(true);
		facultyDao.update(faculty);
		logger.debug("Faculty activated");
	}

	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}

}