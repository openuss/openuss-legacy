package org.openuss.messaging;

import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * FactoryBean to create a new instance of MailSender and configure it from the database 
 * @author Ingo Dueppe
 */
public class MailSenderFactoryBean extends AbstractFactoryBean implements FactoryBean {

	@Override
	protected Object createInstance() throws Exception {
		SystemService systemService = (SystemService) getBeanFactory().getBean("systemService", SystemService.class);
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(systemService.getProperty(SystemProperties.MAIL_HOST_NAME).getValue());
		sender.setUsername(systemService.getProperty(SystemProperties.MAIL_HOST_USR).getValue());
		sender.setPassword(systemService.getProperty(SystemProperties.MAIL_HOST_PWD).getValue());
		
		return sender;
	}

	@Override
	public Class<?> getObjectType() {
		return JavaMailSender.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
