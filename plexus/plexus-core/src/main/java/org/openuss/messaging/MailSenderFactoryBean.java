package org.openuss.messaging;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
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
	
	private SystemService systemService;

	@Override
	protected Object createInstance() throws Exception {
		systemService = (SystemService) getBeanFactory().getBean("systemService", SystemService.class);
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(systemService.getProperty(SystemProperties.MAIL_HOST_NAME).getValue());
		
		sender.setUsername(systemService.getProperty(SystemProperties.MAIL_HOST_USR).getValue());
		sender.setPassword(systemService.getProperty(SystemProperties.MAIL_HOST_PWD).getValue());
 
		Properties properties = new Properties();
		copySystemProperty(properties, SystemProperties.MAIL_SMTP_AUTH);
		sender.setJavaMailProperties(properties);
		
		return sender;
	}

	private void copySystemProperty(Properties properties, String key) {
		String value = systemService.getProperty(key).getValue();
		if (StringUtils.isNotBlank(value)) {
			properties.setProperty(key, systemService.getProperty(key).getValue());
		}
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

