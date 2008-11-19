package org.openuss.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.Application;
import org.openuss.lecture.ApplicationDao;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.messaging.MessageService;
import org.openuss.security.User;
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
	
	private DepartmentDao departmentDao;
	private InstituteDao instituteDao;
	private ApplicationDao applicationDao;
	private MessageService messageService;
	private SystemService systemService;
	
	public void sendApplyInstituteAtDepartmentMail (Long instituteId, Long departmentId, Long userId) {
		logger.debug("Sending Email to User who apllied at the department");
		Validate.notNull(instituteId, "instituteId cannot be null.");
		Validate.notNull(departmentId, "departmentId cannot be null.");
		Validate.notNull(userId, "userId cannot be null.");
		// Load Institute
		Institute institute = instituteDao.load(instituteId);
		Validate.notNull(institute, "no institute found with the instituteId "+instituteId);
		// Load Department
		Department department = departmentDao.load(departmentId);
		Validate.notNull(department, "no department found with the departmentId "+departmentId);
		// Get Application
		Application application = applicationDao.findByInstituteAndDepartment(institute, department);
		Validate.notNull(application, "no application found with ths institute "+institute.getName()+" and the department "+department.getName());
		Validate.notNull(application.getDepartment(), "department cannot be null.");
		
		// Create Link to Department
		String link = "/views/public/department/department.faces?department=" + application.getDepartment().getId();
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

		Map<String, String> parameters = prepareEMailParameters(application, link);
		
		// Determine Recipients (Members of the Department)
		List<User> recipients1 = new ArrayList<User>();
		for (User member : application.getDepartment().getMembership().getMembers()) {
			if (!recipients1.contains(member)){
				recipients1.add(member);
			}
		}
		recipients1.remove(application.getApplyingUser());

		if (recipients1.size() > 0) {
			// Send Email to Members
			messageService.sendMessage(
					"OPENUSS - "+application.getDepartment().getShortcut(),
					"institute.application.members.subject",
					"departmentapplication", 
					parameters, 
					recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(application.getApplyingUser());
		
		// Send Email to new User
		messageService.sendMessage(
				"OPENUSS - "+application.getDepartment().getShortcut(),
				"institute.application.user.subject",
				"departmentapplicationconfirmation", 
				parameters, 
				recipients2);
	}

	private Map<String, String> prepareEMailParameters(Application application, String link) {
		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("institutename", application.getInstitute().getName());
		parameters.put("departmentname", application.getDepartment().getName());
		parameters.put("applyinguserfirstname", application.getApplyingUser().getFirstName());
		parameters.put("applyinguserlastname", application.getApplyingUser().getLastName());
		parameters.put("departmentapplicantlink", link);
		return parameters;
	}
	
	public void sendAcceptApplicationAtDepartmentMail (Long applicationId, Long userId) {
		logger.debug("Sending Email to User who apllied at the department");
		
		Validate.notNull(applicationId, "applicationd cannot be null.");
		
		// Get Application
		Application application = applicationDao.load(applicationId);
		Validate.notNull(application, "no Application found with the ID "+applicationId);
		Validate.notNull(application.getDepartment(), "department cannot be null.");
		
		// Create Link to Department
		String link = "/views/secured/lecture/departmentinstitutes.faces?department=" + application.getDepartment().getId();
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

		Map<String, String> parameters = prepareEMailParameters(application, link);

		// Determine Recipient (the user who created the application)
		List<User> recipients = new ArrayList<User>(1);
		recipients.add(application.getApplyingUser());
		
		// Send Email to new User
		messageService.sendMessage(
				"OPENUSS - "+application.getDepartment().getShortcut(),
				"department.accept.application.user.subject",
				"departmentapplicationapply", 
				parameters, 
				recipients); 
	}
	
	public void sendRejectApplicationAtDepartmentMail (Long applicationId) {
		logger.debug("Sending Email to User who apllied at the department");
		Validate.notNull(applicationId, "applicationd cannot be null.");
		
		// Get Application
		Application application = (Application) applicationDao.load(applicationId);
		Validate.notNull(application, "no application found with the applicationInfo "+applicationId);
		Validate.notNull(application.getDepartment(),"department cannot be null.");

		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("institutename", application.getInstitute().getName());
		parameters.put("departmentname", application.getDepartment().getName());
		parameters.put("applyinguserfirstname", application.getApplyingUser().getFirstName());
		parameters.put("applyinguserlastname", application.getApplyingUser().getLastName());

		// Determine Recipient (the user who created the application)
		List<User> recipients = new ArrayList<User>(1);
		recipients.add(application.getApplyingUser());
		
		// Send Email to new User
		messageService.sendMessage(
				"OPENUSS - "+application.getDepartment().getShortcut(),
				"department.reject.application.user.subject",
				"departmentapplicationreject", 
				parameters, 
				recipients); 
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}
	
	
}
