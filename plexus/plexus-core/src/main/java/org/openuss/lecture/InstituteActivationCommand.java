package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.commands.AbstractDomainCommand;
import org.openuss.commands.DomainCommand;

/**
 * @author Kai Stettner
 * 
 * Command enabling the selected institute.
 */
public class InstituteActivationCommand extends AbstractDomainCommand implements DomainCommand{

	private static final Logger logger = Logger.getLogger(InstituteActivationCommand.class);
	
	private InstituteDao instituteDao;

	public void execute() throws Exception {
		Institute institute = instituteDao.load(getDomainObject().getId());
		institute.setEnabled(true);
		instituteDao.update(institute);
		logger.debug("Institute activated");
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}
}