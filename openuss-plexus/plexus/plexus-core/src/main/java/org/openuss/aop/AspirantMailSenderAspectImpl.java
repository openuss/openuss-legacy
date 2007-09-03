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
 * Aspect for Sending Mails while 
 * adding, accepting and rejecting aspirants .
 * 
 * @author Florian Dondorf
 */

public class AspirantMailSenderAspectImpl {

	private static final Logger logger = Logger.getLogger(AspirantMailSenderAspectImpl.class);
	
	private UserDao userDao;
	private OrganisationDao organisationDao;
	private UniversityDao universityDao;
	private DepartmentDao departmentDao;
	private InstituteDao instituteDao;
	private MessageService messageService;
	private SystemService systemService;
	
	/**
	 * Sends an mails to the applying aspirant and the organisation members
	 * @param organisationId - organisation's id for which the user applies.
	 * @param userId - user's id.
	 */
	public void sendAddAspirantMail (Long organisationId, Long userId) {
		logger.debug("sendAddAspirantMail - User " + userId + " has applied at the organisation " + organisationId);
		
		// Loading User
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "AspirantMailSenderAspectImpl - no user found with the given userId "+userId);
		
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
	 * Sends an mails to the applying aspirant with the information that he was
	 * accepted to join the institute membership.
	 * @param organisationId - organisation's id for which the user is accepted.
	 * @param userId - user's id.
	 */
	public void sendAcceptAspirantMail (Long organisationId, Long userId) {
		logger.debug("sendAcceptAspirantMail - User " + userId + " was accepted at the organisation " + organisationId);
		
		// Loading User
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "AspirantMailSenderAspectImpl - no user found with the given userId "+userId);
		
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
	 * @param organisationId - organisation's id for which the user has applied.
	 * @param userId - user's id.
	 */
	public void sendRejectAspirantMail (Long organisationId, Long userId) {
		logger.debug("sendRejectAspirantMail - User " + userId + " was rejected to join the organisation " + organisationId);
		
		// Loading User
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "AspirantMailSenderAspectImpl - no user found with the given userId "+userId);
		
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

	private void sendAddAspirantMailForUniversity (University university, User user) {
		//TODO: Implement this method stub!
	}


	private void sendAddAspirantMailForDepartment (Department department, User user) {
		//TODO: Implement this method stub!
	}


	private void sendAddAspirantMailForInstitute (Institute institute, User user) {
		logger.debug("sendAddAspirantMailForInstitute - Sending Email to User " + user.getUsername()
				+ " and Members of Institute " + institute.getName());
		
		// Create Link to Institute
		String link = "/openuss-plexus/views/public/institute/institute.faces?institute=" + institute.getId();
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
			messageService.sendMessage(
					"OPENUSS - "+institute.getShortcut(),
					"aspirant.addaspirant.members.subject",
					"instituteapplication", 
					parameters, 
					recipients1);
		}

		// Determine Recipient (the new User)
		List<User> recipients2 = new ArrayList<User>(1);
		recipients2.add(user);
		
		// Send Email to new User
		messageService.sendMessage(
				"OPENUSS - "+institute.getShortcut(),
				"aspirant.addaspirant.user.subject",
				"instituteapplicationconfirmation", 
				parameters, 
				recipients2);
	}
	
	
	private void sendAcceptAspirantMailForUniversity (University university, User user) {
		//TODO: Implement this method stub!
	}


	private void sendAcceptAspirantMailForDepartment (Department department, User user) {
		//TODO: Implement this method stub!
	}


	private void sendAcceptAspirantMailForInstitute (Institute institute, User user) {
		logger.debug("sendAcceptAspirantMailForInstitute - Sending Email to User " + user.getUsername());
		
		// Create Link to Institute
		String link = "/openuss-plexus/views/public/institute/institute.faces?institute=" + institute.getId();
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue() + link;

		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("institutename", institute.getName());
		parameters.put("institutelink", link);

		// Create Recipients list (contains only the accepted user)
		List<User> recipients = new ArrayList<User>(1);
		recipients.add(user);
		
		// Send Email to new User
		messageService.sendMessage(
				"OPENUSS - "+institute.getShortcut(),
				"aspirant.acceptaspirant.user.subject",
				"instituteapplication", 
				parameters, 
				recipients);
	}
	
	
	private void sendRejectAspirantMailForUniversity (University university, User user) {
		//TODO: Implement this method stub!
	}


	private void sendRejectAspirantMailForDepartment (Department department, User user) {
		//TODO: Implement this method stub!
	}


	private void sendRejectAspirantMailForInstitute (Institute institute, User user) {
		logger.debug("sendRejectAspirantMailForInstitute - Sending Email to User " + user.getUsername());

		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("institutename", institute.getName());

		// Create list of Recipients (only the rejected User)
		List<User> recipients = new ArrayList<User>(1);
		recipients.add(user);
		
		// Send Email to new User
		messageService.sendMessage(
				"OPENUSS - "+institute.getShortcut(),
				"aspirant.rejectaspirant.user.subject",
				"instituteapplicationreject", 
				parameters, 
				recipients);
	}

	/*
	 * <---------- Getter and Setter ---------->
	 */
	
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
	
	
}
