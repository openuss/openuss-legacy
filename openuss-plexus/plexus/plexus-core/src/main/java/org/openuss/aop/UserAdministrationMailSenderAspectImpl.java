package org.openuss.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
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
 * Aspect for sending Emails whenever a User has been added to or removed from an Organisation.
 * 
 * @author Ron Haus
 * @author Florian Dondorf
 */

public class UserAdministrationMailSenderAspectImpl {

	private static final Logger logger = Logger.getLogger(UserAdministrationMailSenderAspectImpl.class);

	private UserDao userDao;
	private OrganisationDao organisationDao;
	private UniversityDao universityDao;
	private DepartmentDao departmentDao;
	private InstituteDao instituteDao;
	private MessageService messageService;
	private SystemService systemService;


	/**
	 * Sends Emails to the new Member and all old Members of the Organisation whenever a new Member has been added to
	 * the Organisation.
	 * 
	 * @param organisationId -
	 *            ID of the Organisation.
	 * @param userID -
	 *            ID of the new Member.
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

	/**
	 * Sends Emails to the new Member and all old Members of the Organisation whenever a new Member has been removed
	 * from the Organisation.
	 * 
	 * @param organisationId -
	 *            ID of the Organisation
	 * @param userID -
	 *            ID of the new Member
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

	/**
	 * Sends Emails to the applying User and all old Members of the Organisation whenever a new Aspirant has applied for
	 * the Organisation.
	 * 
	 * @param organisationId -
	 *            ID of the Organisation for which the User applies.
	 * @param userId -
	 *            ID of the Aspirant.
	 */
	public void sendAddAspirantMail(Long organisationId, Long userId) {
		logger.debug("sendAddAspirantMail - User " + userId + " has applied at the organisation " + organisationId);

		// Loading User
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "AspirantMailSenderAspectImpl - no user found with the given userId " + userId);

