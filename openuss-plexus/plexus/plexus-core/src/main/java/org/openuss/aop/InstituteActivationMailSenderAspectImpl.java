package org.openuss.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.InstituteInfo;
import org.openuss.messaging.MessageService;
import org.openuss.registration.RegistrationService;
import org.openuss.security.User;
import org.openuss.security.UserDao;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

/**
 * Aspect for Sending Mails while 
 * creation of institutes.
 * 
 * @author Florian Dondorf
 */

public class InstituteActivationMailSenderAspectImpl extends BaseBean {

	private static final Logger logger = Logger.getLogger(InstituteActivationMailSenderAspectImpl.class);
	
	private UserDao userDao;
	private InstituteDao instituteDao;
	private RegistrationService registrationService;
	private MessageService messageService;
	private SystemService systemService;
	
	
	/**
	 * Sends an activation mail to the creator of the institute.
	 * @param instituteInfo - institute which should be activated
	 * @param userId - user's id.
	 */
	public void sendInstituteActivationMail (InstituteInfo instituteInfo, Long userId) throws Exception {
		logger.debug("sendInstituteActivationMail - User " + userId + " should activate institute");
		
		// Loading User
		User user = this.getUserDao().load(userId);
		Validate.notNull(user, "InstituteActivationMailSenderAspectImpl - no user found with the given userId "+userId);
		
		// Loading institute
		Institute institute = this.getInstituteDao().findByShortcut(instituteInfo.getShortcut());
		Validate.notNull(institute, "InstituteActivationMailSenderAspectImpl - no institute found with the given shortcut "+instituteInfo.getShortcut());
		
		// Create activationCode
		String activationCode = getRegistrationService().generateInstituteActivationCode(institute);
				
		
		String link = "/actions/public/lecture/instituteactivation.faces?code="+activationCode;

		link = applicationAddress() + link;
		
		
		// Create link
		/*String link =
			getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+
				"/actions/public/lecture/instituteactivation.faces?code="+activationCode;*/
		
		// Create parameter map
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("institutename", institute.getName()+"("+institute.getShortcut()+")");
		parameters.put("institutelink", link);
		
		// Create list of recipients (only user who created the institute)
		List<User> recipients = new ArrayList<User>(1);
		recipients.add(user);
		
		// Send mail
		getMessageService().sendMessage(
				"OPENUSS - "+institute.getShortcut(), 
				"institute.activation.subject", 
				"instituteactivation", 
				parameters, 
				recipients);
	}
	
	private String applicationAddress() {
		final HttpServletRequest request = getRequest();
		return systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+request.getContextPath();
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
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

	public RegistrationService getRegistrationService() {
		return registrationService;
	}

	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	
}
