package org.openuss.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.Department;
import org.openuss.lecture.Institute;
import org.openuss.lecture.Organisation;
import org.openuss.lecture.OrganisationDao;
import org.openuss.lecture.University;
import org.openuss.messaging.MessageService;
import org.openuss.security.Authority;
import org.openuss.security.Group;
import org.openuss.security.GroupType;
import org.openuss.security.User;
import org.openuss.security.UserDao;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

/**
 * Aspect for sending Emails whenever a User has been added to or removed from
 * an Organisation.
 * 
 * @author Ron Haus
 * @author Florian Dondorf
 */

public class UserAdministrationMailSenderAspectImpl {

	private static final Logger logger = Logger.getLogger(UserAdministrationMailSenderAspectImpl.class);

	private UserDao userDao;
	private OrganisationDao organisationDao;
	private MessageService messageService;
	private SystemService systemService;

	/**
	 * Sends Emails to the new Member and all old Members of the Organisation
	 * whenever a new Member has been added to the Organisation.
	 * 
	 * @param organisationId - ID of the Organisation.
	 * @param userID - ID of the new Member.
	 */
	public void sendAddMemberMail(Long organisationId, Long userId) {
		logger.debug("sendAddMemberMail - User " + userId + " has been added to Organisation " + organisationId);

		// Identifying type of Organisation
		Organisation organisation = organisationDao.load(organisationId);
		if (organisation != null) {
			String link = generateAddMemberMailLink(organisation);
			final String subjectMembers = "user.membership.addmember.members.subject";
			final String templateMembers = "addmembermembers";
			final String subjectUser = "user.membership.addmember.user.subject";
			final String subjectTemplate = "addmemberuser";

			sendMessage(new SendMessageParameter(organisation, loadUser(userId), link, subjectMembers, templateMembers,
					subjectUser, subjectTemplate));
		}
	}

	/**
	 * Sends Emails to the new Member and all old Members of the Organisation
	 * whenever a new Member has been removed from the Organisation.
	 * 
	 * @param organisationId -
	 *            ID of the Organisation
	 * @param userID -
	 *            ID of the new Member
	 */
	public void sendRemoveMemberMail(Long organisationId, Long userId) {
		logger.debug("sendRemoveMemberMail - User " + userId + " has been removed from Organisation " + organisationId);

		// Loading User
		User user = loadUser(userId);
		Organisation organisation = organisationDao.load(organisationId);
		if (organisation != null) {
			logger.debug("sendRemoveMemberMail for organisation " + organisation.getName());
			String link = generateRemoveMemberMailLink(organisation);
			final String subjectMembers = "user.membership.removemember.members.subject";
			final String templateMembers = "removemembermembers";
			final String subjectUser = "user.membership.removemember.user.subject";
			final String templateUser = "removememberuser";

			sendMessage(new SendMessageParameter(organisation, user, link, subjectMembers, templateMembers,
					subjectUser, templateUser));
		}
	}

	/**
	 * Sends Emails to the applying User and all old Members of the Organisation
	 * whenever a new Aspirant has applied for the Organisation.
	 * 
	 * @param organisationId -
	 *            ID of the Organisation for which the User applies.
	 * @param userId -
	 *            ID of the Aspirant.
	 */
	public void sendAddAspirantMail(Long organisationId, Long userId) {
		logger.debug("sendAddAspirantMail - User " + userId + " has applied at the organisation " + organisationId);

		Organisation organisation = organisationDao.load(organisationId);
		if (organisation != null) {
			logger.debug("sendAddMemberMail for organisation " + organisation.getName());
			String link = generateAddAspirantMailLink(organisation);
			final String subjectMembers = "user.membership.addaspirant.members.subject";
			final String templateMembers = "addaspirantmembers";
			final String subjectUser = "user.membership.addaspirant.user.subject";
			final String templateUser = "addaspirantuser";

			sendMessage(new SendMessageParameter(organisation, loadUser(userId), link, subjectMembers, templateMembers, subjectUser, templateUser));
		}
	}

	/**
	 * Sends an mails to the applying aspirant with the information that he was
	 * accepted to join the institute membership.
	 * 
	 * @param organisationId -
	 *            organisation's id for which the user is accepted.
	 * @param userId -
	 *            user's id.
	 */
	public void sendAcceptAspirantMail(Long organisationId, Long userId) {
		logger.debug("sendAcceptAspirantMail - User " + userId + " was accepted at the organisation " + organisationId);
		Organisation organisation = organisationDao.load(organisationId);
		
		if (organisation != null) {
			logger.debug("sendAddMemberMail to organisation " + organisation.getName());
			final String subjectMember = "user.membership.acceptaspirant.members.subject";
			final String templateMember = "acceptaspirantmembers";
			final String subjectUser = "user.membership.acceptaspirant.user.subject";
			final String templateUser = "acceptaspirantuser";
			final String link = generateAcceptAspirantMailLink(organisation);
			sendMessage(new SendMessageParameter(organisation, loadUser(userId), link, subjectMember, templateMember,	subjectUser, templateUser));
		}
	}

