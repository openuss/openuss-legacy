package org.openuss.aop;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.log4j.Logger;
import org.openuss.lecture.ApplicationDao;
import org.openuss.lecture.InstituteService;
import org.openuss.security.UserDao;

public class ApplicationConfirmationAspectImpl {

	private static final Logger logger = Logger.getLogger(ApplicationConfirmationAspectImpl.class);

	private UserDao userDao;
	private ApplicationDao applicationDao;
	private InstituteService instituteService;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public void checkApplicationForConfirmation(Object applicationId, Long instituteId, Long departmentId, Long userId) {
		logger.debug("----------> Begin method checkApplicationForConfirmation <----------");
		if ((applicationId != null) && (applicationId instanceof Long)) {
			logger.debug("----------> Checking Confirmation for Application " + applicationId + " <----------");

			// Get User
			try {
				UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				logger.debug("----------> User " + user.getUsername() + " identified <----------");
			} catch (Exception e) {
				;
			}
		}

		logger.debug("----------> End method checkApplicationForConfirmation <----------");
	}

}
