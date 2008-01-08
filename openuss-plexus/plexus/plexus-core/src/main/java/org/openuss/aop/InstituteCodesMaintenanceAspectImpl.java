package org.openuss.aop;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.registration.RegistrationService;

/**
 * Aspect for Removing institute codes
 * 
 * @author Florian Dondorf
 */
public class InstituteCodesMaintenanceAspectImpl {

	private static final Logger logger = Logger.getLogger(InstituteActivationMailSenderAspectImpl.class);
	
	private RegistrationService registrationService;
	private InstituteDao instituteDao;
	
	/**
	 * Removes the institute codes of an institute.
	 * @param instituteInfo
	 */
	public void removeInstituteCodes(Long instituteId) {
		logger.debug("InstiuteCodesMaintenanceAspectImpl - removeInstiuteCodes");
		
		Validate.notNull(instituteId, "InstiuteCodesMaintenanceAspectImpl.removeInstiuteCodes -" +
				"instituteId cannot be null.");
		
		Institute institute = this.getInstituteDao().load(instituteId);
		Validate.notNull(institute, "InstiuteCodesMaintenanceAspectImpl.removeInstiuteCodes -" +
				"no institute found with the instituteId "+instituteId);
		
		this.getRegistrationService().removeInstituteCodes(institute);
	}

	public RegistrationService getRegistrationService() {
		return registrationService;
	}

	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}
	
	
}
