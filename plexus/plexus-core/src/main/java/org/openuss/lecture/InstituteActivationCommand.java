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

	private static Logger logger = Logger.getLogger(InstituteActivationCommand.class);
	
	private InstituteService instituteService;

	public void execute() throws Exception {
		instituteService.setInstituteStatus(getDomainObject().getId(), true);
		logger.debug("Institute activated");
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

}