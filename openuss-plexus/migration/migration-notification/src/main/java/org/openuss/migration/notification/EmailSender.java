package org.openuss.migration.notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 
 * @author Ingo Dueppe
 */
public class EmailSender extends SimpleJdbcDaoSupport implements InitializingBean {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(EmailSender.class);

	private static final String SQL_SELECT_EMAILS = "select ID, EMAIL, TEXT from USER_NOTIFICATION where SENDAT is null";

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
			transport.connect("localhost", "plexus@openuss-plexus.com", "plexus");
			try {
				// TODO send mail
			} finally {
				transport.close();
			}
		} catch (NoSuchProviderException e) {
			logger.error(e);
		} catch (MessagingException e) {
			logger.error(e);
		}

	}

	public static class Notification {
		public Long id;
		public String email;
		public String text;
	}

}
