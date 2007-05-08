package org.openuss.web.mail;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.User;

@Bean(name="mailController", scope=Scope.APPLICATION)
public class MailController extends BaseBean{
	
//	@Property(value="#{mailService}")
//	public MailService mailService;

	private static final Logger logger = Logger.getLogger(MailController.class);
	
	public void sendMails(String subject, User user, String templateName, Map model) throws Exception {
		logger.info("\n\n\n------------------> Mail Controller <----------------\n\n\n");
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle(getBundleName(), new Locale (user.getLocale()));		
		String localSubject="";
		try {
			localSubject = resourceBundle.getString(subject);			
		} catch (java.util.MissingResourceException e) {
			logger.debug("Error in retrieving subject resource", e);
			
		}
		
//		mailService.sendMails(localSubject, templateName, model, user.getEmail() );
	}

//	public MailService getMessageService() {
//		return mailService;
//	}
//
//	public void setMailService(MailService mailService) {
//		this.mailService = mailService;
//	}
}