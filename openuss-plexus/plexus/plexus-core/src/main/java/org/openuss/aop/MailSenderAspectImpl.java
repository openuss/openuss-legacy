package org.openuss.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.OrganisationDao;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.messaging.MessageService;
import org.openuss.security.User;
import org.openuss.security.UserDao;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

/**
 * Aspect for Sending Mails.
 * 
 * @author Ron Haus
 */

public class MailSenderAspectImpl {

	private static final Logger logger = Logger.getLogger(MailSenderAspectImpl.class);

	private UserDao userDao;
	private OrganisationDao organisationDao;
	private UniversityDao universityDao;
	private DepartmentDao departmentDao;
	private InstituteDao instituteDao;
	private MessageService messageService;
	private SystemService systemService;


	/**
	 * Sends Emails whenever a Member is added to an Organisation.
	 */
	public void sendAddMemberMail(Long organisationId, Long userId) {
		logger.debug("sendAddMemberMail - User " + userId + " has been added to Organisation " + organisationId);

		// Loading User
		User user = userDao.load(userId);

		// Identifying type of Organisation
		University university = universityDao.load(organisationId);
		if (university != null) {
			logger.debug("sendAddMemberMail - Organisation of type University identified");
			this.sendAddMemberMailForUniversity(university, user);
		} else {
			Department department = departmentDao.load(organisationId);
			if (department != null) {
				logger.debug("sendAddMemberMail - Organisation of type Department identified");
				this.sendAddMemberMailForDepartment(department, user);
			} else {
				Institute institute = instituteDao.load(organisationId);
				if (institute != null) {
					logger.debug("sendAddMemberMail - Organisation of type Institute identified");
					this.sendAddMemberMailForInstitute(institute, user);
				}
			}
		}
	}