	/**
	 * Sends an mails to the applying aspirant that his application was
	 * rejected.
	 * 
	 * @param organisationId -
	 *            organisation's id for which the user has applied.
	 * @param userId -
	 *            user's id.
	 */
	public void sendRejectAspirantMail(Long organisationId, Long userId) {
		logger.debug("sendRejectAspirantMail - User " + userId + " was rejected to join the organisation "
				+ organisationId);

		Organisation organisation = organisationDao.load(organisationId);
		if (organisation != null) {
			logger.debug("sendAddMemberMail to organisation members of " + organisation.getName());
			String link = generateRejectAspirantMailLink(organisation);
			final String subjectMembers = "user.membership.rejectaspirant.members.subject";
			final String templateMembers = "rejectaspirantmembers";
			final String subjectUser = "user.membership.rejectaspirant.user.subject";
			final String templateUser = "rejectaspirantuser";
			sendMessage(new SendMessageParameter(organisation, loadUser(userId), link, subjectMembers, templateMembers,	subjectUser, templateUser));
		}
	}

	private String generateAcceptAspirantMailLink(Organisation organisation) {
		String link = serverUrl() + "/views/public/";
		if (organisation instanceof University) {
			link += "university/university.faces?university=";
		} else if (organisation instanceof Department) {
			link += "department/department.faces?department=";
		} else if (organisation instanceof Institute) {
			link += "institute/institute.faces?institute=";
		}
		return link + organisation.getId();
	}

	private String generateAddMemberMailLink(Organisation organisation) {
		String link = serverUrl() + "/views/public/";
		if (organisation instanceof University) {
			link += "university/university.faces?university=";
		} else if (organisation instanceof Department) {
			link += "department/department.faces?department=";
		} else if (organisation instanceof Institute) {
			link += "institute/institute.faces?institute=";
		}
		return link + organisation.getId();
	}

	/**
	 * Load and validate user by id
	 * 
	 * @param userId
	 * @return user instance
	 * 
	 */
	private User loadUser(Long userId) {
		User user = userDao.load(userId);
		Validate.notNull(user, "No user found with the given userId " + userId);
		return user;
	}

	private String serverUrl() {
		return systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue();
	}

	/*
	 * <----------- Private methods ----------->
	 */
	private List<User> prepareRecipientsUser(User user) {
		// Determine Recipient (the new User)
		List<User> recipients = new ArrayList<User>(1);
		recipients.add(user);
		return recipients;
	}

	private List<User> prepareRecipientsMembers(Organisation organisation, User user) {
		// Determine Administrator Recipients (Members of the University)
		// TODO This should be done within the Membership.getAdministrators()
		List<User> recipients1 = new ArrayList<User>();
		for (Group group : organisation.getMembership().getGroups()){
			if (group.getGroupType() == GroupType.ADMINISTRATOR){
				for (Authority auth : group.getMembers()){
					if (auth instanceof User){
						if (!recipients1.contains((User)auth)){
							recipients1.add((User)auth);
						}
					}
					
				}
			}
		}
		recipients1.remove(user);
		return recipients1;
	}

	private Map<String, String> prerpareParameters(Organisation organisation, User user, String link) {
		// Prepare Parameters for EMail
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", user.getUsername());
		parameters.put("userfirstname", user.getFirstName());
		parameters.put("userlastname", user.getLastName());
		parameters.put("organisationname", organisation.getName());
		parameters.put("organisationlink", link);
		return parameters;
	}

	private String generateAddAspirantMailLink(Organisation organisation) {
		String link = serverUrl() + "/views/secured/lecture/auth/";
		if (organisation instanceof University) {
			link += "universitymembers.faces?university=";
		} else if (organisation instanceof Department) {
			link += "departmentMembers.faces?department=";
		} else if (organisation instanceof Institute) {
			link += "institutemembers.faces?institute=";
		}
		return link + organisation.getId();
	}

	private String generateRemoveMemberMailLink(Organisation organisation) {
		String link = serverUrl() + "/views/public/";
		if (organisation instanceof University) {
			link += "university/university.faces?university=";
		} else if (organisation instanceof Department) {
			link += "department/department.faces?department=";
		} else if (organisation instanceof Institute) {
			link += "institute/institute.faces?institute=";
		}
		return link + organisation.getId();
	}

	private String generateRejectAspirantMailLink(Organisation organisation) {
		String link = serverUrl() + "/views/public/";
		if (organisation instanceof University) {
			link += "university/university.faces?university=";
		} else if (organisation instanceof Department) {
			link += "department/department.faces?department=";
		} else if (organisation instanceof Institute) {
			link += "institute/institute.faces?institute=";
		}
		return link + organisation.getId();
	}

	public static class SendMessageParameter {
		public Organisation organisation;
		public User user;
		public String link;
		public String subjectMembers;
		public String templateMembers;
		public String subjectUser;
		public String templateUser;

		public SendMessageParameter(Organisation organisation, User user, String link, String subjectMembers,
				String templateMembers, String subjectUser, String templateUser) {
			this.organisation = organisation;
			this.user = user;
			this.link = link;
			this.subjectMembers = subjectMembers;
			this.templateMembers = templateMembers;
			this.subjectUser = subjectUser;
			this.templateUser = templateUser;
		}
	}

	private void sendMessage(SendMessageParameter params) {
		Map<String, String> parameters = prerpareParameters(params.organisation, params.user,	params.link);
		List<User> recipientsMembers = prepareRecipientsMembers(params.organisation, params.user);
		if (!recipientsMembers.isEmpty()) {
			messageService.sendMessage("user.membership.sender", params.subjectMembers,
					params.templateMembers, parameters, recipientsMembers);
		}
		List<User> recipientsUser = prepareRecipientsUser(params.user);
		messageService.sendMessage("user.membership.sender", params.subjectUser, params.templateUser, parameters, recipientsUser);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setOrganisationDao(OrganisationDao organisationDao) {
		this.organisationDao = organisationDao;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
}
