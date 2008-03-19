package org.openuss.aop;

import org.apache.log4j.Logger;
import org.openuss.lecture.Application;
import org.openuss.lecture.ApplicationDao;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentService;
import org.openuss.security.SecurityService;
import org.openuss.security.UserDao;
import org.openuss.security.acl.LectureAclEntry;

public class ApplicationConfirmationAspectImpl {

	private static final Logger logger = Logger.getLogger(ApplicationConfirmationAspectImpl.class);

	private UserDao userDao;
	private ApplicationDao applicationDao;
	private DepartmentService departmentService;
	private SecurityService securityService;


	public void checkApplicationForConfirmation(Object applicationId, Long instituteId, Long departmentId, Long userId) {
		logger.debug("----------> Begin method checkApplicationForConfirmation <----------");
		if ((applicationId != null) && (applicationId instanceof Long)) {
			logger.debug("--> Checking Confirmation for Application " + applicationId + " <--");

			// Check User ACL Admin Right for Department
			final Application application = applicationDao.load((Long) applicationId);
			
			if ((application != null) && (!application.isConfirmed())) {
		 		final Department department = application.getDepartment();
				if (department != null) {
					logger.debug("--> everything is fine <--");

					boolean hasPermission = false;

					try {
						hasPermission = securityService.hasPermission(department, new Integer[] { LectureAclEntry.DEPARTMENT_ADMINISTRATION });
					} catch (Exception e) {
						logger.debug("--> Problems during Permission validation, skipping Confirmation <--");
						logger.error(e);
					}
					if (hasPermission) {
						logger.debug("--> User has Admin Permissions, starting Confirmation <--");
						departmentService.acceptApplication((Long) applicationId, userId);
					} else {
						logger.debug("--> User has NO Admin Permissions, skipping Confirmation <--");
					}

				} else {
					logger.debug("--> No Department found corresponding to the ApplicationID " + applicationId + ", skipping Confirmation <--");
				}
			} else {
				logger.debug("--> No non-confirmed Application found corresponding to the ApplicationID " + applicationId + ", skipping Confirmation <--");
			}

		}

		logger.debug("----------> End method checkApplicationForConfirmation <----------");
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}
	
	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}
	
	public void setApplicationDao(final ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}
	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}
	
	public void setDepartmentService(final DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public SecurityService getSecurityService() {
		return securityService;
	}
	
	public void setSecurityService(final SecurityService securityService) {
		this.securityService = securityService;
	}
}