	private void sendAddMemberMailForUniversity(University university, User user) {
		logger.debug("sendAddMemberMailForUniversity - Sending Email to User " + user.getUsername() + " ("
				+ user.getEmail() + ") and Members of University " + university.getName());

		// Create Link to University
		String link = "openuss-plexus/views/public/university/university.faces?university=" + university.getId();
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", user.getUsername());
		parameters.put("userfirstname", user.getFirstName());
		parameters.put("userlastname", user.getLastName());
		parameters.put("organisationname", university.getName());
		parameters.put("organisationlink", link);
		
		// Determine Recipients (Members of the University)
		List<User> recipients1 = new ArrayList<User>();
		for (User member : university.getMembership().getMembers()) {
			recipients1.add(member);
		}
		recipients1.remove(user);

		if (recipients1.size() > 0) {
			// Send Email to Members
			messageService.sendMessage("user.membership.sender", "user.membership.addmember.members.subject",
					"addmembermembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);
		
		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.addmember.user.subject",
				"addmemberuser", parameters, recipients2);
	}

	private void sendAddMemberMailForDepartment(Department department, User user) {
		logger.debug("sendAddMemberMailForDepartment - Sending Email to User " + user.getUsername()
				+ " and Members of Department " + department.getName());
		
		// Create Link to Department
		String link = "openuss-plexus/views/public/department/department.faces?department=" + department.getId();
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", user.getUsername());
		parameters.put("userfirstname", user.getFirstName());
		parameters.put("userlastname", user.getLastName());
		parameters.put("organisationname", department.getName());
		parameters.put("organisationlink", link);
		
		// Determine Recipients (Members of the Department)
		List<User> recipients1 = new ArrayList<User>();
		for (User member : department.getMembership().getMembers()) {
			recipients1.add(member);
		}
		recipients1.remove(user);

		if (recipients1.size() > 0) {
			// Send Email to Members
			messageService.sendMessage("user.membership.sender", "user.membership.addmember.members.subject",
					"addmembermembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);
		
		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.addmember.user.subject",
				"addmemberuser", parameters, recipients2);
	}

	private void sendAddMemberMailForInstitute(Institute institute, User user) {
		logger.debug("sendAddMemberMailForInstitute - Sending Email to User " + user.getUsername()
				+ " and Members of Institute " + institute.getName());
		
		// Create Link to Institute
		String link = "openuss-plexus/views/public/institute/institute.faces?institute=" + institute.getId();
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", user.getUsername());
		parameters.put("userfirstname", user.getFirstName());
		parameters.put("userlastname", user.getLastName());
		parameters.put("organisationname", institute.getName());
		parameters.put("organisationlink", link);
		
		// Determine Recipients (Members of the Institute)
		List<User> recipients1 = new ArrayList<User>();
		for (User member : institute.getMembership().getMembers()) {
			recipients1.add(member);
		}
		recipients1.remove(user);

		if (recipients1.size() > 0) {
			// Send Email to Members
			messageService.sendMessage("user.membership.sender", "user.membership.addmember.members.subject",
					"addmembermembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);
		
		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.addmember.user.subject",
				"addmemberuser", parameters, recipients2);
	}

	/**
	 * Sends Emails whenever a Member is removed from an Organisation.
	 */
	public void sendRemoveMemberMail(Long organisationId, Long userId) {
		logger.debug("sendRemoveMemberMail - User " + userId + " has been removed from Organisation " + organisationId);

		// Loading User
		User user = userDao.load(userId);

		// Identifying type of Organisation
		University university = universityDao.load(organisationId);
		if (university != null) {
			logger.debug("sendRemoveMemberMail - Organisation of type University identified");
			this.sendRemoveMemberMailForUniversity(university, user);
		} else {
			Department department = departmentDao.load(organisationId);
			if (department != null) {
				logger.debug("sendRemoveMemberMail - Organisation of type Department identified");
				this.sendRemoveMemberMailForDepartment(department, user);
			} else {
				Institute institute = instituteDao.load(organisationId);
				if (institute != null) {
					logger.debug("sendRemoveMemberMail - Organisation of type Institute identified");
					this.sendRemoveMemberMailForInstitute(institute, user);
				}
			}
		}
	}
	
	private void sendRemoveMemberMailForUniversity(University university, User user) {
		logger.debug("sendAddMemberMailForUniversity - Sending Email to User " + user.getUsername() + " ("
				+ user.getEmail() + ") and Members of University " + university.getName());

		// Create Link to University
		String link = "openuss-plexus/views/public/university/university.faces?university=" + university.getId();
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", user.getUsername());
		parameters.put("userfirstname", user.getFirstName());
		parameters.put("userlastname", user.getLastName());
		
		// Determine Recipients (Members of the University)
		List<User> recipients1 = new ArrayList<User>();
		for (User member : university.getMembership().getMembers()) {
			recipients1.add(member);
		}
		recipients1.remove(user);

		if (recipients1.size() > 0) {
			// Send Email to Members
			messageService.sendMessage("user.membership.sender", "user.membership.addmember.members.subject",
					"removemembermembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);
		
		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.addmember.user.subject",
				"removememberuser", parameters, recipients2);
	}

	private void sendRemoveMemberMailForDepartment(Department department, User user) {
		logger.debug("sendAddMemberMailForDepartment - Sending Email to User " + user.getUsername()
				+ " and Members of Department " + department.getName());
		
		// Create Link to Department
		String link = "openuss-plexus/views/public/department/department.faces?department=" + department.getId();
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", user.getUsername());
		parameters.put("userfirstname", user.getFirstName());
		parameters.put("userlastname", user.getLastName());
		
		// Determine Recipients (Members of the Department)
		List<User> recipients1 = new ArrayList<User>();
		for (User member : department.getMembership().getMembers()) {
			recipients1.add(member);
		}
		recipients1.remove(user);

		if (recipients1.size() > 0) {
			// Send Email to Members
			messageService.sendMessage("user.membership.sender", "user.membership.addmember.members.subject",
					"removemembermembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);
		
		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.addmember.user.subject",
				"removememberuser", parameters, recipients2);
	}

	private void sendRemoveMemberMailForInstitute(Institute institute, User user) {
		logger.debug("sendAddMemberMailForInstitute - Sending Email to User " + user.getUsername()
				+ " and Members of Institute " + institute.getName());
		
		// Create Link to Institute
		String link = "openuss-plexus/views/public/institute/institute.faces?institute=" + institute.getId();
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", user.getUsername());
		parameters.put("userfirstname", user.getFirstName());
		parameters.put("userlastname", user.getLastName());
		
		// Determine Recipients (Members of the Institute)
		List<User> recipients1 = new ArrayList<User>();
		for (User member : institute.getMembership().getMembers()) {
			recipients1.add(member);
		}
		recipients1.remove(user);

		if (recipients1.size() > 0) {
			// Send Email to Members
			messageService.sendMessage("user.membership.sender", "user.membership.addmember.members.subject",
					"removemembermembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);
		
		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.addmember.user.subject",
				"removememberuser", parameters, recipients2);
	}
	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public OrganisationDao getOrganisationDao() {
		return organisationDao;
	}

	public void setOrganisationDao(OrganisationDao organisationDao) {
		this.organisationDao = organisationDao;
	}

	public UniversityDao getUniversityDao() {
		return universityDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
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
}
