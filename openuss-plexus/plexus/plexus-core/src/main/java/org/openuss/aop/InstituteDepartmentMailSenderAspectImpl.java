package org.openuss.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.Application;
import org.openuss.lecture.ApplicationDao;
import org.openuss.lecture.ApplicationInfo;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.InstituteInfo;
import org.openuss.messaging.MessageService;
import org.openuss.security.User;
import org.openuss.security.UserDao;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

/**
 * Aspect for Sending Mails while 
 * application of institutes at departments.
 * 
 * @author Florian Dondorf
 */

public class InstituteDepartmentMailSenderAspectImpl {

	private static final Logger logger = Logger.getLogger(InstituteDepartmentMailSenderAspectImpl.class);
	
	private UserDao userDao;
	private DepartmentDao departmentDao;
	private InstituteDao instituteDao;
	private ApplicationDao applicationDao;
	private MessageService messageService;
	private SystemService systemService;
	
	public void sendApplyInstituteAtDepartmentMail (ApplicationInfo applicationInfo) {
		// Confirmation Mail to user who applies.
		// Mail to all admins of department to edit application
		
		logger.debug("sendApplyInstituteAtDepartmentMail - Sending Email to User who apllied at the department");
		
		Validate.notNull(applicationInfo, "InstituteDepartmentMailSenderAspectImpl.sendApplyInstituteAtDepartment -" +
				"applicationInfo cannot be null.");
		Validate.notNull(applicationInfo.getId(), "InstituteDepartmentMailSenderAspectImpl.sendApplyInstituteAtDepartment -" +
				"applicationInfo.id cannot be null.");
		
		// Get Application
		Application application = (Application) this.getApplicationDao().load(applicationInfo.getId());
		Validate.notNull(application, "InstituteDepartmentMailSenderAspectImpl.sendApplyInstituteAtDepartment -" +
				"no application found with the applicationInfo "+applicationInfo.getId());
		
		/*
		// Create Link to Department
		String link = "openuss-plexus/views/public/department/department.faces?department=" + department.getId();
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("institutename", institute.getName());
		parameters.put("userfirstname", institute.getName());
		parameters.put("userlastname", institute.getName());
		parameters.put("username", institute.getName());
		parameters.put("instituteapplicantlink", link);
		
		// Determine Recipients (Members of the Institute)
		List<User> recipients1 = new ArrayList<User>();
		for (User member : institute.getMembership().getMembers()) {
			recipients1.add(member);
		}
		recipients1.remove(user);

		if (recipients1.size() > 0) {
			// Send Email to Members
			messageService.sendMessage("user.membership.sender", "user.membership.addmember.members.subject",
					"instituteapplication", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);
		
		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.addmember.user.subject",
				"instituteapplicationconfirmation", parameters, recipients2);
				*/
	}
	
	public void sendAcceptApplicationAtDepartmentMail (InstituteInfo instiuteInfo, Long userId) {
		// Mail to user who applies at the department 
	}
	
	public void sendRejectApplicationAtDepartmentMail (InstituteInfo instiuteInfo, Long userId) {
		// Mail to user who applied at the department
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}
	
	
}