		// Identifying type of Organisation
		University university = universityDao.load(organisationId);
		if (university != null) {
			logger.debug("sendAddMemberMail - Organisation of type University identified");
			this.sendAddAspirantMailForUniversity(university, user);
		} else {
			Department department = departmentDao.load(organisationId);
			if (department != null) {
				logger.debug("sendAddMemberMail - Organisation of type Department identified");
				this.sendAddAspirantMailForDepartment(department, user);
			} else {
				Institute institute = instituteDao.load(organisationId);
				if (institute != null) {
					logger.debug("sendAddMemberMail - Organisation of type Institute identified");
					this.sendAddAspirantMailForInstitute(institute, user);
				}
			}
		}
	}

	/**
	 * Sends an mails to the applying aspirant with the information that he was accepted to join the institute
	 * membership.
	 * 
	 * @param organisationId -
	 *            organisation's id for which the user is accepted.
	 * @param userId -
	 *            user's id.
	 */
	public void sendAcceptAspirantMail(Long organisationId, Long userId) {
		logger.debug("sendAcceptAspirantMail - User " + userId + " was accepted at the organisation " + organisationId);

		// Loading User
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "AspirantMailSenderAspectImpl - no user found with the given userId " + userId);

		// Identifying type of Organisation
		University university = universityDao.load(organisationId);
		if (university != null) {
			logger.debug("sendAddMemberMail - Organisation of type University identified");
			this.sendAcceptAspirantMailForUniversity(university, user);
		} else {
			Department department = departmentDao.load(organisationId);
			if (department != null) {
				logger.debug("sendAddMemberMail - Organisation of type Department identified");
				this.sendAcceptAspirantMailForDepartment(department, user);
			} else {
				Institute institute = instituteDao.load(organisationId);
				if (institute != null) {
					logger.debug("sendAddMemberMail - Organisation of type Institute identified");
					this.sendAcceptAspirantMailForInstitute(institute, user);
				}
			}
		}
	}

	/**
	 * Sends an mails to the applying aspirant that his application was rejected.
	 * 
	 * @param organisationId -
	 *            organisation's id for which the user has applied.
	 * @param userId -
	 *            user's id.
	 */
	public void sendRejectAspirantMail(Long organisationId, Long userId) {
		logger.debug("sendRejectAspirantMail - User " + userId + " was rejected to join the organisation "
				+ organisationId);

		// Loading User
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "AspirantMailSenderAspectImpl - no user found with the given userId " + userId);

		// Identifying type of Organisation
		University university = universityDao.load(organisationId);
		if (university != null) {
			logger.debug("sendAddMemberMail - Organisation of type University identified");
			this.sendRejectAspirantMailForUniversity(university, user);
		} else {
			Department department = departmentDao.load(organisationId);
			if (department != null) {
				logger.debug("sendAddMemberMail - Organisation of type Department identified");
				this.sendRejectAspirantMailForDepartment(department, user);
			} else {
				Institute institute = instituteDao.load(organisationId);
				if (institute != null) {
					logger.debug("sendAddMemberMail - Organisation of type Institute identified");
					this.sendRejectAspirantMailForInstitute(institute, user);
				}
			}
		}
	}

	/*
	 * <----------- Private methods ----------->
	 */

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
		messageService.sendMessage("user.membership.sender", "user.membership.addmember.user.subject", "addmemberuser",
				parameters, recipients2);
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
		messageService.sendMessage("user.membership.sender", "user.membership.addmember.user.subject", "addmemberuser",
				parameters, recipients2);
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
		messageService.sendMessage("user.membership.sender", "user.membership.addmember.user.subject", "addmemberuser",
				parameters, recipients2);
	}

	private void sendAddAspirantMailForUniversity(University university, User user) {
		logger.debug("sendAddAspirantMailForUniversity - Sending Email to User " + user.getUsername()
				+ " and Members of University " + university.getName());

		// Create Link to University
		String link = "openuss-plexus/views/secured/lecture/auth/universitymembers.faces?university=" + university.getId();
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
			messageService.sendMessage("user.membership.sender", "user.membership.addaspirant.members.subject",
					"addaspirantmembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.addaspirant.members.subject",
				"addaspirantuser", parameters, recipients2);
	}

	private void sendAddAspirantMailForDepartment(Department department, User user) {
		logger.debug("sendAddAspirantMailForDepartment - Sending Email to User " + user.getUsername()
				+ " and Members of Department " + department.getName());

		// Create Link to Institute
		String link = "openuss-plexus/views/secured/lecture/auth/departmentMembers.faces?department=" + department.getId();
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", user.getUsername());
		parameters.put("userfirstname", user.getFirstName());
		parameters.put("userlastname", user.getLastName());
		parameters.put("organisationname", department.getName());
		parameters.put("organisationlink", link);

		// Determine Recipients (Members of the Institute)
		List<User> recipients1 = new ArrayList<User>();
		for (User member : department.getMembership().getMembers()) {
			recipients1.add(member);
		}
		recipients1.remove(user);

		if (recipients1.size() > 0) {
			// Send Email to Members
			messageService.sendMessage("user.membership.sender", "user.membership.addaspirant.members.subject",
					"addaspirantmembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.addaspirant.user.subject",
				"addaspirantuser", parameters, recipients2);
	}

	private void sendAddAspirantMailForInstitute(Institute institute, User user) {
		logger.debug("sendAddAspirantMailForInstitute - Sending Email to User " + user.getUsername()
				+ " and Members of Institute " + institute.getName());

		// Create Link to Institute
		String link = "openuss-plexus/views/secured/lecture/auth/aspirants.faces?institute=" + institute.getId();
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
			messageService.sendMessage("user.membership.sender", "user.membership.addaspirant.members.subject",
					"addaspirantmembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.addaspirant.user.subject",
				"addaspirantuser", parameters, recipients2);
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
			messageService.sendMessage("user.membership.sender", "user.membership.removemember.members.subject",
					"removemembermembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.removemember.user.subject",
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
			messageService.sendMessage("user.membership.sender", "user.membership.removemember.members.subject",
					"removemembermembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.removemember.user.subject",
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
			messageService.sendMessage("user.membership.sender", "user.membership.removemember.members.subject",
					"removemembermembers", parameters, recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.removemember.user.subject",
				"removememberuser", parameters, recipients2);
	}

	private void sendAcceptAspirantMailForUniversity(University university, User user) {
		logger.debug("sendAcceptAspirantMailForUniversity - Sending Email to User " + user.getUsername());

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
			messageService.sendMessage("user.membership.sender", "user.membership.acceptaspirant.members.subject",
					"acceptaspirantmembers", parameters, recipients1);
		}

		// Create Recipients list (contains only the accepted user)
		List<User> recipients = new ArrayList<User>(1);
		recipients.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.acceptaspirant.user.subject",
				"acceptaspirantuser", parameters, recipients);
	}

	private void sendAcceptAspirantMailForDepartment(Department department, User user) {
		logger.debug("sendAcceptAspirantMailForDepartment - Sending Email to User " + user.getUsername());

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

		// Determine Recipients (Members of the University)
		List<User> recipients1 = new ArrayList<User>();
		for (User member : department.getMembership().getMembers()) {
			recipients1.add(member);
		}
		recipients1.remove(user);

		if (recipients1.size() > 0) {
			// Send Email to Members
			messageService.sendMessage("user.membership.sender", "user.membership.acceptaspirant.members.subject",
					"acceptaspirantmembers", parameters, recipients1);
		}

		// Create Recipients list (contains only the accepted user)
		List<User> recipients = new ArrayList<User>(1);
		recipients.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.acceptaspirant.user.subject",
				"acceptaspirantuser", parameters, recipients);
	}

	private void sendAcceptAspirantMailForInstitute(Institute institute, User user) {
		logger.debug("sendAcceptAspirantMailForInstitute - Sending Email to User " + user.getUsername());

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

		// Determine Recipients (Members of the University)
		List<User> recipients1 = new ArrayList<User>();
		for (User member : institute.getMembership().getMembers()) {
			recipients1.add(member);
		}
		recipients1.remove(user);

		if (recipients1.size() > 0) {
			// Send Email to Members
			messageService.sendMessage("user.membership.sender", "user.membership.acceptaspirant.members.subject",
					"acceptaspirantmembers", parameters, recipients1);
		}

		// Create Recipients list (contains only the accepted user)
		List<User> recipients = new ArrayList<User>(1);
		recipients.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.acceptaspirant.user.subject",
				"acceptaspirantuser", parameters, recipients);
	}

	private void sendRejectAspirantMailForUniversity(University university, User user) {
		logger.debug("sendRejectAspirantMailForInstitute - Sending Email to User " + user.getUsername());

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
			messageService.sendMessage("user.membership.sender", "user.membership.rejectaspirant.members.subject",
					"rejectaspirantmembers", parameters, recipients1);
		}

		// Create list of Recipients (only the rejected User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.rejectaspirant.user.subject",
				"rejectaspirantuser", parameters, recipients2);
	}

	private void sendRejectAspirantMailForDepartment(Department department, User user) {
		logger.debug("sendRejectAspirantMailForDepartment - Sending Email to User " + user.getUsername());

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
			messageService.sendMessage("user.membership.sender", "user.membership.rejectaspirant.members.subject",
					"rejectaspirantmembers", parameters, recipients1);
		}

		// Create list of Recipients (only the rejected User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.rejectaspirant.user.subject",
				"rejectaspirantuser", parameters, recipients2);
	}

	private void sendRejectAspirantMailForInstitute(Institute institute, User user) {
		logger.debug("sendRejectAspirantMailForInstitute - Sending Email to User " + user.getUsername());

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
			messageService.sendMessage("user.membership.sender", "user.membership.rejectaspirant.members.subject",
					"rejectaspirantmembers", parameters, recipients1);
		}

		// Create list of Recipients (only the rejected User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);

		// Send Email to new User
		messageService.sendMessage("user.membership.sender", "user.membership.rejectaspirant.user.subject",
				"rejectaspirantuser", parameters, recipients2);
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
