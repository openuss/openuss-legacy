package org.openuss.migration.notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 
 * @author Ingo Dueppe
 */
public class EmailSender extends SimpleJdbcDaoSupport implements InitializingBean {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(EmailSender.class);

	private static final String SQL_SELECT_EMAILS = "select ID, EMAIL, TEXT from USER_NOTIFICATION where SENDAT is null";
	private static final String SQL_UPDATE_SEND = "update USER_NOTIFICATION set SENDAT = ? WHERE id = ?";
	
	private String mailHost;
	private String mailUser;
	private String mailPassword;

	public void sendNotifications() {
		List<Notification> msgs = getSimpleJdbcTemplate().query(SQL_SELECT_EMAILS,
				new ParameterizedRowMapper<Notification>() {
					public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
						Notification notification = new Notification();
						notification.id = rs.getLong("id");
						notification.email = rs.getString("email");
						notification.text = rs.getString("text");
						return notification;
					}
				});

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		Session session = mailSender.getSession();
		Transport transport;
		try {
			transport = session.getTransport("smtp");
			transport.connect(mailHost, mailUser, mailPassword);
			try {
				int count = 0;
				for (Notification notification : msgs) {
					count = progress(count);
					MimeMessage message = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message);
					helper.setFrom("no-reply@openuss.uni-muenster.de");
					helper.setTo("plexus@openuss-plexus.com");
					helper.setSubject("Ihr Benutzerkonto bei OpenUSS 3.0 (With reference to your account at OpenUSS 3.0)");
					helper.setText(notification.text, true);
					Date sendat = new Date();
					helper.setSentDate(sendat);
					
					try {
						transport.sendMessage(message, message.getAllRecipients());
						getSimpleJdbcTemplate().update(SQL_UPDATE_SEND, sendat, notification.id);
					} catch (MessagingException me) {
						logger.error(me);
					}
				}
			} finally {
				transport.close();
			}
		} catch (NoSuchProviderException e) {
			logger.error(e);
		} catch (MessagingException e) {
			logger.error(e);
		}

	}

	private int progress(int count) {
		if (count++ % 10 == 0) {
			logger.debug("processed "+count);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
		return count;
	}

	public static class Notification {
		public Long id;
		public String email;
		public String text;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

}
